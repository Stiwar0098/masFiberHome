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
import com.brasmapi.masfiberhome.dao.CajaNivel1DAO;
import com.brasmapi.masfiberhome.entidades.CajaNivel1;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterCajaNivel1;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogBuscarCajaNivel1 implements CajaNivel1DAO.interfazCajaNivel1DAO
{
    static Context context;
    static List<CajaNivel1> listaCajaNivel1,listaCajaNivel1aux;
    static Dialog dialogo;
    static AdapterCajaNivel1 adaptadorItemBuscarCajaNivel1;
    static RecyclerView recyclerViewBuscarCajaNivel1;
    static TextView lblSinCajaNivel1;
    TextInputLayout txtBuscarCajaNivel1;
    private static finalizoDialogBuscarCajaNivel1 interfaz;
    static CajaNivel1DAO CajaNivel1DAO;
    SwipeRefreshLayout refreshLayout;

    public DialogBuscarCajaNivel1(Context context1, finalizoDialogBuscarCajaNivel1 actividad) {
        interfaz = actividad;
        context = context1;
        dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);//false
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_buscar_caja_nivel1);

        refreshLayout=(SwipeRefreshLayout)dialogo.findViewById(R.id.refreshRecycler_DialogBuscarCajaNivel1);
        lblSinCajaNivel1 =(TextView)dialogo.findViewById(R.id.lblCajaNivel1Registrados);
        txtBuscarCajaNivel1=(TextInputLayout) dialogo.findViewById(R.id.txtImputBuscarCajaNivel1_DialogBuscarCajaNivel1);
        txtBuscarCajaNivel1.getEditText().addTextChangedListener(new TextWatcher() {
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
        CajaNivel1DAO =new CajaNivel1DAO(DialogBuscarCajaNivel1.this);
        recyclerViewBuscarCajaNivel1 = (RecyclerView) dialogo.findViewById(R.id.recyclerView_DialogBuscarCajaNivel1);
        recyclerViewBuscarCajaNivel1.setLayoutManager(new LinearLayoutManager(context));
        CajaNivel1DAO.filtarCajaNivel1(filtrar, context,false);
    }
    public static void cargar(){
        if(listaCajaNivel1==null){
            Toast.makeText(context, "No hay CajaNivel1", Toast.LENGTH_SHORT).show();
            listaCajaNivel1= new ArrayList<>();
            adaptadorItemBuscarCajaNivel1 = new AdapterCajaNivel1(listaCajaNivel1);
            recyclerViewBuscarCajaNivel1.setAdapter(adaptadorItemBuscarCajaNivel1);
            adaptadorItemBuscarCajaNivel1.notifyDataSetChanged();
        }else{
            listaCajaNivel1aux=null;
            adaptadorItemBuscarCajaNivel1 = new AdapterCajaNivel1(listaCajaNivel1);
            recyclerViewBuscarCajaNivel1.setAdapter(adaptadorItemBuscarCajaNivel1);
            adaptadorItemBuscarCajaNivel1.notifyDataSetChanged();
            adaptadorItemBuscarCajaNivel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CajaNivel1 us;
                    if (listaCajaNivel1aux==null){
                        us=listaCajaNivel1.get(recyclerViewBuscarCajaNivel1.getChildAdapterPosition(v));
                    }else{
                        us=listaCajaNivel1aux.get(recyclerViewBuscarCajaNivel1.getChildAdapterPosition(v));
                    }
                    interfaz.CajaNivel1Selecionado(us);
                    dialogo.dismiss();
                }
            });
        }
        Procesos.cargandoDetener();
    }
    private void filtrar(String filtrar){
        listaCajaNivel1aux=null;
        if (listaCajaNivel1!=null){
            listaCajaNivel1aux=new ArrayList<>();
            for (CajaNivel1 aux:listaCajaNivel1) {
                if(Procesos.validarBuscarContains(aux.getNombre_cajaNivel1(),filtrar)
                        || Procesos.validarBuscarContains(aux.getNombreVlan(),filtrar)
                        || Procesos.validarBuscarContains(aux.getAbreviatura_cajaNivel1(),filtrar)
                        || Procesos.validarBuscarContains(aux.getNombreCiudad(),filtrar)
                        || Procesos.validarBuscarContains(aux.getDireccion_cajaNivel1(),filtrar)
                        || Procesos.validarBuscarContains(aux.getReferencia_cajaNivel1(),filtrar)
                        || Procesos.validarBuscarContains(aux.getLatitud_cajaNivel1(),filtrar)){
                    listaCajaNivel1aux.add(aux);
                }
            }
            adaptadorItemBuscarCajaNivel1.setAdapterItemBuscarCajaNivel1(listaCajaNivel1aux);
            adaptadorItemBuscarCajaNivel1.notifyDataSetChanged();
        }

    }


    @Override
    public void setCajaNivel1(CajaNivel1 CajaNivel1) {

    }

    @Override
    public void setListaCajaNivel1(List<CajaNivel1> lista) {
        listaCajaNivel1=lista;
        cargar();
    }

    @Override
    public void limpiar() {

    }


    public interface finalizoDialogBuscarCajaNivel1 {
        void CajaNivel1Selecionado(CajaNivel1 CajaNivel1);
    }
}
