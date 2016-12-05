package model;

public class User {
    private  static String login;
    private static String senha;
    private static User user;

    private User() {
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        User.login = login;
    }

    public static String getSenha() {
        return senha;
    }

    public static void setSenha(String senha) {
        User.senha = senha;
    }

    public static User getInstancia(){
        if(user == null){
            user = new User();
        }

        return user;
    }
}
