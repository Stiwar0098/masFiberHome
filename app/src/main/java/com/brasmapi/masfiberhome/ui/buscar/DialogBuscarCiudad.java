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
import com.brasmapi.masfiberhome.dao.CiudadDAO;
import com.brasmapi.masfiberhome.entidades.Ciudad;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterCiudad;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterVlan;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogBuscarCiudad implements CiudadDAO.ciudadDAO
{
    static Context context;
    static List<Ciudad> listaCiudades,listaCiudadesaux;
    static Dialog dialogo;
    static AdapterCiudad adaptadorItemBuscarCiudad;
    static RecyclerView recyclerViewBuscarCiudad;
    static TextView lblSinCiudades;
    TextInputLayout txtBuscarCiudad;
    private static finalizoDialogBuscarCiudad interfaz;
    static CiudadDAO CiudadDAO;

    public DialogBuscarCiudad(Context context1, finalizoDialogBuscarCiudad actividad) {
        interfaz = actividad;
        context = context1;
        dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);//false
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_buscar_ciudad);

        lblSinCiudades =(TextView)dialogo.findViewById(R.id.lblCiudadesRegistrados);
        txtBuscarCiudad=(TextInputLayout) dialogo.findViewById(R.id.txtImputBuscarCiudad_DialogBuscarCiudad);
        txtBuscarCiudad.getEditText().addTextChangedListener(new TextWatcher() {
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
        CiudadDAO =new CiudadDAO(DialogBuscarCiudad.this);
        recyclerViewBuscarCiudad = (RecyclerView) dialogo.findViewById(R.id.recyclerView_DialogBuscarCiudad);
        recyclerViewBuscarCiudad.setLayoutManager(new LinearLayoutManager(context));
        CiudadDAO.filtarCiudad(filtrar, context,false);
    }
    public static void cargar(){
        if(listaCiudades==null){
            Toast.makeText(context, "No hay Ciudades", Toast.LENGTH_SHORT).show();
            listaCiudades= new ArrayList<>();
            adaptadorItemBuscarCiudad = new AdapterCiudad(listaCiudades);
            recyclerViewBuscarCiudad.setAdapter(adaptadorItemBuscarCiudad);
            adaptadorItemBuscarCiudad.notifyDataSetChanged();
        }else{
            listaCiudadesaux=null;
            adaptadorItemBuscarCiudad = new AdapterCiudad(listaCiudades);
            recyclerViewBuscarCiudad.setAdapter(adaptadorItemBuscarCiudad);
            adaptadorItemBuscarCiudad.notifyDataSetChanged();
            adaptadorItemBuscarCiudad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ciudad us;
                    if (listaCiudadesaux==null){
                        us = listaCiudades.get(recyclerViewBuscarCiudad.getChildAdapterPosition(v));
                    }else{
                        us = listaCiudadesaux.get(recyclerViewBuscarCiudad.getChildAdapterPosition(v));
                    }
                    interfaz.CiudadSelecionado(us);
                    dialogo.dismiss();
                }
            });
        }
        Procesos.cargandoDetener();
    }
    private void filtrar(String filtrar){
        listaCiudadesaux=null;
        if (listaCiudades!=null){
            listaCiudadesaux=new ArrayList<>();
            for (Ciudad aux:listaCiudades) {
                if(Procesos.validarBuscarContains(aux.getNombreProvincia(),filtrar)
                        || Procesos.validarBuscarContains(aux.getNombre(),filtrar)){
                    listaCiudadesaux.add(aux);
                }
            }
            adaptadorItemBuscarCiudad.setAdapterItemBuscarCiudad(listaCiudadesaux);
            adaptadorItemBuscarCiudad.notifyDataSetChanged();
        }

    }


    @Override
    public void setCiudad(Ciudad Ciudad) {

    }

    @Override
    public void setListaCiudad(List<Ciudad> lista) {
        listaCiudades=lista;
        cargar();
    }


    public interface finalizoDialogBuscarCiudad {
        void CiudadSelecionado(Ciudad Ciudad);
    }
}
