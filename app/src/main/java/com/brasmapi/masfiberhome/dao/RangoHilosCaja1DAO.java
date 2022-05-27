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
import com.brasmapi.masfiberhome.entidades.RangoHilosCaja1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RangoHilosCaja1DAO {
    List<RangoHilosCaja1> as;
    RequestQueue queue;
    Context context;
    private interfazRangoCaja1DAO interfaz;

    public RangoHilosCaja1DAO(RangoHilosCaja1DAO.interfazRangoCaja1DAO inte) {
        interfaz=inte;
    }

    public void verificarHiloManual(int id_cajanivel1, int numero_rangohiloscaja1 , Context con) {
        Procesos.cargandoIniciar(con);
        String consulta = Procesos.url + "/RangoHilosCaja1/filtrarRangoHilosCaja1.php?id_cajanivel1=" + id_cajanivel1+"&numero_rangohiloscaja1="+numero_rangohiloscaja1;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonObjectRequest requerimiento = new JsonObjectRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("respuesta").toString().equals("ok")) {
                        if(interfaz!=null){
                            interfaz.validarRangoManual(true);
                        }
                    } else {
                        interfaz.validarRangoManual(false);
                    }
                    Procesos.cargandoDetener();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                if(interfaz!=null){
                    interfaz.validarRangoManual(false);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void obtenerHiloAutomatico(int id_cajanivel1, Context con) {
        Procesos.cargandoIniciar(con);
        String consulta = Procesos.url + "/RangoHilosCaja1/obtenerHiloAutomatico.php?id_cajanivel1=" + id_cajanivel1;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    as.add(new RangoHilosCaja1(object.getInt("id_rangohiloscaja1"),null, null, object.getInt("numero_rangohiloscaja1"), ""));
                    Procesos.cargandoDetener();
                    if (interfaz != null) {
                        interfaz.rangoAutomatico(as.get(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con el servidor", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
                if (interfaz != null) {
                    interfaz.rangoAutomatico(null);// no hay hilos disponibles en esa caja
                }
            }
        });
        queue.add(requerimiento);
    }

    public void editarRangoHilosCaja1(RangoHilosCaja1 RangoHilosCaja1, Context con, boolean esDesactivar) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/RangoHilosCaja1/editarRangoHilosCaja1.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_rangohiloscaja1", RangoHilosCaja1.getId_rangocaja1());
                parametros.put("id_cajanivel1", RangoHilosCaja1.getId_cajanivel1());
                parametros.put("id_cajanivel2", RangoHilosCaja1.getId_cajanivel2());
                parametros.put("estado_rangohiloscaja1", RangoHilosCaja1.getEstado());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/RangoHilosCaja1/editarRangoHilosCaja1.php?"
                    +"id_rangohiloscaja1=" +  RangoHilosCaja1.getId_rangocaja1()
                    +"&id_cajanivel1=" +  RangoHilosCaja1.getId_cajanivel1()
                    +"&id_cajanivel2=" +  RangoHilosCaja1.getId_cajanivel2()
                    +"&estado_rangohiloscaja1=" +  RangoHilosCaja1.getEstado();
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
                    Procesos.cargandoDetener();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Problema con el servidor", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }

    public void eliminarRangoHilosCaja1(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/RangoHilosCaja1/eliminarRangoHilosCaja1.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/RangoHilosCaja1/eliminarRangoHilosCaja1.php?id=" + id;
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
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Problema con el servidor", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }

    public interface interfazRangoCaja1DAO {
        void rangoAutomatico(RangoHilosCaja1 rangoHilosCaja1);
        void validarRangoManual(boolean b);
    }
}
