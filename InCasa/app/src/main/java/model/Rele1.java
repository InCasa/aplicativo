package model;

public class Rele1 {
    public int idRele;
    public String nome;
    public String descricao;
    public int porta;

    public static Rele1 rele1;

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

    private Rele1() {
    }

    public static Rele1 getInstancia(){
        if(rele1 == null){
            rele1 = new Rele1();
        }

        return rele1;
    }
}
