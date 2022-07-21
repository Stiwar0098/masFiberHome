package com.brasmapi.masfiberhome.ui.crear;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.ui.buscar.DialogBuscarProvincia;
import com.brasmapi.masfiberhome.dao.CiudadDAO;
import com.brasmapi.masfiberhome.entidades.Ciudad;
import com.brasmapi.masfiberhome.entidades.Provincia;
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
        txtCiudad=vista.findViewById(R.id.txtNombreCiudad_CrearCiudad);
        txtProvincia=vista.findViewById(R.id.txtProvincia_CrearCiudad);
        ciudadDAO=new CiudadDAO(null);
        txtProvincia.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                Procesos.cerrarTeclado(getActivity());
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Confirmación");
                builder.setMessage( "Seguro desea "+opc+" ?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (opc.equals("crear")){
                                    ciudadDAO.crearCiudad(new Ciudad(0,txtCiudad.getEditText().getText().toString(), provincia.getId(), provincia.getNombre(),"activo"),context);
                                }else{//editar
                                    ciudad.setNombre(txtCiudad.getEditText().getText().toString());
                                    if (provincia !=null){
                                        ciudad.setIdProvincia(provincia.getId());
                                    }
                                    ciudadDAO.editarCiudad(ciudad,context,false);
                                }
                                getActivity().onBackPressed();
                            }
                        })
                        .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
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