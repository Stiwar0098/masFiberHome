package com.brasmapi.masfiberhome;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.brasmapi.masfiberhome.ui.dao.PaisesDAO;
import com.brasmapi.masfiberhome.ui.entidades.Pais;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearPaisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearPaisFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearPaisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearPaisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearPaisFragment newInstance(String param1, String param2) {
        CrearPaisFragment fragment = new CrearPaisFragment();
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
    PaisesDAO paisesDAO;
    public static String opc=""; // editar/crear
    public static TextInputLayout txtNombrePais;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista =inflater.inflate(R.layout.fragment_crear_pais, container, false);
        context=vista.getContext();
        paisesDAO = new PaisesDAO();
        txtNombrePais=(TextInputLayout)vista.findViewById(R.id.txtNombrePais_CrearPais);
        Button btnguardar=(Button)vista.findViewById(R.id.btnGuardar_CrearPais);
        if (opc.equals("editar")){
            btnguardar.setText("Editar");
        }
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Procesos.cargandoIniciar(context);
                if (opc.equals("crear")){
                    paisesDAO.crearPais(new Pais(0,txtNombrePais.getEditText().getText().toString(),"activo"),context);
                }else{//editar
                    paisesDAO.editarPais(new Pais());
                }
            }
        });
        context=getActivity();
        return vista;
    }
}