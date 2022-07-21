package com.brasmapi.masfiberhome.ui.crear;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.ModeloOntDAO;
import com.brasmapi.masfiberhome.entidades.ModeloOnt;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearModeloOntFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearModeloOntFragment extends Fragment implements ModeloOntDAO.interfazModeloOntDAO {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearModeloOntFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearModeloOntFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearModeloOntFragment newInstance(String param1, String param2) {
        CrearModeloOntFragment fragment = new CrearModeloOntFragment();
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
    TextInputLayout txtNombreModeloOnt;
    Spinner spinnerTipoModeloOnt;
    ModeloOntDAO modeloOntDAO;
    public static ModeloOnt modeloOnt;
    public static String opc=""; // editar/crear
    String nombre,tipo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_crear_modelo_ont, container, false);
        context=getActivity();
        Button btnguardar=(Button)vista.findViewById(R.id.btnGuardar_CrearModeloOnt);
        txtNombreModeloOnt =vista.findViewById(R.id.txtNombre_crearModeloOnt);
        spinnerTipoModeloOnt =vista.findViewById(R.id.spinnerTipo_CrearModeloOnt);
        modeloOntDAO =new ModeloOntDAO(CrearModeloOntFragment.this);
        String [] opciones={"Selecionar tipo de ont","NORMAL","DOBLE BANDA","BRIDGE"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,opciones);
        spinnerTipoModeloOnt.setAdapter(adapter);
        ((MainActivity)getActivity()).setTitle("Crear ModeloOnt");
        if (opc.equals("editar")){
            btnguardar.setText("Editar");
            txtNombreModeloOnt.getEditText().setText(modeloOnt.getNombre_modeloOnt());
            for (int i=0;i<opciones.length;i++){
                if(modeloOnt.getTipo_modeloOnt().equals(spinnerTipoModeloOnt.getItemAtPosition(i))){
                    spinnerTipoModeloOnt.setSelection(i);
                }
            }
            ((MainActivity)getActivity()).setTitle("Editar ModeloOnt");
        }
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Procesos.cerrarTeclado(getActivity());
                if(Procesos.validarTxtEstaLleno(txtNombreModeloOnt)
                    && spinnerTipoModeloOnt.getSelectedItemPosition()!=0){
                    AlertDialog.Builder builder= new AlertDialog.Builder(context);
                    builder.setTitle("Confirmación");
                    builder.setMessage( "Seguro desea "+opc+" ?")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    nombre = txtNombreModeloOnt.getEditText().getText().toString().trim();
                                    tipo=spinnerTipoModeloOnt.getSelectedItem().toString();
                                    if (opc.equals("crear")) {
                                        modeloOntDAO.crearModeloOnt(new ModeloOnt(0,
                                                nombre,tipo,
                                                "activo"), context);
                                    } else {//editar
                                        modeloOnt.setNombre_modeloOnt(nombre);
                                        modeloOnt.setTipo_modeloOnt(tipo);
                                        modeloOntDAO.editarModeloOnt(modeloOnt, context, false);
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
                }else{
                    Toast.makeText(context, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return vista;
    }

    @Override
    public void setModeloOnt(ModeloOnt ModeloOnt) {
        
    }

    @Override
    public void setListaModeloOnt(List<ModeloOnt> lista) {

    }

    @Override
    public void limpiarModeloOnt() {
        txtNombreModeloOnt.getEditText().setText("");
        spinnerTipoModeloOnt.setSelection(0);
        Procesos.cerrarTeclado(getActivity());
        Procesos.cargandoDetener();
        getActivity().onBackPressed();
        if (opc.equals("editar")){
            //getActivity().onBackPressed();// para retrocede sin que se guarde el activiti anterior  ejemplo a b c
            // con el codigo de abajo si lo aplico en c para ir a b quedaria asi
            //a b c b al momento de dar vuelta atras se ir nuevamente a c y luego a b
            //al aplicar el codigo de arriba en el mismo ejemplo si lo aplicamos en c quedaria asi
            //a b por ende si doy atras nuevamente se iria a a y ya no a c como el en anterior
            //Navigation.findNavController(getActivity().getCurrentFocus()).navigate(R.id.listaCajasNivel2Fragment);
        }
    }
}