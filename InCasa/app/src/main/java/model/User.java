package model;

public class User {
    private static String id;
    private static String login;
    private static String senha;
    private static String nome;
    private static User user;

    private User() {
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        User.id = id;
    }

    public static String getNome() {
        return nome;
    }

    public static void setNome(String nome) {
        User.nome = nome;
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
