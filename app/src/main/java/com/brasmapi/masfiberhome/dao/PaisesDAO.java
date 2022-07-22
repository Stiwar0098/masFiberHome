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
import com.brasmapi.masfiberhome.ui.crear.CrearPaisFragment;
import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.entidades.Pais;

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
    private interfazPaisDao interfaz;

    public PaisesDAO(interfazPaisDao inte) {
        interfaz=inte;
    }

    public void filtarPaises(String buscar, Context con, boolean isElim){
        if (!isElim){
            Procesos.cargandoIniciar(con);
        }
        String consulta = Procesos.url+"/pais/filtrarPais.php?filtrar="+buscar;
        context=con;
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
                if(interfaz!=null){
                    interfaz.setListaPais(as);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //
                as=null;
                if(interfaz!=null){
                    interfaz.setListaPais(as);
                }
            }
        });
        queue.add(requerimiento);
    }
    public void crearPais(Pais pais, Context con){
        Procesos.cargandoIniciar(con);
        context=con;
        String consulta;
        int metodo = 0;
        JSONObject parametros =null;
        queue= Volley.newRequestQueue(context);
        if(Procesos.isPost) {
            consulta = Procesos.url+"/pais/crearPais.php";
            metodo= Request.Method.POST;
            parametros=new JSONObject();
            try {
                parametros.put("nombre", pais.getNombre());
                parametros.put("estado", pais.getEstado());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            consulta = Procesos.url+"/pais/crearPais.php?nombre="+pais.getNombre()+"&estado="+pais.getEstado();
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
                    
                    Toast.makeText(context, "Problema con el servidor", Toast.LENGTH_SHORT).show();
                    Procesos.cargandoDetener();
                }
            });
            queue.add(requerimiento);
    }
    public void editarPais(Pais pais, Context con,boolean esDesactivar){
        Procesos.cargandoIniciar(con);
        context=con;
        String consulta;
        int metodo = 0;
        JSONObject parametros =null;
        queue= Volley.newRequestQueue(context);
        if(Procesos.isPost) {
            consulta = Procesos.url+"/pais/editarPais.php";
            metodo= Request.Method.POST;
            parametros=new JSONObject();
            try {
                parametros.put("id", pais.getId());
                parametros.put("nombre", pais.getNombre());
                parametros.put("estado", pais.getEstado());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            consulta = Procesos.url+"/pais/editarPais.php?nombre="+pais.getNombre()+"&estado="+pais.getEstado()+"&id="+pais.getId();
            metodo= Request.Method.GET;
        }
        JsonObjectRequest requerimiento=new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.get("respuesta").toString().equals("ok")){
                        Toast.makeText(context, "Los datos se modificaron correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, response.get("respuesta").toString(), Toast.LENGTH_SHORT).show();
                    }
                    if (!esDesactivar){
                        CrearPaisFragment.txtNombrePais.getEditText().setText("");
                    }
                    Procesos.cargandoDetener();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
                Toast.makeText(context, "Problema con el servidor", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }
    public void eliminarPais(int id, Context con){
        Procesos.cargandoIniciar(con);
        context=con;
        String consulta;
        int metodo = 0;
        JSONObject parametros =null;
        queue= Volley.newRequestQueue(context);
        if(Procesos.isPost) {
            consulta = Procesos.url+"/pais/eliminarPais.php";
            metodo= Request.Method.POST;
            parametros=new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            consulta = Procesos.url+"/pais/eliminarPais.php?id="+id;
            metodo= Request.Method.GET;
        }
        JsonObjectRequest requerimiento=new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.get("respuesta").toString().equals("ok")){
                        Toast.makeText(context, "Los datos se eliminaron correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, response.get("respuesta").toString(), Toast.LENGTH_SHORT).show();
                    }
                    filtarPaises("",con,true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
                Toast.makeText(context, "Problema con el servidor", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }
    public void eliminarPaisCascada(int id, Context con){
        Procesos.cargandoIniciar(con);
        context=con;
        String consulta;
        int metodo = 0;
        JSONObject parametros =null;
        queue= Volley.newRequestQueue(context);
        if(Procesos.isPost) {
            consulta = Procesos.url+"/pais/eliminarCascadaPais.php";
            metodo= Request.Method.POST;
            parametros=new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            consulta = Procesos.url+"/pais/eliminarCascadaPais.php?id="+id;
            metodo= Request.Method.GET;
        }
        JsonObjectRequest requerimiento=new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.get("respuesta").toString().equals("ok")){
                        Toast.makeText(context, "Los datos se eliminaron correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, response.get("respuesta").toString(), Toast.LENGTH_SHORT).show();
                    }
                    filtarPaises("",con,true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
                Toast.makeText(context, "Problema con el servidor", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }
    public interface interfazPaisDao {
        void setPais(Pais pais);
        void setListaPais(List<Pais> pais);
    }
}
