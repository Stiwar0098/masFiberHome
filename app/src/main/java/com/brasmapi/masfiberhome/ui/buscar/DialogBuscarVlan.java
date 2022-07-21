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
import com.brasmapi.masfiberhome.dao.VlanDAO;
import com.brasmapi.masfiberhome.entidades.Vlan;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterVlan;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogBuscarVlan implements VlanDAO.interfazVlanDAO
{
    static Context context;
    static List<Vlan> listaVlan,listaVlanaux;
    static Dialog dialogo;
    static AdapterVlan adaptadorItemBuscarVlan;
    static RecyclerView recyclerViewBuscarVlan;
    static TextView lblSinVlan;
    TextInputLayout txtBuscarVlan;
    private static finalizoDialogBuscarVlan interfaz;
    static VlanDAO vlanDAO;
    SwipeRefreshLayout refreshLayout;

    public DialogBuscarVlan(Context context1, finalizoDialogBuscarVlan actividad) {
        interfaz = actividad;
        context = context1;
        dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);//false
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_buscar_vlan);

        refreshLayout=(SwipeRefreshLayout)dialogo.findViewById(R.id.refreshRecycler_DialogBuscarVlan);
        lblSinVlan =(TextView)dialogo.findViewById(R.id.lblVlanRegistrados);
        txtBuscarVlan=(TextInputLayout) dialogo.findViewById(R.id.txtImputBuscarVlan_DialogBuscarVlan);
        txtBuscarVlan.getEditText().addTextChangedListener(new TextWatcher() {
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
        vlanDAO =new VlanDAO(DialogBuscarVlan.this);
        recyclerViewBuscarVlan = (RecyclerView) dialogo.findViewById(R.id.recyclerView_DialogBuscarVlan);
        recyclerViewBuscarVlan.setLayoutManager(new LinearLayoutManager(context));
        vlanDAO.filtarVlan(filtrar, context,false);
    }
    public static void cargar(){
        if(listaVlan==null){
            Toast.makeText(context, "No hay Vlan", Toast.LENGTH_SHORT).show();
            listaVlan= new ArrayList<>();
            adaptadorItemBuscarVlan = new AdapterVlan(listaVlan);
            recyclerViewBuscarVlan.setAdapter(adaptadorItemBuscarVlan);
            adaptadorItemBuscarVlan.notifyDataSetChanged();
        }else{
            listaVlanaux=null;
            adaptadorItemBuscarVlan = new AdapterVlan(listaVlan);
            recyclerViewBuscarVlan.setAdapter(adaptadorItemBuscarVlan);
            adaptadorItemBuscarVlan.notifyDataSetChanged();
            adaptadorItemBuscarVlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Vlan us;
                    if (listaVlanaux==null){
                        us = listaVlan.get(recyclerViewBuscarVlan.getChildAdapterPosition(v));
                    }else{
                        us = listaVlanaux.get(recyclerViewBuscarVlan.getChildAdapterPosition(v));
                    }
                    interfaz.VlanSelecionado(us);
                    dialogo.dismiss();
                }
            });
        }
        Procesos.cargandoDetener();
    }
    private void filtrar(String buscar){
        listaVlanaux=null;
        if (listaVlan!=null){
            listaVlanaux=new ArrayList<>();
            for (Vlan aux:listaVlan) {
                if(aux.getNombreVlan().toLowerCase().contains(buscar.toLowerCase())){
                    listaVlanaux.add(aux);
                }
            }
            adaptadorItemBuscarVlan.setAdapterItemBuscarVlan(listaVlanaux);
            adaptadorItemBuscarVlan.notifyDataSetChanged();
        }

    }


    @Override
    public void setVlan(Vlan Vlan) {

    }

    @Override
    public void setListaVlan(List<Vlan> lista) {
        listaVlan=lista;
        cargar();
    }

    @Override
    public void limpiarVlan() {

    }


    public interface finalizoDialogBuscarVlan {
        void VlanSelecionado(Vlan Vlan);
    }
}
