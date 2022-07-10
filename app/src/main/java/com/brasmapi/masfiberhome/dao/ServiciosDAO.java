package com.brasmapi.masfiberhome.dao;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.entidades.Servicios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServiciosDAO {
    RequestQueue queue;
    List<Servicios> as;
    Context context;
    private interfazServicio interfaz;

    public ServiciosDAO(ServiciosDAO.interfazServicio inte) {
        interfaz=inte;
    }
    public void filtarServicio(String buscar, Context con, boolean isElim) {
        if (!isElim) {
            Procesos.cargandoIniciar(con);
        }
        String consulta = Procesos.url + "/Servicio/filtrarServicio.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = new JSONObject(response.get(i).toString());
                        as.add(new Servicios(object.getInt("id_cliente"),
                                object.getString("usuario_cliente"),
                                object.getString("direccion_cliente"),
                                object.getString("referencia_cliente"),
                                object.getString("fecha_instalacion_cliente"),
                                object.getString("longitud_cliente"),
                                object.getString("latitud_cliente"),
                                object.getInt("id_planes"),
                                object.getString("nombre_planes"),
                                object.getInt("id_ont"),
                                object.getString("serie_ont"),
                                object.getInt("id_cajanivel2"),
                                object.getString("nombre_cajanivel2"),
                                object.getInt("id_clientepersona"),
                                object.getString("nombre_clientepersona"),
                                object.getInt("hilocajanivel2_cliente"),
                                object.getString("direccionip_cliente"),
                                object.getInt("ip_cliente"),
                                object.getString("comandoplanes_cliente"),
                                object.getString("interfazponcard_cliente"),
                                object.getString("equipobridge_cliente"),
                                object.getString("quit"),
                                object.getString("eliminarservicio_cliente"),
                                object.getString("agregarserviciopuerto_cliente"),
                                object.getString("agregarplancliente_cliente"),
                                object.getString("agregardescripcionpuerto_cliente"),
                                object.getString("eliminaront_cliente"),
                                object.getInt("id_usuario"),
                                object.getString("estado_cliente")
                                ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(interfaz!=null){
                    interfaz.setListaServicio(as);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                as=null;
                if(interfaz!=null){
                    interfaz.setListaServicio(as);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void filtarServicioPendienteAdmin(Context con) {
        String consulta = Procesos.url + "/Servicio/filtrarServicioPendienteAdmin.php";
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = new JSONObject(response.get(i).toString());
                        as.add(new Servicios(object.getInt("id_cliente"),
                                object.getString("usuario_cliente"),
                                object.getString("direccion_cliente"),
                                object.getString("referencia_cliente"),
                                object.getString("fecha_instalacion_cliente"),
                                object.getString("longitud_cliente"),
                                object.getString("latitud_cliente"),
                                object.getInt("id_planes"),
                                object.getString("nombre_planes"),
                                object.getInt("id_ont"),
                                object.getString("serie_ont"),
                                object.getInt("id_cajanivel2"),
                                object.getString("nombre_cajanivel2"),
                                object.getInt("id_clientepersona"),
                                object.getString("nombre_clientepersona"),
                                object.getInt("hilocajanivel2_cliente"),
                                object.getString("direccionip_cliente"),
                                object.getInt("ip_cliente"),
                                object.getString("comandoplanes_cliente"),
                                object.getString("interfazponcard_cliente"),
                                object.getString("equipobridge_cliente"),
                                object.getString("quit"),
                                object.getString("eliminarservicio_cliente"),
                                object.getString("agregarserviciopuerto_cliente"),
                                object.getString("agregarplancliente_cliente"),
                                object.getString("agregardescripcionpuerto_cliente"),
                                object.getString("eliminaront_cliente"),
                                object.getInt("id_usuario"),
                                object.getString("estado_cliente")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(interfaz!=null){
                    interfaz.setListaServicio(as);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                as=null;
                if(interfaz!=null){
                    interfaz.setListaServicio(as);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void filtarServicioPendienteTec(String tecnico, Context con) {
        String consulta = Procesos.url + "/Servicio/filtrarServicioPendienteTec.php?filtrar=" + tecnico;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = new JSONObject(response.get(i).toString());
                        as.add(new Servicios(object.getInt("id_cliente"),
                                object.getString("usuario_cliente"),
                                object.getString("direccion_cliente"),
                                object.getString("referencia_cliente"),
                                object.getString("fecha_instalacion_cliente"),
                                object.getString("longitud_cliente"),
                                object.getString("latitud_cliente"),
                                object.getInt("id_planes"),
                                object.getString("nombre_planes"),
                                object.getInt("id_ont"),
                                object.getString("serie_ont"),
                                object.getInt("id_cajanivel2"),
                                object.getString("nombre_cajanivel2"),
                                object.getInt("id_clientepersona"),
                                object.getString("nombre_clientepersona"),
                                object.getInt("hilocajanivel2_cliente"),
                                object.getString("direccionip_cliente"),
                                object.getInt("ip_cliente"),
                                object.getString("comandoplanes_cliente"),
                                object.getString("interfazponcard_cliente"),
                                object.getString("equipobridge_cliente"),
                                object.getString("quit"),
                                object.getString("eliminarservicio_cliente"),
                                object.getString("agregarserviciopuerto_cliente"),
                                object.getString("agregarplancliente_cliente"),
                                object.getString("agregardescripcionpuerto_cliente"),
                                object.getString("eliminaront_cliente"),
                                object.getInt("id_usuario"),
                                object.getString("estado_cliente")
                        ));
                    } catch (JSONException e) {
                        Procesos.cargandoDetener();
                        e.printStackTrace();
                    }
                }
                if(interfaz!=null){
                    interfaz.setListaServicio(as);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                as=null;
                if(interfaz!=null){
                    interfaz.setListaServicio(as);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void buscarServicio(String buscar, Context con) {
        Procesos.cargandoIniciar(con);
        String consulta = Procesos.url + "/Servicio/buscarServicio.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    as.add(new Servicios(object.getInt("id_cliente"),
                            object.getString("usuario_cliente"),
                            object.getString("direccion_cliente"),
                            object.getString("referencia_cliente"),
                            object.getString("fecha_instalacion_cliente"),
                            object.getString("longitud_cliente"),
                            object.getString("latitud_cliente"),
                            object.getInt("id_planes"),
                            object.getString("nombre_planes"),
                            object.getInt("id_ont"),
                            object.getString("serie_ont"),
                            object.getInt("id_cajanivel2"),
                            object.getString("nombre_cajanivel2"),
                            object.getInt("id_clientepersona"),
                            object.getString("nombre_clientepersona"),
                            object.getInt("hilocajanivel2_cliente"),
                            object.getString("direccionip_cliente"),
                            object.getInt("ip_cliente"),
                            object.getString("comandoplanes_cliente"),
                            object.getString("interfazponcard_cliente"),
                            object.getString("equipobridge_cliente"),
                            object.getString("quit"),
                            object.getString("eliminarservicio_cliente"),
                            object.getString("agregarserviciopuerto_cliente"),
                            object.getString("agregarplancliente_cliente"),
                            object.getString("agregardescripcionpuerto_cliente"),
                            object.getString("eliminaront_cliente"),
                            object.getInt("id_usuario"),
                            object.getString("estado_cliente")
                    ));
                    if (interfaz != null) {
                        interfaz.setServicio(as.get(0));
                    }
                } catch (JSONException e) {
                    Procesos.cargandoDetener();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con el servidor buscarServicio", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
                if (interfaz != null) {
                    interfaz.setServicio(null);
                }
            }
        });
        queue.add(requerimiento);
    }
    public void crearServicio(Servicios servicios,String serie_ont, Context con) {
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/Servicio/crearServicio.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_cliente", servicios.getId_servicio());
                parametros.put("usuario_cliente", servicios.getUsuario());
                parametros.put("direccion_cliente", servicios.getDireccion());
                parametros.put("referencia_cliente", servicios.getReferencia());
                parametros.put("fecha_instalacion_cliente", servicios.getFecha());
                parametros.put("longitud_cliente", servicios.getLongitud());
                parametros.put("latitud_cliente", servicios.getLatitud());
                parametros.put("id_planes", servicios.getId_planes());
                parametros.put("id_ont", servicios.getId_ont());
                parametros.put("id_cajanivel2 ", servicios.getId_cajanivel2());
                parametros.put("id_clientepersona ", servicios.getId_cliente());
                parametros.put("hilocajanivel2_cliente", servicios.getHiloCajaNivel2());
                parametros.put("direccionip_cliente", servicios.getDireccionip());
                parametros.put("ip_cliente", servicios.getIp_cliente());
                parametros.put("comandoplanes_cliente", servicios.getComandoPlanes());
                parametros.put("interfazponcard_cliente", servicios.getIterfazPonCard());
                parametros.put("agregaront_cliente", servicios.getAgregarOnt());
                parametros.put("equipobridge_cliente", servicios.getEquipoBridge());
                parametros.put("quit", servicios.getQuit());
                parametros.put("eliminarservicio_cliente", servicios.getEliminarServicio());
                parametros.put("agregarserviciopuerto_cliente", servicios.getAgregarServicioPuerto());
                parametros.put("agregarplancliente_cliente", servicios.getComandoPlanes());
                parametros.put("agregardescripcionpuerto_cliente", servicios.getAgregarDescripcionPuerto());
                parametros.put("eliminaront_cliente", servicios.getEliminarOnt());
                parametros.put("id_usuario", servicios.getId_usuario());
                parametros.put("serie_ont", serie_ont);
                parametros.put("estado_cliente", servicios.getEstado());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/Servicio/crearServicio.php?"
           +"id_cliente="+ servicios.getId_servicio()
           +"&usuario_cliente="+ servicios.getUsuario()
           +"&direccion_cliente="+ servicios.getDireccion()
           +"&referencia_cliente="+ servicios.getReferencia()
           +"&fecha_instalacion_cliente="+ servicios.getFecha()
           +"&longitud_cliente="+ servicios.getLongitud()
           +"&latitud_cliente="+ servicios.getLatitud()
           +"&id_planes="+ servicios.getId_planes()
           +"&id_cajanivel2="+ servicios.getId_cajanivel2()
           +"&id_clientepersona="+ servicios.getId_cliente()
           +"&hilocajanivel2_cliente="+ servicios.getHiloCajaNivel2()
           +"&direccionip_cliente="+ servicios.getDireccionip()
           +"&ip_cliente="+ servicios.getIp_cliente()
           +"&comandoplanes_cliente="+ servicios.getComandoPlanes()
           +"&interfazponcard_cliente="+ servicios.getIterfazPonCard()
           +"&agregaront_cliente="+ servicios.getAgregarOnt()
           +"&equipobridge_cliente="+ servicios.getEquipoBridge()
           +"&quit="+ servicios.getQuit()
           +"&eliminarservicio_cliente="+ servicios.getEliminarServicio()
           +"&agregarserviciopuerto_cliente="+ servicios.getAgregarServicioPuerto()
           +"&agregarplancliente_cliente="+ servicios.getComandoPlanes()
           +"&agregardescripcionpuerto_cliente="+ servicios.getAgregarDescripcionPuerto()
           +"&eliminaront_cliente="+ servicios.getEliminarOnt()
           +"&id_usuario="+ servicios.getId_usuario()
           +"&serie_ont="+ serie_ont
           +"&estado_cliente="+ servicios.getEstado();
            metodo = Request.Method.GET;
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("respuesta").toString().equals("ok")) {
                        //Toast.makeText(context, "Los datos se cargaron correctamente", Toast.LENGTH_SHORT).show();
                        if (interfaz!=null){
                            interfaz.limpiarServicio();
                        }
                    } else {
                        Procesos.cargandoDetener();
                        Toast.makeText(context, response.get("respuesta").toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Procesos.cargandoDetener();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Problema con el servidor - serviciodao-crearservicio", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }

    public void validarUsuario(String usuario,Context con) {
        String consulta = Procesos.url + "/Servicio/validarUsuario.php?"
                +"usuario_cliente=" +  usuario;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    if (response.get(0).toString().contains("error")){
                        if (interfaz != null) {
                            interfaz.setUsuarioRepetido(true);
                        }
                    }else{
                        interfaz.setUsuarioRepetido(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con el servidor validarUsuario", Toast.LENGTH_SHORT).show();
                if (interfaz != null) {
                    interfaz.setUsuarioRepetido(false);// no hay hilos disponibles en esa caja
                }
            }
        });
        queue.add(requerimiento);
    }
    public void editarServicio(Servicios servicios, Context con, boolean esDesactivar) {
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/Servicio/editarServicio.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_cliente", servicios.getId_servicio());
                parametros.put("usuario_cliente", servicios.getUsuario());
                parametros.put("direccion_cliente", servicios.getDireccion());
                parametros.put("referencia_cliente", servicios.getReferencia());
                parametros.put("fecha_instalacion_cliente", servicios.getFecha());
                parametros.put("longitud_cliente", servicios.getLongitud());
                parametros.put("latitud_cliente", servicios.getLatitud());
                parametros.put("id_planes", servicios.getId_planes());
                parametros.put("id_ont", servicios.getId_ont());
                parametros.put("id_cajanivel2 ", servicios.getId_cajanivel2());
                parametros.put("id_clientepersona ", servicios.getId_cliente());
                parametros.put("hilocajanivel2_cliente", servicios.getHiloCajaNivel2());
                parametros.put("direccionip_cliente", servicios.getDireccionip());
                parametros.put("ip_cliente", servicios.getIp_cliente());
                parametros.put("comandoplanes_cliente", servicios.getComandoPlanes());
                parametros.put("interfazponcard_cliente", servicios.getIterfazPonCard());
                parametros.put("agregaront_cliente", servicios.getAgregarOnt());
                parametros.put("equipobridge_cliente", servicios.getEquipoBridge());
                parametros.put("quit", servicios.getQuit());
                parametros.put("eliminarservicio_cliente", servicios.getEliminarServicio());
                parametros.put("agregarserviciopuerto_cliente", servicios.getAgregarServicioPuerto());
                parametros.put("agregarplancliente_cliente", servicios.getComandoPlanes());
                parametros.put("agregardescripcionpuerto_cliente", servicios.getAgregarDescripcionPuerto());
                parametros.put("eliminaront_cliente", servicios.getEliminarOnt());
                parametros.put("id_usuario", servicios.getId_usuario());
                parametros.put("estado_cliente", servicios.getEstado());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/Servicio/editarServicio.php?"
                    +"id_cliente="+ servicios.getId_servicio()
                    +"&usuario_cliente="+ servicios.getUsuario()
                    +"&direccion_cliente="+ servicios.getDireccion()
                    +"&referencia_cliente="+ servicios.getReferencia()
                    +"&fecha_instalacion_cliente="+ servicios.getFecha()
                    +"&longitud_cliente="+ servicios.getLongitud()
                    +"&latitud_cliente="+ servicios.getLatitud()
                    +"&id_planes="+ servicios.getId_planes()
                    +"&id_ont="+ servicios.getId_ont()
                    +"&id_cajanivel2="+ servicios.getId_cajanivel2()
                    +"&id_clientepersona="+ servicios.getId_cliente()
                    +"&hilocajanivel2_cliente="+ servicios.getHiloCajaNivel2()
                    +"&direccionip_cliente="+ servicios.getDireccionip()
                    +"&ip_cliente="+ servicios.getIp_cliente()
                    +"&comandoplanes_cliente="+ servicios.getComandoPlanes()
                    +"&interfazponcard_cliente="+ servicios.getIterfazPonCard()
                    +"&agregaront_cliente="+ servicios.getAgregarOnt()
                    +"&equipobridge_cliente="+ servicios.getEquipoBridge()
                    +"&quit="+ servicios.getQuit()
                    +"&eliminarservicio_cliente="+ servicios.getEliminarServicio()
                    +"&agregarserviciopuerto_cliente="+ servicios.getAgregarServicioPuerto()
                    +"&agregarplancliente_cliente="+ servicios.getComandoPlanes()
                    +"&agregardescripcionpuerto_cliente="+ servicios.getAgregarDescripcionPuerto()
                    +"&eliminaront_cliente="+ servicios.getEliminarOnt()
                    +"&id_usuario="+ servicios.getId_usuario()
                    +"&estado_cliente="+ servicios.getEstado();
            metodo = Request.Method.GET;
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("respuesta").toString().equals("ok")) {
                        Toast.makeText(context, "Los datos se modificaron correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, response.get("respuesta").toString(), Toast.LENGTH_SHORT).show();
                    }
                    if (!esDesactivar) {
                        if (interfaz != null) {
                            interfaz.limpiarServicio();
                        }
                    }
                } catch (JSONException e) {
                    Procesos.cargandoDetener();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage()+" error servicio editar dao", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Problema con el servidor editarservicio", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }

    public void eliminarServicio(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/Servicio/eliminarServicio.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/Servicio/eliminarServicio.php?id=" + id;
            metodo = Request.Method.GET;
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("respuesta").toString().equals("ok")) {
                        Toast.makeText(context, "Los datos se eliminaron correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, response.get("respuesta").toString(), Toast.LENGTH_SHORT).show();
                    }
                    filtarServicio("", con, true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Problema con el servidor eliminarservicio", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }

    
    public interface interfazServicio {
        void setUsuarioRepetido(boolean estaRepetido);
        void setServicio(Servicios servicios);
        void setListaServicio(List<Servicios> lista);
        void limpiarServicio();
    }
}
