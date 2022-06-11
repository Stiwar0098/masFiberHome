package com.brasmapi.masfiberhome.entidades;

public class CajaNivel2 {
    private int id_CajaNivel2, id_CajaNivel1,cantidadHilos,hiloCaja1,idvlan;
    private String nombre_CajaNivel2,direccion_CajaNivel2,referencia_CajaNivel2,latitud_CajaNivel2,longitud_CajaNivel2,estado_CajaNivel2, nombreCajaNivel1,abreviatura,abreviaturaCajaNivel1;

    public CajaNivel2() {
    }

    public CajaNivel2(int id_CajaNivel2, String nombre_CajaNivel2, String abreviatura, String direccion_CajaNivel2, String referencia_CajaNivel2, String latitud_CajaNivel2, String longitud_CajaNivel2, int id_CajaNivel1, String nombreCajaNivel1, int hiloCaja1, int cantidadHilos, String estado_CajaNivel2,String abreviaturaCajaNivel1,int idvlan) {
        this.id_CajaNivel2 = id_CajaNivel2;
        this.id_CajaNivel1 = id_CajaNivel1;
        this.nombre_CajaNivel2 = nombre_CajaNivel2;
        this.referencia_CajaNivel2=referencia_CajaNivel2;
        this.direccion_CajaNivel2 = direccion_CajaNivel2;
        this.latitud_CajaNivel2 = latitud_CajaNivel2;
        this.longitud_CajaNivel2 = longitud_CajaNivel2;
        this.estado_CajaNivel2 = estado_CajaNivel2;
        this.nombreCajaNivel1 = nombreCajaNivel1;
        this.abreviatura=abreviatura;
        this.hiloCaja1=hiloCaja1;
        this.cantidadHilos=cantidadHilos;
        this.abreviaturaCajaNivel1=abreviaturaCajaNivel1;
        this.idvlan=idvlan;
    }

    public int getIdvlan() {
        return idvlan;
    }

    public void setIdvlan(int idvlan) {
        this.idvlan = idvlan;
    }

    public String getAbreviaturaCajaNivel1() {
        return abreviaturaCajaNivel1;
    }

    public void setAbreviaturaCajaNivel1(String abreviaturaCajaNivel1) {
        this.abreviaturaCajaNivel1 = abreviaturaCajaNivel1;
    }

    public int getCantidadHilos() {
        return cantidadHilos;
    }

    public void setCantidadHilos(int cantidadHilos) {
        this.cantidadHilos = cantidadHilos;
    }

    public int getHiloCaja1() {
        return hiloCaja1;
    }

    public void setHiloCaja1(int hiloCaja1) {
        this.hiloCaja1 = hiloCaja1;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public int getId_CajaNivel2() {
        return id_CajaNivel2;
    }

    public void setId_CajaNivel2(int id_CajaNivel2) {
        this.id_CajaNivel2 = id_CajaNivel2;
    }

    public int getId_CajaNivel1() {
        return id_CajaNivel1;
    }

    public void setId_CajaNivel1(int id_CajaNivel1) {
        this.id_CajaNivel1 = id_CajaNivel1;
    }

    public String getNombre_CajaNivel2() {
        return nombre_CajaNivel2;
    }

    public void setNombre_CajaNivel2(String nombre_CajaNivel2) {
        this.nombre_CajaNivel2 = nombre_CajaNivel2;
    }

    public String getDireccion_CajaNivel2() {
        return direccion_CajaNivel2;
    }

    public void setDireccion_CajaNivel2(String direccion_CajaNivel2) {
        this.direccion_CajaNivel2 = direccion_CajaNivel2;
    }

    public String getReferencia_CajaNivel2() {
        return referencia_CajaNivel2;
    }

    public void setReferencia_CajaNivel2(String referencia_CajaNivel2) {
        this.referencia_CajaNivel2 = referencia_CajaNivel2;
    }

    public String getLatitud_CajaNivel2() {
        return latitud_CajaNivel2;
    }

    public void setLatitud_CajaNivel2(String latitud_CajaNivel2) {
        this.latitud_CajaNivel2 = latitud_CajaNivel2;
    }

    public String getLongitud_CajaNivel2() {
        return longitud_CajaNivel2;
    }

    public void setLongitud_CajaNivel2(String longitud_CajaNivel2) {
        this.longitud_CajaNivel2 = longitud_CajaNivel2;
    }

    public String getEstado_CajaNivel2() {
        return estado_CajaNivel2;
    }

    public void setEstado_CajaNivel2(String estado_CajaNivel2) {
        this.estado_CajaNivel2 = estado_CajaNivel2;
    }

    public String getNombreCajaNivel1() {
        return nombreCajaNivel1;
    }

    public void setNombreCajaNivel1(String nombreCajaNivel1) {
        this.nombreCajaNivel1 = nombreCajaNivel1;
    }
}
