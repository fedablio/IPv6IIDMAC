package br.com.fedablio.model;

public class Ipv6 {

    private String bits;
    private String mac;
    private String prefixo;
    private String resultado;

    public Ipv6(){
    }

    public Ipv6(String bits, String mac, String prefixo, String resultado){
        this.bits = bits;
        this.mac = mac;
        this.prefixo = prefixo;
        this.resultado = resultado;
    }

    public String getBits() {
        return bits;
    }

    public void setBits(String bits) {
        this.bits = bits;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

}