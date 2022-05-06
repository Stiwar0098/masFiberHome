package com.brasmapi.masfiberhome;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;


import com.brasmapi.masfiberhome.ui.entidades.Usuario;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Procesos extends AppCompatActivity {
    public static String id;
    static ProgressDialog cargando;
    static Usuario user;
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
    }

    public static String controlarDecimales(double numDecimal) {
        DecimalFormat df = new DecimalFormat("#.00");
        String deci = df.format(numDecimal);
        String str = String.valueOf(numDecimal);
        String[] vector = deci.split(".");
        String decimales;
        try {
            decimales = str.substring(str.indexOf('.') + 1);
        } catch (Exception e) {
            decimales = str.substring(str.indexOf(',') + 1);
        }
        if (decimales.length() > 2) {
            return df.format(numDecimal);
        }
        return numDecimal + "";
    }

    private static boolean esNumero(String numero) {
        int num = 0;
        try {
            num = Integer.parseInt(numero);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String controlarEnteros(String numDecimal) {
        String str = numDecimal;
        String decimales;
        try {
            decimales = str.substring(str.indexOf('.') + 1);
        } catch (Exception e) {
            decimales = str.substring(str.indexOf(',') + 1);
        }
        if (decimales.equals("0")) {
            return cogerSoloEnteros(Double.parseDouble(numDecimal)) + "";
        } else {
            return numDecimal;
        }
    }

    public static int cogerSoloEnteros(double numero) {
        String str = String.valueOf(numero);
        int intNumber = Integer.parseInt(str.substring(0, str.indexOf('.')));
        return intNumber;
    }

    public static Date sumarRestarDiasAFecha(int dia, int mes, int ano, int dias) {
        TimeZone timezone = TimeZone.getDefault();
        Calendar calendar = new GregorianCalendar(timezone);
        calendar.set(ano, mes, dia);
        if (dias == 0) {
            return calendar.getTime();
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, dias);
        }
        //  calendar.get(Calendar.YEAR);
        return calendar.getTime();
    }

    public static String diaSemana(Date fecha) {
        int dia, mes, ano;
        dia = fecha.getDate();
        mes = fecha.getMonth();
        ano = fecha.getYear();
        String letraD = "";
        TimeZone timezone = TimeZone.getDefault();
        Calendar calendar = new GregorianCalendar(timezone);
        calendar.set(ano, mes, dia);
        int nD = calendar.get(Calendar.DAY_OF_WEEK);
        switch (nD) {
            case 1:
                letraD = "Domingo";
                break;
            case 2:
                letraD = "Lunes";
                break;
            case 3:
                letraD = "Martes";
                break;
            case 4:
                letraD = "Miercoles";
                break;
            case 5:
                letraD = "Jueves";
                break;
            case 6:
                letraD = "Viernes";
                break;
            case 7:
                letraD = "Sábado";
                break;
        }
        return letraD;
    }
}
