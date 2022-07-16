package com.brasmapi.masfiberhome.ui.crear;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.UsuariosDAO;
import com.brasmapi.masfiberhome.entidades.Usuario;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearUsuariosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearUsuariosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearUsuariosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearUsuariosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearUsuariosFragment newInstance(String param1, String param2) {
        CrearUsuariosFragment fragment = new CrearUsuariosFragment();
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
    Context context;
    UsuariosDAO usuariosDAO;
    public static String opc=""; // editar/crear
    public static TextInputLayout txtNombreUsuario, txtUsuario,txtContra;
    public static Usuario usuario;
    Spinner spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         vista=inflater.inflate(R.layout.fragment_crearusuarios, container, false);
         context=getActivity();
        spinner=vista.findViewById(R.id.spinnerRol_CrearUsuario);
        String [] opciones={"ADMINISTRADOR","TECNICO"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,opciones);
        spinner.setAdapter(adapter);
        usuariosDAO = new UsuariosDAO(null);
        txtNombreUsuario=(TextInputLayout)vista.findViewById(R.id.txtNombreUsuario_CrearUsuario);
        txtUsuario=(TextInputLayout)vista.findViewById(R.id.txtUsuario_CrearUsuario);
        txtContra=(TextInputLayout)vista.findViewById(R.id.txtContra_CrearUsuario);
        Button btnguardar=(Button)vista.findViewById(R.id.btnGuardar_CrearUsuario);
        ((MainActivity)getActivity()).setTitle("Crear Usuario");
        if (opc.equals("editar")){
            btnguardar.setText("Editar");
            txtNombreUsuario.getEditText().setText(usuario.getNombre());
            txtUsuario.getEditText().setText(usuario.getUsuario());
            txtContra.getEditText().setText(usuario.getContrasena());
            spinner.setSelection(usuario.getRol()-1);
            ((MainActivity)getActivity()).setTitle("Editar Usuario");
        }
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opc.equals("crear")){
                    usuariosDAO.crearUsuario(new Usuario(0,txtNombreUsuario.getEditText().getText().toString(),txtUsuario.getEditText().getText().toString(),txtContra.getEditText().getText().toString(),spinner.getSelectedItemPosition()+1,"activo"),context);
                }else{//editar
                    usuario.setNombre(txtNombreUsuario.getEditText().getText().toString());
                    usuario.setUsuario(txtUsuario.getEditText().getText().toString().trim());
                    usuario.setContrasena(txtContra.getEditText().getText().toString().trim());
                    usuario.setRol(spinner.getSelectedItemPosition()+1);
                    usuariosDAO.editarUsuario(usuario,context,false);
                }
                getActivity().onBackPressed();
            }
        });
        return vista;
    }
}