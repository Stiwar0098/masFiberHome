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
import com.brasmapi.masfiberhome.ui.buscar.DialogBuscarPais;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.ProvinciaDAO;
import com.brasmapi.masfiberhome.entidades.Pais;
import com.brasmapi.masfiberhome.entidades.Provincia;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearProvinciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearProvinciaFragment extends Fragment implements DialogBuscarPais.finalizoDialogBuscarPais {

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
    public static TextInputLayout txtPais, txtProvincia;
    Pais pais = null;
    ProvinciaDAO provinciaDAO;
    public static Provincia provincia;
    public static String opc = ""; // editar/crear

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_crear_provincia, container, false);
        context = getActivity();
        txtPais = vista.findViewById(R.id.txtPais_CrearProvincia);
        txtProvincia = vista.findViewById(R.id.txtProvincia_CrearProvincia);
        provinciaDAO = new ProvinciaDAO(null);
        txtPais.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogBuscarPais(context, CrearProvinciaFragment.this);
            }
        });

        Button btnguardar = (Button) vista.findViewById(R.id.btnGuardar_CrearProvincia);
        ((MainActivity) getActivity()).setTitle("Crear provincia");
        if (opc.equals("editar")) {
            btnguardar.setText("Editar");
            txtProvincia.getEditText().setText(provincia.getNombre());
            txtPais.getEditText().setText(provincia.getNombre_pais());
            ((MainActivity) getActivity()).setTitle("Editar provincia");
        }
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Procesos.cerrarTeclado(getActivity());
                if (Procesos.validarTxtEstaLleno(txtProvincia)
                        && Procesos.validarTxtEstaLleno(txtPais)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirmación");
                    builder.setMessage("Seguro desea " + opc + " ?")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (opc.equals("crear")) {
                                        provinciaDAO.crearProvincia(new Provincia(0, txtProvincia.getEditText().getText().toString(), pais.getId(), pais.getNombre(), "activo"), context);
                                    } else {//editar
                                        if (provincia.getNombre_pais().equals(Procesos.obtenerTxtEnString(txtPais)) &&
                                                provincia.getNombre().equals(Procesos.obtenerTxtEnString(txtProvincia))) {
                                            Toast.makeText(context, "No se a cambiado la información", Toast.LENGTH_SHORT).show();
                                        } else {
                                            provincia.setNombre(txtProvincia.getEditText().getText().toString());
                                            if (pais != null) {
                                                provincia.setPais(pais.getId());
                                            }
                                            provinciaDAO.editarProvincia(provincia, context, false);
                                        }
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
                } else {
                    Toast.makeText(context, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return vista;
    }

    @Override
    public void PaisSelecionado(Pais pais) {
        txtPais.getEditText().setText(pais.getNombre());
        this.pais = pais;
    }
}