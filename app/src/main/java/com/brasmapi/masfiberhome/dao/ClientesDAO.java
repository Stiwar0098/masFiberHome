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
import com.brasmapi.masfiberhome.entidades.Clientes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClientesDAO {
    List<Clientes> as;
    RequestQueue queue;
    Context context;
    private interfazClientesDAO interfaz;

    public ClientesDAO(interfazClientesDAO inte) {
        interfaz=inte;
    }

    public void filtarClientes(String buscar, Context con, boolean isElim) {
        if (!isElim) {
            Procesos.cargandoIniciar(con);
        }
        String consulta = Procesos.url + "/Clientes/filtrarClientes.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = new JSONObject(response.get(i).toString());
                       as.add(new Clientes(object.getInt("id_clientepersona"), object.getString("cedula_clientepersona"), object.getString("nombre_clientepersona"), object.getString("apellido_clientepersona"), object.getString("correo_clientepersona"), object.getString("telefono1_clientepersona"),object.getString("telefono2_clientepersona"),object.getString("estado_clientepersona")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(interfaz!=null){
                    interfaz.setListaClientes(as);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                as=null;
                if(interfaz!=null){
                    interfaz.setListaClientes(as);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void buscarClientes(String buscar, Context con) {
        String consulta = Procesos.url + "/Clientes/buscarClientes.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    as.add(new Clientes(object.getInt("id_clientepersona"), object.getString("cedula_clientepersona"), object.getString("nombre_clientepersona"), object.getString("apellido_clientepersona"), object.getString("correo_clientepersona"), object.getString("telefono1_clientepersona"),object.getString("telefono2_clientepersona"),object.getString("estado_clientepersona")));
                    if (interfaz != null) {
                        interfaz.setClientes(as.get(0));
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
                Toast.makeText(context, "Problema con el servidor", Toast.LENGTH_SHORT).show();
                if (interfaz != null) {
                    interfaz.setClientes(null);
                }
            }
        });
        queue.add(requerimiento);
    }


    public void crearClientes(Clientes Clientes, Context con) {
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/Clientes/crearClientes.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("cedula_clientepersona", Clientes.getCedula());
                parametros.put("nombre_clientepersona", Clientes.getNombre());
                parametros.put("apellido_clientepersona", Clientes.getApellido());
                parametros.put("correo_clientepersona", Clientes.getCorreo());
                parametros.put("telefono1_clientepersona", Clientes.getTelefono1());
                parametros.put("telefono2_clientepersona", Clientes.getTelefono2());
                parametros.put("estado_clientepersona", Clientes.getEstado());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/Clientes/crearClientes.php?"
                    +"cedula_clientepersona=" +  Clientes.getCedula()
                    +"&nombre_clientepersona=" +  Clientes.getNombre()
                    +"&apellido_clientepersona=" +  Clientes.getApellido()
                    +"&correo_clientepersona=" +  Clientes.getCorreo()
                    +"&telefono1_clientepersona=" +  Clientes.getTelefono1()
                    +"&telefono2_clientepersona=" +  Clientes.getTelefono2()
                    +"&estado_clientepersona=" +  Clientes.getEstado();

            metodo = Request.Method.GET;
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("respuesta").toString().equals("ok")) {
                        Toast.makeText(context, "Los datos se cargaron correctamente", Toast.LENGTH_SHORT).show();
                        if (interfaz!=null){
                            interfaz.limpiarClientes();
                        }
                    } else {
                        Toast.makeText(context, response.get("respuesta").toString(), Toast.LENGTH_SHORT).show();
                        Procesos.cargandoDetener();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Procesos.cargandoDetener();
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

    public void editarClientes(Clientes Clientes, Context con, boolean esDesactivar) {
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/Clientes/editarClientes.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_clientepersona", Clientes.getId_cliente());
                parametros.put("cedula_clientepersona", Clientes.getCedula());
                parametros.put("nombre_clientepersona", Clientes.getNombre());
                parametros.put("apellido_clientepersona", Clientes.getApellido());
                parametros.put("correo_clientepersona", Clientes.getCorreo());
                parametros.put("telefono1_clientepersona", Clientes.getTelefono1());
                parametros.put("telefono2_clientepersona", Clientes.getTelefono2());
                parametros.put("estado_clientepersona", Clientes.getEstado());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/Clientes/editarClientes.php?"
                    +"id_clientepersona=" +  Clientes.getId_cliente()
                    +"&cedula_clientepersona=" +  Clientes.getCedula()
                    +"&nombre_clientepersona=" +  Clientes.getNombre()
                    +"&apellido_clientepersona=" +  Clientes.getApellido()
                    +"&correo_clientepersona=" +  Clientes.getCorreo()
                    +"&telefono1_clientepersona=" +  Clientes.getTelefono1()
                    +"&telefono2_clientepersona=" +  Clientes.getTelefono2()
                    +"&estado_clientepersona=" +  Clientes.getEstado();
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
                            interfaz.limpiarClientes();
                        }
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
                Toast.makeText(context, "Problema con el servidor", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }

    public void eliminarClientes(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/Clientes/eliminarClientes.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/Clientes/eliminarClientes.php?id=" + id;
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
                    filtarClientes("", con, true);
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

    public void eliminarClientesCascada(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "Clientes/eliminarCascadaClientes.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/Clientes/eliminarCascadaClientes.php?id=" + id;
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
                    filtarClientes("", con, true);
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
    public interface interfazClientesDAO {
        void setClientes(Clientes Clientes);
        void setListaClientes(List<Clientes> lista);
        void limpiarClientes();
    }
}
