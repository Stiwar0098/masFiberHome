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
        String consulta = Procesos.url + "/RangoHilosCaja1/verificarHiloManual.php?id_cajanivel1=" + id_cajanivel1+"&numero_rangohiloscaja1="+numero_rangohiloscaja1;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    if (!response.get(0).toString().contains("error")){
                        as.add(new RangoHilosCaja1(object.getInt("id_rangohiloscaja1"),null, null, object.getInt("numero_rangohiloscaja1"), ""));
                        if (interfaz != null) {
                            interfaz.validarHiloManual(as.get(0));
                        }
                    }else{
                        interfaz.validarHiloManual(null);
                    }
                } catch (JSONException e) {
                    interfaz.validarHiloManual(null);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                if(interfaz!=null){
                    interfaz.validarHiloManual(null);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void obtenerHiloAutomatico(int id_cajanivel1, Context con) {
        String consulta = Procesos.url + "/RangoHilosCaja1/obtenerHiloAutomatico.php?id_cajanivel1=" + id_cajanivel1;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    if (!response.get(0).toString().contains("error")) {
                        as.add(new RangoHilosCaja1(object.getInt("id_rangohiloscaja1"), null, null, object.getInt("numero_rangohiloscaja1"), ""));
                        if (interfaz != null) {
                            interfaz.hiloAutomatico(as.get(0));
                        }
                    }else{
                        interfaz.hiloAutomatico(null);
                    }
                } catch (JSONException e) {
                    interfaz.hiloAutomatico(null);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con el servidor obtenerHiloAutomatico", Toast.LENGTH_SHORT).show();
                if (interfaz != null) {
                    interfaz.hiloAutomatico(null);// no hay hilos disponibles en esa caja
                }
            }
        });
        queue.add(requerimiento);
    }

    public void obtenerHiloAnterior(int id_cajanivel1,int id_cajanivel2, Context con) {
        String consulta = Procesos.url + "/RangoHilosCaja1/obtenerHiloAnterior.php?id_cajanivel1=" + id_cajanivel1+"&id_cajanivel2="+id_cajanivel2;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    as.add(new RangoHilosCaja1(object.getInt("id_rangohiloscaja1"),null, null, object.getInt("numero_rangohiloscaja1"), ""));
                    if (interfaz != null) {
                        interfaz.hiloAnterior(as.get(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con el servidor obtenerHiloAnterior", Toast.LENGTH_SHORT).show();
                if (interfaz != null) {
                    interfaz.hiloAnterior(null);// no hay hilos disponibles en esa caja
                }
            }
        });
        queue.add(requerimiento);
    }

    public void editarRangoHilosCaja1(RangoHilosCaja1 RangoHilosCaja1,String nombreCaja2, Context con) {
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/RangoHilosCaja1/editar.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_rangohiloscaja1", RangoHilosCaja1.getId_rangocaja1());
                parametros.put("estado_rangohiloscaja1", RangoHilosCaja1.getEstado());
                parametros.put("nombre_cajanivel2", nombreCaja2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/RangoHilosCaja1/editar.php?"
                    +"id_rangohiloscaja1=" +  RangoHilosCaja1.getId_rangocaja1()
                    +"&estado_rangohiloscaja1=" +  RangoHilosCaja1.getEstado()
                    +"&nombre_cajanivel2=" +  nombreCaja2;
            metodo = Request.Method.GET;
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("respuesta").toString().equals("ok")) {
                        //Toast.makeText(context, "Los datos se modificaron correctamente", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Problema con el servidor editarRangoHilosCaja1", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(requerimiento);
    }
    public void editarRangoHilosCaja1Anterior(int idRangoHilosCaja1, Context con) {
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/RangoHilosCaja1/editarHiloAnterior.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_rangohiloscaja1", idRangoHilosCaja1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/RangoHilosCaja1/editarHiloAnterior.php?"
                    +"id_rangohiloscaja1=" +  idRangoHilosCaja1;
            metodo = Request.Method.GET;
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("respuesta").toString().equals("ok")) {
                        //Toast.makeText(context, "Los datos se modificaron correctamente", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Problema con el servidor editarRangoHilosCaja1Anterior", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Problema con el servidor eliminarRangoHilosCaja1", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }

    public interface interfazRangoCaja1DAO {
        void hiloAutomatico(RangoHilosCaja1 rangoHilosCaja1);
        void validarHiloManual(RangoHilosCaja1 rangoHilos);
        void hiloAnterior(RangoHilosCaja1 rangoHilos);
    }
}
