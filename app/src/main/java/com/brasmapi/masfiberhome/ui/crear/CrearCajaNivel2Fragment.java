package com.brasmapi.masfiberhome.ui.crear;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.brasmapi.masfiberhome.dao.CajaNivel2DAO;
import com.brasmapi.masfiberhome.dao.RangoHilosCaja1DAO;
import com.brasmapi.masfiberhome.entidades.CajaNivel1;
import com.brasmapi.masfiberhome.entidades.CajaNivel2;
import com.brasmapi.masfiberhome.entidades.RangoHilosCaja1;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.ui.buscar.DialogBuscarCajaNivel1;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearCajaNivel2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearCajaNivel2Fragment extends Fragment implements DialogBuscarCajaNivel1.finalizoDialogBuscarCajaNivel1, CajaNivel2DAO.interfazCajaNivel2DAO, Procesos.LatitudLongitud, View.OnClickListener, RangoHilosCaja1DAO.interfazRangoCaja1DAO {

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
    TextInputLayout txtNombreCajaNivel2, txtDireccion, txtReferencia, txtLatitud, txtLongitud, txtNombreCajaNivel1,txtAbreviatura,txtHiloCaja1;
    CajaNivel2DAO cajaNivel2DAO;
    public static CajaNivel2 cajaNivel2;
    CajaNivel1 cajaNivel1Seleccionada =null;
    public static String opc=""; // editar/crear
    Spinner spinner;
    String abreviatura;
    int hiloCaja1,hilos;
    Switch switchAutoManu, switchHiloAutoManu;
    String nombre, direccion, referencia, latitud, longitud;

    RangoHilosCaja1 rangoHilosCaja1,rangoHilosCaja1Anterior;
    RangoHilosCaja1DAO rangoHilosCaja1DAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_crear_caja_nivel2, container, false);
        context=getActivity();
        rangoHilosCaja1DAO=new RangoHilosCaja1DAO(CrearCajaNivel2Fragment.this);
        rangoHilosCaja1=new RangoHilosCaja1();
        Button btnguardar=(Button)vista.findViewById(R.id.btnGuardar_CrearCajaNivel2);
        txtNombreCajaNivel2 =vista.findViewById(R.id.txtNombreCajaNivel2_CrearCajaNivel2);
        switchAutoManu =vista.findViewById(R.id.switch_CrearCajaNivel2);
        switchHiloAutoManu =vista.findViewById(R.id.switchHilo_CrearCajaNivel2);
        txtDireccion =vista.findViewById(R.id.txtDireccion_CrearCajaNivel2);
        txtReferencia =vista.findViewById(R.id.txtReferencia_CrearCajaNivel2);
        txtLatitud =vista.findViewById(R.id.txtLatitud_CrearCajaNivel2);
        txtLongitud =vista.findViewById(R.id.txtLongitud_CrearCajaNivel2);
        txtNombreCajaNivel1 =vista.findViewById(R.id.txtCajaNivel1_CrearCajaNivel2);
        txtAbreviatura =vista.findViewById(R.id.txtAbreviatura_CrearCajaNivel2);
        txtHiloCaja1 =vista.findViewById(R.id.txtHiloCaja1_CrearCajaNivel2);
        spinner =vista.findViewById(R.id.spinnerNumeroHilos_CrearCajaNive2);
        cajaNivel2DAO =new CajaNivel2DAO(CrearCajaNivel2Fragment.this);
        String [] opciones={"Seleccionar cantidad hilos","4","8","16"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,opciones);
        spinner.setAdapter(adapter);
        ((MainActivity)getActivity()).setTitle("Crear caja nivel 2");

        txtNombreCajaNivel1.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogBuscarCajaNivel1(context,CrearCajaNivel2Fragment.this);
            }
        });
        if (opc.equals("editar")){
            btnguardar.setText("Editar");
            txtNombreCajaNivel2.getEditText().setText(cajaNivel2.getNombre_CajaNivel2());
            txtDireccion.getEditText().setText(cajaNivel2.getDireccion_CajaNivel2()+"");
            txtReferencia.getEditText().setText(cajaNivel2.getReferencia_CajaNivel2()+"");
            txtLatitud.getEditText().setText(cajaNivel2.getLatitud_CajaNivel2()+"");
            txtLongitud.getEditText().setText(cajaNivel2.getLongitud_CajaNivel2());
            txtNombreCajaNivel1.getEditText().setText(cajaNivel2.getNombreCajaNivel1());
            txtAbreviatura.getEditText().setText(cajaNivel2.getAbreviatura());
            txtHiloCaja1.getEditText().setText(cajaNivel2.getHiloCaja1()+"");
            switch (cajaNivel2.getCantidadHilos()){
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
            ((MainActivity)getActivity()).setTitle("Editar caja nivel 2");
            switchAutoManu.setChecked(false);
            rangoHilosCaja1DAO.obtenerHiloAnterior(cajaNivel2.getId_CajaNivel1(),cajaNivel2.getId_CajaNivel2(),context);
            spinner.setEnabled(false);
            switchHiloAutoManu.setChecked(false);
            latLonAutomaticoApagado();
            hiloCaja1AutomaticoApagado();
        }else {
            latLonAutomaticoEncendido();
            hiloCaja1AutomaticoEncendido();
        }
        btnguardar.setOnClickListener(this);
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
        switchHiloAutoManu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    hiloCaja1AutomaticoEncendido();
                }else{
                    hiloCaja1AutomaticoApagado();
                    txtHiloCaja1.getEditText().setText("");
                }
            }
        });
        return vista;
    }
    public void hiloCaja1AutomaticoApagado(){
        switchHiloAutoManu.setText("Manual");
        txtHiloCaja1.getEditText().setFocusableInTouchMode(true);
    }
    public void hiloCaja1AutomaticoEncendido(){
        switchHiloAutoManu.setText("Automático");
        txtHiloCaja1.getEditText().setFocusableInTouchMode(false);
        if(cajaNivel1Seleccionada!=null){
            rangoHilosCaja1DAO.obtenerHiloAutomatico(cajaNivel1Seleccionada.getId_cajaNivel1(),context);
        }
    }
    public void latLonAutomaticoApagado(){
        switchAutoManu.setText("Manual");
        txtLatitud.getEditText().setFocusableInTouchMode(true);
        txtLongitud.getEditText().setFocusableInTouchMode(true);
        Procesos.detenerObtenerLatitudLongitud();
    }
    public void latLonAutomaticoEncendido(){
        switchAutoManu.setText("Automático");
        txtLatitud.getEditText().setFocusableInTouchMode(false);
        txtLongitud.getEditText().setFocusableInTouchMode(false);
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
        if (!opc.equals("editar")){
            txtNombreCajaNivel2.getEditText().setText("");
            txtAbreviatura.getEditText().setText("");
            txtDireccion.getEditText().setText("");
            txtReferencia.getEditText().setText("");
            txtLatitud.getEditText().setText("");
            txtLongitud.getEditText().setText("");
            txtNombreCajaNivel1.getEditText().setText("");
            txtHiloCaja1.getEditText().setText("");
            switchHiloAutoManu.setChecked(true);
            txtNombreCajaNivel2.getEditText().requestFocusFromTouch();
            txtNombreCajaNivel2.setErrorEnabled(false);
            txtDireccion.setErrorEnabled(false);
            txtReferencia.setErrorEnabled(false);
            txtLatitud.setErrorEnabled(false);
            txtLongitud.setErrorEnabled(false);
            txtNombreCajaNivel1.setErrorEnabled(false);
            spinner.setSelection(0);
        }
        Procesos.cerrarTeclado(getActivity());
        Procesos.cargandoDetener();
        if (hiloModificado){
            rangoHilosCaja1.setEstado("activo");
            rangoHilosCaja1DAO.editarRangoHilosCaja1(rangoHilosCaja1,nombre,context);
        }
        if (opc.equals("editar")){
            getActivity().onBackPressed();// para retrocede sin que se guarde el activiti anterior  ejemplo a b c
            // con el codigo de abajo si lo aplico en c para ir a b quedaria asi
            //a b c b al momento de dar vuelta atras se ir nuevamente a c y luego a b
            //al aplicar el codigo de arriba en el mismo ejemplo si lo aplicamos en c quedaria asi
            //a b por ende si doy atras nuevamente se iria a a y ya no a c como el en anterior
            //Navigation.findNavController(getActivity().getCurrentFocus()).navigate(R.id.listaCajasNivel2Fragment);
        }
    }

    @Override
    public void CajaNivel1Selecionado(CajaNivel1 cajaNivel1) {
        this.cajaNivel1Seleccionada =cajaNivel1;
        txtNombreCajaNivel1.getEditText().setText(cajaNivel1.getNombre_cajaNivel1());
        if(switchHiloAutoManu.isChecked()){
            rangoHilosCaja1DAO.obtenerHiloAutomatico(cajaNivel1.getId_cajaNivel1(),context);
        }
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
                abreviatura=txtAbreviatura.getEditText().getText().toString().trim();
                hiloCaja1=Integer.parseInt(txtHiloCaja1.getEditText().getText().toString().trim());
                hilos=Integer.parseInt(spinner.getSelectedItem().toString());
                 if (switchHiloAutoManu.isChecked()){
                     crearEditar();
                 }else{
                     if (cajaNivel2 != null) {
                         if (cajaNivel1Seleccionada!=null){
                             rangoHilosCaja1DAO.verificarHiloManual(cajaNivel1Seleccionada.getId_cajaNivel1(),hiloCaja1,context);
                         }else{
                             if(cajaNivel2.getHiloCaja1()!=hiloCaja1){
                                 rangoHilosCaja1DAO.verificarHiloManual(cajaNivel2.getId_CajaNivel1(),hiloCaja1,context);
                             }else{
                                 crearEditar();
                             }
                         }
                     }else{
                         rangoHilosCaja1DAO.verificarHiloManual(cajaNivel1Seleccionada.getId_cajaNivel1(),hiloCaja1,context);
                     }
                 }
                break;
        }
    }
    @Override
    public void hiloAutomatico(RangoHilosCaja1 rangoHilosCaja1) {
        if (rangoHilosCaja1!=null){
        txtHiloCaja1.getEditText().setText(rangoHilosCaja1.getNumero_rangocaja1()+"");
        this.rangoHilosCaja1=rangoHilosCaja1;
        }else{
            Toast.makeText(context, "No hay hilos disponible en esta caja nivel 1", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void validarHiloManual(RangoHilosCaja1 rangoHilos) {
        if (rangoHilos!=null){
            this.rangoHilosCaja1=rangoHilos;
            crearEditar();
        }else{
            Toast.makeText(context, "Numero de hilo incorrecto o no disponible", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void hiloAnterior(RangoHilosCaja1 rangoHilos) {
        rangoHilosCaja1Anterior=rangoHilos;
    }
    boolean hiloModificado=true;
    public void crearEditar(){
    if (opc.equals("crear")){
        hiloModificado=true;
        cajaNivel2DAO.crearCajaNivel2(new CajaNivel2(0,
                nombre,
                abreviatura,
                direccion,
                referencia,
                latitud,
                longitud,
                cajaNivel1Seleccionada.getId_cajaNivel1(),
                "",
                hiloCaja1,
                hilos,
                "activo"),context);
    }else{//editar
        String nombre=cajaNivel2.getNombre_CajaNivel2();
        cajaNivel2.setNombre_CajaNivel2(nombre);
        cajaNivel2.setDireccion_CajaNivel2(direccion);
        cajaNivel2.setReferencia_CajaNivel2(referencia);
        cajaNivel2.setLatitud_CajaNivel2(latitud);
        cajaNivel2.setLongitud_CajaNivel2(longitud);
        cajaNivel2.setAbreviatura(abreviatura);
        cajaNivel2.setCantidadHilos(hilos);
        if (cajaNivel1Seleccionada !=null){
            cajaNivel2.setId_CajaNivel1(cajaNivel1Seleccionada.getId_cajaNivel1());
            cajaNivel2.setHiloCaja1(hiloCaja1);
            rangoHilosCaja1DAO.editarRangoHilosCaja1Anterior(rangoHilosCaja1Anterior.getId_rangocaja1(),context);
        }else{
            if(cajaNivel2.getHiloCaja1()!=hiloCaja1){
                rangoHilosCaja1DAO.editarRangoHilosCaja1Anterior(rangoHilosCaja1Anterior.getId_rangocaja1(),context);
                cajaNivel2.setHiloCaja1(hiloCaja1);
            }
            hiloModificado=false;
        }
        cajaNivel2DAO.editarCajaNivel2(cajaNivel2,context,false);
        }
    }

}