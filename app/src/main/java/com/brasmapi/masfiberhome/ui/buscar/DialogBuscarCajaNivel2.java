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
import com.brasmapi.masfiberhome.dao.CajaNivel2DAO;
import com.brasmapi.masfiberhome.entidades.CajaNivel2;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterCajaNivel2;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogBuscarCajaNivel2 implements CajaNivel2DAO.interfazCajaNivel2DAO
{
    static Context context;
    static List<CajaNivel2> listaCajaNivel2;
    static Dialog dialogo;
    static AdapterCajaNivel2 adaptadorItemBuscarCajaNivel2;
    static RecyclerView recyclerViewBuscarCajaNivel2;
    static TextView lblSinCajaNivel2;
    TextInputLayout txtBuscarCajaNivel2;
    private static finalizoDialogBuscarCajaNivel2 interfaz;
    static CajaNivel2DAO CajaNivel2DAO;
    SwipeRefreshLayout refreshLayout;

    public DialogBuscarCajaNivel2(Context context1, finalizoDialogBuscarCajaNivel2 actividad) {
        interfaz = actividad;
        context = context1;
        dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);//false
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_buscar_caja_nivel2);

        refreshLayout=(SwipeRefreshLayout)dialogo.findViewById(R.id.refreshRecycler_DialogBuscarCajaNivel2);
        lblSinCajaNivel2 =(TextView)dialogo.findViewById(R.id.lblCajaNivel2Registrados);
        txtBuscarCajaNivel2=(TextInputLayout) dialogo.findViewById(R.id.txtImputBuscarCajaNivel2_DialogBuscarCajaNivel2);
        txtBuscarCajaNivel2.getEditText().addTextChangedListener(new TextWatcher() {
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
        CajaNivel2DAO =new CajaNivel2DAO(DialogBuscarCajaNivel2.this);
        recyclerViewBuscarCajaNivel2 = (RecyclerView) dialogo.findViewById(R.id.recyclerView_DialogBuscarCajaNivel2);
        recyclerViewBuscarCajaNivel2.setLayoutManager(new LinearLayoutManager(context));
        CajaNivel2DAO.filtarCajaNivel2(filtrar, context,false);
    }
    public static void cargar(){
        if(listaCajaNivel2==null){
            Toast.makeText(context, "No hay CajaNivel2", Toast.LENGTH_SHORT).show();
            listaCajaNivel2= new ArrayList<>();
            adaptadorItemBuscarCajaNivel2 = new AdapterCajaNivel2(listaCajaNivel2);
            recyclerViewBuscarCajaNivel2.setAdapter(adaptadorItemBuscarCajaNivel2);
            adaptadorItemBuscarCajaNivel2.notifyDataSetChanged();
        }else{
            adaptadorItemBuscarCajaNivel2 = new AdapterCajaNivel2(listaCajaNivel2);
            recyclerViewBuscarCajaNivel2.setAdapter(adaptadorItemBuscarCajaNivel2);
            adaptadorItemBuscarCajaNivel2.notifyDataSetChanged();
            adaptadorItemBuscarCajaNivel2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CajaNivel2 us = listaCajaNivel2.get(recyclerViewBuscarCajaNivel2.getChildAdapterPosition(v));
                    interfaz.CajaNivel2Selecionado(us);
                    dialogo.dismiss();
                }
            });
        }
        Procesos.cargandoDetener();
    }
    private void filtrar(String buscar){
        if (listaCajaNivel2!=null){
            List<CajaNivel2> aux2=new ArrayList<>();
            for (CajaNivel2 aux:listaCajaNivel2) {
                if(aux.getNombre_CajaNivel2().toLowerCase().contains(buscar.toLowerCase())){
                    aux2.add(aux);
                }
            }
            adaptadorItemBuscarCajaNivel2.setAdapterItemBuscarCajaNivel2(aux2);
            adaptadorItemBuscarCajaNivel2.notifyDataSetChanged();
        }

    }


    @Override
    public void setCajaNivel2(CajaNivel2 CajaNivel2) {

    }

    @Override
    public void setListaCajaNivel2(List<CajaNivel2> lista) {
        listaCajaNivel2=lista;
        cargar();
    }

    @Override
    public void limpiar() {

    }


    public interface finalizoDialogBuscarCajaNivel2 {
        void CajaNivel2Selecionado(CajaNivel2 CajaNivel2);
    }
}
