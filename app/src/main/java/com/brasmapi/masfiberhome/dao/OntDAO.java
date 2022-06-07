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
import com.brasmapi.masfiberhome.entidades.Ont;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OntDAO {
    List<Ont> as;
    RequestQueue queue;
    Context context;
    private interfazOntDAO interfaz;

    public OntDAO(interfazOntDAO inte) {
        interfaz=inte;
    }

    public void filtarOnt(String buscar, Context con, boolean isElim) {
        if (!isElim) {
            Procesos.cargandoIniciar(con);
        }
        String consulta = Procesos.url + "/Ont/filtrarOnt.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = new JSONObject(response.get(i).toString());
                       as.add(new Ont(object.getInt("id_ont"), object.getString("serie_ont"), object.getInt("id_modelo"), object.getString("nombre_modelosont"), object.getString("responsable_ont"), object.getInt("numeroont"), object.getString("estado_ont")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(interfaz!=null){
                    interfaz.setListaOnt(as);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                as=null;
                if(interfaz!=null){
                    interfaz.setListaOnt(as);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void buscarOnt(String buscar, Context con) {
        Procesos.cargandoIniciar(con);
        String consulta = Procesos.url + "/Ont/buscarOnt.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    as.add(new Ont(object.getInt("id_ont"), object.getString("serie_ont"), object.getInt("id_modelo"), object.getString("nombre_modelosont"), object.getString("responsable_ont"), object.getInt("numeroont"), object.getString("estado_ont")));
                    Procesos.cargandoDetener();
                    if (interfaz != null) {
                        interfaz.setOnt(as.get(0));
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
                if (interfaz != null) {
                    interfaz.setOnt(null);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void crearOnt(Ont Ont, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/Ont/crearOnt.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("serie_ont", Ont.getSerieOnt());
                parametros.put("id_modelo", Ont.getId_modeloOnt());
                parametros.put("responsable_ont", Ont.getResponsable());
                parametros.put("numeroont", Ont.getNumeroOnt());
                parametros.put("estado_ont", Ont.getEstado());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/Ont/crearOnt.php?"
                    +"serie_ont=" +  Ont.getSerieOnt()
                    +"&id_modelo=" +  Ont.getId_modeloOnt()
                    +"&responsable_ont=" +  Ont.getResponsable()
                    +"&numeroont=" +  Ont.getNumeroOnt()
                    +"&estado_ont=" +  Ont.getEstado();

            metodo = Request.Method.GET;
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("respuesta").toString().equals("ok")) {
                        Toast.makeText(context, "Los datos se cargaron correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, response.get("respuesta").toString(), Toast.LENGTH_SHORT).show();
                    }
                    if (interfaz!=null){
                        interfaz.limpiar();
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

    public void editarOnt(Ont Ont, Context con, boolean esDesactivar) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/Ont/editarOnt.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_ont", Ont.getId());
                parametros.put("serie_ont", Ont.getSerieOnt());
                parametros.put("id_modelo", Ont.getId_modeloOnt());
                parametros.put("responsable_ont", Ont.getResponsable());
                parametros.put("numeroont", Ont.getNumeroOnt());
                parametros.put("estado_ont", Ont.getEstado());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/Ont/editarOnt.php?"
                    +"id_ont=" +  Ont.getId()
                    +"&serie_ont=" +  Ont.getSerieOnt()
                    +"&id_modelo=" +  Ont.getId_modeloOnt()
                    +"&responsable_ont=" +  Ont.getResponsable()
                    +"&numeroont=" +  Ont.getNumeroOnt()
                    +"&estado_ont=" +  Ont.getEstado();
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
                            interfaz.limpiar();
                        }
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

    public void eliminarOnt(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/Ont/eliminarOnt.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/Ont/eliminarOnt.php?id=" + id;
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
                    filtarOnt("", con, true);
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

    public void eliminarOntCascada(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "Ont/eliminarCascadaOnt.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/Ont/eliminarCascadaOnt.php?id=" + id;
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
                    filtarOnt("", con, true);
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
    public interface interfazOntDAO {
        void setOnt(Ont Ont);
        void setListaOnt(List<Ont> lista);
        void limpiar();
    }
}
