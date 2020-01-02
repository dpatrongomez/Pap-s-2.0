package es.dpatrongomez.papas.modelo;

import java.io.Serializable;

public class UserData implements Serializable {
    private String user;
    private String password;

    public UserData(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public UserData(){

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
