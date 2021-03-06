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
import android.widget.EditText;
import android.widget.Toast;

import com.brasmapi.masfiberhome.dao.HistorialServiciosDAO;
import com.brasmapi.masfiberhome.dao.ServiciosDAO;
import com.brasmapi.masfiberhome.entidades.HistorialServicios;
import com.brasmapi.masfiberhome.entidades.Servicios;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterServicios;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterServicios2;
import com.brasmapi.masfiberhome.ui.crear.CrearServicioFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivacionesPendientesAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivacionesPendientesAdminFragment extends Fragment implements ServiciosDAO.interfazServicio, HistorialServiciosDAO.interfazServicio{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ActivacionesPendientesAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivacionesPendientesAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivacionesPendientesAdminFragment newInstance(String param1, String param2) {
        ActivacionesPendientesAdminFragment fragment = new ActivacionesPendientesAdminFragment();
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
    public static AdapterServicios2 adaptador;
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
        vista= inflater.inflate(R.layout.fragment_activaciones_pendientes_admin, container, false);
        context=getActivity();
        Procesos.cargandoDetener();
        serviciosDAO =new ServiciosDAO(ActivacionesPendientesAdminFragment.this);
        ((MainActivity)getActivity()).setTitle("Activaciones pendientes admin");
        mostrarDatos();
        txtBuscar=(TextInputLayout)vista.findViewById(R.id.txtBuscar_ListaServiciosPendientesAdmin);
        refreshLayout=(SwipeRefreshLayout)vista.findViewById(R.id.refreshRecycler_listaServiciosPendientesAdmin);
        fragmentManager = getActivity().getSupportFragmentManager();
        // Definir una transacci??n
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
                mostrarDatos();
                refreshLayout.setRefreshing(false);
            }
        });
        Procesos.detenerObtenerLatitudLongitud();
        return vista;
    }
    public void mostrarDatos(){
        // crear lista de carview dentro del recycleview
        recyclerView = (RecyclerView)vista.findViewById(R.id.recyclerView_ListaServiciosPendientesAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        Procesos.cargandoIniciar(context);
        serviciosDAO.filtarServicioPendienteAdmin(vista.getContext());
    }
    boolean isEliminar=false;
    public void cargar(){
        if(lista==null){
            Toast.makeText(context, "No hay servicios pendientes por activar", Toast.LENGTH_LONG).show();
            lista= new ArrayList<>();
            adaptador = new AdapterServicios2(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }else{
            listaaux=null;
            adaptador = new AdapterServicios2(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Servicios us;
                    if (listaaux==null){
                        us = lista.get(recyclerView.getChildAdapterPosition(v));
                    }else{
                        us = listaaux.get(recyclerView.getChildAdapterPosition(v));
                    }
                    Procesos.copiarEnElPortapapeles(context, us.getComando_copiar_cliente().replace("@","\n"),getActivity());
                    Toast.makeText(context, us.getUsuario(), Toast.LENGTH_SHORT).show();
                    /*CrearServicioFragment.opc="editar";
                    Servicios us = lista.get(recyclerView.getChildAdapterPosition(v));
                    CrearServicioFragment.servicios =us;
                    Navigation.findNavController(v).navigate(R.id.crearServicioFragment);
                    fragmentTransaction.addToBackStack(null);
                    // Cambiar
                    fragmentTransaction.commit();*/
                }
            });
            adaptador.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Servicios us;
                    if (listaaux==null){
                        us = lista.get(recyclerView.getChildAdapterPosition(v));
                    }else{
                        us = listaaux.get(recyclerView.getChildAdapterPosition(v));
                    }
                    String msj="",pc34="";
                    if (us.getOpcion_cliente().equals("eliminar")){
                        msj="Se eliminar?? este servicio:";
                        pc34="Siguiente";
                    }else{
                        msj="??Seguro que desea activar este servicio?";
                        pc34="Aceptar";
                    }
                    AlertDialog.Builder builder= new AlertDialog.Builder(context);
                    builder.setTitle("Confirmaci??n");
                    builder.setMessage( msj +
                                    "\nServiport: "+us.getId_servicio()+"\n" +
                                    "Usuario: "+us.getUsuario())
                            .setPositiveButton(pc34, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    us.setEstado("activo");
                                    if (us.getOpcion_cliente().equals("eliminar")){
                                        AlertDialog.Builder dial=new AlertDialog.Builder(context);
                                        dial.setTitle("Eliminar");
                                        final EditText contraAdmin = new EditText(context);
                                        contraAdmin.setInputType(InputType.TYPE_CLASS_TEXT);
                                        dial.setView(contraAdmin);
                                        dial.setMessage("Para poder eliminar: "+us.getUsuario()+"\n \nIngrese la contrase??a admin:")
                                                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(final DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        if (contraAdmin.getText().toString().trim().equals("admin")){
                                                            isEliminar=true;
                                                            serviciosDAO.eliminarServicio(us.getId_servicio(),context);
                                                            HistorialServiciosDAO op= new HistorialServiciosDAO(ActivacionesPendientesAdminFragment.this);
                                                            op.crearServicio(us,us.getSerieOnt(),context);
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
                                    }else{
                                        Procesos.cargandoIniciar(context);
                                        serviciosDAO.editarServicio(us,context,false);
                                        HistorialServiciosDAO op= new HistorialServiciosDAO(ActivacionesPendientesAdminFragment.this);
                                        op.crearServicio(us,us.getSerieOnt(),context);
                                    }

                                }
                            })
                            .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            })
                            .show();


                    //eliminarRegistroDialog(us);
                    return true;
                }
            });
        }
        Procesos.cargandoDetener();
    }
    private void filtrar(String filtrar){
        listaaux=null;
        if (lista!=null){
            Procesos.cargandoIniciar(context);
            listaaux=new ArrayList<>();
            for (Servicios aux:lista) {
                if((aux.getId_servicio()+"").toLowerCase().contains(filtrar.toLowerCase())){
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
        Procesos.cargandoDetener();
    }

    @Override
    public void limpiarServicio() {
        mostrarDatos();
        Toast.makeText(context, "Servicio Activado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setListaHistorialServicio(List<HistorialServicios> lista) {

    }

    @Override
    public void limpiarHistorialServicio() {
        if (isEliminar){
            Toast.makeText(context, "Servicio eliminado", Toast.LENGTH_SHORT).show();
        }
    }
}