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
import com.brasmapi.masfiberhome.CrearProvinciaFragment;
import com.brasmapi.masfiberhome.ListaProvinciasFragment;
import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.ui.entidades.Provincia;
import com.brasmapi.masfiberhome.ui.entidades.Provincia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProvinciaDAO {
    List<Provincia> as;
    RequestQueue queue;
    Context context;
    private setProvincia interfaz;

    public ProvinciaDAO( setProvincia inte) {
        interfaz=inte;
    }

    public void filtarProvincia(String buscar, Context con, boolean isElim) {
        if (!isElim) {
            Procesos.cargandoIniciar(con);
        }
        String consulta = Procesos.url + "/provincia/filtrarProvincia.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = new JSONObject(response.get(i).toString());
                        as.add(new Provincia(object.getInt("id_provincia"), object.getString("nombre_provincia"), object.getInt("id_pais"),object.getString("nombre_pais"), object.getString("estado_provincia")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        e.printStackTrace();
                        e.printStackTrace();
                        e.printStackTrace();
                    }
                }
                ListaProvinciasFragment.listaProvincias = as;
                ListaProvinciasFragment.cargar();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                ListaProvinciasFragment.listaProvincias = null;
                ListaProvinciasFragment.cargar();
            }
        });
        queue.add(requerimiento);
    }

    public void buscarProvincia(String buscar, Context con) {
        Procesos.cargandoIniciar(con);
        String consulta = Procesos.url + "/provincia/buscarProvincia.php?filtrar=" + buscar;
        context = con;
        queue = Volley.newRequestQueue(context);
        JsonArrayRequest requerimiento = new JsonArrayRequest(Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                as = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(response.get(0).toString());
                    as.add(new Provincia(Integer.parseInt(object.getString("id_provincia")), object.getString("nombre_provincia"), object.getInt("id_pais"),object.getString("nombre_pais"), object.getString("estado_provincia")));
                    Procesos.cargandoDetener();
                    if (interfaz != null) {
                        interfaz.setProvincia(as.get(0));
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
                    interfaz.setProvincia(null);
                }
            }
        });
        queue.add(requerimiento);
    }

    public void crearProvincia(Provincia provincia, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/provincia/crearProvincia.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("nombre_provincia", provincia.getNombre());
                parametros.put("id_pais", provincia.getNombre());
                parametros.put("estado_provincia", provincia.getEstado());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/provincia/crearProvincia.php?nombre_provincia=" + provincia.getNombre() + "&id_pais=" + provincia.getPais() + "&estado_provincia=" + provincia.getEstado();
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
                    CrearProvinciaFragment.txtProvincia.getEditText().setText("");
                    CrearProvinciaFragment.txtPais.getEditText().setText("");
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

    public void editarProvincia(Provincia provincia, Context con, boolean esDesactivar) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/provincia/editarProvincia.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id_provincia", provincia.getId());
                parametros.put("nombre_provincia", provincia.getNombre());
                parametros.put("id_pais", provincia.getNombre());
                parametros.put("estado_provincia", provincia.getEstado());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/provincia/editarProvincia.php?nombre_provincia=" + provincia.getNombre() + "&id_pais=" + provincia.getPais() + "&estado_provincia=" + provincia.getEstado() + "&id_provincia=" + provincia.getId();
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
                        CrearProvinciaFragment.txtProvincia.getEditText().setText("");
                        CrearProvinciaFragment.txtPais.getEditText().setText("");
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

    public void eliminarProvincia(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/provincia/eliminarProvincia.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/provincia/eliminarProvincia.php?id=" + id;
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
                    filtarProvincia("", con, true);
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

    public void eliminarProvinciaCascada(int id, Context con) {
        Procesos.cargandoIniciar(con);
        context = con;
        String consulta;
        int metodo = 0;
        JSONObject parametros = null;
        queue = Volley.newRequestQueue(context);
        if (Procesos.isPost) {
            consulta = Procesos.url + "/provincia/eliminarCascadaProvincia.php";
            metodo = Request.Method.POST;
            parametros = new JSONObject();
            try {
                parametros.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            consulta = Procesos.url + "/provincia/eliminarCascadaProvincia.php?id=" + id;
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
                    filtarProvincia("", con, true);
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
    public interface setProvincia {
        void setProvincia(Provincia provincia);
    }
}
