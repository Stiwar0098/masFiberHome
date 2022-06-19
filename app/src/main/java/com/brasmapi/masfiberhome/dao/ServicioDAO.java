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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServicioDAO {
    RequestQueue queue;
    Context context;
    private interfazServicio interfaz;

    public ServicioDAO(ServicioDAO.interfazServicio inte) {
        interfaz=inte;
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

    public void eliminarEnServiciosLibres(int numeroServicio, Context con) {
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/Servicio/validarEnServicioLibres.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", numeroServicio);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/Servicio/validarEnServicioLibres.php?numeroServicio=" + numeroServicio;
            metodo = Request.Method.GET;
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Problema con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(requerimiento);
    }

    
    public interface interfazServicio {
        void setUsuarioRepetido(boolean estaRepetido);
    }
}
