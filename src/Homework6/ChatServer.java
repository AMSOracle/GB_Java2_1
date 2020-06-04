package Homework6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
    private static Socket socket;
    private static boolean halt;

    public static void doWrite(DataOutputStream outputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(reader);
        try {
            while (!halt) {
                while (!halt && socket.isConnected() && !reader.ready()) {
                    Thread.sleep(100);
                }
                if (halt || socket.isClosed()) break;
                String str = sc.nextLine();
                outputStream.writeUTF(str);
                if (str.equals("/end")) {
                    halt = true;
                    break;
                }

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void doListen(DataInputStream inputStream) {
        try {
            while (!halt) {
                while (!halt && socket.isConnected() && (inputStream.available() == 0)) {
                    Thread.sleep(100);
                }
                if (halt || socket.isClosed()) break;
                String str = inputStream.readUTF();
                System.out.println("Client says: " + str);
                if (str.equals("/end")) {
                    halt = true;
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void start() {
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Сервер запущен, ожидаем подключения...");
            socket = serverSocket.accept();
            System.out.println("Клиент подключился");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            halt = false;
            new Thread(() -> doListen(in)).start();
            doWrite(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        start();
    }

}
