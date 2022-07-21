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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.ModeloOntDAO;
import com.brasmapi.masfiberhome.entidades.ModeloOnt;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterModeloOnt;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogBuscarModeloOnt implements ModeloOntDAO.interfazModeloOntDAO
{
    static Context context;
    static List<ModeloOnt> listaModeloOnt,listaModeloOntaux;
    static Dialog dialogo;
    static AdapterModeloOnt adaptadorItemBuscarModeloOnt;
    static RecyclerView recyclerViewBuscarModeloOnt;
    static TextView lblSinModeloOntes;
    TextInputLayout txtBuscarModeloOnt;
    private static finalizoDialogBuscarModeloOnt interfaz;
    static ModeloOntDAO ModeloOntDAO;
    SwipeRefreshLayout refreshLayout;
    public DialogBuscarModeloOnt(Context context1, finalizoDialogBuscarModeloOnt actividad) {
        interfaz = actividad;
        context = context1;
        dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);//false
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_buscar_modeloont);

        refreshLayout=(SwipeRefreshLayout)dialogo.findViewById(R.id.refreshRecycler_DialogBuscarModeloOnt);
        lblSinModeloOntes =(TextView)dialogo.findViewById(R.id.lblModelosOntRegistrados);
        txtBuscarModeloOnt=(TextInputLayout) dialogo.findViewById(R.id.txtImputBuscarModeloOnt_DialogBuscarModeloOnt);
        txtBuscarModeloOnt.getEditText().addTextChangedListener(new TextWatcher() {
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
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mostrarDatos("");
                refreshLayout.setRefreshing(false);
            }
        });
        dialogo.show();
        mostrarDatos("");
    }

    public void mostrarDatos(String filtrar) {
        // crear lista de carview dentro del recycleview
        ModeloOntDAO =new ModeloOntDAO(DialogBuscarModeloOnt.this);
        recyclerViewBuscarModeloOnt = (RecyclerView) dialogo.findViewById(R.id.recyclerView_DialogBuscarModeloOnt);
        recyclerViewBuscarModeloOnt.setLayoutManager(new LinearLayoutManager(context));
        ModeloOntDAO.filtarModeloOnt(filtrar, context,false);
    }
    public static void cargar(){
        if(listaModeloOnt ==null){
            Toast.makeText(context, "No hay ModeloOnt", Toast.LENGTH_SHORT).show();
            listaModeloOnt = new ArrayList<>();
            adaptadorItemBuscarModeloOnt = new AdapterModeloOnt(listaModeloOnt);
            recyclerViewBuscarModeloOnt.setAdapter(adaptadorItemBuscarModeloOnt);
            adaptadorItemBuscarModeloOnt.notifyDataSetChanged();
        }else{
            listaModeloOntaux=null;
            adaptadorItemBuscarModeloOnt = new AdapterModeloOnt(listaModeloOnt);
            recyclerViewBuscarModeloOnt.setAdapter(adaptadorItemBuscarModeloOnt);
            adaptadorItemBuscarModeloOnt.notifyDataSetChanged();
            adaptadorItemBuscarModeloOnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModeloOnt us;
                    if (listaModeloOntaux==null){
                        us = listaModeloOnt.get(recyclerViewBuscarModeloOnt.getChildAdapterPosition(v));
                    }else{
                        us = listaModeloOntaux.get(recyclerViewBuscarModeloOnt.getChildAdapterPosition(v));
                    }
                    interfaz.ModeloOntSelecionado(us);
                    dialogo.dismiss();
                }
            });
        }
        Procesos.cargandoDetener();
    }
    private void filtrar(String buscar){
        listaModeloOntaux=null;
        if (listaModeloOnt !=null){
            listaModeloOntaux=new ArrayList<>();
            for (ModeloOnt aux: listaModeloOnt) {
                if(aux.getNombre_modeloOnt().toLowerCase().contains(buscar.toLowerCase())){
                    listaModeloOntaux.add(aux);
                }
            }
            adaptadorItemBuscarModeloOnt.setAdapterItemBuscarModeloOnt(listaModeloOntaux);
            adaptadorItemBuscarModeloOnt.notifyDataSetChanged();
        }

    }


    @Override
    public void setModeloOnt(ModeloOnt ModeloOnt) {

    }

    @Override
    public void setListaModeloOnt(List<ModeloOnt> lista) {
        listaModeloOnt =lista;
        cargar();
    }

    @Override
    public void limpiarModeloOnt() {

    }


    public interface finalizoDialogBuscarModeloOnt {
        void ModeloOntSelecionado(ModeloOnt ModeloOnt);
    }
}
