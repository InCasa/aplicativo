package model;

public class Umidade {

    private int idUmidade;
    private String nome;
    private String descricao;
    public static Umidade umi;

    public Umidade() {

    }

    public int getIdUmidade() {
        return idUmidade;
    }

    public void setIdUmidade(int idUmidade) {
        this.idUmidade = idUmidade;
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

    public static Umidade getInstancia(){
        if(umi == null){
            umi = new Umidade();
        }

        return umi;
    }
}
