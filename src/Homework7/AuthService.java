package Homework7;

public interface AuthService {
    boolean changeNick(String oldNick, String newNick);

    String getNickbyLoginandPwd(String login, String pwd);
}
