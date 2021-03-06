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
import com.brasmapi.masfiberhome.dao.PaisesDAO;
import com.brasmapi.masfiberhome.entidades.Pais;
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
    public static String opc = ""; // editar/crear
    public static TextInputLayout txtNombrePais;
    public static Pais pais;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_crear_pais, container, false);
        context = vista.getContext();
        paisesDAO = new PaisesDAO(null);
        txtNombrePais = (TextInputLayout) vista.findViewById(R.id.txtNombrePais_CrearPais);
        Button btnguardar = (Button) vista.findViewById(R.id.btnGuardar_CrearPais);
        ((MainActivity) getActivity()).setTitle("Crear pais");
        if (opc.equals("editar")) {
            btnguardar.setText("Editar");
            txtNombrePais.getEditText().setText(pais.getNombre());
            ((MainActivity) getActivity()).setTitle("Editar pais");
        }
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Procesos.cerrarTeclado(getActivity());
                if (Procesos.validarTxtEstaLleno(txtNombrePais)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirmaci??n");
                    builder.setMessage("Seguro desea " + opc + " ?")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (opc.equals("crear")) {
                                        paisesDAO.crearPais(new Pais(0, txtNombrePais.getEditText().getText().toString(), "activo"), context);
                                    } else {//editar
                                        if (pais.getNombre().equals(Procesos.obtenerTxtEnString(txtNombrePais))) {
                                            Toast.makeText(context, "No se a cambiado la informaci??n", Toast.LENGTH_SHORT).show();
                                        } else {
                                            pais.setNombre(txtNombrePais.getEditText().getText().toString());
                                            paisesDAO.editarPais(pais, context, false);
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
}