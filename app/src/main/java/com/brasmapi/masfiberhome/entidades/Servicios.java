package com.brasmapi.masfiberhome.entidades;

public class Servicios {
    private int id_servicio,id_planes,id_ont,id_cajanivel2,id_cliente,hiloCajaNivel2,id_usuario;
    private String usuario,direccion,referencia,fecha,longitud,latitud,estado,direccionip,comandoPlanes,iterfazPonCard,agregarOnt,equipoBridge,quit,eliminarServicio,agregarServicioPuerto,agregarDescripcionPuerto,eliminarOnt;

    public Servicios() {
    }

    public Servicios(int id_servicio, int id_planes, int id_ont, int id_cajanivel2, int id_cliente, int hiloCajaNivel2, int id_usuario, String usuario, String direccion, String referencia, String fecha, String longitud, String latitud, String estado, String direccionip, String comandoPlanes, String iterfazPonCard, String agregarOnt, String equipoBridge, String quit, String eliminarServicio, String agregarServicioPuerto, String agregarDescripcionPuerto, String eliminarOnt) {
        this.id_servicio = id_servicio;
        this.id_planes = id_planes;
        this.id_ont = id_ont;
        this.id_cajanivel2 = id_cajanivel2;
        this.id_cliente = id_cliente;
        this.hiloCajaNivel2 = hiloCajaNivel2;
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.direccion = direccion;
        this.referencia = referencia;
        this.fecha = fecha;
        this.longitud = longitud;
        this.latitud = latitud;
        this.estado = estado;
        this.direccionip = direccionip;
        this.comandoPlanes = comandoPlanes;
        this.iterfazPonCard = iterfazPonCard;
        this.agregarOnt = agregarOnt;
        this.equipoBridge = equipoBridge;
        this.quit = quit;
        this.eliminarServicio = eliminarServicio;
        this.agregarServicioPuerto = agregarServicioPuerto;
        this.agregarDescripcionPuerto = agregarDescripcionPuerto;
        this.eliminarOnt = eliminarOnt;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public int getId_planes() {
        return id_planes;
    }

    public void setId_planes(int id_planes) {
        this.id_planes = id_planes;
    }

    public int getId_ont() {
        return id_ont;
    }

    public void setId_ont(int id_ont) {
        this.id_ont = id_ont;
    }

    public int getId_cajanivel2() {
        return id_cajanivel2;
    }

    public void setId_cajanivel2(int id_cajanivel2) {
        this.id_cajanivel2 = id_cajanivel2;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getHiloCajaNivel2() {
        return hiloCajaNivel2;
    }

    public void setHiloCajaNivel2(int hiloCajaNivel2) {
        this.hiloCajaNivel2 = hiloCajaNivel2;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDireccionip() {
        return direccionip;
    }

    public void setDireccionip(String direccionip) {
        this.direccionip = direccionip;
    }

    public String getComandoPlanes() {
        return comandoPlanes;
    }

    public void setComandoPlanes(String comandoPlanes) {
        this.comandoPlanes = comandoPlanes;
    }

    public String getIterfazPonCard() {
        return iterfazPonCard;
    }

    public void setIterfazPonCard(String iterfazPonCard) {
        this.iterfazPonCard = iterfazPonCard;
    }

    public String getAgregarOnt() {
        return agregarOnt;
    }

    public void setAgregarOnt(String agregarOnt) {
        this.agregarOnt = agregarOnt;
    }

    public String getEquipoBridge() {
        return equipoBridge;
    }

    public void setEquipoBridge(String equipoBridge) {
        this.equipoBridge = equipoBridge;
    }

    public String getQuit() {
        return quit;
    }

    public void setQuit(String quit) {
        this.quit = quit;
    }

    public String getEliminarServicio() {
        return eliminarServicio;
    }

    public void setEliminarServicio(String eliminarServicio) {
        this.eliminarServicio = eliminarServicio;
    }

    public String getAgregarServicioPuerto() {
        return agregarServicioPuerto;
    }

    public void setAgregarServicioPuerto(String agregarServicioPuerto) {
        this.agregarServicioPuerto = agregarServicioPuerto;
    }

    public String getAgregarDescripcionPuerto() {
        return agregarDescripcionPuerto;
    }

    public void setAgregarDescripcionPuerto(String agregarDescripcionPuerto) {
        this.agregarDescripcionPuerto = agregarDescripcionPuerto;
    }

    public String getEliminarOnt() {
        return eliminarOnt;
    }

    public void setEliminarOnt(String eliminarOnt) {
        this.eliminarOnt = eliminarOnt;
    }
}
