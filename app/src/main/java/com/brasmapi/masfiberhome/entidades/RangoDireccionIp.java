package com.brasmapi.masfiberhome.entidades;

public class RangoDireccionIp {
    private Integer id_rangodireccionesip, id_vlan,id_cliente,ip_rangodireccionesip;//idcaja1 se cambia por idcaja2 y idcaja2 se cambia por idcliete(servicio)
    private String estado;

    public RangoDireccionIp() {
    }

    public RangoDireccionIp(Integer id_rangodireccionesip, Integer id_vlan, Integer id_cliente, Integer ip_rangodireccionesip, String estado) {
        this.id_rangodireccionesip = id_rangodireccionesip;
        this.id_vlan = id_vlan;
        this.id_cliente = id_cliente;
        this.ip_rangodireccionesip = ip_rangodireccionesip;
        this.estado = estado;
    }

    public Integer getid_rangodireccionesip() {
        return id_rangodireccionesip;
    }

    public void setid_rangodireccionesip(Integer id_rangodireccionesip) {
        this.id_rangodireccionesip = id_rangodireccionesip;
    }

    public Integer getid_vlan() {
        return id_vlan;
    }

    public void setid_vlan(Integer id_vlan) {
        this.id_vlan = id_vlan;
    }

    public Integer getid_cliente() {
        return id_cliente;
    }

    public void setid_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Integer getip_rangodireccionesip() {
        return ip_rangodireccionesip;
    }

    public void setip_rangodireccionesip(Integer ip_rangodireccionesip) {
        this.ip_rangodireccionesip = ip_rangodireccionesip;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
