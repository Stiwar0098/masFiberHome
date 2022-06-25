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
import com.brasmapi.masfiberhome.entidades.RangoDireccionIp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RangoDireccionIpDAO {
    List<RangoDireccionIp> as;
    RequestQueue queue;
    Context context;
    private interfazRangoDireccionIp interfaz;

    public RangoDireccionIpDAO(RangoDireccionIpDAO.interfazRangoDireccionIp inte) {
        interfaz=inte;
    }
    public void verificarDireccionIpManual(int id_vlan, int ip_RangoDireccionIp , Context con) {
        String consulta = Procesos.url + "/RangoDireccionIp/verificarHiloManual.php?id_vlan=" + id_vlan+"&ip_rangodireccionesip="+ip_RangoDireccionIp;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    if (!response.get(0).toString().contains("error")){
                        as.add(new RangoDireccionIp(object.getInt("id_rangodireccionesip"),null, null, object.getInt("ip_rangodireccionesip"), ""));
                        if (interfaz != null) {
                            interfaz.validarDireccionIpManual(as.get(0));
                        }
                    }else{
                        interfaz.validarDireccionIpManual(null);
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
                if(interfaz!=null){
                    interfaz.validarDireccionIpManual(null);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void obtenerDireccionIpAutomatico(int id_vlan, Context con) {
        String consulta = Procesos.url + "/RangoDireccionIp/obtenerHiloAutomatico.php?id_vlan=" + id_vlan;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    as.add(new RangoDireccionIp(object.getInt("id_rangodireccionesip"),null, null, object.getInt("ip_rangodireccionesip"), ""));
                    if (interfaz != null) {
                        interfaz.direccionIptAutomatico(as.get(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con el servidor rangodireccionipdao-ipautomatica", Toast.LENGTH_SHORT).show();
                if (interfaz != null) {
                    interfaz.direccionIptAutomatico(null);// no hay hilos disponibles en esa caja
                }
            }
        });
        queue.add(requerimiento);
    }

    public void obtenerDireccionIpAnterior(int id_vlan, int id_cliente, Context con) {
        String consulta = Procesos.url + "/RangoDireccionIp/obtenerHiloAnterior.php?id_vlan=" + id_vlan+"&id_cliente="+id_cliente;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    as.add(new RangoDireccionIp(object.getInt("id_rangodireccionesip"),null, null, object.getInt("ip_rangodireccionesip"), ""));
                    if (interfaz != null) {
                        interfaz.direccionIpAnterior(as.get(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con el servidor", Toast.LENGTH_SHORT).show();
                if (interfaz != null) {
                    interfaz.direccionIpAnterior(null);// no hay hilos disponibles en esa caja
                }
            }
        });
        queue.add(requerimiento);
    }

    public void editarRangoDireccionIp(RangoDireccionIp RangoDireccionIp, String usuario, Context con) {
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/RangoDireccionIp/editar.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_rangodireccionesip", RangoDireccionIp.getid_rangodireccionesip());
                parametros.put("estado_rangodireccionesip", RangoDireccionIp.getEstado());
                parametros.put("usuario_cliente", usuario);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/RangoDireccionIp/editar.php?"
                    +"id_rangodireccionesip=" +  RangoDireccionIp.getid_rangodireccionesip()
                    +"&estado_rangodireccionesip=" +  RangoDireccionIp.getEstado()
                    +"&usuario_cliente=" +  usuario;
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Problema con el servidor rangodireccionipdao-editarRangodireccionip", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(requerimiento);
    }
    public void editarRangoDireccionIpAnterior(int idRangoDireccionIp, Context con) {
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/RangoDireccionIp/editarHiloAnterior.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_rangodireccionesip", idRangoDireccionIp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/RangoDireccionIp/editarHiloAnterior.php?"
                    +"id_rangodireccionesip=" +  idRangoDireccionIp;
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
                Toast.makeText(context, "Problema con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(requerimiento);
    }

    public void eliminarDireccionIp(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/RangoDireccionIp/eliminarRangoDireccionIp.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/RangoDireccionIp/eliminarRangoDireccionIp.php?id=" + id;
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
                Toast.makeText(context, "Problema con el servidor", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }

    public interface interfazRangoDireccionIp {
        void direccionIptAutomatico(RangoDireccionIp rangoDireccionIp);
        void validarDireccionIpManual(RangoDireccionIp rangoDireccionIp);
        void direccionIpAnterior(RangoDireccionIp rangoDireccionIp);
    }
}
