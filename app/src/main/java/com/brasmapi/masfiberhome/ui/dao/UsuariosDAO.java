package com.brasmapi.masfiberhome.ui.dao;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.brasmapi.masfiberhome.CrearUsuariosFragment;
import com.brasmapi.masfiberhome.ListaUsuariosFragment;
import com.brasmapi.masfiberhome.LoginActivity;
import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.ui.entidades.Pais;
import com.brasmapi.masfiberhome.ui.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsuariosDAO {
    Procesos op = new Procesos();
    List<Usuario> as;
    RequestQueue queue;
    LoginActivity loginActivity;
    Context context;
    private usuarioBaseDeDatos interfaz;
    public void filtarUsuarios(String buscar, Context con, boolean isElim){
        if (!isElim){
            Procesos.cargandoIniciar(con);
        }
        String consulta = Procesos.url+"/usuario/filtrarUsuario.php?filtrar="+buscar;
        context=con;
        queue= Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento=new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as=new ArrayList<>();
                for(int i=0; i<response.length();i++){
                    try {
                        JSONObject object = new JSONObject(response.get(i).toString());
                        as.add(new Usuario(Integer.parseInt(object.getString("id_usuario")),object.getString("nombre_usuario"),object.getString("usuario_usuario"),object.getString("contrasena_usuario"),Integer.parseInt(object.getString("rol_usuario")),object.getString("estado_usuario")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ListaUsuariosFragment.listaUsuarios=as;
                //ListaUsuariosFragment.cargar();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                ListaUsuariosFragment.listaUsuarios=null;
                //ListaUsuariosFragment.cargar();
            }
        });
        queue.add(requerimiento);
    }

    public void buscarUsuario(String buscar, Context con, usuarioBaseDeDatos inte){
        interfaz=inte;
        Procesos.cargandoIniciar(con);
        String consulta = Procesos.url+"/usuario/filtrarUsuario.php?filtrar="+buscar;
        context=con;
        queue= Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento=new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as=new ArrayList<>();
                    try {
                        JSONObject object = new JSONObject(response.get(0).toString());
                        as.add(new Usuario(Integer.parseInt(object.getString("id_usuario")),object.getString("nombre_usuario"),object.getString("usuario_usuario"),object.getString("contraseña_usuario"),Integer.parseInt(object.getString("id_rol")),object.getString("estado_usuario")));
                        Procesos.user= as.get(0);
                        Procesos.cargandoDetener();
                        if (interfaz!=null){
                            interfaz.usuarioSelecionado();
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
                Procesos.user=null;
                Procesos.cargandoDetener();
                if (interfaz!=null){
                    interfaz.usuarioSelecionado();
                }
            }
        });
        queue.add(requerimiento);
    }
    public void crearUsuario(Usuario usuario, Context con){
        Procesos.cargandoIniciar(con);
        context=con;
        String consulta;
        int metodo = 0;
        JSONObject parametros =null;
        queue= Volley.newRequestQueue(context);
        if(Procesos.isPost) {
            consulta = Procesos.url+"/usuario/crearUsuario.php";
            metodo= Request.Method.POST;
            parametros=new JSONObject();
            try {
                parametros.put("nombre", usuario.getNombre());
                parametros.put("usuario", usuario.getUsuario());
                parametros.put("contrasena", usuario.getContrasena());
                parametros.put("rol", usuario.getRol());
                parametros.put("estado", usuario.getEstado());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            consulta = Procesos.url+"/usuario/crearUsuario.php?nombre="+usuario.getNombre()+"&usuario="+usuario.getUsuario()+"&contrasena="+usuario.getContrasena()+"&rol="+usuario.getRol()+"&estado="+usuario.getEstado();
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
                  //      CrearUsuariosFragment.txtNombreusuario.getEditText().setText("");
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
    public void editarUsuario(Usuario usuario, Context con,boolean esDesactivar){
        Procesos.cargandoIniciar(con);
        context=con;
        String consulta;
        int metodo = 0;
        JSONObject parametros =null;
        queue= Volley.newRequestQueue(context);
        if(Procesos.isPost) {
            consulta = Procesos.url+"/usuario/editarUsuario.php";
            metodo= Request.Method.POST;
            parametros=new JSONObject();
            try {
                parametros.put("nombre", usuario.getNombre());
                parametros.put("usuario", usuario.getUsuario());
                parametros.put("contrasena", usuario.getContrasena());
                parametros.put("rol", usuario.getRol());
                parametros.put("estado", usuario.getEstado());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            consulta = Procesos.url+"/usuario/editarUsuario.php?nombre="+usuario.getNombre()+"&usuario="+usuario.getUsuario()+"&contrasena="+usuario.getContrasena()+"&rol="+usuario.getRol()+"&estado="+usuario.getEstado()+"&id="+usuario.getId();
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
                    //    CrearUsuariosFragment.txtNombreUsuario.getEditText().setText("");
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
    public void eliminarUsuario(int id, Context con){
        Procesos.cargandoIniciar(con);
        context=con;
        String consulta;
        int metodo = 0;
        JSONObject parametros =null;
        queue= Volley.newRequestQueue(context);
        if(Procesos.isPost) {
            consulta = Procesos.url+"/usuario/eliminarUsuario.php";
            metodo= Request.Method.POST;
            parametros=new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            consulta = Procesos.url+"/usuario/eliminarUsuario.php?id="+id;
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
                    filtarUsuarios("",con,true);
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
    public void eliminarUsuarioCascada(int id, Context con){
        Procesos.cargandoIniciar(con);
        context=con;
        String consulta;
        int metodo = 0;
        JSONObject parametros =null;
        queue= Volley.newRequestQueue(context);
        if(Procesos.isPost) {
            consulta = Procesos.url+"/usuario/eliminarCascadaUsuario.php";
            metodo= Request.Method.POST;
            parametros=new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            consulta = Procesos.url+"/usuario/eliminarCascadaUsuario.php?id="+id;
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
                    filtarUsuarios("",con,true);
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
    public interface usuarioBaseDeDatos {
        void usuarioSelecionado();
    }
}
