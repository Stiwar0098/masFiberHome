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
import com.brasmapi.masfiberhome.dao.ClientesDAO;
import com.brasmapi.masfiberhome.entidades.Clientes;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterClientes;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogBuscarCliente implements ClientesDAO.interfazClientesDAO
{
    static Context context;
    static List<Clientes> listaClientes,listaClientesaux;
    static Dialog dialogo;
    static AdapterClientes adaptadorItemBuscarClientes;
    static RecyclerView recyclerViewBuscarClientes;
    static TextView lblSinClientes;
    TextInputLayout txtBuscarClientes;
    private static finalizoDialogBuscarClientes interfaz;
    static ClientesDAO ClientesDAO;
    SwipeRefreshLayout refreshLayout;

    public DialogBuscarCliente(Context context1, finalizoDialogBuscarClientes actividad) {
        interfaz = actividad;
        context = context1;
        dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);//false
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_buscar_clientes);

        refreshLayout=(SwipeRefreshLayout)dialogo.findViewById(R.id.refreshRecycler_DialogBuscarClientes);
        lblSinClientes =(TextView)dialogo.findViewById(R.id.lblClientesRegistrados);
        txtBuscarClientes=(TextInputLayout) dialogo.findViewById(R.id.txtImputBuscarClientes_DialogBuscarClientes);
        txtBuscarClientes.getEditText().addTextChangedListener(new TextWatcher() {
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
        ClientesDAO =new ClientesDAO(DialogBuscarCliente.this);
        recyclerViewBuscarClientes = (RecyclerView) dialogo.findViewById(R.id.recyclerView_DialogBuscarClientes);
        recyclerViewBuscarClientes.setLayoutManager(new LinearLayoutManager(context));
        ClientesDAO.filtarClientes(filtrar, context,false);
    }
    public static void cargar(){
        if(listaClientes==null){
            Toast.makeText(context, "No hay Clientes", Toast.LENGTH_SHORT).show();
            listaClientes= new ArrayList<>();
            adaptadorItemBuscarClientes = new AdapterClientes(listaClientes);
            recyclerViewBuscarClientes.setAdapter(adaptadorItemBuscarClientes);
            adaptadorItemBuscarClientes.notifyDataSetChanged();
        }else{
            listaClientesaux=null;
            adaptadorItemBuscarClientes = new AdapterClientes(listaClientes);
            recyclerViewBuscarClientes.setAdapter(adaptadorItemBuscarClientes);
            adaptadorItemBuscarClientes.notifyDataSetChanged();
            adaptadorItemBuscarClientes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Clientes us;
                    if (listaClientesaux==null){
                        us = listaClientes.get(recyclerViewBuscarClientes.getChildAdapterPosition(v));
                    }else{
                        us = listaClientesaux.get(recyclerViewBuscarClientes.getChildAdapterPosition(v));
                    }

                    interfaz.ClientesSelecionado(us);
                    dialogo.dismiss();
                }
            });
        }
        Procesos.cargandoDetener();
    }
    private void filtrar(String filtrar){
        listaClientesaux=null;
        if (listaClientes!=null){
            listaClientesaux=new ArrayList<>();
            for (Clientes aux:listaClientes) {
                if(Procesos.validarBuscarContains(aux.getCedula(),filtrar)
                        || Procesos.validarBuscarContains(aux.getNombre(),filtrar)
                        || Procesos.validarBuscarContains(aux.getApellido(),filtrar)
                        || Procesos.validarBuscarContains(aux.getCorreo(),filtrar)
                        || Procesos.validarBuscarContains(aux.getTelefono1(),filtrar)
                        || Procesos.validarBuscarContains(aux.getTelefono2(),filtrar)){
                    listaClientesaux.add(aux);
                }
            }
            adaptadorItemBuscarClientes.setAdapterItemBuscarClientes(listaClientesaux);
            adaptadorItemBuscarClientes.notifyDataSetChanged();
        }

    }


    @Override
    public void setClientes(Clientes Clientes) {

    }


    @Override
    public void setListaClientes(List<Clientes> lista) {
        listaClientes=lista;
        cargar();
    }

    @Override
    public void limpiarClientes() {

    }


    public interface finalizoDialogBuscarClientes {
        void ClientesSelecionado(Clientes Clientes);
    }
}
