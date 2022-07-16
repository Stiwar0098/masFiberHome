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
import com.brasmapi.masfiberhome.entidades.RangoHilosCaja2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RangoHilosCaja2DAO {
    List<RangoHilosCaja2> as;
    RequestQueue queue;
    Context context;
    private interfazRangoCaja2DAO interfaz;

    public RangoHilosCaja2DAO(RangoHilosCaja2DAO.interfazRangoCaja2DAO inte) {
        interfaz=inte;
    }
    public void verificarHiloCaja2Manual(int id_cajanivel2, int numero_RangoHilosCaja2 , Context con) {
        Procesos.cargandoIniciar(con);
        String consulta = Procesos.url + "/RangoHilosCaja2/verificarHiloManual.php?id_cajanivel2=" + id_cajanivel2+"&numero_rangohiloscaja2="+numero_RangoHilosCaja2;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    if (!response.get(0).toString().contains("error")){
                        as.add(new RangoHilosCaja2(object.getInt("id_rangohiloscaja2"),null, null, object.getInt("numero_rangohiloscaja2"), ""));
                        if (interfaz != null) {
                            interfaz.validarHiloCaja2Manual(as.get(0));
                        }
                    }else{
                        interfaz.validarHiloCaja2Manual(null);
                    }
                } catch (JSONException e) {
                    interfaz.validarHiloCaja2Manual(null);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                if(interfaz!=null){
                    interfaz.validarHiloCaja2Manual(null);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void obtenerHiloCaja2Automatico(int id_cajanivel2, Context con) {
        Procesos.cargandoIniciar(con);
        String consulta = Procesos.url + "/RangoHilosCaja2/obtenerHiloAutomatico.php?id_cajanivel2=" + id_cajanivel2;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    if (!response.get(0).toString().contains("error")) {
                        as.add(new RangoHilosCaja2(object.getInt("id_rangohiloscaja2"), null, null, object.getInt("numero_rangohiloscaja2"), ""));
                        if (interfaz != null) {
                            interfaz.hiloCaja2Automatico(as.get(0));
                        }
                    }else{
                        interfaz.hiloCaja2Automatico(null);
                    }
                } catch (JSONException e) {
                    interfaz.hiloCaja2Automatico(null);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con el servidor obtenerHiloCaja2Automatico", Toast.LENGTH_SHORT).show();
                if (interfaz != null) {
                    interfaz.hiloCaja2Automatico(null);// no hay hilos disponibles en esa caja
                }
            }
        });
        queue.add(requerimiento);
    }

    public void obtenerHiloCaja2Anterior(int id_cajanivel2, int id_cliente, Context con) {
        Procesos.cargandoIniciar(con);
        String consulta = Procesos.url + "/RangoHilosCaja2/obtenerHiloAnterior.php?id_cajanivel2=" + id_cajanivel2+"&id_cliente="+id_cliente;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    as.add(new RangoHilosCaja2(object.getInt("id_rangohiloscaja2"),null, null, object.getInt("numero_rangohiloscaja2"), ""));
                    if (interfaz != null) {
                        interfaz.hiloCaja2Anterior(as.get(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Problema con el servidor obtenerHiloCaja2Anterior", Toast.LENGTH_SHORT).show();
                if (interfaz != null) {
                    interfaz.hiloCaja2Anterior(null);// no hay hilos disponibles en esa caja
                }
            }
        });
        queue.add(requerimiento);
    }

    public void editarRangoHilosCaja2(RangoHilosCaja2 RangoHilosCaja2,String usuarioCliente, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/RangoHilosCaja2/editar.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_rangohiloscaja2", RangoHilosCaja2.getId_rangocaja2());
                parametros.put("estado_rangohiloscaja2", RangoHilosCaja2.getEstado());
                parametros.put("usuario_cliente", usuarioCliente);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/RangoHilosCaja2/editar.php?"
                    +"id_rangohiloscaja2=" +  RangoHilosCaja2.getId_rangocaja2()
                    +"&estado_rangohiloscaja2=" +  RangoHilosCaja2.getEstado()
                    +"&usuario_cliente=" +  usuarioCliente;
            metodo = Request.Method.GET;
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("respuesta").toString().equals("ok")) {
                        //Toast.makeText(context, "Los datos se modificaron correctamente rangohilocaja2", Toast.LENGTH_SHORT).show();
                    } else {
                        Procesos.cargandoDetener();
                        Toast.makeText(context, response.get("respuesta").toString(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Problema con el servidor rangohiloscaja2-editarrango1", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }
    public void editarRangoHilosCaja2Anterior(int idRangoHilosCaja2, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/RangoHilosCaja2/editarHiloAnterior.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_rangohiloscaja2", idRangoHilosCaja2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/RangoHilosCaja2/editarHiloAnterior.php?"
                    +"id_rangohiloscaja2=" +  idRangoHilosCaja2;
            metodo = Request.Method.GET;
        }
        JsonObjectRequest requerimiento = new JsonObjectRequest(metodo, consulta, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("respuesta").toString().equals("ok")) {
                        //Toast.makeText(context, "Los datos se modificaron correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Procesos.cargandoDetener();
                        Toast.makeText(context, response.get("respuesta").toString(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Problema con el servidor editarRangoHilosCaja2Anterior", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }

    public void eliminarRangoHilosCaja2(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/RangoHilosCaja2/eliminarRangoHilosCaja2.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/RangoHilosCaja2/eliminarRangoHilosCaja2.php?id=" + id;
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
                Toast.makeText(context, "Problema con el servidor eliminarRangoHilosCaja2", Toast.LENGTH_SHORT).show();
                Procesos.cargandoDetener();
            }
        });
        queue.add(requerimiento);
    }

    public interface interfazRangoCaja2DAO {
        void hiloCaja2Automatico(RangoHilosCaja2 rangoHilosCaja2);
        void validarHiloCaja2Manual(RangoHilosCaja2 rangoHilosCaja2);
        void hiloCaja2Anterior(RangoHilosCaja2 rangoHilosCaja2);
    }
}
