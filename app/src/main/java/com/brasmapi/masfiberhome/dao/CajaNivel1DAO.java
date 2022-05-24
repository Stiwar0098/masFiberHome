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
import com.brasmapi.masfiberhome.entidades.CajaNivel1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CajaNivel1DAO {
    List<CajaNivel1> as;
    RequestQueue queue;
    Context context;
    private interfazCajaNivel1DAO interfaz;

    public CajaNivel1DAO(interfazCajaNivel1DAO inte) {
        interfaz=inte;
    }

    public void filtarCajaNivel1(String buscar, Context con, boolean isElim) {
        if (!isElim) {
            Procesos.cargandoIniciar(con);
        }
        String consulta = Procesos.url + "/cajanivel1/filtrarCajaNivel1.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = new JSONObject(response.get(i).toString());
                       as.add(new CajaNivel1(object.getInt("id_cajanivel1"), object.getString("nombre_cajanivel1"), object.getString("direccion_cajanivel1"),object.getString("referencia_cajanivel1"), object.getString("latitud_cajanivel1"), object.getString("longitud_cajanivel1"), object.getInt("id_vlan"), object.getString("nombre_vlan"), object.getInt("id_ciudad"), object.getString("nombre_ciudad"), object.getString("estado_cajanivel1"), object.getString("abreviatura_cajanivel1"), object.getInt("cantidadhilos_cajanivel1")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(interfaz!=null){
                    interfaz.setListaCajaNivel1(as);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                as=null;
                if(interfaz!=null){
                    interfaz.setListaCajaNivel1(as);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void buscarCajaNivel1(String buscar, Context con) {
        Procesos.cargandoIniciar(con);
        String consulta = Procesos.url + "/cajanivel1/buscarCajaNivel1.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    as.add(new CajaNivel1(object.getInt("id_cajanivel1"), object.getString("nombre_cajanivel1"), object.getString("direccion_cajanivel1"),object.getString("referencia_cajanivel1"), object.getString("latitud_cajanivel1"), object.getString("longitud_cajanivel1"), object.getInt("id_vlan"), object.getString("nombre_vlan"), object.getInt("id_ciudad"), object.getString("nombre_ciudad"), object.getString("estado_cajanivel1"), object.getString("abreviatura_cajanivel1"), object.getInt("cantidadhilos_cajanivel1")));
                    Procesos.cargandoDetener();
                    if (interfaz != null) {
                        interfaz.setCajaNivel1(as.get(0));
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
                    interfaz.setCajaNivel1(null);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void crearCajaNivel1(CajaNivel1 cajaNivel1, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/cajanivel1/crearCajaNivel1.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("nombre_cajanivel1", cajaNivel1.getNombre_cajaNivel1());
                parametros.put("abreviatura_cajanivel1", cajaNivel1.getAbreviatura_cajaNivel1());
                parametros.put("direccion_cajanivel1", cajaNivel1.getDireccion_cajaNivel1());
                parametros.put("referencia_cajanivel1", cajaNivel1.getReferencia_cajaNivel1());
                parametros.put("latitud_cajanivel1", cajaNivel1.getLatitud_cajaNivel1());
                parametros.put("longitud_cajanivel1", cajaNivel1.getLongitud_cajaNivel1());
                parametros.put("id_vlan", cajaNivel1.getId_vlan());
                parametros.put("id_ciudad", cajaNivel1.getId_ciudad());
                parametros.put("cantidadhilos_cajanivel1", cajaNivel1.getNumeroHilos_cajaNivel1());
                parametros.put("estado_cajanivel1", cajaNivel1.getEstado_cajaNivel1());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/cajanivel1/crearCajaNivel1.php?"
                    +"&nombre_cajanivel1=" +  cajaNivel1.getNombre_cajaNivel1()
                    +"&abreviatura_cajanivel1=" +  cajaNivel1.getAbreviatura_cajaNivel1()
                    +"&direccion_cajanivel1=" +  cajaNivel1.getDireccion_cajaNivel1()
                    +"&referencia_cajanivel1=" +  cajaNivel1.getReferencia_cajaNivel1()
                    +"&latitud_cajanivel1=" +  cajaNivel1.getLatitud_cajaNivel1()
                    +"&longitud_cajanivel1=" +  cajaNivel1.getLongitud_cajaNivel1()
                    +"&id_vlan=" +  cajaNivel1.getId_vlan()
                    +"&id_ciudad=" +  cajaNivel1.getId_ciudad()
                    +"&cantidadhilos_cajanivel1=" +  cajaNivel1.getNumeroHilos_cajaNivel1()
                    +"&estado_cajanivel1=" +  cajaNivel1.getEstado_cajaNivel1();
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

    public void editarCajaNivel1(CajaNivel1 cajaNivel1, Context con, boolean esDesactivar) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/cajanivel1/editarCajaNivel1.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_cajanivel1", cajaNivel1.getId_cajaNivel1());
                parametros.put("nombre_cajanivel1", cajaNivel1.getNombre_cajaNivel1());
                parametros.put("abreviatura_cajanivel1", cajaNivel1.getAbreviatura_cajaNivel1());
                parametros.put("direccion_cajanivel1", cajaNivel1.getDireccion_cajaNivel1());
                parametros.put("referencia_cajanivel1", cajaNivel1.getReferencia_cajaNivel1());
                parametros.put("latitud_cajanivel1", cajaNivel1.getLatitud_cajaNivel1());
                parametros.put("longitud_cajanivel1", cajaNivel1.getLongitud_cajaNivel1());
                parametros.put("id_vlan", cajaNivel1.getId_vlan());
                parametros.put("id_ciudad", cajaNivel1.getId_ciudad());
                parametros.put("cantidadhilos_cajanivel1", cajaNivel1.getNumeroHilos_cajaNivel1());
                parametros.put("estado_cajanivel1", cajaNivel1.getEstado_cajaNivel1());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/cajanivel1/editarCajaNivel1.php?"
                    +"&id_cajanivel1=" +  cajaNivel1.getId_cajaNivel1()
                    +"&nombre_cajanivel1=" +  cajaNivel1.getNombre_cajaNivel1()
                    +"&abreviatura_cajanivel1=" +  cajaNivel1.getAbreviatura_cajaNivel1()
                    +"&direccion_cajanivel1=" +  cajaNivel1.getDireccion_cajaNivel1()
                    +"&referencia_cajanivel1=" +  cajaNivel1.getReferencia_cajaNivel1()
                    +"&latitud_cajanivel1=" +  cajaNivel1.getLatitud_cajaNivel1()
                    +"&longitud_cajanivel1=" +  cajaNivel1.getLongitud_cajaNivel1()
                    +"&id_vlan=" +  cajaNivel1.getId_vlan()
                    +"&id_ciudad=" +  cajaNivel1.getId_ciudad()
                    +"&cantidadhilos_cajanivel1=" +  cajaNivel1.getNumeroHilos_cajaNivel1()
                    +"&estado_cajanivel1=" +  cajaNivel1.getEstado_cajaNivel1();
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

    public void eliminarCajaNivel1(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/cajanivel1/eliminarCajaNivel1.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/cajanivel1/eliminarCajaNivel1.php?id=" + id;
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
                    filtarCajaNivel1("", con, true);
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

    public void eliminarCajaNivel1Cascada(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "cajanivel1/eliminarCascadaCajaNivel1.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/cajanivel1/eliminarCascadaCajaNivel1.php?id=" + id;
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
                    filtarCajaNivel1("", con, true);
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
    public interface interfazCajaNivel1DAO {
        void setCajaNivel1(CajaNivel1 CajaNivel1);
        void setListaCajaNivel1(List<CajaNivel1> lista);
        void limpiar();
    }
}
