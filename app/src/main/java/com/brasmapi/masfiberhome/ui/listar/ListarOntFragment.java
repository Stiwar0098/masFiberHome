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
import android.widget.EditText;
import android.widget.Toast;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.OntDAO;
import com.brasmapi.masfiberhome.entidades.Ont;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterOnt;
import com.brasmapi.masfiberhome.ui.crear.CrearOntFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarOntFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarOntFragment extends Fragment implements OntDAO.interfazOntDAO {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListarOntFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarOntFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarOntFragment newInstance(String param1, String param2) {
        ListarOntFragment fragment = new ListarOntFragment();
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
    static OntDAO ontDAO;
    public static AdapterOnt adaptador;
    public static RecyclerView recyclerView;
    public static List<Ont> lista,listaaux;
    TextInputLayout txtBuscar;
    FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;
    SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_listar_ont, container, false);
        context=getActivity();
        ontDAO =new OntDAO(ListarOntFragment.this);
        ((MainActivity)getActivity()).setTitle("Listar Onts");
        mostrarDatos("");
        txtBuscar=(TextInputLayout)vista.findViewById(R.id.txtBuscar_ListaOnt);
        refreshLayout=(SwipeRefreshLayout)vista.findViewById(R.id.refreshRecycler_listaOnt);
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
                mostrarDatos("");
                refreshLayout.setRefreshing(false);
            }
        });
        return vista;
    }
    public void mostrarDatos(String filtrar){
        // crear lista de carview dentro del recycleview
        recyclerView = (RecyclerView)vista.findViewById(R.id.recyclerView_ListaOnt);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ontDAO.filtarOnt(filtrar, vista.getContext(),false);
    }
    public static void cargar(){
        if(lista==null){
            Toast.makeText(context, "No hay Ont", Toast.LENGTH_SHORT).show();
            lista= new ArrayList<>();
            adaptador = new AdapterOnt(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
        }else{
            listaaux=null;
            adaptador = new AdapterOnt(lista);
            recyclerView.setAdapter(adaptador);
            adaptador.notifyDataSetChanged();
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrearOntFragment.opc="editar";
                    Ont us;
                    if (listaaux==null){
                        us = lista.get(recyclerView.getChildAdapterPosition(v));
                    }else{
                        us = listaaux.get(recyclerView.getChildAdapterPosition(v));
                    }
                    CrearOntFragment.ont =us;
                    Navigation.findNavController(v).navigate(R.id.crearOntFragment);
                    fragmentTransaction.addToBackStack(null);
                    // Cambiar
                    fragmentTransaction.commit();
                }
            });
        }
        Procesos.cargandoDetener();
    }
    private static void eliminarRegistroDialog(Ont us) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Confirmaci??n");
        builder.setMessage("Esta eliminaci??n, eliminar?? toda la data relacionada con: "+us.getSerieOnt()+" \n\nPorfavor verifique que ha modificado la dependecia de este dato en: \n\n-Servicios")
                .setPositiveButton("Siguiente", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                        builder.setTitle("Eliminar");
                        final EditText contraAdmin = new EditText(context);
                        contraAdmin.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(contraAdmin);
                        builder.setMessage("Para poder eliminar: "+us.getSerieOnt()+"\n \nIngrese la contrase??a admin:")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if (contraAdmin.getText().toString().trim().equals("admin")){
                                            ontDAO.eliminarOnt(us.getId(),context);
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
    /*private static void eliminarRegistroDialog(Ont us) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Opciones");
        builder.setMessage("??Elija la opcion que desea con: "+us.getSerieOnt()+" ?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                        builder.setTitle("Eliminar");
                        builder.setMessage("??Que tipo de eliminacion desea realizar: "+us.getSerieOnt()+" ?")
                                .setPositiveButton("Normal", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                                        builder.setTitle("Eliminar normal");
                                        builder.setMessage("??Est?? seguro que desea realizar una eliminacion normal: "+us.getSerieOnt()+" ?")
                                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(final DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        ontDAO.eliminarOnt(us.getId(),context);
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
                                        dial.setMessage("Para poder elimanar en cascada: "+us.getSerieOnt()+"%n ingrese la contrase??a admin ")
                                                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(final DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        if (contraAdmin.getText().toString().trim().equals("pullasancho")){
                                                            ontDAO.eliminarOntCascada(us.getId(),context);
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
                        builder.setMessage("??Est?? seguro que desea desactivar: "+us.getSerieOnt()+" ?")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        us.setEstado("desactivo");
                                        ontDAO.editarOnt(us,context,true);
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
    }*/

    private void filtrar(String filtrar){
        listaaux=null;
        if (lista!=null){
            Procesos.cargandoIniciar(context);
            listaaux=new ArrayList<>();
            for (Ont aux:lista) {
                if(Procesos.validarBuscarContains(aux.getSerieOnt(),filtrar)
                        || Procesos.validarBuscarContains(aux.getNombreModelo(),filtrar)
                        || Procesos.validarBuscarContains(aux.getResponsable(),filtrar)){
                    listaaux.add(aux);
                }
            }
            adaptador.setAdapterItemBuscarOnt(listaaux);
            adaptador.notifyDataSetChanged();
            Procesos.cargandoDetener();
        }
    }
    @Override
    public void setOnt(Ont Ont) {

    }

    @Override
    public void setListaOnt(List<Ont> lista) {
        this.lista=lista;
        cargar();
    }

    @Override
    public void limpiarOnt() {

    }

}