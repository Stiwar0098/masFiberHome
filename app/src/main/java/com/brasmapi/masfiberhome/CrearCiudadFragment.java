package com.brasmapi.masfiberhome;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.brasmapi.masfiberhome.ui.DialogBuscarProvincia;
import com.brasmapi.masfiberhome.ui.dao.CiudadDAO;
import com.brasmapi.masfiberhome.ui.entidades.Ciudad;
import com.brasmapi.masfiberhome.ui.entidades.Pais;
import com.brasmapi.masfiberhome.ui.entidades.Provincia;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearCiudadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearCiudadFragment extends Fragment implements DialogBuscarProvincia.finalizoDialogBuscarProvincia {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearCiudadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearCiudadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearCiudadFragment newInstance(String param1, String param2) {
        CrearCiudadFragment fragment = new CrearCiudadFragment();
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
    Button btnBuscarProvincias;
    public static TextInputLayout txtCiudad,txtProvincia;
    Provincia provincia=null;
    CiudadDAO ciudadDAO;
    public static Ciudad ciudad;
    public static String opc=""; // editar/crear
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_crear_ciudad, container, false);
        context=getActivity();
        btnBuscarProvincias =vista.findViewById(R.id.btnBuscarProvincia_CrearCiudad);
        txtCiudad=vista.findViewById(R.id.txtNombreCiudad_CrearCiudad);
        txtProvincia=vista.findViewById(R.id.txtProvincia_CrearCiudad);
        ciudadDAO=new CiudadDAO(null);
        btnBuscarProvincias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogBuscarProvincia(context,CrearCiudadFragment.this);
            }
        });
        Button btnguardar=(Button)vista.findViewById(R.id.btnGuardar_CrearCiudad);
        ((MainActivity)getActivity()).setTitle("Crear Ciudad");
        if (opc.equals("editar")){
            btnguardar.setText("Editar");
            txtCiudad.getEditText().setText(ciudad.getNombre());
            txtProvincia.getEditText().setText(ciudad.getNombreProvincia());
            ((MainActivity)getActivity()).setTitle("Editar Ciudad");
        }
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (opc.equals("crear")){
                    ciudadDAO.crearCiudad(new Ciudad(0,txtCiudad.getEditText().getText().toString(), provincia.getId(), provincia.getNombre(),"activo"),context);
                }else{//editar
                    ciudad.setNombre(txtCiudad.getEditText().getText().toString());
                    if (provincia !=null){
                        ciudad.setIdProvincia(provincia.getId());
                    }
                    ciudadDAO.editarCiudad(ciudad,context,false);
                }
            }
        });
        return vista;
    }

    @Override
    public void ProvinciaSelecionado(Provincia provincia) {
        txtProvincia.getEditText().setText(provincia.getNombre());
        this.provincia=provincia;
    }
}