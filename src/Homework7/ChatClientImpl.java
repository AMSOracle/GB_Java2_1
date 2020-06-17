package Homework7;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatClientImpl implements ChatClient {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean halt;
    private boolean auth;
    private List<MessageReaderListener> listenerList;
    private Thread readThread;

    {
        listenerList = new ArrayList<>();
    }

    @Override
    public boolean isAuth() {
        return auth;
    }

    private void setAuth(boolean a){
        this.auth = a;
    }

    @Override
    public boolean isConnected() {
        return socket.isConnected();
    }

    @Override
    public void addListener(MessageReaderListener messageReaderListener) {
        listenerList.add(messageReaderListener);
    }

    public void openConnection() throws IOException {
        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        halt = false;
    }
    
    public void startReadMessages(){
        this.readThread = new Thread(() -> {
            try {
                while (!halt) {
                    while (!halt && socket.isConnected() && (in.available() == 0)) {
                        Thread.sleep(100);
                    }
                    if (halt || socket.isClosed()) break;
                    notifyNewMessage(readMessage());
                }
            } catch (SocketException se) {
                System.out.println("Соединение закрыто");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        readThread.start();
    }

    private void notifyNewMessage(String newMessage) {
        for (MessageReaderListener listener: listenerList) {
            listener.newMessage(newMessage);
        }
    }

    public void closeConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            readThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doAuth(String login, String password) throws IOException{
        sendMsg("/auth " + login + " " + password);
        // Максимум 5 секунд ждем
        for (int i = 0; i < 5; i++) {
            try {
                //Даем серверу время авторизовать нас и ответить
                Thread.sleep(1000);
                String s = readMessage();
                setAuth((s != null) && (s.startsWith("/authok")));
                if (isAuth()) break;
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }
    @Override
    public String readMessage() throws IOException  {
        if  (isConnected() && (in.available() > 0)){
            return in.readUTF();
        }
        return null;
    }

    public void sendMsg(String s) throws IOException {
        if (!halt && isConnected()){
            out.writeUTF(s);
            if (s.equals("/end"))  halt = true;
        }
    }

    @Override
    public void Logoff() {
        try {
            sendMsg("/end");
        }catch (IOException e){
            ;
        }finally {
            closeConnection();
        }

    }
}
