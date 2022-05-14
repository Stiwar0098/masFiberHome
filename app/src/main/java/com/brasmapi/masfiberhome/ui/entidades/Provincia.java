package com.brasmapi.masfiberhome.ui.entidades;

public class Provincia {
    private int id;
    private String nombre;
    private int pais;
    private String nombre_pais;
    private String estado;

    public Provincia() {
    }

    public Provincia(int id, String nombre, int pais, String nombre_pais, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.nombre_pais = nombre_pais;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPais() {
        return pais;
    }

    public void setPais(int pais) {
        this.pais = pais;
    }

    public String getNombre_pais() {
        return nombre_pais;
    }

    public void setNombre_pais(String nombre_pais) {
        this.nombre_pais = nombre_pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
