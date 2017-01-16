package model;

public class Luminosidade {

    private int idLuminosidade;
    private String nome;
    private String descricao;
    public static Luminosidade lumi;

    public Luminosidade() {

    }

    public int getIdLuminosidade() {
        return idLuminosidade;
    }

    public void setIdLuminosidade(int idLuminosidade) {
        this.idLuminosidade = idLuminosidade;
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

    public static Luminosidade getInstancia(){
        if(lumi == null){
            lumi = new Luminosidade();
        }

        return lumi;
    }
}
