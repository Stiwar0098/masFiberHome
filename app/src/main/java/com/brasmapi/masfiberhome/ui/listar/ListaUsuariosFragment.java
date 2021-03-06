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
import com.brasmapi.masfiberhome.ui.adaptadores.AdapterUsuario;
import com.brasmapi.masfiberhome.dao.UsuariosDAO;
import com.brasmapi.masfiberhome.entidades.Usuario;
import com.brasmapi.masfiberhome.ui.crear.CrearUsuariosFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaUsuariosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaUsuariosFragment extends Fragment implements UsuariosDAO.usuarioBaseDeDatos{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaUsuariosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaUsuariosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaUsuariosFragment newInstance(String param1, String param2) {
        ListaUsuariosFragment fragment = new ListaUsuariosFragment();
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
    Button btnCrearUsuario;
    static UsuariosDAO usuarioDAO;
    public static AdapterUsuario adaptadorUsuario;
    public static RecyclerView recyclerViewUsuarios;
    public static List<Usuario> listaUsuarios,listaUsuariosaux;
    TextInputLayout txtBuscar;
    FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;
    SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista =inflater.inflate(R.layout.fragment_lista_usuarios, container, false);
        context=getActivity();
        usuarioDAO = new UsuariosDAO(ListaUsuariosFragment.this);
        context=getActivity();
        ((MainActivity)getActivity()).setTitle("Listar usuarios");
        mostrarDatos("");
        btnCrearUsuario=(Button)vista.findViewById(R.id.btnCrearUsuario_listaUsusarios);
        txtBuscar=(TextInputLayout)vista.findViewById(R.id.txtBuscar_ListaUsuarios);
        refreshLayout=(SwipeRefreshLayout)vista.findViewById(R.id.refreshRecycler_listaUsuarios);
        fragmentManager = getActivity().getSupportFragmentManager();
        // Definir una transacci??n
        fragmentTransaction = fragmentManager.beginTransaction();
        // Remplazar el contenido principal por el fragmento
        btnCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearUsuariosFragment.opc="crear";
                Navigation.findNavController(v).navigate(R.id.crearUsuariosFragment);
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
        recyclerViewUsuarios = (RecyclerView)vista.findViewById(R.id.recyclerView_ListaUsuarios);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(context));
        usuarioDAO.filtarUsuarios(filtrar, vista.getContext(),false);
    }
    public static void cargar(){
        if(listaUsuarios==null){
            Toast.makeText(context, "No hay Usuarios", Toast.LENGTH_SHORT).show();
            listaUsuarios= new ArrayList<>();
            adaptadorUsuario = new AdapterUsuario(listaUsuarios);
            recyclerViewUsuarios.setAdapter(adaptadorUsuario);
            adaptadorUsuario.notifyDataSetChanged();
        }else{
            listaUsuariosaux=null;
            adaptadorUsuario = new AdapterUsuario(listaUsuarios);
            recyclerViewUsuarios.setAdapter(adaptadorUsuario);
            adaptadorUsuario.notifyDataSetChanged();
            adaptadorUsuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrearUsuariosFragment.opc="editar";
                    Usuario us;
                    if (listaUsuariosaux==null){
                        us = listaUsuarios.get(recyclerViewUsuarios.getChildAdapterPosition(v));
                    }else{
                        us = listaUsuariosaux.get(recyclerViewUsuarios.getChildAdapterPosition(v));
                    }
                    CrearUsuariosFragment.usuario =us;
                    Navigation.findNavController(v).navigate(R.id.crearUsuariosFragment);
                    fragmentTransaction.addToBackStack(null);
                    // Cambiar
                    fragmentTransaction.commit();
                }
            });
            adaptadorUsuario.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Usuario us;
                    if (listaUsuariosaux==null){
                        us = listaUsuarios.get(recyclerViewUsuarios.getChildAdapterPosition(v));
                    }else{
                        us = listaUsuariosaux.get(recyclerViewUsuarios.getChildAdapterPosition(v));
                    }
                    eliminarRegistroDialog(us);
                    return true;
                }
            });

        }
        Procesos.cargandoDetener();
    }
    private static void eliminarRegistroDialog(Usuario us) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("Confirmaci??n");
        builder.setMessage("Esta eliminaci??n, eliminar?? toda la data relacionada con: "+us.getNombre()+" \n\nPorfavor verifique que ha modificado la dependecia de este dato en: \n\n-Servicios")
                .setPositiveButton("Siguiente", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AlertDialog.Builder builder= new AlertDialog.Builder(context);
                        builder.setTitle("Eliminar");
                        final EditText contraAdmin = new EditText(context);
                        contraAdmin.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(contraAdmin);
                        builder.setMessage("Para poder eliminar: "+us.getNombre()+"\n \nIngrese la contrase??a admin:")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(final DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if (contraAdmin.getText().toString().trim().equals("admin")){
                                            usuarioDAO.eliminarUsuario(us.getId(),context);
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
        listaUsuariosaux=null;
        if (listaUsuarios!=null){
            Procesos.cargandoIniciar(context);
            listaUsuariosaux=new ArrayList<>();
            for (Usuario aux:listaUsuarios) {
                if(Procesos.validarBuscarContains(aux.getNombre(),filtrar)
                        || Procesos.validarBuscarContains(aux.getUsuario(),filtrar)
                        || Procesos.validarBuscarContains(aux.getRol()+"",filtrar)){
                    listaUsuariosaux.add(aux);
                }
            }
            adaptadorUsuario.setAdapterItemBuscarUsuario(listaUsuariosaux);
            adaptadorUsuario.notifyDataSetChanged();
            Procesos.cargandoDetener();
        }
    }

    @Override
    public void usuarioSelecionado() {

    }

    @Override
    public void setListaUsuario(List<Usuario> lista) {
        listaUsuarios=lista;
        cargar();
    }
}