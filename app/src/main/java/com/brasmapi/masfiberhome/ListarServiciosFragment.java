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
import com.brasmapi.masfiberhome.entidades.Clientes;
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
    public static List<Servicios> lista;
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
        ((MainActivity)getActivity()).setTitle("Listar servicos");
        mostrarDatos("");
        btnCrear =(Button)vista.findViewById(R.id.btnCrearServicios_ListaServicios);
        txtBuscar=(TextInputLayout)vista.findViewById(R.id.txtBuscar_ListaServicios);
        refreshLayout=(SwipeRefreshLayout)vista.findViewById(R.id.refreshRecycler_listaServicios);
        fragmentManager = getActivity().getSupportFragmentManager();
        // Definir una transacción
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
            adaptador = new AdapterServicios(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrearServicioFragment.opc="editar";
                    Servicios us = lista.get(recyclerView.getChildAdapterPosition(v));
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
                        Servicios us = lista.get(recyclerView.getChildAdapterPosition(v));
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
        builder.setTitle("Opciones");
        builder.setMessage("¿Elija la opcion que desea con: Serviport: "+ us.getId_servicio()+" ?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                        builder.setTitle("Eliminar");
                        builder.setMessage("¿Que tipo de eliminacion desea realizar: Serviport: "+us.getId_servicio()+" ?")
                                .setPositiveButton("Normal", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                                        builder.setTitle("Eliminar normal");
                                        builder.setMessage("¿Está seguro que desea realizar una eliminacion normal: Serviport: "+us.getId_servicio()+" ?")
                                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(final DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        serviciosDAO.eliminarServicio(us.getId_servicio(),context);
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
                                })
                                .setNegativeButton("En Cascada", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        AlertDialog.Builder dial=new AlertDialog.Builder(context);
                                        dial.setTitle("Eliminar en cascada");
                                        final EditText contraAdmin = new EditText(context);
                                        contraAdmin.setInputType(InputType.TYPE_CLASS_TEXT);
                                        dial.setView(contraAdmin);
                                        dial.setMessage("Para poder elimanar en cascada: Serviport: "+us.getId_servicio()+"%n ingrese la contraseña admin ")
                                                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(final DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        if (contraAdmin.getText().toString().trim().equals("pullasancho")){
                                                            serviciosDAO.eliminarServicio(us.getId_servicio(),context);
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
                                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                })
                .setNegativeButton("Desactivar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                        builder.setTitle("Desactivar");
                        builder.setMessage("¿Está seguro que desea desactivar: Serviport: "+us.getId_servicio()+" ?")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        us.setEstado("desactivo");
                                        serviciosDAO.editarServicio(us,context,true);
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
                })
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .show();
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
    }

    @Override
    public void limpiarServicio() {

    }
}