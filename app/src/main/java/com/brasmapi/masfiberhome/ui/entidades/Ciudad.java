package com.brasmapi.masfiberhome.ui.entidades;

public class Ciudad {
    private int id;
    private String nombre;
    private int idProvincia;
    private String nombreProvincia;
    private String estado;

    public Ciudad() {
    }

    public Ciudad(int id, String nombre, int idProvincia, String nombreProvincia, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.idProvincia = idProvincia;
        this.nombreProvincia = nombreProvincia;
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

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
