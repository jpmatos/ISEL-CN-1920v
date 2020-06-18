package dao;

public class User {
    public String username;
    public String password;
    public boolean premium;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", premium=" + premium +
                '}';
    }

    public User() {
    }
}
