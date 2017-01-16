package model;

public class Temperatura {

    private int idTemperatura;
    private String nome;
    private String descricao;
    public static Temperatura temp;

    private Temperatura() {

    }

    public int getIdTemperatura() {
        return idTemperatura;
    }

    public void setIdTemperatura(int idTemperatura) {
        this.idTemperatura = idTemperatura;
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

    public static Temperatura getInstancia(){
        if(temp == null){
            temp = new Temperatura();
        }

        return temp;
    }
}
