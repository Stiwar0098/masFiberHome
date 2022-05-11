package com.brasmapi.masfiberhome.ui.dao;

import android.app.DownloadManager;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brasmapi.masfiberhome.CrearPaisFragment;
import com.brasmapi.masfiberhome.ListaPaisesFragment;
import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.ui.entidades.Pais;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaisesDAO {
    Procesos op = new Procesos();
    List<Pais> as;
    RequestQueue queue;
    Context context;
    public List<Pais> filtarPaises(String buscar, Context con){
        String consulta = Procesos.url+"webservicesbrasmapi/api/pais/filtrarpaises.php?filtrar="+buscar;
        context=con;
        return enviarRecibirDatos(consulta);
    }
    public List<Pais> enviarRecibirDatos(String consulta)  {
        queue= Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento=new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as=new ArrayList<>();
                for(int i=0; i<response.length();i++){
                    try {
                        JSONObject object = new JSONObject(response.get(i).toString());
                        as.add(new Pais(Integer.parseInt(object.getString("id_pais")),object.getString("nombre_pais"),object.getString("estado_pais")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ListaPaisesFragment.listaPaises=as;
                ListaPaisesFragment.cargar();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                ListaPaisesFragment.cargar();
            }
        });
        queue.add(requerimiento);
        return as;
    }

    public void crearPais(Pais pais, Context con){
        context=con;
        String consulta;
        boolean isPost=false;
        int metodo = 0;
        JSONObject parametros =null;
        queue= Volley.newRequestQueue(context);
        if(isPost) {
            consulta = Procesos.url+"webservicesbrasmapi/api/pais/crearpais.php";
            metodo= Request.Method.POST;
            parametros=new JSONObject();
            try {
                parametros.put("nombre", pais.getNombre());
                parametros.put("estado", pais.getEstado());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            consulta = Procesos.url+"webservicesbrasmapi/api/pais/crearpais.php?nombre="+pais.getNombre()+"&estado="+pais.getEstado();
            metodo= Request.Method.GET;
        }
            JsonObjectRequest requerimiento=new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.get("respuesta").toString().equals("ok")){
                            Toast.makeText(context, "Los datos se cargaron correctamente", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, response.get("respuesta").toString(), Toast.LENGTH_SHORT).show();
                        }
                        CrearPaisFragment.txtNombrePais.getEditText().setText("");
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
    public boolean editarPais(Pais pais){
        return false;
    }
    public boolean eliminarPais(int id){
        return false;
    }

}
