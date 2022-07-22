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

import com.brasmapi.masfiberhome.ui.crear.CrearPlanesFragment;
import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.PlanesDAO;
import com.brasmapi.masfiberhome.entidades.Planes;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterPlanes;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaPlanesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaPlanesFragment extends Fragment implements PlanesDAO.interfazPlanesDAO {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaPlanesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaPlanesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaPlanesFragment newInstance(String param1, String param2) {
        ListaPlanesFragment fragment = new ListaPlanesFragment();
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
    static PlanesDAO planesDAO;
    public static AdapterPlanes adaptador;
    public static RecyclerView recyclerView;
    public static List<Planes> lista,listaaux;
    TextInputLayout txtBuscar;
    FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;
    SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_lista_planes, container, false);
        context=getActivity();
        planesDAO =new PlanesDAO(ListaPlanesFragment.this);
        ((MainActivity)getActivity()).setTitle("Listar Planes");
        mostrarDatos("");
        btnCrear =(Button)vista.findViewById(R.id.btnCrearPlan_ListaPlan);
        txtBuscar=(TextInputLayout)vista.findViewById(R.id.txtBuscar_ListaPlan);
        refreshLayout=(SwipeRefreshLayout)vista.findViewById(R.id.refreshRecycler_listaPlan);
        fragmentManager = getActivity().getSupportFragmentManager();
        // Definir una transacción
        fragmentTransaction = fragmentManager.beginTransaction();
        // Remplazar el contenido principal por el fragmento
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearPlanesFragment.opc="crear";
                Navigation.findNavController(v).navigate(R.id.crearPlanesFragment);
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
        recyclerView = (RecyclerView)vista.findViewById(R.id.recyclerView_ListaPlan);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        planesDAO.filtarPlanes(filtrar, vista.getContext(),false);
    }
    public static void cargar(){
        if(lista==null){
            Toast.makeText(context, "No hay Planes", Toast.LENGTH_SHORT).show();
            lista= new ArrayList<>();
            adaptador = new AdapterPlanes(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }else{
            listaaux=null;
            adaptador = new AdapterPlanes(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrearPlanesFragment.opc="editar";
                    Planes us;
                    if (listaaux==null){
                        us = lista.get(recyclerView.getChildAdapterPosition(v));
                    }else{
                        us = listaaux.get(recyclerView.getChildAdapterPosition(v));
                    }
                    CrearPlanesFragment.planes =us;
                    Navigation.findNavController(v).navigate(R.id.crearPlanesFragment);
                    fragmentTransaction.addToBackStack(null);
                    // Cambiar
                    fragmentTransaction.commit();
                }
            });
            adaptador.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Planes us;
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
    private static void eliminarRegistroDialog(Planes us) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Confirmación");
        builder.setMessage("Esta eliminación, eliminará toda la data relacionada con: "+us.getNombre()+" \n\nPorfavor verifique que ha modificado la dependecia de este dato en: \n\n-Servicios")
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
                                            planesDAO.eliminarPlanes(us.getId(),context);
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
        listaaux=null;
        if (lista!=null){
            Procesos.cargandoIniciar(context);
            listaaux=new ArrayList<>();
            for (Planes aux:lista) {
                if(Procesos.validarBuscarContains(aux.getNombre()+"",filtrar)){
                    listaaux.add(aux);
                }
            }
            adaptador.setAdapterItemBuscarPlanes(listaaux);
            adaptador.notifyDataSetChanged();
            Procesos.cargandoDetener();
        }
    }
    @Override
    public void setPlanes(Planes Planes) {

    }

    @Override
    public void setListaPlanes(List<Planes> lista) {
        this.lista=lista;
        cargar();
    }

    @Override
    public void limpiarPlanes() {

    }
}