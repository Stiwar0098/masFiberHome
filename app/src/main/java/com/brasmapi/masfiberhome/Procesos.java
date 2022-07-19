package com.brasmapi.masfiberhome;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;


import com.brasmapi.masfiberhome.entidades.Usuario;
import com.brasmapi.masfiberhome.entidades.Vlan;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Procesos extends AppCompatActivity {
    public static String id;
    public static final String url = "https://app.masfiberhome.com/webservicesbrasmapi/api";
    public static ProgressDialog cargando=null;
    public static boolean isPost = false;
    public static Usuario user = null;
    public static boolean yaSeValidoUsuario=false;
    static int i = 0;
    //private static DatabaseReference fireReference;


    public Procesos() {

    }
    public static String obtenerTxtEnString(TextInputLayout txt){
        return txt.getEditText().getText().toString().trim();
    }
    public static Integer obtenerTxtEnEntero(TextInputLayout txt){
        String aux=txt.getEditText().getText().toString();
        if (aux!=null || !aux.equals("")){
            return Integer.parseInt(txt.getEditText().getText().toString().trim());
        }else{
            return -1;
        }
    }
    public static boolean validarTxtEstaLleno(TextInputLayout txt){
        String aux=txt.getEditText().getText().toString();
        if (aux==null || aux.equals("")){
            return false;
        }else{
            return true;
        }
    }
    public static void cargandoIniciar(Context context) {
        if (cargando==null || !cargando.isShowing()){
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
    }

    public static void cargandoDetener() {
        cargando.dismiss();
        cargando.dismiss();
    }

    public static String obtenerFechaActualConHora(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }
    public static boolean validarDireccionIp(String ip) {

        // Regex for digit from 0 to 255.
        String zeroTo255
                = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";

        // Regex for a digit from 0 to 255 and
        // followed by a dot, repeat 4 times.
        // this is the regex to validate an IP address.
        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        ip = ip.trim();
        // If the IP address is empty
        // return false
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        if ((ip.length() < 6) & (ip.length() > 15)) {
            return false;
        }
        // Pattern class contains matcher() method
        // to find matching between given IP address
        // and regular expression.
        Matcher m = p.matcher(ip);

        // Return if the IP address
        // matched the ReGex
        return m.matches();
    }

    public static void cerrarTeclado(FragmentActivity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static int[] descomponerDireccionIp(String ip) {
        String[] ar = ip.split("\\.");
        int[] array = new int[4];
        array[0] = Integer.parseInt(ar[0]);
        array[1] = Integer.parseInt(ar[1]);
        array[2] = Integer.parseInt(ar[2]);
        array[3] = Integer.parseInt(ar[3]);
        return array;
    }

    public static Intent comoLlegar(FragmentActivity getActiviti,String latitud,String longitud){
        String latitudlongitud="";
        Uri gmmIntentUri;
        Intent mapIntent;
        latitudlongitud = latitud+", "+longitud;
        gmmIntentUri = Uri.parse("google.navigation:q=" + latitudlongitud);
        mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActiviti.getPackageManager()) != null) {
            return mapIntent;
        }
        return mapIntent;
    }


    public interface LatitudLongitud {
        void setLatitudLongitud(String latitud, String longitud);
    }
    public static LatitudLongitud interfaceLatLon;
    public static FusedLocationProviderClient mFusedLocationClient;
    static LocationRequest locationRequest;
    static LocationCallback locationCallback;

    @SuppressLint("MissingPermission")
    public static void obtenerLatitudLongitud(Context context,LatitudLongitud inte,ContentResolver getContentResolver){
        interfaceLatLon=inte;
        if(!gpsEncendido(getContentResolver)){
            Toast.makeText(context, "Active el GPS para poder obtener lat y long en automatico", Toast.LENGTH_LONG).show();
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5 * 1000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {

                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        interfaceLatLon.setLatitudLongitud(location.getLatitude()+"",location.getLongitude()+"");
                    }
                }
            }
        };
        mFusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
    }
    public static void detenerObtenerLatitudLongitud(){
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(locationCallback);

        }
    }
    public static boolean gpsEncendido(ContentResolver getContentResolver) {
        String provider = Settings.Secure.getString(getContentResolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        System.out.println("Provider contains=> " + provider);
        if (provider.contains("gps") || provider.contains("network")){
            return true;
        }
        return false;
    }
    public static void copiarEnElPortapapeles(Context context, String text, FragmentActivity getActiviti){
        ClipboardManager clipboard = (ClipboardManager) getActiviti.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text",  text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Copiado en el portapapeles", Toast.LENGTH_SHORT).show();
    }

}
