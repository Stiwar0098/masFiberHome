package com.brasmapi.masfiberhome;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brasmapi.masfiberhome.ui.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class Procesos extends AppCompatActivity {
    public static String id;
    public static final String url="https://app.masfiberhome.com/webservicesbrasmapi/api";
    public static ProgressDialog cargando;
    public static boolean isPost =false;
    public static Usuario user=null;
    static int i = 0;
    //private static DatabaseReference fireReference;


    public Procesos() {

    }

    public static void cargandoIniciar(Context context) {
        //inicializamos progres dialog
        cargando = new ProgressDialog(context);
        //mostramos progres
        cargando.show();
        //no se puede salir
        cargando.setCancelable(false);
        // enviamos el contenido del dilogo
        cargando.setContentView(R.layout.dialog_activity_cargando);
        //transparente
        cargando.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public static void cargandoDetener() {
        cargando.dismiss();
        cargando.dismiss();
    }


}
