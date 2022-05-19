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

import com.brasmapi.masfiberhome.ui.adaptadores.AdapterVlan;
import com.brasmapi.masfiberhome.ui.dao.VlanDAO;
import com.brasmapi.masfiberhome.ui.entidades.Vlan;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaVlansFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaVlansFragment extends Fragment implements VlanDAO.interfazVlanDAO{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaVlansFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaVlansFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaVlansFragment newInstance(String param1, String param2) {
        ListaVlansFragment fragment = new ListaVlansFragment();
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
    static VlanDAO vlanDAO;
    public static AdapterVlan adaptador;
    public static RecyclerView recyclerView;
    public static List<Vlan> lista;
    TextInputLayout txtBuscar;
    FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;
    SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_lista_vlans, container, false);
        context=getActivity();
        vlanDAO =new VlanDAO(ListaVlansFragment.this);
        ((MainActivity)getActivity()).setTitle("Listar Vlan");
        mostrarDatos("");
        btnCrear =(Button)vista.findViewById(R.id.btnCrearVlan_ListaVlan);
        txtBuscar=(TextInputLayout)vista.findViewById(R.id.txtBuscar_ListaVlan);
        refreshLayout=(SwipeRefreshLayout)vista.findViewById(R.id.refreshRecycler_listaVlan);
        fragmentManager = getActivity().getSupportFragmentManager();
        // Definir una transacción
        fragmentTransaction = fragmentManager.beginTransaction();
        // Remplazar el contenido principal por el fragmento
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearVlanFragment.opc="crear";
                Navigation.findNavController(v).navigate(R.id.crearVlanFragment);
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
        recyclerView = (RecyclerView)vista.findViewById(R.id.recyclerView_ListaVlan);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        vlanDAO.filtarVlan(filtrar, vista.getContext(),false);
    }
    public static void cargar(){
        if(lista==null){
            Toast.makeText(context, "No hay Vlan", Toast.LENGTH_SHORT).show();
            lista= new ArrayList<>();
            adaptador = new AdapterVlan(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }else{
            adaptador = new AdapterVlan(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrearVlanFragment.opc="editar";
                    Vlan us = lista.get(recyclerView.getChildAdapterPosition(v));

                    CrearVlanFragment.vlan =us;
                    Navigation.findNavController(v).navigate(R.id.crearVlanFragment);
                    fragmentTransaction.addToBackStack(null);
                    // Cambiar
                    fragmentTransaction.commit();
                }
            });
            adaptador.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Vlan us = lista.get(recyclerView.getChildAdapterPosition(v));
                    eliminarRegistroDialog(us);
                    return true;
                }
            });

        }
        Procesos.cargandoDetener();
    }
    private static void eliminarRegistroDialog(Vlan us) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Opciones");
        builder.setMessage("¿Elija la opcion que desea con: "+us.getNombreVlan()+" ?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                        builder.setTitle("Eliminar");
                        builder.setMessage("¿Que tipo de eliminacion desea realizar: "+us.getNombreVlan()+" ?")
                                .setPositiveButton("Normal", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                                        builder.setTitle("Eliminar normal");
                                        builder.setMessage("¿Está seguro que desea realizar una eliminacion normal: "+us.getNombreVlan()+" ?")
                                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(final DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        vlanDAO.eliminarVlan(us.getId(),context);
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
                                        dial.setMessage("Para poder elimanar en cascada: "+us.getNumeroOlt()+"%n ingrese la contraseña admin ")
                                                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(final DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        if (contraAdmin.getText().toString().trim().equals("pullasancho")){
                                                            vlanDAO.eliminarVlanCascada(us.getId(),context);
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
                        builder.setMessage("¿Está seguro que desea desactivar: "+us.getNombreVlan()+" ?")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        us.setEstado("desactivo");
                                        vlanDAO.editarVlan(us,context,true);
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
            List<Vlan> aux2=new ArrayList<>();
            for (Vlan aux:lista) {
                if(aux.getNombreVlan().toLowerCase().contains(filtrar.toLowerCase())){
                    aux2.add(aux);
                }
            }
            adaptador.setAdapterItemBuscarVlan(aux2);
            adaptador.notifyDataSetChanged();
            Procesos.cargandoDetener();
        }
    }

    @Override
    public void setVlan(Vlan Vlan) {
        
    }

    @Override
    public void setListaVlan(List<Vlan> lista) {
        this.lista=lista;
        cargar();
    }

    @Override
    public void limpiar() {

    }
}