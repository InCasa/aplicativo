package model;

public class Rele2 {
    public int idRele;
    public String nome;
    public String descricao;
    public int porta;

    public static Rele2 rele2;

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

    private Rele2() {
    }

    public static Rele2 getInstancia(){
        if(rele2 == null){
            rele2 = new Rele2();
        }

        return rele2;
    }
}
