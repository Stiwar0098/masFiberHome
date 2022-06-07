package com.brasmapi.masfiberhome.entidades;

public class Ont {
    private int id;
    private String serieOnt;
    private int id_modeloOnt;
    private String nombreModelo;
    private String responsable;
    private int numeroOnt;
    private String estado;

    public Ont() {
    }

    public Ont(int id, String serieOnt, int id_modeloOnt, String nombreModelo, String responsable, int numeroOnt, String estado) {
        this.id = id;
        this.serieOnt = serieOnt;
        this.id_modeloOnt = id_modeloOnt;
        this.nombreModelo = nombreModelo;
        this.responsable = responsable;
        this.numeroOnt = numeroOnt;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerieOnt() {
        return serieOnt;
    }

    public void setSerieOnt(String serieOnt) {
        this.serieOnt = serieOnt;
    }

    public int getId_modeloOnt() {
        return id_modeloOnt;
    }

    public void setId_modeloOnt(int id_modeloOnt) {
        this.id_modeloOnt = id_modeloOnt;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public int getNumeroOnt() {
        return numeroOnt;
    }

    public void setNumeroOnt(int numeroOnt) {
        this.numeroOnt = numeroOnt;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
