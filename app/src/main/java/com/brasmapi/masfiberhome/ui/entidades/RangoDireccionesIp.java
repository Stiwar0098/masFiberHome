package com.brasmapi.masfiberhome.ui.entidades;

public class RangoDireccionesIp {
    private int id_rangoDireccionIp,id_vlan,id_direccionIp,ip_rangoDireccionesIp;
    private String estado;

    public RangoDireccionesIp() {
    }

    public RangoDireccionesIp(int id_rangoDireccionIp, int id_vlan, int id_direccionIp, int ip_rangoDireccionesIp, String estado) {
        this.id_rangoDireccionIp = id_rangoDireccionIp;
        this.id_vlan = id_vlan;
        this.id_direccionIp = id_direccionIp;
        this.ip_rangoDireccionesIp = ip_rangoDireccionesIp;
        this.estado = estado;
    }

    public int getId_rangoDireccionIp() {
        return id_rangoDireccionIp;
    }

    public void setId_rangoDireccionIp(int id_rangoDireccionIp) {
        this.id_rangoDireccionIp = id_rangoDireccionIp;
    }

    public int getId_vlan() {
        return id_vlan;
    }

    public void setId_vlan(int id_vlan) {
        this.id_vlan = id_vlan;
    }

    public int getId_direccionIp() {
        return id_direccionIp;
    }

    public void setId_direccionIp(int id_direccionIp) {
        this.id_direccionIp = id_direccionIp;
    }

    public int getIp_rangoDireccionesIp() {
        return ip_rangoDireccionesIp;
    }

    public void setIp_rangoDireccionesIp(int ip_rangoDireccionesIp) {
        this.ip_rangoDireccionesIp = ip_rangoDireccionesIp;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
