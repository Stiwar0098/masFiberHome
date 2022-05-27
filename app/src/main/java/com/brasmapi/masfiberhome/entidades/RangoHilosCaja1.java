package com.brasmapi.masfiberhome.entidades;

public class RangoHilosCaja1
{
    private Integer id_rangocaja1, id_cajanivel1,id_cajanivel2,numero_rangocaja1;
    private String estado;

    public RangoHilosCaja1() {
    }

    public RangoHilosCaja1(Integer id_rangocaja1, Integer id_cajanivel1, Integer id_cajanivel2, Integer numero_rangocaja1, String estado) {
        this.id_rangocaja1 = id_rangocaja1;
        this.id_cajanivel1 = id_cajanivel1;
        this.id_cajanivel2 = id_cajanivel2;
        this.numero_rangocaja1 = numero_rangocaja1;
        this.estado = estado;
    }

    public Integer getId_rangocaja1() {
        return id_rangocaja1;
    }

    public void setId_rangocaja1(Integer id_rangocaja1) {
        this.id_rangocaja1 = id_rangocaja1;
    }

    public Integer getId_cajanivel1() {
        return id_cajanivel1;
    }

    public void setId_cajanivel1(Integer id_cajanivel1) {
        this.id_cajanivel1 = id_cajanivel1;
    }

    public Integer getId_cajanivel2() {
        return id_cajanivel2;
    }

    public void setId_cajanivel2(Integer id_cajanivel2) {
        this.id_cajanivel2 = id_cajanivel2;
    }

    public Integer getNumero_rangocaja1() {
        return numero_rangocaja1;
    }

    public void setNumero_rangocaja1(Integer numero_rangocaja1) {
        this.numero_rangocaja1 = numero_rangocaja1;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
