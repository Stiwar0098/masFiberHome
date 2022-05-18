package com.brasmapi.masfiberhome;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.brasmapi.masfiberhome.ui.dao.VlanDAO;
import com.brasmapi.masfiberhome.ui.entidades.Vlan;
import com.brasmapi.masfiberhome.ui.entidades.Provincia;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearVlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearVlanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearVlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearVlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearVlanFragment newInstance(String param1, String param2) {
        CrearVlanFragment fragment = new CrearVlanFragment();
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
    public static TextInputLayout txtVlan,txtProvincia;
    VlanDAO VlanDAO;
    public static Vlan vlan;
    public static String opc=""; // editar/crear
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).setTitle("Crear Vlan");
        return inflater.inflate(R.layout.fragment_crear_vlan, container, false);
    }
}