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
import com.brasmapi.masfiberhome.entidades.Vlan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VlanDAO {
    List<Vlan> as;
    RequestQueue queue;
    Context context;
    private interfazVlanDAO interfaz;

    public VlanDAO(interfazVlanDAO inte) {
        interfaz=inte;
    }

    public void filtarVlan(String buscar, Context con, boolean isElim) {
        if (!isElim) {
            Procesos.cargandoIniciar(con);
        }
        String consulta = Procesos.url + "/vlan/filtrarVlan.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = new JSONObject(response.get(i).toString());
                        as.add(new Vlan(object.getInt("id_vlan"),object.getInt("numerovlan_vlan"), object.getString("nombre_vlan"), object.getInt("numeroolt_vlan"),object.getInt("tarjetaolt_vlan"), object.getInt("puertoolt_vlan"), object.getString("ipinicio_vlan"), object.getString("ipfin_vlan"), object.getString("mascara_vlan"), object.getString("gateway_vlan"), object.getString("estado_vlan")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(interfaz!=null){
                    interfaz.setListaVlan(as);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                as=null;
                if(interfaz!=null){
                    interfaz.setListaVlan(as);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void buscarVlan(String buscar, Context con) {
        String consulta = Procesos.url + "/vlan/buscarVlan.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    as.add(new Vlan(object.getInt("id_vlan"),object.getInt("numerovlan_vlan"), object.getString("nombre_vlan"), object.getInt("numeroolt_vlan"),object.getInt("tarjetaolt_vlan"), object.getInt("puertoolt_vlan"), object.getString("ipinicio_vlan"), object.getString("ipfin_vlan"), object.getString("mascara_vlan"), object.getString("gateway_vlan"), object.getString("estado_vlan")));
                    if (interfaz != null) {
                        interfaz.setVlan(as.get(0));
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
                    interfaz.setVlan(null);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void crearVlan(Vlan vlan, Context con, int ini, int buckle2) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/vlan/crearVlan.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("numerovlan_vlan", vlan.getNumeroVlan());
                parametros.put("nombre_vlan", vlan.getNombreVlan());
                parametros.put("numeroolt_vlan", vlan.getNumeroOlt());
                parametros.put("tarjetaolt_vlan", vlan.getTarjetaOlt());
                parametros.put("puertoolt_vlan", vlan.getPuertoOlt());
                parametros.put("ipinicio_vlan", vlan.getIpInicio());
                parametros.put("ipfin_vlan", vlan.getIpFin());
                parametros.put("mascara_vlan", vlan.getMascara());
                parametros.put("gateway_vlan", vlan.getGateway());
                parametros.put("estado_vlan", vlan.getEstado());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/vlan/crearVlan.php?nombre_vlan=" + vlan.getNombreVlan()
                    + "&numerovlan_vlan=" + vlan.getNumeroVlan()
                    + "&numeroolt_vlan=" + vlan.getNumeroOlt()
                    + "&tarjetaolt_vlan=" + vlan.getTarjetaOlt()
                    + "&puertoolt_vlan=" + vlan.getPuertoOlt()
                    + "&ipinicio_vlan=" + vlan.getIpInicio()
                    + "&ipfin_vlan=" + vlan.getIpFin()
                    + "&mascara_vlan=" + vlan.getMascara()
                    + "&gateway_vlan=" + vlan.getGateway()
                    + "&estado_vlan=" + vlan.getEstado()
                    + "&ip_rangodireccionesip=" +ini
                    + "&estado_rangodireccionesip=desactivo"
                    + "&buckle2=" + buckle2;
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
                        interfaz.limpiarVlan();
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

    public void editarVlan(Vlan vlan, Context con, boolean esDesactivar) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/vlan/editarVlan.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_vlan", vlan.getId());
                parametros.put("numerovlan_vlan", vlan.getNombreVlan());
                parametros.put("nombre_vlan", vlan.getNombreVlan());
                parametros.put("numeroolt_vlan", vlan.getNumeroOlt());
                parametros.put("tarjetaolt_vlan", vlan.getTarjetaOlt());
                parametros.put("puertoolt_vlan", vlan.getPuertoOlt());
                parametros.put("ipinicio_vlan", vlan.getIpInicio());
                parametros.put("ipfin_vlan", vlan.getIpFin());
                parametros.put("mascara_vlan", vlan.getMascara());
                parametros.put("gateway_vlan", vlan.getGateway());
                parametros.put("estado_vlan", vlan.getEstado());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/vlan/editarVlan.php?nombre_vlan=" + vlan.getNombreVlan()
                    + "&numerovlan_vlan=" + vlan.getNumeroOlt()
                    + "&numeroolt_vlan=" + vlan.getNumeroOlt()
                    + "&tarjetaolt_vlan=" + vlan.getTarjetaOlt()
                    + "&puertoolt_vlan=" + vlan.getPuertoOlt()
                    + "&ipinicio_vlan=" + vlan.getIpInicio()
                    + "&ipfin_vlan=" + vlan.getIpFin()
                    + "&mascara_vlan=" + vlan.getMascara()
                    + "&gateway_vlan=" + vlan.getGateway()
                    + "&estado_vlan=" + vlan.getEstado()
                    + "&id_vlan=" + vlan.getId();
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
                            interfaz.limpiarVlan();
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

    public void eliminarVlan(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/vlan/eliminarVlan.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/vlan/eliminarVlan.php?id=" + id;
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
                    filtarVlan("", con, true);
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

    public void eliminarVlanCascada(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "vlan/eliminarCascadaVlan.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/vlan/eliminarCascadaVlan.php?id=" + id;
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
                    filtarVlan("", con, true);
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
    public interface interfazVlanDAO {
        void setVlan(Vlan Vlan);
        void setListaVlan(List<Vlan> lista);
        void limpiarVlan();
    }
}
