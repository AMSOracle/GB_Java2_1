package Homework7;

import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private class User {
        private String login;
        private String pwd;
        private String nick;

        public User(String login, String pwd, String nick) {
            this.login = login;
            this.pwd = pwd;
            this.nick = nick;
        }
    }

    private List<User> userList;

    public AuthService() {
        userList = new ArrayList<>();
        userList.add(new User("login1", "pwd1", "nick1"));
        userList.add(new User("login2", "pwd2", "nick2"));
    }

    public String getNickbyLoginandPwd(String login, String pwd) {
        for (User u : userList) {
            if (u.login.equals(login) && u.pwd.equals(pwd)) {
                return u.nick;
            }
        }
        return null;
    }
}
