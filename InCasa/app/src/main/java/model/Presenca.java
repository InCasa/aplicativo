package model;

public class Presenca {

    private int idPresenca;
    private String nome;
    private String descricao;
    public static Presenca presenca;

    public Presenca() {

    }

    public int getIdPresenca() {
        return idPresenca;
    }

    public void setIdPresenca(int idPresenca) {
        this.idPresenca = idPresenca;
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

    public static Presenca getInstancia(){
        if(presenca == null){
            presenca = new Presenca();
        }

        return presenca;
    }
}
