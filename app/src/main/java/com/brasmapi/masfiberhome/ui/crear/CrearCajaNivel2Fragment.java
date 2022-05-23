package com.brasmapi.masfiberhome.ui.crear;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.CajaNivel2DAO;
import com.brasmapi.masfiberhome.entidades.CajaNivel1;
import com.brasmapi.masfiberhome.entidades.CajaNivel2;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.ui.buscar.DialogBuscarCajaNivel1;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearCajaNivel2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearCajaNivel2Fragment extends Fragment implements DialogBuscarCajaNivel1.finalizoDialogBuscarCajaNivel1, CajaNivel2DAO.interfazCajaNivel2DAO, Procesos.LatitudLongitud, View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearCajaNivel2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearCajaNivel2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearCajaNivel2Fragment newInstance(String param1, String param2) {
        CrearCajaNivel2Fragment fragment = new CrearCajaNivel2Fragment();
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
    TextInputLayout txtNombreCajaNivel2, txtDireccion, txtReferencia, txtLatitud, txtLongitud, txtNombreCajaNivel1;
    CajaNivel2DAO cajaNivel2DAO;
    public static CajaNivel2 cajaNivel2;
    CajaNivel1 cajaNivel1Seleccionada =null;
    public static String opc=""; // editar/crear

    Button btnBuscarCajaNivel1;
    Switch switchAutoManu;
    String nombre, direccion, referencia, latitud, longitud;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_crear_caja_nivel2, container, false);
        context=getActivity();
        Button btnguardar=(Button)vista.findViewById(R.id.btnGuardar_CrearCajaNivel2);
        btnBuscarCajaNivel1 =(Button)vista.findViewById(R.id.btnBuscarCajaNivel1_CrearCajaNivel2);
        txtNombreCajaNivel2 =vista.findViewById(R.id.txtNombreCajaNivel2_CrearCajaNivel2);
        switchAutoManu =vista.findViewById(R.id.switch_CrearCajaNivel2);
        txtDireccion =vista.findViewById(R.id.txtDireccion_CrearCajaNivel2);
        txtReferencia =vista.findViewById(R.id.txtReferencia_CrearCajaNivel2);
        txtLatitud =vista.findViewById(R.id.txtLatitud_CrearCajaNivel2);
        txtLongitud =vista.findViewById(R.id.txtLongitud_CrearCajaNivel2);
        txtNombreCajaNivel1 =vista.findViewById(R.id.txtCajaNivel1_CrearCajaNivel2);
        cajaNivel2DAO =new CajaNivel2DAO(CrearCajaNivel2Fragment.this);
        ((MainActivity)getActivity()).setTitle("Crear caja nivel 2");
        if (opc.equals("editar")){
            btnguardar.setText("Editar");
            txtNombreCajaNivel2.getEditText().setText(cajaNivel2.getNombre_CajaNivel2());
            txtDireccion.getEditText().setText(cajaNivel2.getDireccion_CajaNivel2()+"");
            txtReferencia.getEditText().setText(cajaNivel2.getReferencia_CajaNivel2()+"");
            txtLatitud.getEditText().setText(cajaNivel2.getLatitud_CajaNivel2()+"");
            txtLongitud.getEditText().setText(cajaNivel2.getLongitud_CajaNivel2());
            txtNombreCajaNivel1.getEditText().setText(cajaNivel2.getNombreCajaNivel1());
            ((MainActivity)getActivity()).setTitle("Editar caja nivel 2");
            switchAutoManu.setChecked(false);
            latLonAutomaticoApagado();
        }else {
            latLonAutomaticoEncendido();
        }
        btnguardar.setOnClickListener(this);
        btnBuscarCajaNivel1.setOnClickListener(this);
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
        Procesos.obtenerLatitudLongitud(context, CrearCajaNivel2Fragment.this,getActivity().getContentResolver());
    }
    @Override
    public void setLatitudLongitud(String latitud, String longitud) {
        txtLatitud.getEditText().setText(latitud);
        txtLongitud.getEditText().setText(longitud);
    }

    @Override
    public void setCajaNivel2(CajaNivel2 CajaNivel2) {

    }

    @Override
    public void setListaCajaNivel2(List<CajaNivel2> lista) {

    }

    @Override
    public void limpiar() {
        txtNombreCajaNivel2.getEditText().setText("");
        txtDireccion.getEditText().setText("");
        txtReferencia.getEditText().setText("");
        txtLatitud.getEditText().setText("");
        txtLongitud.getEditText().setText("");
        txtNombreCajaNivel1.getEditText().setText("");
        txtNombreCajaNivel2.getEditText().requestFocusFromTouch();
        Procesos.cerrarTeclado(getActivity());
        txtNombreCajaNivel2.setErrorEnabled(false);
        txtDireccion.setErrorEnabled(false);
        txtReferencia.setErrorEnabled(false);
        txtLatitud.setErrorEnabled(false);
        txtLongitud.setErrorEnabled(false);
        txtNombreCajaNivel1.setErrorEnabled(false);
        Procesos.cargandoDetener();
        if (opc.equals("editar")){
            Navigation.findNavController(getActivity().getCurrentFocus()).navigate(R.id.listaCajasNivel2Fragment);
        }
    }

    @Override
    public void CajaNivel1Selecionado(CajaNivel1 cajaNivel1) {
        this.cajaNivel1Seleccionada =cajaNivel1;
        txtNombreCajaNivel1.getEditText().setText(cajaNivel1.getNombre_cajaNivel1());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardar_CrearCajaNivel2:
                nombre=txtNombreCajaNivel2.getEditText().getText().toString().trim();
                direccion = txtDireccion.getEditText().getText().toString().trim();
                referencia = txtReferencia.getEditText().getText().toString().trim();
                latitud = txtLatitud.getEditText().getText().toString().trim();
                longitud =txtLongitud.getEditText().getText().toString().trim();
                if (opc.equals("crear")){
                    cajaNivel2DAO.crearCajaNivel2(new CajaNivel2(0,
                            nombre,
                            direccion,
                            referencia,
                            latitud,
                            longitud,
                            cajaNivel1Seleccionada.getId_cajaNivel1(),
                            "",
                            "activo"),context);
                }else{//editar
                    cajaNivel2.setNombre_CajaNivel2(nombre);
                    cajaNivel2.setDireccion_CajaNivel2(direccion);
                    cajaNivel2.setReferencia_CajaNivel2(referencia);
                    cajaNivel2.setLatitud_CajaNivel2(latitud);
                    cajaNivel2.setLongitud_CajaNivel2(longitud);
                    if (cajaNivel1Seleccionada !=null){
                        cajaNivel2.setId_CajaNivel1(cajaNivel1Seleccionada.getId_cajaNivel1());
                    }
                    cajaNivel2DAO.editarCajaNivel2(cajaNivel2,context,false);
                }
                break;
            case R.id.btnBuscarCajaNivel1_CrearCajaNivel2:
                new DialogBuscarCajaNivel1(context,CrearCajaNivel2Fragment.this);
                break;
        }
    }
}