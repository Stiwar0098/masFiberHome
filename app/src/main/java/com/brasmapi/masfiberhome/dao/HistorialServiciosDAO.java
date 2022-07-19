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
import com.brasmapi.masfiberhome.entidades.HistorialServicios;
import com.brasmapi.masfiberhome.entidades.Servicios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistorialServiciosDAO {
    RequestQueue queue;
    List<HistorialServicios> as;
    Context context;
    private interfazServicio interfaz;

    public HistorialServiciosDAO(HistorialServiciosDAO.interfazServicio inte) {
        interfaz=inte;
    }
    public void filtarServicio(String buscar, Context con, boolean isElim) {
        if (!isElim) {
            Procesos.cargandoIniciar(con);
        }
        String consulta = Procesos.url + "/Historial_Servicio/filtrarHistorialServicio.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = new JSONObject(response.get(i).toString());
                        as.add(new HistorialServicios(object.getInt("id"),object.getInt("id_cliente"),
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
                                object.getString("agregaront_cliente"),
                                object.getString("equipobridge_cliente"),
                                object.getString("quit"),
                                object.getString("eliminarservicio_cliente"),
                                object.getString("agregarserviciopuerto_cliente"),
                                object.getString("agregardescripcionpuerto_cliente"),
                                object.getString("eliminaront_cliente"),
                                object.getInt("id_usuario"),
                                object.getString("opcion_cliente"),
                                object.getString("comando_copiar_cliente"),
                                object.getString("date2"),
                                object.getString("estado_cliente")
                                ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(interfaz!=null){
                    interfaz.setListaHistorialServicio(as);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                as=null;
                if(interfaz!=null){
                    interfaz.setListaHistorialServicio(as);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void crearServicio(Servicios HistorialServicios, String serie_ont, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/Historial_Servicio/crearHistorialServicio.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try
            {
                parametros.put("id_cliente", HistorialServicios.getId_servicio());
                parametros.put("usuario_cliente", HistorialServicios.getUsuario());
                parametros.put("direccion_cliente", HistorialServicios.getDireccion());
                parametros.put("referencia_cliente", HistorialServicios.getReferencia());
                parametros.put("fecha_instalacion_cliente", HistorialServicios.getFecha());
                parametros.put("longitud_cliente", HistorialServicios.getLongitud());
                parametros.put("latitud_cliente", HistorialServicios.getLatitud());
                parametros.put("id_planes", HistorialServicios.getId_planes());
                parametros.put("id_ont", HistorialServicios.getId_ont());
                parametros.put("id_cajanivel2 ", HistorialServicios.getId_cajanivel2());
                parametros.put("id_clientepersona ", HistorialServicios.getId_cliente());
                parametros.put("hilocajanivel2_cliente", HistorialServicios.getHiloCajaNivel2());
                parametros.put("direccionip_cliente", HistorialServicios.getDireccionip());
                parametros.put("ip_cliente", HistorialServicios.getIp_cliente());
                parametros.put("comandoplanes_cliente", HistorialServicios.getComandoPlanes());
                parametros.put("interfazponcard_cliente", HistorialServicios.getIterfazPonCard());
                parametros.put("agregaront_cliente", HistorialServicios.getAgregarOnt());
                parametros.put("equipobridge_cliente", HistorialServicios.getEquipoBridge());
                parametros.put("quit", HistorialServicios.getQuit());
                parametros.put("eliminarservicio_cliente", HistorialServicios.getEliminarServicio());
                parametros.put("agregarserviciopuerto_cliente", HistorialServicios.getAgregarServicioPuerto());
                parametros.put("agregarplancliente_cliente", HistorialServicios.getComandoPlanes());
                parametros.put("agregardescripcionpuerto_cliente", HistorialServicios.getAgregarDescripcionPuerto());
                parametros.put("eliminaront_cliente", HistorialServicios.getEliminarOnt());
                parametros.put("id_usuario", HistorialServicios.getId_usuario());
                parametros.put("opcion_cliente", HistorialServicios.getOpcion_cliente());
                parametros.put("comando_copiar_cliente", HistorialServicios.getComando_copiar_cliente());
                parametros.put("date2", HistorialServicios.getDate2());
                parametros.put("serie_ont", serie_ont);
                parametros.put("estado_cliente", HistorialServicios.getEstado());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/Historial_Servicio/crearHistorialServicio.php?"
           +"id_cliente="+ HistorialServicios.getId_servicio()
           +"&usuario_cliente="+ HistorialServicios.getUsuario()
           +"&direccion_cliente="+ HistorialServicios.getDireccion()
           +"&referencia_cliente="+ HistorialServicios.getReferencia()
           +"&fecha_instalacion_cliente="+ HistorialServicios.getFecha()
           +"&longitud_cliente="+ HistorialServicios.getLongitud()
           +"&latitud_cliente="+ HistorialServicios.getLatitud()
           +"&id_planes="+ HistorialServicios.getId_planes()
           +"&id_cajanivel2="+ HistorialServicios.getId_cajanivel2()
           +"&id_clientepersona="+ HistorialServicios.getId_cliente()
           +"&hilocajanivel2_cliente="+ HistorialServicios.getHiloCajaNivel2()
           +"&direccionip_cliente="+ HistorialServicios.getDireccionip()
           +"&ip_cliente="+ HistorialServicios.getIp_cliente()
           +"&comandoplanes_cliente="+ HistorialServicios.getComandoPlanes()
           +"&interfazponcard_cliente="+ HistorialServicios.getIterfazPonCard()
           +"&agregaront_cliente="+ HistorialServicios.getAgregarOnt()
           +"&equipobridge_cliente="+ HistorialServicios.getEquipoBridge()
           +"&quit="+ HistorialServicios.getQuit()
           +"&eliminarservicio_cliente="+ HistorialServicios.getEliminarServicio()
           +"&agregarserviciopuerto_cliente="+ HistorialServicios.getAgregarServicioPuerto()
           +"&agregarplancliente_cliente="+ HistorialServicios.getComandoPlanes()
           +"&agregardescripcionpuerto_cliente="+ HistorialServicios.getAgregarDescripcionPuerto()
           +"&eliminaront_cliente="+ HistorialServicios.getEliminarOnt()
           +"&id_usuario="+ HistorialServicios.getId_usuario()
           +"&opcion_cliente="+ HistorialServicios.getOpcion_cliente()
           +"&comando_copiar_cliente="+ HistorialServicios.getComando_copiar_cliente()
           +"&date2="+ HistorialServicios.getDate2()
           +"&serie_ont="+ serie_ont
           +"&estado_cliente="+ HistorialServicios.getEstado();
            metodo = Request.Method.GET;
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("respuesta").toString().equals("ok")) {
                        //Toast.makeText(context, "Los datos se cargaron correctamente", Toast.LENGTH_SHORT).show();
                        if (interfaz!=null){
                            interfaz.limpiarHistorialServicio();
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

    public void eliminarServicio(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/Historial_Servicio/eliminarHistorialServicio.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/Historial_Servicio/eliminarHistorialServicio.php?id=" + id;
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
        void setListaHistorialServicio(List<HistorialServicios> lista);
        void limpiarHistorialServicio();
    }
}
