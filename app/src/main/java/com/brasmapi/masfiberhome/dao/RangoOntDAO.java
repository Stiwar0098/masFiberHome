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
import com.brasmapi.masfiberhome.entidades.RangoOnt;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RangoOntDAO {
    List<RangoOnt> as;
    RequestQueue queue;
    Context context;
    private interfazRangoOnt interfaz;

    public RangoOntDAO(RangoOntDAO.interfazRangoOnt inte) {
        interfaz=inte;
    }
    public void verificarNumeroOntManual(int id_vlan, int numero_rangoont , Context con) {
        Procesos.cargandoIniciar(con);
        String consulta = Procesos.url + "/RangoOnt/verificarHiloManual.php?id_vlan=" + id_vlan+"&numero_rangoont="+numero_rangoont;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    if (!response.get(0).toString().contains("error")){
                        as.add(new RangoOnt(object.getInt("id_rangoont"),null, null, object.getInt("numero_rangoont"), ""));
                        if (interfaz != null) {
                            interfaz.validarNumeroOntManual(as.get(0));
                        }
                    }else{
                        interfaz.validarNumeroOntManual(null);
                    }
                } catch (JSONException e) {
                    interfaz.validarNumeroOntManual(null);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
                if(interfaz!=null){
                    interfaz.validarNumeroOntManual(null);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void obtenerNumeroOntAutomatico(int id_vlan, Context con) {
        Procesos.cargandoIniciar(con);
        String consulta = Procesos.url + "/RangoOnt/obtenerHiloAutomatico.php?id_vlan=" + id_vlan;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    if (!response.get(0).toString().contains("error")) {
                        as.add(new RangoOnt(object.getInt("id_rangoont"), null, null, object.getInt("numero_rangoont"), ""));
                        if (interfaz != null) {
                            interfaz.numeroOntAutomatico(as.get(0));
                        }
                    }else{
                        interfaz.numeroOntAutomatico(null);
                    }
                } catch (JSONException e) {
                    interfaz.numeroOntAutomatico(null);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con el servidor obtenerNumeroOntAutomatico", Toast.LENGTH_SHORT).show();
                if (interfaz != null) {
                    interfaz.numeroOntAutomatico(null);// no hay hilos disponibles en esa caja
                }
            }
        });
        queue.add(requerimiento);
    }

    public void obtenerNumeroOntAnterior(int id_vlan, int id_ont, Context con) {
        Procesos.cargandoIniciar(con);
        String consulta = Procesos.url + "/RangoOnt/obtenerHiloAnterior.php?id_vlan=" + id_vlan+"&id_ont="+id_ont;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    as.add(new RangoOnt(object.getInt("id_rangoont"),null, null, object.getInt("numero_rangoont"), ""));
                    if (interfaz != null) {
                        interfaz.numeroOntAnterior(as.get(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con el servidor obtenerNumeroOntAnterior", Toast.LENGTH_SHORT).show();
                if (interfaz != null) {
                    interfaz.numeroOntAnterior(null);// no hay hilos disponibles en esa caja
                }
            }
        });
        queue.add(requerimiento);
    }

    public void editarRangoOnt(RangoOnt rangoont, String serieOnt, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/RangoOnt/editar.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_rangoont", rangoont.getId_rangoont());
                parametros.put("estado_rangoont", rangoont.getEstado());
                parametros.put("serie_ont", serieOnt);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/RangoOnt/editar.php?"
                    +"id_rangoont=" +  rangoont.getId_rangoont()
                    +"&estado_rangoont=" +  rangoont.getEstado()
                    +"&serie_ont=" +  serieOnt;
            metodo = Request.Method.GET;
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("respuesta").toString().equals("ok")) {
                       // Toast.makeText(context, "Los datos se modificaron correctamente rangont", Toast.LENGTH_SHORT).show();
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
                
                Toast.makeText(context, "Problema con el servidor rangoontdao-editarrango1", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }
    public void editarRangoOntAnterior(int idRangoOnt, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/RangoOnt/editarHiloAnterior.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_rangoont", idRangoOnt);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/RangoOnt/editarHiloAnterior.php?"
                    +"id_rangoont=" +  idRangoOnt;
            metodo = Request.Method.GET;
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("respuesta").toString().equals("ok")) {
                        //Toast.makeText(context, "Los datos se modificaron correctamente", Toast.LENGTH_SHORT).show();
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
                
                Toast.makeText(context, "Problema con el servidor editarRangoOntAnterior", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }

    public void eliminarNumeroOnt(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/RangoOnt/eliminarRangoOnt.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/RangoOnt/eliminarRangoOnt.php?id=" + id;
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
                Toast.makeText(context, "Problema con el servidor eliminarNumeroOnt", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }

    public interface interfazRangoOnt {
        void numeroOntAutomatico(RangoOnt rangoOnt);
        void validarNumeroOntManual(RangoOnt rangoOnt);
        void numeroOntAnterior(RangoOnt rangoOnt);
    }
}
