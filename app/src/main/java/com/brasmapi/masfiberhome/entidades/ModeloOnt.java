package com.brasmapi.masfiberhome.entidades;

public class ModeloOnt {
    private int id_modeloOnt;
    private String nombre_modeloOnt,tipo_modeloOnt,estado_modeloOnt;

    public ModeloOnt() {
    }

    public ModeloOnt(int id_modeloOnt, String nombre_modeloOnt, String tipo_modeloOnt, String estado_modeloOnt) {
        this.id_modeloOnt = id_modeloOnt;
        this.nombre_modeloOnt = nombre_modeloOnt;
        this.tipo_modeloOnt = tipo_modeloOnt;
        this.estado_modeloOnt = estado_modeloOnt;
    }

    public int getId_modeloOnt() {
        return id_modeloOnt;
    }

    public void setId_modeloOnt(int id_modeloOnt) {
        this.id_modeloOnt = id_modeloOnt;
    }

    public String getNombre_modeloOnt() {
        return nombre_modeloOnt;
    }

    public void setNombre_modeloOnt(String nombre_modeloOnt) {
        this.nombre_modeloOnt = nombre_modeloOnt;
    }

    public String getTipo_modeloOnt() {
        return tipo_modeloOnt;
    }

    public void setTipo_modeloOnt(String tipo_modeloOnt) {
        this.tipo_modeloOnt = tipo_modeloOnt;
    }

    public String getEstado_modeloOnt() {
        return estado_modeloOnt;
    }

    public void setEstado_modeloOnt(String estado_modeloOnt) {
        this.estado_modeloOnt = estado_modeloOnt;
    }
}
