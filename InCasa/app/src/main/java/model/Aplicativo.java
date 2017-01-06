package model;

public class Aplicativo {
    private int idAplicativo;
    private String nome;
    private String mac;

    private static Aplicativo app;

    public int getIdAplicativo() {
        return idAplicativo;
    }

    public void setIdAplicativo(int idAplicativo) {
        this.idAplicativo = idAplicativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    private Aplicativo() {

    }

    public static Aplicativo getInstancia(){
        //Para garantir uma unica configuração do aplicativo, foi implementado Singleton nesse modelo.
        if(app == null){
            app = new Aplicativo();
        }

        return app;
    }

    public static boolean checkInstancia(){
        if (app == null){
            return true;
        }
        return false;
    }
}
