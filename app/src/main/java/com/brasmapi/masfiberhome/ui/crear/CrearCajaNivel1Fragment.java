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
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.CajaNivel1DAO;
import com.brasmapi.masfiberhome.entidades.CajaNivel1;
import com.brasmapi.masfiberhome.entidades.Ciudad;
import com.brasmapi.masfiberhome.entidades.Vlan;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.ui.buscar.DialogBuscarCiudad;
import com.brasmapi.masfiberhome.ui.buscar.DialogBuscarVlan;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearCajaNivel1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearCajaNivel1Fragment extends Fragment implements CajaNivel1DAO.interfazCajaNivel1DAO, DialogBuscarCiudad.finalizoDialogBuscarCiudad, DialogBuscarVlan.finalizoDialogBuscarVlan, View.OnClickListener,Procesos.LatitudLongitud {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearCajaNivel1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearCajaNivel1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearCajaNivel1Fragment newInstance(String param1, String param2) {
        CrearCajaNivel1Fragment fragment = new CrearCajaNivel1Fragment();
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
    TextInputLayout txtNombreCajaNivel1, txtDireccion, txtReferencia, txtLatitud, txtLongitud, txtNombreVlan, txtNombreCiudad,txtAbreviatura;
    CajaNivel1DAO cajaNivel1DAO;
    public static CajaNivel1 cajaNivel1;
    public static String opc=""; // editar/crear

    Ciudad ciudad=null;
    Vlan vlan=null;
    Button btnObtenerLatLon,btnVerUbicacoin;
    Switch switchAutoManu;
    String nombre, direccion, referencia, latitud, longitud,abreviatura;
    int hilos;
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_crear_caja_nivel1, container, false);
        context=getActivity();
        Button btnguardar=(Button)vista.findViewById(R.id.btnGuardar_CrearCajaNivel1);
        btnVerUbicacoin=(Button)vista.findViewById(R.id.btnVerUbicacion_CrearCajaNivel1);
        btnObtenerLatLon=(Button)vista.findViewById(R.id.btnObtenerLatitudLongitud_CrearCajaNivel1);
        txtNombreCajaNivel1 =vista.findViewById(R.id.txtNombreCajaNivel1_CrearCajaNivel1);
        switchAutoManu =vista.findViewById(R.id.switch_CrearCajaNivel1);
        txtDireccion =vista.findViewById(R.id.txtDireccion_CrearCajaNivel1);
        txtReferencia =vista.findViewById(R.id.txtReferencia_CrearCajaNivel1);
        txtLatitud =vista.findViewById(R.id.txtLatitud_CrearCajaNivel1);
        txtLongitud =vista.findViewById(R.id.txtLongitud_CrearCajaNivel1);
        txtNombreVlan =vista.findViewById(R.id.txtVlan_CrearCajaNivel1);
        txtNombreCiudad =vista.findViewById(R.id.txtCiudad_CrearCajaNivel1);
        txtAbreviatura =vista.findViewById(R.id.txtAbreviatura_CrearCajaNivel1);
        spinner =vista.findViewById(R.id.spinnerNumeroHilos_CrearCajaNivel1);
        cajaNivel1DAO =new CajaNivel1DAO(CrearCajaNivel1Fragment.this);
        spinner=vista.findViewById(R.id.spinnerNumeroHilos_CrearCajaNivel1);

        String [] opciones={"Seleccionar cantidad hilos","4","8","16"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,opciones);
        spinner.setAdapter(adapter);
        ((MainActivity)getActivity()).setTitle("Crear caja nivel 1");
        if (opc.equals("editar")){
            btnguardar.setText("Editar");
            txtNombreCajaNivel1.getEditText().setText(cajaNivel1.getNombre_cajaNivel1());
            txtDireccion.getEditText().setText(cajaNivel1.getDireccion_cajaNivel1()+"");
            txtReferencia.getEditText().setText(cajaNivel1.getReferencia_cajaNivel1()+"");
            txtLatitud.getEditText().setText(cajaNivel1.getLatitud_cajaNivel1()+"");
            txtLongitud.getEditText().setText(cajaNivel1.getLongitud_cajaNivel1());
            txtNombreVlan.getEditText().setText(cajaNivel1.getNombreVlan());
            txtNombreCiudad.getEditText().setText(cajaNivel1.getNombreCiudad());
            txtAbreviatura.getEditText().setText(cajaNivel1.getAbreviatura_cajaNivel1());
            switch (cajaNivel1.getNumeroHilos_cajaNivel1()){
                case 4:
                    spinner.setSelection(1);
                    break;
                case 8:
                    spinner.setSelection(2);
                    break;
                case 16:
                    spinner.setSelection(3);
                    break;
            }
            ((MainActivity)getActivity()).setTitle("Editar caja nivel 1");
            switchAutoManu.setChecked(false);
            latLonAutomaticoApagado();
        }else {
            latLonAutomaticoEncendido();
        }
        btnguardar.setOnClickListener(this);
        btnObtenerLatLon.setOnClickListener(this);
        btnVerUbicacoin.setOnClickListener(this);

        txtNombreVlan.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogBuscarVlan(context,CrearCajaNivel1Fragment.this);
            }
        });

        txtNombreCiudad.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogBuscarCiudad(context,CrearCajaNivel1Fragment.this);
            }
        });

        switchAutoManu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    latLonAutomaticoEncendido();
                }else{
                    latLonAutomaticoApagado();
                    txtLatitud.getEditText().setText("");
                    txtLongitud.getEditText().setText("");
                }
            }
        });
        return vista;
    }
    public void latLonAutomaticoApagado(){
        switchAutoManu.setText("Manual");
        txtLatitud.getEditText().setEnabled(true);
        txtLongitud.getEditText().setEnabled(true);
        Procesos.detenerObtenerLatitudLongitud();
    }
    public void latLonAutomaticoEncendido(){
        switchAutoManu.setText("Automático");
        txtLatitud.getEditText().setEnabled(false);
        txtLongitud.getEditText().setEnabled(false);
        Procesos.obtenerLatitudLongitud(context, CrearCajaNivel1Fragment.this,getActivity().getContentResolver());
    }
    @Override
    public void setCajaNivel1(CajaNivel1 CajaNivel1) {

    }

    @Override
    public void setListaCajaNivel1(List<CajaNivel1> lista) {

    }

    @Override
    public void limpiar() {
        txtNombreCajaNivel1.getEditText().setText("");
        txtDireccion.getEditText().setText("");
        txtReferencia.getEditText().setText("");
        txtLatitud.getEditText().setText("");
        txtLongitud.getEditText().setText("");
        txtNombreVlan.getEditText().setText("");
        txtNombreCiudad.getEditText().setText("");
        txtAbreviatura.getEditText().setText("");
        spinner.setSelection(0);
        txtNombreCajaNivel1.getEditText().requestFocusFromTouch();
        Procesos.cerrarTeclado(getActivity());
        txtNombreCajaNivel1.setErrorEnabled(false);
        txtDireccion.setErrorEnabled(false);
        txtReferencia.setErrorEnabled(false);
        txtLatitud.setErrorEnabled(false);
        txtLongitud.setErrorEnabled(false);
        txtNombreVlan.setErrorEnabled(false);
        txtNombreCiudad.setErrorEnabled(false);
        txtAbreviatura.setErrorEnabled(false);
        Procesos.cargandoDetener();
        getActivity().onBackPressed();
    }

    @Override
    public void CiudadSelecionado(Ciudad ciudad) {
        this.ciudad=ciudad;
        txtNombreCiudad.getEditText().setText(ciudad.getNombre());
    }

    @Override
    public void VlanSelecionado(Vlan vlan) {
        this.vlan=vlan;
        txtNombreVlan.getEditText().setText(vlan.getNombreVlan());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardar_CrearCajaNivel1:
                Procesos.cerrarTeclado(getActivity());
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Confirmación");
                builder.setMessage( "Seguro desea "+opc+" ?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                nombre=txtNombreCajaNivel1.getEditText().getText().toString().trim();
                                direccion = txtDireccion.getEditText().getText().toString().trim();
                                referencia = txtReferencia.getEditText().getText().toString().trim();
                                latitud = txtLatitud.getEditText().getText().toString().trim();
                                longitud =txtLongitud.getEditText().getText().toString().trim();
                                abreviatura =txtAbreviatura.getEditText().getText().toString().trim();
                                hilos=Integer.parseInt(spinner.getSelectedItem().toString());
                                if (opc.equals("crear")){
                                    cajaNivel1DAO.crearCajaNivel1(new CajaNivel1(0,
                                            nombre,
                                            direccion,
                                            referencia,
                                            latitud,
                                            longitud,
                                            vlan.getId(),
                                            "",
                                            ciudad.getId(),
                                            "",
                                            "activo",abreviatura,hilos),context);
                                }else{//editar
                                    cajaNivel1.setNombre_cajaNivel1(nombre);
                                    cajaNivel1.setDireccion_cajaNivel1(direccion);
                                    cajaNivel1.setReferencia_cajaNivel1(referencia);
                                    cajaNivel1.setLatitud_cajaNivel1(latitud);
                                    cajaNivel1.setLongitud_cajaNivel1(longitud);
                                    cajaNivel1.setAbreviatura_cajaNivel1(abreviatura);
                                    cajaNivel1.setNumeroHilos_cajaNivel1(hilos);
                                    if (vlan!=null && ciudad!=null){
                                        cajaNivel1.setId_vlan(vlan.getId());
                                        cajaNivel1.setId_ciudad(ciudad.getId());
                                    }
                                    cajaNivel1DAO.editarCajaNivel1(cajaNivel1,context,false);
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
                break;
            case R.id.btnObtenerLatitudLongitud_CrearCajaNivel1:
                Procesos.obtenerLatitudLongitud(context, CrearCajaNivel1Fragment.this,getActivity().getContentResolver());
                //Procesos.obtenerLatitudLongitud(context,CrearCajaNivel1Fragment.this);
                break;
            case R.id.btnVerUbicacion_CrearCajaNivel1:
                startActivity(Procesos.comoLlegar(getActivity(),txtLatitud.getEditText().getText().toString(),txtLongitud.getEditText().getText().toString()));
                break;
        }
    }


    @Override
    public void setLatitudLongitud(String latitud, String longitud) {
        txtLatitud.getEditText().setText(latitud);
        txtLongitud.getEditText().setText(longitud);
    }
}