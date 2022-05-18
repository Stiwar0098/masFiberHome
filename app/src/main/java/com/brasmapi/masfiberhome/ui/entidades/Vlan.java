package com.brasmapi.masfiberhome.ui.entidades;

public class Vlan {
    private int id;
    private String nombreVlan;
    private int numeroOlt;
    private int tarjetaOlt;
    private int puertoOlt;
    private String ipInicio;
    private String ipFin;
    private String mascara;
    private String gateway;
    private String estado;

    public Vlan() {
    }

    public Vlan(int id, String nombreVlan, int numeroOlt, int tarjetaOlt, int puertoOlt, String ipInicio, String ipFin, String mascara, String gateway, String estado) {
        this.id = id;
        this.nombreVlan = nombreVlan;
        this.numeroOlt = numeroOlt;
        this.tarjetaOlt = tarjetaOlt;
        this.puertoOlt = puertoOlt;
        this.ipInicio = ipInicio;
        this.ipFin = ipFin;
        this.mascara = mascara;
        this.gateway = gateway;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreVlan() {
        return nombreVlan;
    }

    public void setNombreVlan(String nombreVlan) {
        this.nombreVlan = nombreVlan;
    }

    public int getNumeroOlt() {
        return numeroOlt;
    }

    public void setNumeroOlt(int numeroOlt) {
        this.numeroOlt = numeroOlt;
    }

    public int getTarjetaOlt() {
        return tarjetaOlt;
    }

    public void setTarjetaOlt(int tarjetaOlt) {
        this.tarjetaOlt = tarjetaOlt;
    }

    public int getPuertoOlt() {
        return puertoOlt;
    }

    public void setPuertoOlt(int puertoOlt) {
        this.puertoOlt = puertoOlt;
    }

    public String getIpInicio() {
        return ipInicio;
    }

    public void setIpInicio(String ipInicio) {
        this.ipInicio = ipInicio;
    }

    public String getIpFin() {
        return ipFin;
    }

    public void setIpFin(String ipFin) {
        this.ipFin = ipFin;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
