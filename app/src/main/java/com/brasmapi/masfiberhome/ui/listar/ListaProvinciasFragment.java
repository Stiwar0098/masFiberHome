package com.brasmapi.masfiberhome.ui.listar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterProvincia;
import com.brasmapi.masfiberhome.dao.ProvinciaDAO;
import com.brasmapi.masfiberhome.entidades.Provincia;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterVlan;
import com.brasmapi.masfiberhome.ui.crear.CrearProvinciaFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaProvinciasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaProvinciasFragment extends Fragment implements ProvinciaDAO.setProvincia{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaProvinciasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaProvinciasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaProvinciasFragment newInstance(String param1, String param2) {
        ListaProvinciasFragment fragment = new ListaProvinciasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    View vista;
    Button btnCrearProvincia;
    static Context context;
    static ProvinciaDAO provinciaDAO;
    public static AdapterProvincia adaptadorProvincia;
    public static RecyclerView recyclerViewProvincias;
    public static List<Provincia> listaProvincias,listaProvinciasaux;
    TextInputLayout txtBuscar;
    FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;
    SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista =inflater.inflate(R.layout.fragment_lista_provincias, container, false);
        context=getActivity();
        provinciaDAO = new ProvinciaDAO(ListaProvinciasFragment.this);
        ((MainActivity)getActivity()).setTitle("Listar Provincias");
        mostrarDatos("");
        btnCrearProvincia =(Button)vista.findViewById(R.id.btnCrearProvincia_ListaProvincias);
        txtBuscar=(TextInputLayout)vista.findViewById(R.id.txtBuscar_ListaProvincia);
        refreshLayout=(SwipeRefreshLayout)vista.findViewById(R.id.refreshRecycler_listaProvincias);
        fragmentManager = getActivity().getSupportFragmentManager();
        // Definir una transacción
        fragmentTransaction = fragmentManager.beginTransaction();
        // Remplazar el contenido principal por el fragmento
        btnCrearProvincia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearProvinciaFragment.opc="crear";
                Navigation.findNavController(v).navigate(R.id.crearProvinciaFragment);
                fragmentTransaction.addToBackStack(null);
                // Cambiar
                fragmentTransaction.commit();
            }
        });
        txtBuscar.getEditText().addTextChangedListener(new TextWatcher() {
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
        return vista;
    }
    public void mostrarDatos(String filtrar){
        // crear lista de carview dentro del recycleview
        recyclerViewProvincias = (RecyclerView)vista.findViewById(R.id.recyclerView_ListaProvincias);
        recyclerViewProvincias.setLayoutManager(new LinearLayoutManager(context));
        provinciaDAO.filtarProvincia(filtrar, context,false);
    }
    public void cargar(){
        if(listaProvincias==null){
            Toast.makeText(context, "No hay Provincias", Toast.LENGTH_SHORT).show();
            listaProvincias= new ArrayList<>();
            adaptadorProvincia = new AdapterProvincia(listaProvincias);
            recyclerViewProvincias.setAdapter(adaptadorProvincia);
            adaptadorProvincia.notifyDataSetChanged();
        }else{
            listaProvinciasaux=null;
            adaptadorProvincia = new AdapterProvincia(listaProvincias);
            recyclerViewProvincias.setAdapter(adaptadorProvincia);
            adaptadorProvincia.notifyDataSetChanged();
            adaptadorProvincia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrearProvinciaFragment.opc="editar";
                    Provincia us;
                    if (listaProvinciasaux==null){
                        us = listaProvincias.get(recyclerViewProvincias.getChildAdapterPosition(v));
                    }else{
                        us = listaProvinciasaux.get(recyclerViewProvincias.getChildAdapterPosition(v));
                    }
                    CrearProvinciaFragment.provincia =us;
                    Navigation.findNavController(v).navigate(R.id.crearProvinciaFragment);
                    fragmentTransaction.addToBackStack(null);
                    // Cambiar
                    fragmentTransaction.commit();
                }
            });
            adaptadorProvincia.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Provincia us;
                    if (listaProvinciasaux==null){
                        us = listaProvincias.get(recyclerViewProvincias.getChildAdapterPosition(v));
                    }else{
                        us = listaProvinciasaux.get(recyclerViewProvincias.getChildAdapterPosition(v));
                    }
                    eliminarRegistroDialog(us);
                    return true;
                }
            });

        }
        Procesos.cargandoDetener();
    }
    private static void eliminarRegistroDialog(Provincia us) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Confirmación");
        builder.setMessage("Esta eliminación, eliminará toda la data relacionada con: "+us.getNombre()+" \n\nPorfavor verifique que ha modificado la dependecia de este dato en: \n\n-Ciudad")
                .setPositiveButton("Siguiente", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                        builder.setTitle("Eliminar");
                        final EditText contraAdmin = new EditText(context);
                        contraAdmin.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(contraAdmin);
                        builder.setMessage("Para poder eliminar: "+us.getNombre()+"\n \nIngrese la contraseña admin:")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if (contraAdmin.getText().toString().trim().equals("admin")){
                                            provinciaDAO.eliminarProvincia(us.getId(),context);
                                        }else{
                                            Toast.makeText(context, "contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                            contraAdmin.setText("");
                                        }
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void filtrar(String filtrar){
        listaProvinciasaux=null;
        if (listaProvincias!=null){
            Procesos.cargandoIniciar(context);
            listaProvinciasaux=new ArrayList<>();
            for (Provincia aux:listaProvincias) {
                if(Procesos.validarBuscarContains(aux.getNombre(),filtrar)
                        || Procesos.validarBuscarContains(aux.getNombre_pais(),filtrar)){
                    listaProvinciasaux.add(aux);
                }
            }
            adaptadorProvincia.setAdapterItemBuscarProvincia(listaProvinciasaux);
            adaptadorProvincia.notifyDataSetChanged();
            Procesos.cargandoDetener();
        }
    }

    @Override
    public void setProvincia(Provincia provincia) {

    }

    @Override
    public void setListaProvincia(List<Provincia> lista) {
        listaProvincias=lista;
        cargar();
    }
}