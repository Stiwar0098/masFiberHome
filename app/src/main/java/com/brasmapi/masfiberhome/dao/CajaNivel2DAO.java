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
import com.brasmapi.masfiberhome.entidades.CajaNivel2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CajaNivel2DAO {
    List<CajaNivel2> as;
    RequestQueue queue;
    Context context;
    private interfazCajaNivel2DAO interfaz;

    public CajaNivel2DAO(interfazCajaNivel2DAO inte) {
        interfaz=inte;
    }

    public void filtarCajaNivel2(String buscar, Context con, boolean isElim) {
        if (!isElim) {
            Procesos.cargandoIniciar(con);
        }
        String consulta = Procesos.url + "/cajanivel2/filtrarCajaNivel2.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = new JSONObject(response.get(i).toString());
                        as.add(new CajaNivel2(object.getInt("id_cajanivel2"), object.getString("nombre_cajanivel2"), object.getString("abreviatura_cajanivel2"), object.getString("direccion_cajanivel2"),object.getString("referencia"), object.getString("latitud_cajanivel2"), object.getString("longitud_cajanivel2"), object.getInt("id_cajanivel1"), object.getString("nombre_cajanivel1"), object.getInt("hilocajanivel1_cajanivel2"), object.getInt("cantidadhilos_cajanivel2"), object.getString("estado_cajanivel2"), object.getString("abreviatura_cajanivel1"),object.getInt("id_vlan")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(interfaz!=null){
                    interfaz.setListaCajaNivel2(as);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                as=null;
                if(interfaz!=null){
                    interfaz.setListaCajaNivel2(as);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void buscarCajaNivel2(String buscar, Context con) {
        Procesos.cargandoIniciar(con);
        String consulta = Procesos.url + "/cajanivel2/buscarCajaNivel2.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    as.add(new CajaNivel2(object.getInt("id_cajanivel2"), object.getString("nombre_cajanivel2"), object.getString("abreviatura_cajanivel2"), object.getString("direccion_cajanivel2"),object.getString("referencia"), object.getString("latitud_cajanivel2"), object.getString("longitud_cajanivel2"), object.getInt("id_cajanivel1"), object.getString("nombre_cajanivel1"), object.getInt("hilocajanivel1_cajanivel2"), object.getInt("cantidadhilos_cajanivel2"), object.getString("estado_cajanivel2"), object.getString("abreviatura_cajanivel1"),object.getInt("id_vlan")));
                    if (interfaz != null) {
                        interfaz.setCajaNivel2(as.get(0));
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
                    interfaz.setCajaNivel2(null);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void crearCajaNivel2(CajaNivel2 cajaNivel2, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/cajanivel2/crearCajaNivel2.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("nombre_cajanivel2", cajaNivel2.getNombre_CajaNivel2());
                parametros.put("abreviatura_cajanivel2", cajaNivel2.getAbreviatura());
                parametros.put("direccion_cajanivel2", cajaNivel2.getDireccion_CajaNivel2());
                parametros.put("referencia", cajaNivel2.getReferencia_CajaNivel2());
                parametros.put("latitud_cajanivel2", cajaNivel2.getLatitud_CajaNivel2());
                parametros.put("longitud_cajanivel2", cajaNivel2.getLongitud_CajaNivel2());
                parametros.put("id_cajanivel1", cajaNivel2.getId_CajaNivel1());
                parametros.put("hilocajanivel1_cajanivel2", cajaNivel2.getHiloCaja1());
                parametros.put("cantidadhilos_cajanivel2", cajaNivel2.getCantidadHilos());
                parametros.put("estado_cajanivel2", cajaNivel2.getEstado_CajaNivel2());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/cajanivel2/crearCajaNivel2.php?"
                    +"nombre_cajanivel2=" +  cajaNivel2.getNombre_CajaNivel2()
                    +"&abreviatura_cajanivel2=" +  cajaNivel2.getAbreviatura()
                    +"&direccion_cajanivel2=" +  cajaNivel2.getDireccion_CajaNivel2()
                    +"&referencia=" +  cajaNivel2.getReferencia_CajaNivel2()
                    +"&latitud_cajanivel2=" +  cajaNivel2.getLatitud_CajaNivel2()
                    +"&longitud_cajanivel2=" +  cajaNivel2.getLongitud_CajaNivel2()
                    +"&id_cajanivel1=" +  cajaNivel2.getId_CajaNivel1()
                    +"&hilocajanivel1_cajanivel2=" +  cajaNivel2.getHiloCaja1()
                    +"&cantidadhilos_cajanivel2=" +  cajaNivel2.getCantidadHilos()
                    +"&estado_cajanivel2=" +  cajaNivel2.getEstado_CajaNivel2();
            metodo = Request.Method.GET;
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("respuesta").toString().equals("ok")) {
                        Toast.makeText(context, "Los datos se cargaron correctamente", Toast.LENGTH_SHORT).show();
                        if (interfaz!=null){
                            interfaz.limpiarCajaNivel2();
                        }
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

    public void editarCajaNivel2(CajaNivel2 cajaNivel2, Context con, boolean esDesactivar) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/cajanivel2/editarCajaNivel2.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_cajanivel2", cajaNivel2.getId_CajaNivel2());
                parametros.put("nombre_cajanivel2", cajaNivel2.getNombre_CajaNivel2());
                parametros.put("abreviatura_cajanivel2", cajaNivel2.getAbreviatura());
                parametros.put("direccion_cajanivel2", cajaNivel2.getDireccion_CajaNivel2());
                parametros.put("referencia", cajaNivel2.getReferencia_CajaNivel2());
                parametros.put("latitud_cajanivel2", cajaNivel2.getLatitud_CajaNivel2());
                parametros.put("longitud_cajanivel2", cajaNivel2.getLongitud_CajaNivel2());
                parametros.put("id_cajanivel1", cajaNivel2.getId_CajaNivel1());
                parametros.put("hilocajanivel1_cajanivel2", cajaNivel2.getHiloCaja1());
                parametros.put("cantidadhilos_cajanivel2", cajaNivel2.getCantidadHilos());
                parametros.put("estado_cajanivel2", cajaNivel2.getEstado_CajaNivel2());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/cajanivel2/editarCajaNivel2.php?"
                    +"id_cajanivel2=" +  cajaNivel2.getId_CajaNivel2()
                    +"&nombre_cajanivel2=" +  cajaNivel2.getNombre_CajaNivel2()
                    +"&abreviatura_cajanivel2=" +  cajaNivel2.getAbreviatura()
                    +"&direccion_cajanivel2=" +  cajaNivel2.getDireccion_CajaNivel2()
                    +"&referencia=" +  cajaNivel2.getReferencia_CajaNivel2()
                    +"&latitud_cajanivel2=" +  cajaNivel2.getLatitud_CajaNivel2()
                    +"&longitud_cajanivel2=" +  cajaNivel2.getLongitud_CajaNivel2()
                    +"&id_cajanivel1=" +  cajaNivel2.getId_CajaNivel1()
                    +"&hilocajanivel1_cajanivel2=" +  cajaNivel2.getHiloCaja1()
                    +"&cantidadhilos_cajanivel2=" +  cajaNivel2.getCantidadHilos()
                    +"&estado_cajanivel2=" +  cajaNivel2.getEstado_CajaNivel2();
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
                            interfaz.limpiarCajaNivel2();
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

    public void eliminarCajaNivel2(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/cajanivel2/eliminarCajaNivel2.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/cajanivel2/eliminarCajaNivel2.php?id=" + id;
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
                    filtarCajaNivel2("", con, true);
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

    public void eliminarCajaNivel2Cascada(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "cajanivel2/eliminarCascadaCajaNivel2.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/cajanivel2/eliminarCascadaCajaNivel2.php?id=" + id;
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
                    filtarCajaNivel2("", con, true);
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
    public interface interfazCajaNivel2DAO {
        void setCajaNivel2(CajaNivel2 CajaNivel2);
        void setListaCajaNivel2(List<CajaNivel2> lista);
        void limpiarCajaNivel2();
    }
}
