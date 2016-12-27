package model;

public class Arduino {
    private int idArduino;
    private String ip;
    private String mac;
    private String gateway;
    private String mask;
    private String porta;

    private String PinoDHT22;
    private String PinoRele1;
    private String PinoRele2;
    private String PinoRele3;
    private String PinoRele4;
    private String PinoLDR;
    private String PinoPresenca;

    private static Arduino arduino;

    private Arduino(){
    }

    public static Arduino getInstancia(){
        //Para garantir uma unica configuração do Arduino, foi implementado Singleton nesse modelo.
        if(arduino == null){
            arduino = new Arduino();
        }

        return arduino;
    }

    public int getIdArduino() {
        return idArduino;
    }

    public void setIdArduino(int idArduino) {
        this.idArduino = idArduino;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getPinoDHT22() {
        return PinoDHT22;
    }

    public void setPinoDHT22(String pinoDHT22) {
        PinoDHT22 = pinoDHT22;
    }

    public String getPinoRele1() {
        return PinoRele1;
    }

    public void setPinoRele1(String pinoRele1) {
        PinoRele1 = pinoRele1;
    }

    public String getPinoRele2() {
        return PinoRele2;
    }

    public void setPinoRele2(String pinoRele2) {
        PinoRele2 = pinoRele2;
    }

    public String getPinoRele3() {
        return PinoRele3;
    }

    public void setPinoRele3(String pinoRele3) {
        PinoRele3 = pinoRele3;
    }

    public String getPinoRele4() {
        return PinoRele4;
    }

    public void setPinoRele4(String pinoRele4) {
        PinoRele4 = pinoRele4;
    }

    public String getPinoLDR() {
        return PinoLDR;
    }

    public void setPinoLDR(String pinoLDR) {
        PinoLDR = pinoLDR;
    }

    public String getPinoPresenca() {
        return PinoPresenca;
    }

    public void setPinoPresenca(String pinoPresenca) {
        PinoPresenca = pinoPresenca;
    }
}
