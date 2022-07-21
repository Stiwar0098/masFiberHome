package com.brasmapi.masfiberhome.ui.buscar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterProvincia;
import com.brasmapi.masfiberhome.dao.ProvinciaDAO;
import com.brasmapi.masfiberhome.entidades.Provincia;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogBuscarProvincia implements ProvinciaDAO.setProvincia
{
    static Context context;
    static List<Provincia> listaProvincia,listaProvinciaaux;
    static Dialog dialogo;
    static AdapterProvincia adaptadorItemBuscarProvincia;
    static RecyclerView recyclerViewBuscarProvincia;
    static TextView lblSinProvincia;
    TextInputLayout txtBuscarProvincia;
    private static finalizoDialogBuscarProvincia interfaz;
    static ProvinciaDAO ProvinciaDAO;

    public DialogBuscarProvincia(Context context1, finalizoDialogBuscarProvincia actividad) {
        interfaz = actividad;
        context = context1;
        dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);//false
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_buscar_provincia);

        lblSinProvincia =(TextView)dialogo.findViewById(R.id.lblProvinciasRegistrados);
        txtBuscarProvincia=(TextInputLayout) dialogo.findViewById(R.id.txtImputBuscarProvincia_DialogBuscarProvincia);
        txtBuscarProvincia.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")){
                    filtrar(s.toString());
                }else{
                    filtrar("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialogo.show();
        mostrarDatos("");
    }

    public void mostrarDatos(String filtrar) {
        // crear lista de carview dentro del recycleview
        ProvinciaDAO =new ProvinciaDAO(DialogBuscarProvincia.this);
        recyclerViewBuscarProvincia = (RecyclerView) dialogo.findViewById(R.id.recyclerView_DialogBuscarProvincia);
        recyclerViewBuscarProvincia.setLayoutManager(new LinearLayoutManager(context));
        ProvinciaDAO.filtarProvincia(filtrar, context,false);
    }
    public static void cargar(){
        if(listaProvincia==null){
            Toast.makeText(context, "No hay Provincia", Toast.LENGTH_SHORT).show();
            lblSinProvincia.setVisibility(View.VISIBLE);
        }else {
            listaProvinciaaux=null;
            lblSinProvincia.setVisibility(View.GONE);
            adaptadorItemBuscarProvincia = new AdapterProvincia(listaProvincia);
            recyclerViewBuscarProvincia.setAdapter(adaptadorItemBuscarProvincia);
            adaptadorItemBuscarProvincia.notifyDataSetChanged();
            adaptadorItemBuscarProvincia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Provincia us;
                    if (listaProvinciaaux==null){
                        us = listaProvincia.get(recyclerViewBuscarProvincia.getChildAdapterPosition(v));
                    }else{
                        us = listaProvinciaaux.get(recyclerViewBuscarProvincia.getChildAdapterPosition(v));
                    }

                    interfaz.ProvinciaSelecionado(us);
                    dialogo.dismiss();
                }
            });
        }
        Procesos.cargandoDetener();
    }
    private void filtrar(String buscar){
        listaProvinciaaux=null;
        if (listaProvincia!=null){
            listaProvinciaaux=new ArrayList<>();
            for (Provincia aux:listaProvincia) {
                if(aux.getNombre().toLowerCase().contains(buscar.toLowerCase())){
                    listaProvinciaaux.add(aux);
                }
            }
            adaptadorItemBuscarProvincia.setAdapterItemBuscarProvincia(listaProvinciaaux);
            adaptadorItemBuscarProvincia.notifyDataSetChanged();
        }

    }


    @Override
    public void setProvincia(Provincia provincia) {

    }

    @Override
    public void setListaProvincia(List<Provincia> lista) {
        listaProvincia=lista;
        cargar();
    }


    public interface finalizoDialogBuscarProvincia {
        void ProvinciaSelecionado(Provincia Provincia);
    }
}
