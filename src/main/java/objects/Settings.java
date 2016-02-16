package objects;

import java.io.Serializable;

public class Settings implements Serializable{

    private String userName;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    private String server;
    private String serverPrefix = "jdbc:mysql://";
    public String getServerWithPrefix() {
        return serverPrefix + server;
    }
    public String getServer() {
        return server;
    }
    public void setServer(String server) {
        this.server = server;
    }

}
