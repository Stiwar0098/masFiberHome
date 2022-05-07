package com.brasmapi.masfiberhome;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.brasmapi.masfiberhome.ui.items.ItemBuscarPais;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearProvinciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearProvinciaFragment extends Fragment implements DialogBuscarPais.finalizoDialogBuscarPais{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearProvinciaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearProvinciaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearProvinciaFragment newInstance(String param1, String param2) {
        CrearProvinciaFragment fragment = new CrearProvinciaFragment();
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
    Button btnBuscarPaises;
    TextInputLayout txtPais;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista =inflater.inflate(R.layout.fragment_crear_provincia, container, false);
        context=getActivity();
        btnBuscarPaises=vista.findViewById(R.id.btnBuscarPais_CrearProvincia);
        txtPais=vista.findViewById(R.id.txtPais_CrearProvincia);
        btnBuscarPaises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogBuscarPais(context,CrearProvinciaFragment.this);
            }
        });
        return vista;
    }

    @Override
    public void PaisSelecionado(ItemBuscarPais pais) {
        txtPais.getEditText().setText(pais.getNombre());
    }
}