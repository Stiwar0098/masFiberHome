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

import com.brasmapi.masfiberhome.ui.crear.CrearModeloOntFragment;
import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.ModeloOntDAO;
import com.brasmapi.masfiberhome.entidades.ModeloOnt;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterModeloOnt;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaModeloOntFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaModeloOntFragment extends Fragment implements ModeloOntDAO.interfazModeloOntDAO {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaModeloOntFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaModeloOntFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaModeloOntFragment newInstance(String param1, String param2) {
        ListaModeloOntFragment fragment = new ListaModeloOntFragment();
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
    Button btnCrear;
    static Context context;
    static ModeloOntDAO ModeloOntDAO;
    public static AdapterModeloOnt adaptador;
    public static RecyclerView recyclerView;
    public static List<ModeloOnt> lista,listaaux;
    TextInputLayout txtBuscar;
    FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;
    SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_lista_modelo_ont, container, false);
        context=getActivity();
        ModeloOntDAO =new ModeloOntDAO(ListaModeloOntFragment.this);
        ((MainActivity)getActivity()).setTitle("Listar ModeloOnt");
        mostrarDatos("");
        btnCrear =(Button)vista.findViewById(R.id.btnCrearModeloOnt_ListaModeloOnt);
        txtBuscar=(TextInputLayout)vista.findViewById(R.id.txtBuscar_ListaModeloOnt);
        refreshLayout=(SwipeRefreshLayout)vista.findViewById(R.id.refreshRecycler_listaModeloOnt);
        fragmentManager = getActivity().getSupportFragmentManager();
        // Definir una transacci??n
        fragmentTransaction = fragmentManager.beginTransaction();
        // Remplazar el contenido principal por el fragmento
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearModeloOntFragment.opc="crear";
                Navigation.findNavController(v).navigate(R.id.crearModeloOntFragment);
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
        recyclerView = (RecyclerView)vista.findViewById(R.id.recyclerView_ListaModeloOnt);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ModeloOntDAO.filtarModeloOnt(filtrar, vista.getContext(),false);
    }
    public static void cargar(){
        if(lista==null){
            Toast.makeText(context, "No hay ModeloOnt", Toast.LENGTH_SHORT).show();
            lista= new ArrayList<>();
            adaptador = new AdapterModeloOnt(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }else{
            listaaux=null;
            adaptador = new AdapterModeloOnt(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrearModeloOntFragment.opc="editar";
                    ModeloOnt us;
                    if (listaaux==null){
                        us = lista.get(recyclerView.getChildAdapterPosition(v));
                    }else{
                        us = listaaux.get(recyclerView.getChildAdapterPosition(v));
                    }
                    CrearModeloOntFragment.modeloOnt =us;
                    Navigation.findNavController(v).navigate(R.id.crearModeloOntFragment);
                    fragmentTransaction.addToBackStack(null);
                    // Cambiar
                    fragmentTransaction.commit();
                }
            });
            adaptador.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ModeloOnt us;
                    if (listaaux==null){
                        us = lista.get(recyclerView.getChildAdapterPosition(v));
                    }else{
                        us = listaaux.get(recyclerView.getChildAdapterPosition(v));
                    }
                    eliminarRegistroDialog(us);
                    return true;
                }
            });

        }
        Procesos.cargandoDetener();
    }
    private static void eliminarRegistroDialog(ModeloOnt us) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Confirmaci??n");
        builder.setMessage("Esta eliminaci??n, eliminar?? toda la data relacionada con: "+us.getNombre_modeloOnt()+" \n\nPorfavor verifique que ha modificado la dependecia de este dato en: \n\n-Ont")
                .setPositiveButton("Siguiente", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                        builder.setTitle("Eliminar");
                        final EditText contraAdmin = new EditText(context);
                        contraAdmin.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(contraAdmin);
                        builder.setMessage("Para poder eliminar: "+us.getNombre_modeloOnt()+"\n \nIngrese la contrase??a admin:")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if (contraAdmin.getText().toString().trim().equals("admin")){
                                            ModeloOntDAO.eliminarModeloOnt(us.getId_modeloOnt(),context);
                                        }else{
                                            Toast.makeText(context, "contrase??a incorrecta", Toast.LENGTH_SHORT).show();
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
        listaaux=null;
        if (lista!=null){
            Procesos.cargandoIniciar(context);
            listaaux=new ArrayList<>();
            for (ModeloOnt aux:lista) {
                if(Procesos.validarBuscarContains(aux.getNombre_modeloOnt(),filtrar)
                        || Procesos.validarBuscarContains(aux.getTipo_modeloOnt(),filtrar)){
                    listaaux.add(aux);
                }
            }
            adaptador.setAdapterItemBuscarModeloOnt(listaaux);
            adaptador.notifyDataSetChanged();
            Procesos.cargandoDetener();
        }
    }
    @Override
    public void setModeloOnt(ModeloOnt ModeloOnt) {

    }

    @Override
    public void setListaModeloOnt(List<ModeloOnt> lista) {
        this.lista=lista;
        cargar();
    }

    @Override
    public void limpiarModeloOnt() {

    }
}