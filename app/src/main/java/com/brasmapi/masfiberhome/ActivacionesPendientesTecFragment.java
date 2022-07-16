package com.brasmapi.masfiberhome;

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

import com.brasmapi.masfiberhome.dao.ServiciosDAO;
import com.brasmapi.masfiberhome.entidades.Servicios;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterServicios;
import com.brasmapi.masfiberhome.ui.crear.CrearServicioFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivacionesPendientesTecFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivacionesPendientesTecFragment extends Fragment implements ServiciosDAO.interfazServicio{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ActivacionesPendientesTecFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivacionesPendientesTecFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivacionesPendientesTecFragment newInstance(String param1, String param2) {
        ActivacionesPendientesTecFragment fragment = new ActivacionesPendientesTecFragment();
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
    static Context context;
    static ServiciosDAO serviciosDAO;
    public static AdapterServicios adaptador;
    public static RecyclerView recyclerView;
    public static List<Servicios> lista;
    TextInputLayout txtBuscar;
    FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;
    SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_activaciones_pendientes_tec, container, false);
        context=getActivity();
        Procesos.cargandoDetener();
        serviciosDAO =new ServiciosDAO(ActivacionesPendientesTecFragment.this);
        ((MainActivity)getActivity()).setTitle("Activaciones pendientes");
        mostrarDatos(Procesos.user.getId()+"");
        txtBuscar=(TextInputLayout)vista.findViewById(R.id.txtBuscar_ListaServiciosPendientesTec);
        refreshLayout=(SwipeRefreshLayout)vista.findViewById(R.id.refreshRecycler_listaServiciosPendientesTec);
        fragmentManager = getActivity().getSupportFragmentManager();
        // Definir una transacción
        fragmentTransaction = fragmentManager.beginTransaction();
        // Remplazar el contenido principal por el fragmento

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
                mostrarDatos(Procesos.user.getId()+"");
                refreshLayout.setRefreshing(false);
            }
        });
        Procesos.detenerObtenerLatitudLongitud();
        return vista;
    }

    public void mostrarDatos(String filtrar){
        // crear lista de carview dentro del recycleview
        recyclerView = (RecyclerView)vista.findViewById(R.id.recyclerView_ListaServiciosPendientesTec);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        Procesos.cargandoIniciar(context);
        serviciosDAO.filtarServicioPendienteTec(filtrar, vista.getContext());
    }
    
    public static void cargar(){
        if(lista==null){
            Toast.makeText(context, "No hay servicios pendientes por ser activados", Toast.LENGTH_LONG).show();
            lista= new ArrayList<>();
            adaptador = new AdapterServicios(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }else{
            adaptador = new AdapterServicios(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }
        Procesos.cargandoDetener();
    }
    private void filtrar(String filtrar){
        if (lista!=null){
            Procesos.cargandoIniciar(context);
            List<Servicios> aux2=new ArrayList<>();
            for (Servicios aux:lista) {
                if((aux.getId_servicio()+"").toLowerCase().contains(filtrar.toLowerCase())){
                    aux2.add(aux);
                }
            }
            adaptador.setAdapterItemBuscarServicios(aux2);
            adaptador.notifyDataSetChanged();
            Procesos.cargandoDetener();
        }
    }

    @Override
    public void setUsuarioRepetido(boolean estaRepetido) {

    }

    @Override
    public void setServicio(Servicios servicios) {

    }

    @Override
    public void setListaServicio(List<Servicios> lista) {
        this.lista=lista;
        cargar();
        Procesos.cargandoDetener();
    }

    @Override
    public void limpiarServicio() {

    }
}