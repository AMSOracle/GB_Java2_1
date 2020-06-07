package Homework7;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ChatClient {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean halt;

    public ChatClient() {
        try {
            openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() throws IOException {
        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        halt = false;
        System.out.println("Подключен к серверу");
        new Thread(() -> {
            try {
                while (!halt) {
                    while (!halt && socket.isConnected() && (in.available() == 0)) {
                        Thread.sleep(100);
                    }
                    if (halt || socket.isClosed()) break;
                    String str = in.readUTF();
                    if (str.equalsIgnoreCase("/end")) {
                        halt = true;
                        break;
                    }
                    System.out.println(str);
                }
            } catch (SocketException se) {
                System.out.println("Соединение закрыто");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
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
    }

    public void doWrite() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(reader);
        try {
            while (!halt) {
                while (!halt && socket.isConnected() && !reader.ready()) {
                    Thread.sleep(100);
                }
                if (halt || socket.isClosed()) break;
                String str = sc.nextLine();
                out.writeUTF(str);
                if (str.equals("/end")) {
                    halt = true;
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.doWrite();
        client.closeConnection();
    }
}
