package model;

public class Rele3 {
    public int idRele;
    public String nome;
    public String descricao;
    public int porta;

    public static Rele3 rele3;

    public int getIdRele() {
        return idRele;
    }

    public void setIdRele(int idRele) {
        this.idRele = idRele;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    private Rele3() {
    }

    public static Rele3 getInstancia(){
        if(rele3 == null){
            rele3 = new Rele3();
        }

        return rele3;
    }
}
