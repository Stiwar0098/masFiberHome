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
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterPais;
import com.brasmapi.masfiberhome.dao.PaisesDAO;
import com.brasmapi.masfiberhome.entidades.Pais;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogBuscarPais implements PaisesDAO.interfazPaisDao
{
    static Context context;
    static List<Pais> listaPaises,listaPaisesaux;
    static Dialog dialogo;
    static AdapterPais adaptadorItemBuscarPais;
    static RecyclerView recyclerViewBuscarPais;
    static TextView lblSinPaises;
    TextInputLayout txtBuscarPais;
    private static finalizoDialogBuscarPais interfaz;
    static PaisesDAO paisesDAO;

    public DialogBuscarPais(Context context1, finalizoDialogBuscarPais actividad) {
        interfaz = actividad;
        context = context1;
        dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);//false
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_buscar_pais);

        lblSinPaises =(TextView)dialogo.findViewById(R.id.lblPaisesRegistrados);
        txtBuscarPais=(TextInputLayout) dialogo.findViewById(R.id.txtImputBuscarPais_DialogBuscarPais);
        txtBuscarPais.getEditText().addTextChangedListener(new TextWatcher() {
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
        paisesDAO =new PaisesDAO(DialogBuscarPais.this);
        recyclerViewBuscarPais = (RecyclerView) dialogo.findViewById(R.id.recyclerView_DialogBuscarPais);
        recyclerViewBuscarPais.setLayoutManager(new LinearLayoutManager(context));
        paisesDAO.filtarPaises(filtrar, context,false);
    }
    public static void cargar(){
        if(listaPaises==null){
            Toast.makeText(context, "No hay paises", Toast.LENGTH_SHORT).show();
            lblSinPaises.setVisibility(View.VISIBLE);
        }else {
            listaPaisesaux=null;
            lblSinPaises.setVisibility(View.GONE);
            adaptadorItemBuscarPais = new AdapterPais(listaPaises);
            recyclerViewBuscarPais.setAdapter(adaptadorItemBuscarPais);
            adaptadorItemBuscarPais.notifyDataSetChanged();
            adaptadorItemBuscarPais.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pais us;
                    if (listaPaisesaux==null){
                        us = listaPaises.get(recyclerViewBuscarPais.getChildAdapterPosition(v));
                    }else{
                        us = listaPaisesaux.get(recyclerViewBuscarPais.getChildAdapterPosition(v));
                    }
                    interfaz.PaisSelecionado(us);
                    dialogo.dismiss();
                }
            });
        }
        Procesos.cargandoDetener();
    }
    private void filtrar(String buscar){
        listaPaisesaux=null;
        if (listaPaises!=null){
            listaPaisesaux=new ArrayList<>();
            for (Pais aux:listaPaises) {
                if(aux.getNombre().toLowerCase().contains(buscar.toLowerCase())){
                    listaPaisesaux.add(aux);
                }
            }
            adaptadorItemBuscarPais.setAdapterItemBuscarPais(listaPaisesaux);
            adaptadorItemBuscarPais.notifyDataSetChanged();
        }

    }

    @Override
    public void setPais(Pais pais) {
    }

    @Override
    public void setListaPais(List<Pais> pais) {
        listaPaises=pais;
        cargar();
    }


    public interface finalizoDialogBuscarPais {
        void PaisSelecionado(Pais pais);
    }
}
