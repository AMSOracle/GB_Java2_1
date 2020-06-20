package Homework7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private String myNick;
    private boolean isAuth;
    private DataInputStream in;
    private DataOutputStream out;

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public boolean getAuth() {
        return isAuth;
    }

    public String getNick() {
        return myNick;
    }

    private void setNick(String nick) {
        this.myNick = nick;
    }

    public ClientHandler(MyServer myServer, Socket socket) {
        this.myServer = myServer;
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ClientHandler createAndStart(MyServer myServer, Socket socket) {
        ClientHandler clientHandler = new ClientHandler(myServer, socket);
        new Thread(clientHandler::start).start();
        return clientHandler;
    }

    public void start() {
        try {
            authenticate();
            if (getAuth()) {
                readMsg();
            }
            closeConnection();
        } catch (IOException | InterruptedException e) {
            closeConnection();
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        setAuth(false);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myServer.Logoff(this);
        System.out.println(getNick() + " left the chat");
        myServer.broadcast(myServer.getName(), getNick() + " left the chat");
    }

    private void readMsg() throws IOException, InterruptedException {
        while (true) {
            //Если пока нет доступных к чтению данных
            while (socket.isConnected() && (in.available() == 0)) {
                Thread.sleep(100);
            }
            //соединение закрыто
            if (!socket.isConnected()) {
                break;
            }
            String msg = in.readUTF();
            System.out.println(msg);
            //Клиент уходит
            if (msg.contains("/end")) {
                break;
            }
            if (msg.startsWith("/w")) {
                String[] s = msg.split(" ", 3);
                if ((s.length > 2) && (myServer.isLoggedIn(s[1]))) {
                    myServer.sendPrivateMsg(getNick(), s[1], s[2]);
                }
            } else if (msg.startsWith("/ch")) {
                String[] s = msg.split(" ", 2);
                if (s.length > 1) {
                    if (myServer.getAuthService().changeNick(getNick(), s[1])) {
                        String oldNick = getNick();
                        setNick(s[1]);
                        myServer.broadcast(myServer.getName(), oldNick + " changed nick to " + getNick());
                    } else {
                        sendMsg("Failed to change nick ");
                    }
                }
            } else {
                myServer.broadcast(getNick(), msg);
            }
        }
    }

    public void sendMsg(String msg) throws IOException {
        if (socket.isConnected()) out.writeUTF(msg);
        else
            myServer.Logoff(this);
    }

    private void authenticate() throws IOException, InterruptedException {
        out.writeUTF("To join the chat, please name yourself /auth Login Password \n");
        int wait, times = 0;
        while (true) {
            wait = 0;
            while (socket.isConnected() && (in.available() == 0)) {
                Thread.sleep(500);
                // Ждем минуту
                if (++wait >= 120) {
                    out.writeUTF("Authentication timeout");
                    return;
                }
            }
            String authStr = in.readUTF();
            if (authStr.startsWith("/auth")) {
                System.out.println(authStr);
                String[] s = authStr.split(" ");
                String nick = null;
                if (s.length == 3) {
                    nick = myServer.getAuthService().getNickbyLoginandPwd(s[1], s[2]);
                }
                if (nick == null) {
                    out.writeUTF("Unknown login/password. Try again");
                } else if (myServer.isLoggedIn(nick)) {
                    out.writeUTF("You are already logged in on other device");
                } else {
                    setNick(nick);
                    setAuth(true);
                    out.writeUTF("/authok");
                    out.writeUTF("Welcome to the chat: " + getNick());
                    myServer.broadcast(myServer.getName(), getNick() + " joined us");
                    myServer.LogOn(this);
                    break;
                }
            }
            if (++times > 2) {
                out.writeUTF("Number of attempts exceeded");
                break;
            }
        }
    }
}
