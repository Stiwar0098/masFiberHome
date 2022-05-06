package com.brasmapi.masfiberhome;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brasmapi.masfiberhome.ui.adaptadores.AdapterItemBuscarPais;
import com.brasmapi.masfiberhome.ui.items.ItemBuscarPais;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogBuscarPais
{
    final Context context;
    List<ItemBuscarPais> listaPaises;
    Dialog dialogo;
    AdapterItemBuscarPais adaptadorItemBuscarPais;
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

        /*
        fireReference.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaPaises.clear();
                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    Usuario user = snapShot.getValue(Usuario.class);
                    listaPaises.add(user);
                }
                cardViewBuscarUsuario.setVisibility(View.VISIBLE);
                adaptadorItemBuscarPais.notifyDataSetChanged();
                if(dataSnapshot.exists()){
                    lblSinPaises.setVisibility(View.GONE);
                    recyclerViewBuscarPais.setVisibility(View.VISIBLE);
                }else{
                    lblSinPaises.setVisibility(View.VISIBLE);
                    recyclerViewBuscarPais.setVisibility(View.GONE);
                }
                Procesos.cargandoDetener();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });*/
        // crear lista de carview dentro del recycleview
        recyclerViewBuscarPais = (RecyclerView) dialogo.findViewById(R.id.recyclerView_DialogBuscarPais);
        recyclerViewBuscarPais.setLayoutManager(new LinearLayoutManager(context));

        adaptadorItemBuscarPais = new AdapterItemBuscarPais(listarPaises());
        recyclerViewBuscarPais.setAdapter(adaptadorItemBuscarPais);
        adaptadorItemBuscarPais.notifyDataSetChanged();
        lblSinPaises.setVisibility(View.GONE);
        Procesos.cargandoDetener();
        adaptadorItemBuscarPais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemBuscarPais us = listaPaises.get(recyclerViewBuscarPais.getChildAdapterPosition(v));
                interfaz.PaisSelecionado(us);
                dialogo.dismiss();
            }
        });
    }
    private void filtrar(String buscar){
        List<ItemBuscarPais> aux2=new ArrayList<>();
        for (ItemBuscarPais aux:listaPaises) {
            if(aux.getNombre().toLowerCase().contains(buscar.toLowerCase())){
                aux2.add(aux);
            }
        }
        adaptadorItemBuscarPais.setAdapterItemBuscarPais(aux2);
        adaptadorItemBuscarPais.notifyDataSetChanged();
    }

    public List<ItemBuscarPais> listarPaises(){
        listaPaises = new ArrayList<>();
        listaPaises.add(new ItemBuscarPais("Ecuador"));
        listaPaises.add(new ItemBuscarPais("Peru"));
        return listaPaises;
    }

    public interface finalizoDialogBuscarPais {
        void PaisSelecionado(ItemBuscarPais pais);
    }
}
