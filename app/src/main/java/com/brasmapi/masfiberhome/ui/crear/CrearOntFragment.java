package com.brasmapi.masfiberhome.ui.crear;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.brasmapi.masfiberhome.ActivacionesPendientesAdminFragment;
import com.brasmapi.masfiberhome.CustomScannerActivity;
import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.HistorialServiciosDAO;
import com.brasmapi.masfiberhome.dao.OntDAO;
import com.brasmapi.masfiberhome.entidades.ModeloOnt;
import com.brasmapi.masfiberhome.entidades.Ont;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.ui.buscar.DialogBuscarModeloOnt;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearOntFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearOntFragment extends Fragment implements OntDAO.interfazOntDAO, DialogBuscarModeloOnt.finalizoDialogBuscarModeloOnt {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearOntFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearOntFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearOntFragment newInstance(String param1, String param2) {
        CrearOntFragment fragment = new CrearOntFragment();
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
    TextInputLayout txtserie, txtmodelo;
    Spinner spinnerResponsable;
    OntDAO OntDAO;
    ModeloOnt modeloOnt = null;
    public static Ont ont;
    public static String opc = ""; // editar/crear
    String serie, modelo, responsable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_crear_ont, container, false);
        context = getActivity();
        Button btnguardar = (Button) vista.findViewById(R.id.btnGuardar_CrearOnt);
        txtserie = vista.findViewById(R.id.txtSerieOnt_CrearOnt);
        txtmodelo = vista.findViewById(R.id.txtIdModelo_CrearOnt);
        spinnerResponsable = vista.findViewById(R.id.spinnerResponsable_CrearOnt);
        String[] opciones = {"Selecionar el responsable", "CLIENTE", "EMPRESA"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, opciones);
        spinnerResponsable.setAdapter(adapter);
        OntDAO = new OntDAO(CrearOntFragment.this);
        ((MainActivity) getActivity()).setTitle("Crear Ont");
        if (opc.equals("editar")) {
            btnguardar.setText("Editar");
            txtserie.getEditText().setText(ont.getSerieOnt());
            txtmodelo.getEditText().setText(ont.getNombreModelo());
            for (int i = 0; i < opciones.length; i++) {
                if (ont.getResponsable().equals(spinnerResponsable.getItemAtPosition(i))) {
                    spinnerResponsable.setSelection(i);
                }
            }
            ((MainActivity) getActivity()).setTitle("Editar Ont");
        }
        txtmodelo.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogBuscarModeloOnt(context, CrearOntFragment.this);
            }
        });
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Procesos.cerrarTeclado(getActivity());
                if (Procesos.validarTxtEstaLleno(txtserie)
                        && Procesos.validarTxtEstaLleno(txtmodelo)
                        && spinnerResponsable.getSelectedItemPosition() != 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirmación");
                    builder.setMessage("Seguro desea " + opc + " ?")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    serie = txtserie.getEditText().getText().toString().trim();
                                    modelo = txtmodelo.getEditText().getText().toString().trim();
                                    responsable = spinnerResponsable.getSelectedItem().toString();
                                    if (opc.equals("crear")) {
                                    } else if (opc.equals("editar")) {//editar
                                        if (ont.getSerieOnt().equals(Procesos.obtenerTxtEnString(txtserie)) &&
                                                ont.getNombreModelo().equals(Procesos.obtenerTxtEnString(txtmodelo)) &&
                                                ont.getResponsable().equals(spinnerResponsable.getSelectedItem().toString())) {
                                            Toast.makeText(context, "No se a cambiado la información", Toast.LENGTH_SHORT).show();
                                        } else {
                                            ont.setSerieOnt(serie);
                                            if (modeloOnt != null) {
                                                ont.setId_modeloOnt(modeloOnt.getId_modeloOnt());
                                                ont.setNombreModelo(modeloOnt.getNombre_modeloOnt());
                                            }
                                            ont.setResponsable(responsable);
                                            OntDAO.editarOnt(ont, context, false);
                                        }
                                    }
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
        txtserie.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpQRCode();
            }
        });
        txtmodelo.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogBuscarModeloOnt(context, CrearOntFragment.this);
            }
        });
        return vista;
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Intent originalIntent = result.getOriginalIntent();
                    if (originalIntent == null) {
                        Log.d("MainActivity", "Cancelled scan1");
                        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
                    } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                        Log.d("MainActivity", "Cancelled scan due to missing camera permission");
                        Toast.makeText(getActivity(), "Cancelled due to missing camera permission", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("MainActivity", "Scanned");
                    txtserie.getEditText().setText(result.getContents() + "");
                    Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            });

    public void setUpQRCode() {
        ScanOptions options = new ScanOptions().setOrientationLocked(false).setCaptureActivity(CustomScannerActivity.class);
        options.setBeepEnabled(false);
        barcodeLauncher.launch(options);
    }

    @Override
    public void setOnt(Ont Ont) {

    }

    @Override
    public void setListaOnt(List<Ont> lista) {

    }

    @Override
    public void limpiarOnt() {
        Procesos.cerrarTeclado(getActivity());
        Procesos.cargandoDetener();
        if (opc.equals("editar")) {
            getActivity().onBackPressed();// para retrocede sin que se guarde el activiti anterior  ejemplo a b c
            // con el codigo de abajo si lo aplico en c para ir a b quedaria asi
            //a b c b al momento de dar vuelta atras se ir nuevamente a c y luego a b
            //al aplicar el codigo de arriba en el mismo ejemplo si lo aplicamos en c quedaria asi
            //a b por ende si doy atras nuevamente se iria a a y ya no a c como el en anterior
            //Navigation.findNavController(getActivity().getCurrentFocus()).navigate(R.id.listaCajasNivel2Fragment);
        }
    }

    @Override
    public void ModeloOntSelecionado(ModeloOnt modeloOnt) {
        txtmodelo.getEditText().setText(modeloOnt.getNombre_modeloOnt());
        this.modeloOnt = modeloOnt;
    }
}