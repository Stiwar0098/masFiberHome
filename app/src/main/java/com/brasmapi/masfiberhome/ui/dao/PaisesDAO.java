package com.brasmapi.masfiberhome.ui.dao;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
        String consulta = "https://masfiberhome.com/webservices/appfh/filtrarpaises64.php?filtrar="+buscar;
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
                //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                ListaPaisesFragment.cargar();
            }
        });
        queue.add(requerimiento);
        return as;
    }

    private List<Pais> obtenerLista(JSONArray array) {
        List<Pais> lista = null;

        for(int i=0;i<array.length();i+=3){//i+= el numero de campos de la base de datos
            try {
                lista.add(new Pais(Integer.parseInt(array.getString(i)),array.getString(i+1),array.getString(i+2)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    public boolean crearPais(Pais pais){
        return false;
    }
    public boolean modificarPais(Pais pais){
        return false;
    }
    public boolean eliminarPais(int id){
        return false;
    }

}
