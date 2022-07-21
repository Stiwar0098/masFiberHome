package com.brasmapi.masfiberhome.ui.listar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.HistorialServiciosDAO;
import com.brasmapi.masfiberhome.entidades.HistorialServicios;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterHistorialServicios;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaHistorialServiciosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaHistorialServiciosFragment extends Fragment implements HistorialServiciosDAO.interfazServicio {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaHistorialServiciosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaHistorialServiciosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaHistorialServiciosFragment newInstance(String param1, String param2) {
        ListaHistorialServiciosFragment fragment = new ListaHistorialServiciosFragment();
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
    Button btnBorrar;
    static Context context;
    static HistorialServiciosDAO historialServiciosDAO;
    public static AdapterHistorialServicios adaptador;
    public static RecyclerView recyclerView;
    public static List<HistorialServicios> lista;
    TextInputLayout txtBuscar;
    FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;
    SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_lista_historial_servicios, container, false);
        context=getActivity();
        Procesos.cargandoDetener();
        historialServiciosDAO =new HistorialServiciosDAO(ListaHistorialServiciosFragment.this);
        ((MainActivity)getActivity()).setTitle("Listar historial servicos");
        mostrarDatos("");
        btnBorrar =(Button)vista.findViewById(R.id.btnBorrarHistorialServicios_ListaHistorialServicios);
        txtBuscar=(TextInputLayout)vista.findViewById(R.id.txtBuscar_ListaHistorialServicios);
        refreshLayout=(SwipeRefreshLayout)vista.findViewById(R.id.refreshRecycler_listaHistorialServicios);
        fragmentManager = getActivity().getSupportFragmentManager();
        // Definir una transacción
        fragmentTransaction = fragmentManager.beginTransaction();
        // Remplazar el contenido principal por el fragmento
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Eliminar historial");
                builder.setMessage("¿Está seguro que desea eliminar el historial?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                dialog.dismiss();
                                historialServiciosDAO.eliminarServicio(0,context);
                                mostrarDatos("");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).show();
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
        Procesos.detenerObtenerLatitudLongitud();
        return vista;
    }
    public void mostrarDatos(String filtrar){
        // crear lista de carview dentro del recycleview
        recyclerView = (RecyclerView)vista.findViewById(R.id.recyclerView_ListaHistorialServicios);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        historialServiciosDAO.filtarServicio(filtrar, vista.getContext(),false);
    }
    public static void cargar(){
        if(lista==null){
            Toast.makeText(context, "No hay historial servicios", Toast.LENGTH_SHORT).show();
            lista= new ArrayList<>();
            adaptador = new AdapterHistorialServicios(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }else{
            adaptador = new AdapterHistorialServicios(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }
        Procesos.cargandoDetener();
    }

    private void filtrar(String filtrar){
        if (lista!=null){
            Procesos.cargandoIniciar(context);
            List<HistorialServicios> aux2=new ArrayList<>();
            if (!filtrar.equals("")){
                for (HistorialServicios aux:lista) {
                    if((aux.getId_servicio()+"").equals(filtrar)){
                        aux2.add(aux);
                    }
                }
            }else{
                aux2=lista;
            }
            adaptador.setAdapterItemBuscarServicios(aux2);
            adaptador.notifyDataSetChanged();
            Procesos.cargandoDetener();
        }
    }

    @Override
    public void setListaHistorialServicio(List<HistorialServicios> lista) {
        this.lista=lista;
        cargar();
    }

    @Override
    public void limpiarHistorialServicio() {

    }
}