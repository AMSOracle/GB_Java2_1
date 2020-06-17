package Homework7;

import com.sun.org.apache.xpath.internal.objects.XString;

import java.io.IOException;

public interface ChatClient {
    void openConnection() throws IOException;
    void closeConnection();
    void doAuth(String login, String password) throws IOException;
    void sendMsg(String s) throws IOException;
    String readMessage() throws IOException;
    boolean isAuth();
    boolean isConnected();
    void startReadMessages();
    void addListener(MessageReaderListener messageReaderListener);
    void Logoff();
}
