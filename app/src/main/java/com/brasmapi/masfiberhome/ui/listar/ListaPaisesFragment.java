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
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterPais;
import com.brasmapi.masfiberhome.dao.PaisesDAO;
import com.brasmapi.masfiberhome.entidades.Pais;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterVlan;
import com.brasmapi.masfiberhome.ui.crear.CrearPaisFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaPaisesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaPaisesFragment extends Fragment implements PaisesDAO.interfazPaisDao{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaPaisesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaPaisesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaPaisesFragment newInstance(String param1, String param2) {
        ListaPaisesFragment fragment = new ListaPaisesFragment();
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
    Button btnCrearPais;
    static PaisesDAO paisesDAO;
    public static AdapterPais adaptadorPaises;
    public static RecyclerView recyclerViewPaises;
    public static List<Pais> listaPaises,listaPaisesaux;
    TextInputLayout txtBuscar;
    FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;
    SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_lista_paises, container, false);
        paisesDAO = new PaisesDAO(ListaPaisesFragment.this);
        context=getActivity();
        ((MainActivity)getActivity()).setTitle("Listar paises");
        mostrarDatos("");
        btnCrearPais =(Button)vista.findViewById(R.id.btnCrearPais_ListaPais);
        txtBuscar=(TextInputLayout)vista.findViewById(R.id.txtBuscarPais_ListaPaises);
        refreshLayout=(SwipeRefreshLayout)vista.findViewById(R.id.refreshRecycler_listaPais);
       fragmentManager = getActivity().getSupportFragmentManager();
        // Definir una transacción
        fragmentTransaction = fragmentManager.beginTransaction();
        // Remplazar el contenido principal por el fragmento
        btnCrearPais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearPaisFragment.opc="crear";
                Navigation.findNavController(v).navigate(R.id.crearPaisFragment);
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
        recyclerViewPaises = (RecyclerView)vista.findViewById(R.id.recyclerView_ListaPaises);
        recyclerViewPaises.setLayoutManager(new LinearLayoutManager(context));
        paisesDAO.filtarPaises(filtrar, vista.getContext(),false);
    }
    public static void cargar(){
        if(listaPaises==null){
            Toast.makeText(context, "No hay paises", Toast.LENGTH_SHORT).show();
            listaPaises= new ArrayList<>();
            adaptadorPaises = new AdapterPais(listaPaises);
            recyclerViewPaises.setAdapter(adaptadorPaises);
            adaptadorPaises.notifyDataSetChanged();
        }else{
            listaPaisesaux=null;
            adaptadorPaises = new AdapterPais(listaPaises);
            recyclerViewPaises.setAdapter(adaptadorPaises);
            adaptadorPaises.notifyDataSetChanged();
            adaptadorPaises.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrearPaisFragment.opc="editar";
                    Pais us;
                    if (listaPaisesaux==null){
                        us = listaPaises.get(recyclerViewPaises.getChildAdapterPosition(v));
                    }else{
                        us = listaPaisesaux.get(recyclerViewPaises.getChildAdapterPosition(v));
                    }
                    CrearPaisFragment.pais=us;
                    Navigation.findNavController(v).navigate(R.id.crearPaisFragment);
                    fragmentTransaction.addToBackStack(null);
                    // Cambiar
                    fragmentTransaction.commit();
                }
            });
            adaptadorPaises.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Pais us;
                    if (listaPaisesaux==null){
                        us = listaPaises.get(recyclerViewPaises.getChildAdapterPosition(v));
                    }else{
                        us = listaPaisesaux.get(recyclerViewPaises.getChildAdapterPosition(v));
                    }
                    eliminarRegistroDialog(us);
                    return true;
                }
            });
            
        }
        Procesos.cargandoDetener();
    }

    private static void eliminarRegistroDialog(Pais us) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Opciones");
        builder.setMessage("¿Elija la opcion que desea con: "+us.getNombre()+" ?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                        builder.setTitle("Eliminar");
                        builder.setMessage("¿Que tipo de eliminacion desea realizar: "+us.getNombre()+" ?")
                                .setPositiveButton("Normal", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                                        builder.setTitle("Eliminar normal");
                                        builder.setMessage("¿Está seguro que desea realizar una eliminacion normal: "+us.getNombre()+" ?")
                                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(final DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        paisesDAO.eliminarPais(us.getId(),context);
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
                                        dial.setMessage("Para poder elimanar en cascada: "+us.getNombre()+"%n ingrese la contraseña admin ")
                                                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(final DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        if (contraAdmin.getText().toString().trim().equals("pullasancho")){
                                                            paisesDAO.eliminarPaisCascada(us.getId(),context);
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
                        builder.setMessage("¿Está seguro que desea desactivar: "+us.getNombre()+" ?")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        us.setEstado("desactivo");
                                        paisesDAO.editarPais(us,context,true);
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
        listaPaisesaux=null;
        if (listaPaises!=null){
            Procesos.cargandoIniciar(context);
            listaPaisesaux=new ArrayList<>();
            for (Pais aux:listaPaises) {
                if(Procesos.validarBuscarContains(aux.getNombre(),filtrar)){
                    listaPaisesaux.add(aux);
                }
            }
            adaptadorPaises.setAdapterItemBuscarPais(listaPaisesaux);
            adaptadorPaises.notifyDataSetChanged();
            Procesos.cargandoDetener();
        }
    }

    @Override
    public void setPais(Pais pais) {

    }

    @Override
    public void setListaPais(List<Pais> pais) {
        listaPaises=pais;
        cargar();
    }
}