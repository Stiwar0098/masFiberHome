package com.brasmapi.masfiberhome;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brasmapi.masfiberhome.ui.adaptadores.AdapterPais;
import com.brasmapi.masfiberhome.ui.entidades.Pais;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogBuscarPais
{
    final Context context;
    List<Pais> listaPaises;
    Dialog dialogo;
    AdapterPais adaptadorItemBuscarPais;
    RecyclerView recyclerViewBuscarPais;
    //DatabaseReference fireReference;
    TextView lblSinPaises;
    TextInputLayout txtBuscarPais;
    private finalizoDialogBuscarPais interfaz;

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

    public void mostrarDatos(String buscar) {
        Procesos.cargandoIniciar(dialogo.getContext());

        // crear lista de carview dentro del recycleview
        recyclerViewBuscarPais = (RecyclerView) dialogo.findViewById(R.id.recyclerView_DialogBuscarPais);
        recyclerViewBuscarPais.setLayoutManager(new LinearLayoutManager(context));

        adaptadorItemBuscarPais = new AdapterPais(listarPaises());
        recyclerViewBuscarPais.setAdapter(adaptadorItemBuscarPais);
        adaptadorItemBuscarPais.notifyDataSetChanged();
        lblSinPaises.setVisibility(View.GONE);
        Procesos.cargandoDetener();
        adaptadorItemBuscarPais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pais us = listaPaises.get(recyclerViewBuscarPais.getChildAdapterPosition(v));
                interfaz.PaisSelecionado(us);
                dialogo.dismiss();
            }
        });
    }
    private void filtrar(String buscar){
        List<Pais> aux2=new ArrayList<>();
        for (Pais aux:listaPaises) {
            if(aux.getNombre().toLowerCase().contains(buscar.toLowerCase())){
                aux2.add(aux);
            }
        }
        adaptadorItemBuscarPais.setAdapterItemBuscarPais(aux2);
        adaptadorItemBuscarPais.notifyDataSetChanged();
    }

    public List<Pais> listarPaises(){
        listaPaises = new ArrayList<>();
        //listaPaises.add(new Pais("Ecuador"));
        //listaPaises.add(new Pais("Peru"));
        return listaPaises;
    }

    public interface finalizoDialogBuscarPais {
        void PaisSelecionado(Pais pais);
    }
}
