package model;

public class Rele4 {
    public int idRele;
    public String nome;
    public String descricao;
    public int porta;

    public static Rele4 rele4;

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

    private Rele4() {
    }

    public static Rele4 getInstancia(){
        if(rele4 == null){
            rele4 = new Rele4();
        }

        return rele4;
    }
}
