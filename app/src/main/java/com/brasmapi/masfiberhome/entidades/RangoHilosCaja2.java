package com.brasmapi.masfiberhome.entidades;

public class RangoHilosCaja2
{
    private Integer id_rangocaja2, id_cajanivel2, id_cliente,numero_rangocaja2;//idcaja1 se cambia por idcaja2 y idcaja2 se cambia por idcliete(servicio)
    private String estado;

    public RangoHilosCaja2() {
    }

    public RangoHilosCaja2(Integer id_rangocaja2, Integer id_cajanivel2, Integer id_cliente, Integer numero_rangocaja2, String estado) {
        this.id_rangocaja2 = id_rangocaja2;
        this.id_cajanivel2 = id_cajanivel2;
        this.id_cliente = id_cliente;
        this.numero_rangocaja2 = numero_rangocaja2;
        this.estado = estado;
    }

    public Integer getId_rangocaja2() {
        return id_rangocaja2;
    }

    public void setId_rangocaja2(Integer id_rangocaja2) {
        this.id_rangocaja2 = id_rangocaja2;
    }

    public Integer getId_cajanivel2() {
        return id_cajanivel2;
    }

    public void setId_cajanivel2(Integer id_cajanivel2) {
        this.id_cajanivel2 = id_cajanivel2;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Integer getNumero_rangocaja2() {
        return numero_rangocaja2;
    }

    public void setNumero_rangocaja2(Integer numero_rangocaja2) {
        this.numero_rangocaja2 = numero_rangocaja2;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
