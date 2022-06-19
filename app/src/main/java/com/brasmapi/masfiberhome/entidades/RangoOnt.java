package com.brasmapi.masfiberhome.entidades;

public class RangoOnt {
    private Integer id_rangoont, id_vlan,id_ont,numero_rangoont;//idcaja1 se cambia por idcaja2 y idcaja2 se cambia por idcliete(servicio)
    private String estado;

    public RangoOnt() {
    }

    public RangoOnt(Integer id_rangoont, Integer id_vlan, Integer id_ont, Integer numero_rangoont, String estado) {
        this.id_rangoont = id_rangoont;
        this.id_vlan = id_vlan;
        this.id_ont = id_ont;
        this.numero_rangoont = numero_rangoont;
        this.estado = estado;
    }

    public Integer getId_rangoont() {
        return id_rangoont;
    }

    public void setId_rangoont(Integer id_rangoont) {
        this.id_rangoont = id_rangoont;
    }

    public Integer getid_vlan() {
        return id_vlan;
    }

    public void setid_vlan(Integer id_vlan) {
        this.id_vlan = id_vlan;
    }

    public Integer getid_ont() {
        return id_ont;
    }

    public void setid_ont(Integer id_ont) {
        this.id_ont = id_ont;
    }

    public Integer getNumero_rangoont() {
        return numero_rangoont;
    }

    public void setNumero_rangoont(Integer numero_rangoont) {
        this.numero_rangoont = numero_rangoont;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
