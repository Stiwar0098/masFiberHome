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

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
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
 * Use the {@link ListarServiciosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarServiciosFragment extends Fragment implements ServiciosDAO.interfazServicio {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListarServiciosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarServiciosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarServiciosFragment newInstance(String param1, String param2) {
        ListarServiciosFragment fragment = new ListarServiciosFragment();
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
    static ServiciosDAO serviciosDAO;
    public static AdapterServicios adaptador;
    public static RecyclerView recyclerView;
    public static List<Servicios> lista,listaaux;
    TextInputLayout txtBuscar;
    FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;
    SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_listar_servicios, container, false);
        context=getActivity();
        Procesos.cargandoDetener();
        serviciosDAO =new ServiciosDAO(ListarServiciosFragment.this);
        ((MainActivity)getActivity()).setTitle("Listar servicios");
        mostrarDatos("");
        btnCrear =(Button)vista.findViewById(R.id.btnCrearServicios_ListaServicios);
        txtBuscar=(TextInputLayout)vista.findViewById(R.id.txtBuscar_ListaServicios);
        refreshLayout=(SwipeRefreshLayout)vista.findViewById(R.id.refreshRecycler_listaServicios);
        fragmentManager = getActivity().getSupportFragmentManager();
        // Definir una transacci??n
        fragmentTransaction = fragmentManager.beginTransaction();
        // Remplazar el contenido principal por el fragmento
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearServicioFragment.opc="crear";
                Navigation.findNavController(v).navigate(R.id.crearServicioFragment);
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
        Procesos.detenerObtenerLatitudLongitud();
        return vista;
    }
    public void mostrarDatos(String filtrar){
        // crear lista de carview dentro del recycleview
        recyclerView = (RecyclerView)vista.findViewById(R.id.recyclerView_ListaServicios);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        serviciosDAO.filtarServicio(filtrar, vista.getContext(),false);
    }
    public static void cargar(){
        if(lista==null){
            Toast.makeText(context, "No hay servicios", Toast.LENGTH_SHORT).show();
            lista= new ArrayList<>();
            adaptador = new AdapterServicios(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }else{
            listaaux=null;
            adaptador = new AdapterServicios(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrearServicioFragment.opc="editar";
                    Servicios us;
                    if (listaaux==null){
                        us = lista.get(recyclerView.getChildAdapterPosition(v));
                    }else{
                        us = listaaux.get(recyclerView.getChildAdapterPosition(v));
                    }
                    CrearServicioFragment.servicios =us;
                    Navigation.findNavController(v).navigate(R.id.crearServicioFragment);
                    fragmentTransaction.addToBackStack(null);
                    // Cambiar
                    fragmentTransaction.commit();
                }
            });
            adaptador.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (Procesos.user.getRol()==1){// si es administrador
                        Servicios us;
                        if (listaaux==null){
                            us = lista.get(recyclerView.getChildAdapterPosition(v));
                        }else{
                            us = listaaux.get(recyclerView.getChildAdapterPosition(v));
                        }
                        eliminarRegistroDialog(us);
                    }else{//es tecnico
                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                        builder.setTitle("Advertencia");
                        builder.setMessage("Solo los administradores pueden eliminar")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                    return true;
                }
            });

        }
        Procesos.cargandoDetener();
    }
    private static void eliminarRegistroDialog(Servicios us) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Confirmaci??n");
        builder.setMessage("??Seguro que desea pre eliminar Serviport: "+ us.getId_servicio()+" ?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.dismiss();
                        us.setOpcion_cliente("eliminar");
                        us.setEstado("pendiente");
                        us.setDate2(Procesos.obtenerFechaActualConHora());
                        us.setComando_copiar_cliente(us.getEliminarServicio()+"@"+us.getIterfazPonCard()+"@"+us.getEliminarOnt());
                        serviciosDAO.editarServicio(us,context,false);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).show();
    }

    private void filtrar(String filtrar){
        listaaux=null;
        if (lista!=null){
            Procesos.cargandoIniciar(context);
            listaaux=new ArrayList<>();
            for (Servicios aux:lista) {
                if((aux.getId_servicio()+"").equals(filtrar)
                        || Procesos.validarBuscarContains(aux.getNombreCliente(),filtrar)
                        || Procesos.validarBuscarContains(aux.getNombreCaja(),filtrar)
                        || Procesos.validarBuscarContains(aux.getSerieOnt(),filtrar)
                        || Procesos.validarBuscarContains(aux.getUsuario(),filtrar)
                        || Procesos.validarBuscarContains(aux.getDireccion(),filtrar)
                        || Procesos.validarBuscarContains(aux.getReferencia(),filtrar)
                        || aux.getLatitud().equals(filtrar)){
                    listaaux.add(aux);
                }
            }
            adaptador.setAdapterItemBuscarServicios(listaaux);
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
    }

    @Override
    public void limpiarServicio() {
    Procesos.cargandoDetener();
    mostrarDatos("");
    }
}