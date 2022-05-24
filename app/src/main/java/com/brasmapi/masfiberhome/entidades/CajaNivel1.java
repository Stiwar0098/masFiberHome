package com.brasmapi.masfiberhome.entidades;

public class CajaNivel1 {
    private int id_cajaNivel1,id_vlan,id_ciudad, numeroHilos_cajaNivel1;
    private String nombre_cajaNivel1,direccion_cajaNivel1,referencia_cajaNivel1,latitud_cajaNivel1,longitud_cajaNivel1,estado_cajaNivel1, nombreVlan,nombreCiudad,abreviatura_cajaNivel1;

    public CajaNivel1() {
    }

    public CajaNivel1(int id_cajaNivel1, String nombre_cajaNivel1, String direccion_cajaNivel1,String referencia_cajaNivel1, String latitud_cajaNivel1, String longitud_cajaNivel1, int id_vlan, String nombreVlan, int id_ciudad, String nombreCiudad, String estado_cajaNivel1,String abreviatura,int numeroHilos) {
        this.id_cajaNivel1 = id_cajaNivel1;
        this.id_vlan = id_vlan;
        this.id_ciudad = id_ciudad;
        this.nombre_cajaNivel1 = nombre_cajaNivel1;
        this.referencia_cajaNivel1=referencia_cajaNivel1;
        this.direccion_cajaNivel1 = direccion_cajaNivel1;
        this.latitud_cajaNivel1 = latitud_cajaNivel1;
        this.longitud_cajaNivel1 = longitud_cajaNivel1;
        this.estado_cajaNivel1 = estado_cajaNivel1;
        this.nombreVlan = nombreVlan;
        this.nombreCiudad = nombreCiudad;
        this.abreviatura_cajaNivel1=abreviatura;
        this.numeroHilos_cajaNivel1=numeroHilos;
    }

    public int getNumeroHilos_cajaNivel1() {
        return numeroHilos_cajaNivel1;
    }

    public void setNumeroHilos_cajaNivel1(int numeroHilos_cajaNivel1) {
        this.numeroHilos_cajaNivel1 = numeroHilos_cajaNivel1;
    }

    public String getAbreviatura_cajaNivel1() {
        return abreviatura_cajaNivel1;
    }

    public void setAbreviatura_cajaNivel1(String abreviatura_cajaNivel1) {
        this.abreviatura_cajaNivel1 = abreviatura_cajaNivel1;
    }

    public int getId_cajaNivel1() {
        return id_cajaNivel1;
    }

    public String getReferencia_cajaNivel1() {
        return referencia_cajaNivel1;
    }

    public void setReferencia_cajaNivel1(String referencia_cajaNivel1) {
        this.referencia_cajaNivel1 = referencia_cajaNivel1;
    }

    public void setId_cajaNivel1(int id_cajaNivel1) {
        this.id_cajaNivel1 = id_cajaNivel1;
    }

    public int getId_vlan() {
        return id_vlan;
    }

    public void setId_vlan(int id_vlan) {
        this.id_vlan = id_vlan;
    }

    public int getId_ciudad() {
        return id_ciudad;
    }

    public void setId_ciudad(int id_ciudad) {
        this.id_ciudad = id_ciudad;
    }

    public String getNombre_cajaNivel1() {
        return nombre_cajaNivel1;
    }

    public void setNombre_cajaNivel1(String nombre_cajaNivel1) {
        this.nombre_cajaNivel1 = nombre_cajaNivel1;
    }

    public String getDireccion_cajaNivel1() {
        return direccion_cajaNivel1;
    }

    public void setDireccion_cajaNivel1(String direccion_cajaNivel1) {
        this.direccion_cajaNivel1 = direccion_cajaNivel1;
    }

    public String getLatitud_cajaNivel1() {
        return latitud_cajaNivel1;
    }

    public void setLatitud_cajaNivel1(String latitud_cajaNivel1) {
        this.latitud_cajaNivel1 = latitud_cajaNivel1;
    }

    public String getLongitud_cajaNivel1() {
        return longitud_cajaNivel1;
    }

    public void setLongitud_cajaNivel1(String longitud_cajaNivel1) {
        this.longitud_cajaNivel1 = longitud_cajaNivel1;
    }

    public String getEstado_cajaNivel1() {
        return estado_cajaNivel1;
    }

    public void setEstado_cajaNivel1(String estado_cajaNivel1) {
        this.estado_cajaNivel1 = estado_cajaNivel1;
    }

    public String getNombreVlan() {
        return nombreVlan;
    }

    public void setNombreVlan(String nombreVlan) {
        this.nombreVlan = nombreVlan;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }
}
