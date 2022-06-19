package com.brasmapi.masfiberhome.ui.crear;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.ModeloOntDAO;
import com.brasmapi.masfiberhome.dao.RangoHilosCaja2DAO;
import com.brasmapi.masfiberhome.dao.RangoOntDAO;
import com.brasmapi.masfiberhome.dao.ServicioDAO;
import com.brasmapi.masfiberhome.dao.ServiportDAO;
import com.brasmapi.masfiberhome.dao.VlanDAO;
import com.brasmapi.masfiberhome.entidades.CajaNivel2;
import com.brasmapi.masfiberhome.entidades.Clientes;
import com.brasmapi.masfiberhome.entidades.ModeloOnt;
import com.brasmapi.masfiberhome.entidades.Ont;
import com.brasmapi.masfiberhome.entidades.RangoHilosCaja2;
import com.brasmapi.masfiberhome.entidades.RangoOnt;
import com.brasmapi.masfiberhome.entidades.Servicios;
import com.brasmapi.masfiberhome.entidades.Vlan;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.ui.buscar.DialogBuscarCajaNivel2;
import com.brasmapi.masfiberhome.ui.buscar.DialogBuscarCliente;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearServicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearServicioFragment extends Fragment implements DialogBuscarCliente.finalizoDialogBuscarClientes,
        DialogBuscarCajaNivel2.finalizoDialogBuscarCajaNivel2,
        VlanDAO.interfazVlanDAO,
        DialogCrearOnt.finalizoDialogCrearOnt,
        ModeloOntDAO.interfazModeloOntDAO,
        Procesos.LatitudLongitud,
        RangoHilosCaja2DAO.interfazRangoCaja2DAO,
        RangoOntDAO.interfazRangoOnt,
        ServiportDAO.interfazServiport, ServicioDAO.interfazServicio{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearServicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearServicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearServicioFragment newInstance(String param1, String param2) {
        CrearServicioFragment fragment = new CrearServicioFragment();
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
    Calendar calendar = Calendar.getInstance();
    public static String opc;//editar / crear
    Context context;
    DatePickerDialog datePickerDialog;
    View vista;
    TextInputLayout txtFechaInstalacion;
    int ano,mes,dia;
    Vlan vlan;
    VlanDAO vlanDAO;
    Clientes clientes;
    CajaNivel2 cajaNivel2;
    Ont ont;
    ModeloOnt modeloOnt;
    ModeloOntDAO modeloOntDAO;
    Spinner spinnerPlan;
    TextInputLayout txtServiPort,txtCliente,txtCajaNivel2,txtOnt,txtQuit,txtNumeroOnt,txtUsuario,txtDireccion,txtReferencia,txtHiloEnCaja2,txtDireccionIp,txtLatitud,txtLongitud;
    TextInputLayout txtComandoPlanes,txtInterfazPoncard,txtAgregarOnt,txtEquipoBridge,txtEliminarServicio, txtAgregarServicioAlPuerto,txtAgregarPlanCliente,txtAgregarDescripcionPuerto,txtEliminarOnt;
    Switch switchNumeroOnt,switchHiloEnCaja2,switchLatLon,switchDireccionIp;
    Button btnGuardar;
    LinearLayout groupComando;
    RangoHilosCaja2DAO rangoHilosCaja2DAO;
    RangoHilosCaja2 rangoHilosCaja2;
    RangoHilosCaja2 rangoHilosCaja2Anterior;

    RangoOntDAO rangoOntDAO;
    RangoOnt rangoOnt;
    RangoOnt rangoOntAnterior;

    ServiportDAO serviportDAO;

    Servicios servicios;
    ServicioDAO servicioDAO;

    int numeroHiloCaja2,numeroOnt;
    String nombreCaja2;
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Intent originalIntent = result.getOriginalIntent();
                    if (originalIntent == null) {
                        Log.d("MainActivity", "Cancelled scan");
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show();
                    } else if(originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                        Log.d("MainActivity", "Cancelled scan due to missing camera permission");
                        Toast.makeText(context, "Cancelled due to missing camera permission", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("MainActivity", "Scanned");
                    //txtserie.getEditText().setText(result.getContents()+"");
                    DialogCrearOnt.txtserie.getEditText().setText(result.getContents());
                    //interfazScanerCrearServicio.setEscaner(result.getContents());
                    Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            });
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         vista=inflater.inflate(R.layout.fragment_crear_servicio, container, false);
        context=getActivity();
        ((MainActivity)getActivity()).setTitle("Crear servicio");
        Procesos.cargandoDetener();
        modeloOntDAO=new ModeloOntDAO(CrearServicioFragment.this);
        vlanDAO=new VlanDAO(CrearServicioFragment.this);
        rangoHilosCaja2DAO=new RangoHilosCaja2DAO(CrearServicioFragment.this);
        serviportDAO=new ServiportDAO(CrearServicioFragment.this);
        rangoOntDAO=new RangoOntDAO(CrearServicioFragment.this);
        servicioDAO=new ServicioDAO(CrearServicioFragment.this);
        rangoHilosCaja2=new RangoHilosCaja2();
        rangoOnt=new RangoOnt();
        groupComando=(LinearLayout) vista.findViewById(R.id.linearGrupoComando_CrearServicio);
        btnGuardar=(Button) vista.findViewById(R.id.btnGuardar_CrearServicio);
        switchNumeroOnt=(Switch)vista.findViewById(R.id.switchNumeroOnt_CrearServicio);
        switchHiloEnCaja2=(Switch)vista.findViewById(R.id.switchHiloCaja2_CrearServicio);
        switchLatLon=(Switch)vista.findViewById(R.id.switchLatitudLongitud_CrearServicio);
        switchDireccionIp=(Switch)vista.findViewById(R.id.switchDireccionIp_CrearServicio);
        txtFechaInstalacion= (TextInputLayout) vista.findViewById(R.id.txtImputFechaInicio_CrearServicio);
        txtServiPort= (TextInputLayout) vista.findViewById(R.id.txtServiPort_CrearServicio);
        txtCliente= (TextInputLayout) vista.findViewById(R.id.txtBuscarCliente_CrearServicio);
        txtCajaNivel2= (TextInputLayout) vista.findViewById(R.id.txtBuscarCajaNivel2_CrearServicio);
        txtOnt= (TextInputLayout) vista.findViewById(R.id.txtOnt_CrearServicio);
        txtNumeroOnt= (TextInputLayout) vista.findViewById(R.id.txtNumeroOnt_CrearServicio);
        txtUsuario= (TextInputLayout) vista.findViewById(R.id.txtUsuario_CrearServicio);
        txtDireccion= (TextInputLayout) vista.findViewById(R.id.txtDireccion_CrearServicio);
        txtReferencia= (TextInputLayout) vista.findViewById(R.id.txtReferencia_CrearServicio);
        txtHiloEnCaja2= (TextInputLayout) vista.findViewById(R.id.txtHiloCaja2_CrearUsuario);
        txtDireccionIp= (TextInputLayout) vista.findViewById(R.id.txtDireccionIp_CrearServicio);
        txtLatitud= (TextInputLayout) vista.findViewById(R.id.txtLatitud_CrearServicio);
        txtLongitud= (TextInputLayout) vista.findViewById(R.id.txtLongitud_CrearServicio);
        txtComandoPlanes= (TextInputLayout) vista.findViewById(R.id.txtComandoPlanes_CrearServicio);
        txtAgregarOnt= (TextInputLayout) vista.findViewById(R.id.txtAgregarOnt_CrearServicio);
        txtEquipoBridge= (TextInputLayout) vista.findViewById(R.id.txtEquipoBridge_CrearServicio);
        txtEliminarServicio= (TextInputLayout) vista.findViewById(R.id.txtEliminarServicio__CrearServicio);
        txtAgregarServicioAlPuerto = (TextInputLayout) vista.findViewById(R.id.txtAgregarServicioAlPuerto_CrearServicio);
        txtAgregarPlanCliente= (TextInputLayout) vista.findViewById(R.id.txtAgregarPlanAlCliente_CrearServicio);
        txtAgregarDescripcionPuerto= (TextInputLayout) vista.findViewById(R.id.txtAgregarDescipcionAlPuerto_CrearServicio);
        txtEliminarOnt= (TextInputLayout) vista.findViewById(R.id.txtEliminarOnt_CrearServicio);
        txtInterfazPoncard= (TextInputLayout) vista.findViewById(R.id.txtInterfazPoncard_CrearServicio);
        txtQuit= (TextInputLayout) vista.findViewById(R.id.txtQuit_CrearServicio);
        spinnerPlan =vista.findViewById(R.id.spinnerPlan_CrearUsuario);
        String [] opciones={"Seleccionar el plan","40 megas","80 megas","16 megas"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,opciones);
        spinnerPlan.setAdapter(adapter);
        txtFechaInstalacion.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtFechaInstalacion.getEditText().setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, ano, mes, dia);
                datePickerDialog.show();
            }
        });
        if (opc.equals("editar")){
            latLonAutomaticoApagado();
            String[] fecha= obtenerFechaPorDiaMesAno(servicios.getFecha());
            dia = Integer.parseInt(fecha[0]);
            mes = Integer.parseInt(fecha[1]);
            ano = Integer.parseInt(fecha[2]);
            rangoHilosCaja2DAO.obtenerHiloCaja2Anterior(servicios.getId_cajanivel2(),servicios.getId_servicio(),context);
            rangoOntDAO.obtenerNumeroOntAnterior(vlan.getId(),servicios.getId_ont(),context);
        }else{
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            ano = calendar.get(Calendar.YEAR);
            serviportAutomatico();
            latLonAutomaticoEncendido();
        }
        if (Procesos.user!=null){
            if (Procesos.user.getRol()!=1){
                groupComando.setVisibility(View.GONE);
            }
        }
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numeroHiloCaja2 =Procesos.obtenerTxtEnEntero(txtHiloEnCaja2);
                nombreCaja2=Procesos.obtenerTxtEnString(txtCajaNivel2);
                numeroOnt=Procesos.obtenerTxtEnEntero(txtNumeroOnt);
                validar1HiloEnCaja2();
            }
        });
        serviportDAO.obtenerServiportAutomatico(context);
        addTextChangedListener();
        setOnCheckedChangeListener();
        setEndIconOnClickListener();
        return vista;
    }

    public void crearEditar(){
        if (opc.equals("crear")){

        }else if(opc.equals("editar")) {

        }
    }
    private void setEndIconOnClickListener() {
        txtCliente.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogBuscarCliente(context,CrearServicioFragment.this);
            }
        });
        txtCajaNivel2.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogBuscarCajaNivel2(context,CrearServicioFragment.this);
            }
        });
        txtComandoPlanes.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtComandoPlanes.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,txtComandoPlanes,getActivity());
                }
            }
        });
        txtInterfazPoncard.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtInterfazPoncard.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,txtInterfazPoncard,getActivity());
                }
            }
        });
        txtAgregarOnt.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtAgregarOnt.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,txtAgregarOnt,getActivity());
                }
            }
        });
        txtEquipoBridge.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtEquipoBridge.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,txtEquipoBridge,getActivity());
                }
            }
        });
        txtQuit.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtQuit.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,txtQuit,getActivity());
                }
            }
        });
        txtEliminarServicio.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtEliminarServicio.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,txtEliminarServicio,getActivity());
                }
            }
        });
        txtAgregarServicioAlPuerto.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtAgregarServicioAlPuerto.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,txtAgregarServicioAlPuerto,getActivity());
                }
            }
        });
        txtAgregarPlanCliente.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtAgregarPlanCliente.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,txtAgregarPlanCliente,getActivity());
                }
            }
        });
        txtAgregarDescripcionPuerto.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtAgregarDescripcionPuerto.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,txtAgregarDescripcionPuerto,getActivity());
                }
            }
        });
        txtEliminarOnt.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtEliminarOnt.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,txtEliminarOnt,getActivity());
                }
            }
        });
        txtOnt.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogCrearOnt(context,CrearServicioFragment.this,barcodeLauncher);
            }
        });
    }
    boolean hiloModificadoCreado=true;
    public void validar1HiloEnCaja2(){
        if (switchHiloEnCaja2.isChecked()){
            validar2NumeroOnt();
        }else{//manual
            if (servicios!=null){//quiere decir que opc=editar
                if(cajaNivel2!=null){//si es diferente a null es decir que si se seleciono otra caja y debemos validar el nuevo numero de hilo
                    rangoHilosCaja2DAO.verificarHiloCaja2Manual(cajaNivel2.getId_CajaNivel2(), numeroHiloCaja2,context);
                    rangoHilosCaja2DAO.editarRangoHilosCaja2Anterior(rangoHilosCaja2Anterior.getId_rangocaja2(),context);
                    servicios.setId_cajanivel2(cajaNivel2.getId_CajaNivel2());
                    servicios.setHiloCajaNivel2(numeroHiloCaja2);
                }else{//si es = null es decir que no se seleciono otra caja
                    if (servicios.getHiloCajaNivel2()!=numeroHiloCaja2){//como no se seleciono otra caja se verifica si cambiaron el numero manualmente
                        // si los numeros son diferentes es decir que cambiaron el numero de hilo manualmente y debemos validar
                        rangoHilosCaja2DAO.verificarHiloCaja2Manual(cajaNivel2.getId_CajaNivel2(), numeroHiloCaja2,context);
                        rangoHilosCaja2DAO.editarRangoHilosCaja2Anterior(rangoHilosCaja2Anterior.getId_rangocaja2(),context);
                        servicios.setHiloCajaNivel2(numeroHiloCaja2);
                    }else{// los numeros de hilo son los mismos no es necesario validar nuevamente
                        hiloModificadoCreado=false;
                        validar2NumeroOnt();//ojo en cada validar true se llama al siguiente validar
                    }
                }
            }else{//quiere decir que opc=crear por ende se debe validar
                hiloModificadoCreado=true;
                rangoHilosCaja2DAO.verificarHiloCaja2Manual(cajaNivel2.getId_CajaNivel2(), numeroHiloCaja2,context);
            }
        }
    }
    boolean numeroOntModificadoCreado=true;
    public void validar2NumeroOnt(){
        if (switchNumeroOnt.isChecked()){
            validar3ServiPort();
        }else{//manual
            if (servicios!=null){//quiere decir que opc=editar
                if(vlan!=null){//si es diferente a null es decir que si se seleciono otra caja y debemos validar el nuevo numero de hilo
                    rangoOntDAO.verificarNumeroOntManual(vlan.getId(), numeroOnt,context);
                    rangoOntDAO.editarRangoOntAnterior(rangoOntAnterior.getId_rangoont(),context);
                    //servicios.setId_cajanivel2(cajaNivel2.getId_CajaNivel2());
                    //servicios.setHiloCajaNivel2(numeroHiloCaja2);
                    ont.setNumeroOnt(numeroOnt);
                }else{//si es = null es decir que no se seleciono otra caja
                    if (ont.getNumeroOnt()!=numeroOnt){//como no se seleciono otra caja se verifica si cambiaron el numero manualmente
                        // si los numeros son diferentes es decir que cambiaron el numero de hilo manualmente y debemos validar
                        rangoOntDAO.verificarNumeroOntManual(vlan.getId(), numeroOnt,context);
                        rangoOntDAO.editarRangoOntAnterior(rangoOntAnterior.getId_rangoont(),context);
                        ont.setNumeroOnt(numeroOnt);
                        //servicios.setHiloCajaNivel2(numeroHiloCaja2);
                    }else{// los numeros de hilo son los mismos no es necesario validar nuevamente
                        numeroOntModificadoCreado=false;
                        validar3ServiPort();//ojo en cada validar true se llama al siguiente validar
                    }
                }
            }else{//quiere decir que opc=crear por ende se debe validar
                numeroOntModificadoCreado=true;
                rangoOntDAO.verificarNumeroOntManual(vlan.getId(), numeroOnt,context);
            }
        }
    }
    public void validar3ServiPort(){

    }
    private void setOnCheckedChangeListener() {
        switchLatLon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    latLonAutomaticoEncendido();
                }else{
                    latLonAutomaticoApagado();
                    txtLatitud.getEditText().setText("");
                    txtLongitud.getEditText().setText("");
                }
            }
        });
        switchNumeroOnt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    switchNumeroOnt.setText("Automático");
                    numeroOntAutomatico();
                    txtNumeroOnt.getEditText().setEnabled(false);
                }else{
                    switchNumeroOnt.setText("Manual");
                    txtNumeroOnt.getEditText().setEnabled(true);
                    txtNumeroOnt.getEditText().setText("");
                }
            }
        });
        switchHiloEnCaja2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    switchHiloEnCaja2.setText("Automático");
                    hiloEnCaja2Automatico();
                    txtHiloEnCaja2.getEditText().setEnabled(false);
                }else{
                    switchHiloEnCaja2.setText("Manual");
                    txtHiloEnCaja2.getEditText().setEnabled(true);
                    txtHiloEnCaja2.getEditText().setText("");
                }
            }
        });
        switchDireccionIp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    switchDireccionIp.setText("Automático");
                    direccionIpAutomatico();
                    txtDireccionIp.getEditText().setEnabled(false);
                }else{
                    switchDireccionIp.setText("Manual");
                    txtDireccionIp.getEditText().setEnabled(true);
                    txtDireccionIp.getEditText().setText("");
                }
            }
        });
    }

    private void direccionIpAutomatico() {
    }

    private void hiloEnCaja2Automatico() {
        txtHiloEnCaja2.getEditText().setEnabled(false);
        if(cajaNivel2!=null){
            rangoHilosCaja2DAO.obtenerHiloCaja2Automatico(cajaNivel2.getId_CajaNivel2(),context);
        }
    }

    private void numeroOntAutomatico() {
        txtNumeroOnt.getEditText().setEnabled(false);
        if(vlan!=null){
            rangoOntDAO.obtenerNumeroOntAutomatico(vlan.getId(),context);
        }
    }

    private void latLonAutomaticoApagado() {
        switchLatLon.setText("Manual");
        txtLatitud.getEditText().setEnabled(true);
        txtLongitud.getEditText().setEnabled(true);
        Procesos.detenerObtenerLatitudLongitud();
    }

    private void latLonAutomaticoEncendido() {
        switchLatLon.setText("Automático");
        txtLatitud.getEditText().setEnabled(false);
        txtLongitud.getEditText().setEnabled(false);
        Procesos.obtenerLatitudLongitud(context, CrearServicioFragment.this,getActivity().getContentResolver());
    }

    private void serviportAutomatico() {
    }

    public void addTextChangedListener(){
        txtServiPort.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    llenarComandoPlanes();
                    llenarEliminarServicio();
                    llenarAgregarServicioAlPuerto();
                    llenarAgregarDescripcionAlPuerto();
                }else{
                    txtComandoPlanes.getEditText().setText("");
                    txtEliminarServicio.getEditText().setText("");
                    txtAgregarServicioAlPuerto.getEditText().setText("");
                    txtAgregarDescripcionPuerto.getEditText().setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        spinnerPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    txtComandoPlanes.getEditText().setText("");
                }else{
                    llenarComandoPlanes();
                    usuarioRepetido="brayan222222";
                    llenarUsuario();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        txtCliente.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    llenarUsuario();
                    llenarAgregarOnt();
                    llenarEliminarServicio();
                }else{
                    txtUsuario.getEditText().setText("");
                    txtAgregarOnt.getEditText().setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtCajaNivel2.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    llenarUsuario();
                    llenarInterfazPoncard();
                    llenarAgregarOnt();
                    llenarAgregarServicioAlPuerto();
                    llenarEquipoBridge();
                }else{
                    txtUsuario.getEditText().setText("");
                    txtInterfazPoncard.getEditText().setText("");
                    txtAgregarOnt.getEditText().setText("");
                    txtAgregarServicioAlPuerto.getEditText().setText("");
                    txtEquipoBridge.getEditText().setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtNumeroOnt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    llenarAgregarOnt();
                    llenarAgregarServicioAlPuerto();
                    llenarEliminarOnt();
                    llenarEquipoBridge();
                }else{
                    txtAgregarOnt.getEditText().setText("");
                    txtAgregarServicioAlPuerto.getEditText().setText("");
                    txtEliminarOnt.getEditText().setText("");
                    txtEquipoBridge.getEditText().setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtComandoPlanes.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    llenarAgregarPlanAlCliente();
                }else{
                    txtAgregarPlanCliente.getEditText().setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtOnt.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    llenarAgregarOnt();
                    llenarAgregarServicioAlPuerto();
                    llenarEliminarOnt();
                    llenarEquipoBridge();
                }else{
                    txtAgregarOnt.getEditText().setText("");
                    txtAgregarServicioAlPuerto.getEditText().setText("");
                    txtEliminarOnt.getEditText().setText("");
                    txtEquipoBridge.getEditText().setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtUsuario.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    llenarAgregarDescripcionAlPuerto();
                }else{
                    txtAgregarDescripcionPuerto.getEditText().setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtDireccionIp.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    llenarAgregarDescripcionAlPuerto();
                }else{
                    txtAgregarDescripcionPuerto.getEditText().setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

}
    public void llenarComandoPlanes(){
        String conca,serviport,plan;
        serviport=txtServiPort.getEditText().getText().toString().trim();
        if (!serviport.equals("") && serviport!=null && spinnerPlan.getSelectedItemPosition()!=0){
            plan=obtenerPrimerApellido(spinnerPlan.getSelectedItem().toString());
            conca="service-port "+serviport+" inbound traffic-table index "+plan+" outbound traffic-table index "+plan;
            txtComandoPlanes.getEditText().setText(conca);
        }
    }
    String usuarioRepetido="";
    public void llenarUsuario(){
        if(clientes!=null && cajaNivel2!=null){
            String conca;
            conca=obtenerPrimerLetra(clientes.getNombre())+obtenerPrimerApellido(clientes.getApellido())+usuarioRepetido+"_"+cajaNivel2.getAbreviaturaCajaNivel1()+"_"+cajaNivel2.getAbreviatura();
            txtUsuario.getEditText().setText(conca);
            servicioDAO.validarUsuario(conca,context);
        }
    }
    public void llenarInterfazPoncard(){
        if(vlan!=null){
            txtInterfazPoncard.getEditText().setText("interface gpon "+vlan.getNumeroOlt()+"/"+vlan.getTarjetaOlt());
        }
    }

    public void llenarAgregarOnt(){
        String numeroOnt2=txtNumeroOnt.getEditText().getText().toString().trim();
        if(vlan!=null && !numeroOnt2.equals("") && clientes!=null && cajaNivel2!=null && ont!=null){
            txtAgregarOnt.getEditText().setText("ont add "+vlan.getPuertoOlt()+" "+numeroOnt2+" sn-auth "+"\""+ont.getSerieOnt()+"\""+
                    " omci ont-lineprofile-id 5 ont-srvprofile-id 5 desc "+"\""+txtUsuario.getEditText().getText().toString().trim()+"\"");
        }
    }
    public void llenarEquipoBridge(){
        String numeroOnt32=txtNumeroOnt.getEditText().getText().toString().trim();
        if(!numeroOnt32.equals("") && vlan!=null && modeloOnt!=null ){
            if(modeloOnt.getTipo_modeloOnt().equals("BRIDGE")){
                txtEquipoBridge.getEditText().setText("ont port native-vlan "+vlan.getPuertoOlt()+" "+numeroOnt32+" eth 1 vlan 100 priority 0");
            }
        }
    }
    public void llenarEliminarServicio(){
        String serviport3=txtServiPort.getEditText().getText().toString().trim();
        if (!serviport3.equals("")){
            txtEliminarServicio.getEditText().setText("undo service-port "+serviport3);
        }
    }
    public void llenarAgregarServicioAlPuerto(){
        String serviport3=txtServiPort.getEditText().getText().toString().trim();
        String numeroOnt3=txtNumeroOnt.getEditText().getText().toString().trim();
        if (!serviport3.equals("") && vlan!=null && cajaNivel2!=null && !numeroOnt3.equals("")){
            txtAgregarServicioAlPuerto.getEditText().setText("service-port "+serviport3+" vlan "+vlan.getNumeroVlan()+" gpon "+
                    vlan.getTarjetaOlt()+"/"+vlan.getTarjetaOlt()+"/"+vlan.getPuertoOlt()+" ont "
                    +numeroOnt3+ " gemport 100 multi-service user-vlan 100 tag-transform translate");
        }
    }
    public void llenarAgregarPlanAlCliente(){
        String comandoPlanes=txtComandoPlanes.getEditText().getText().toString();
        if (!comandoPlanes.equals("")){
            txtAgregarPlanCliente.getEditText().setText(comandoPlanes);
        }
    }

    public void llenarAgregarDescripcionAlPuerto(){
        String serviport3=txtServiPort.getEditText().getText().toString().trim();
        String usuario23=txtUsuario.getEditText().getText().toString().trim();
        String ip43=txtDireccionIp.getEditText().getText().toString().trim();
        if(!serviport3.equals("") && !usuario23.equals("") && !ip43.equals("")){
            txtAgregarDescripcionPuerto.getEditText().setText("service-port desc "+serviport3+" description "+"\""+usuario23+"-"+ip43+"\"");
        }
    }

    public void llenarEliminarOnt(){
        String numeroOnt23=txtNumeroOnt.getEditText().getText().toString();
        if(vlan!=null && !numeroOnt23.equals("")){
            txtEliminarOnt.getEditText().setText("ont delete "+vlan.getPuertoOlt()+" "+numeroOnt23);
        }
    }

    private String obtenerPrimerApellido(String apellido) {
        if(apellido == null || apellido.length() == 0){
            return null;
        } else{
            String[] ar = apellido.split("\\s+");
            return ar[0];
        }
    }

    private String[] obtenerFechaPorDiaMesAno(String fecha) {
        if(fecha == null || fecha.length() == 0){
            return null;
        } else{
            String[] ar = fecha.split("/");
            return ar;
        }
    }

    private String obtenerPrimerLetra(String nombre) {
        if(nombre == null || nombre.length() == 0){
            return null;
        } else{
            return nombre.substring(0, 1);
        }
    }

    @Override
    public void ClientesSelecionado(Clientes Clientes) {
        this.clientes=Clientes;
        txtCliente.getEditText().setText(clientes.getNombre()+" "+clientes.getApellido());
    }

    @Override
    public void CajaNivel2Selecionado(CajaNivel2 CajaNivel2) {
        this.cajaNivel2=CajaNivel2;
        txtCajaNivel2.getEditText().setText(cajaNivel2.getNombre_CajaNivel2());
        vlanDAO.buscarVlan(cajaNivel2.getIdvlan()+"",context);
    }

    @Override
    public void setVlan(Vlan Vlan) {
        this.vlan=Vlan;
        llenarInterfazPoncard();
        llenarAgregarOnt();
        llenarAgregarServicioAlPuerto();
        llenarEquipoBridge();
        llenarEliminarOnt();

        //----
        numeroOntAutomatico();
        hiloEnCaja2Automatico();
        direccionIpAutomatico();

    }

    @Override
    public void setListaVlan(List<Vlan> lista) {

    }

    @Override
    public void setModeloOnt(ModeloOnt ModeloOnt) {
        this.modeloOnt=ModeloOnt;
        llenarEquipoBridge();
    }

    @Override
    public void setListaModeloOnt(List<ModeloOnt> lista) {

    }

    @Override
    public void limpiar() {//debe ser el limpiar de servicioDao
        if(hiloModificadoCreado){
            rangoHilosCaja2.setEstado("activo");
            rangoHilosCaja2DAO.editarRangoHilosCaja2(rangoHilosCaja2,nombreCaja2,context);
        }
        if(numeroOntModificadoCreado){
            rangoOnt.setEstado("activo");
            rangoOntDAO.editarRangoOnt(rangoOnt,vlan.getNombreVlan(),context);
        }
    }

    @Override
    public void setOnt(Ont ont) {
        this.ont=ont;
        txtOnt.getEditText().setText(ont.getSerieOnt());
        modeloOntDAO.buscarModeloOnt(ont.getId_modeloOnt()+"",context);
    }

    @Override
    public void setLatitudLongitud(String latitud, String longitud) {
        txtLatitud.getEditText().setText(latitud);
        txtLongitud.getEditText().setText(longitud);
    }

    @Override
    public void hiloCaja2Automatico(RangoHilosCaja2 rangoHilosCaja2) {
        if (rangoHilosCaja2!=null){
            txtHiloEnCaja2.getEditText().setText(rangoHilosCaja2.getNumero_rangocaja2()+"");
            this.rangoHilosCaja2=rangoHilosCaja2;
        }else{
            Toast.makeText(context, "No hay hilos disponible en esta caja nivel 2", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void validarHiloCaja2Manual(RangoHilosCaja2 rangoHilosCaja2) {
        if (rangoHilosCaja2 !=null){
            this.rangoHilosCaja2= rangoHilosCaja2;
            validar2NumeroOnt();
        }else{
            Toast.makeText(context, "Numero de hilo incorrecto o no disponible", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void hiloCaja2Anterior(RangoHilosCaja2 rangoHilosCaja2) {
        rangoHilosCaja2Anterior= rangoHilosCaja2;
    }

    @Override
    public void numeroOntAutomatico(RangoOnt rangoOnt) {
        if (rangoHilosCaja2!=null){
            txtNumeroOnt.getEditText().setText(rangoOnt.getNumero_rangoont()+"");
            this.rangoOnt= rangoOnt;
        }else{
            Toast.makeText(context, "No hay numero de ont disponible en esta vlan", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void validarNumeroOntManual(RangoOnt rangoOnt) {
        if (rangoOnt !=null){
            this.rangoOnt= rangoOnt;
            validar3ServiPort();
        }else{
            Toast.makeText(context, "Numero de ont incorrecto o no disponible", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void numeroOntAnterior(RangoOnt rangoOnt) {
        if (rangoOnt !=null){
            rangoOntAnterior= rangoOnt;
        }else{
            Toast.makeText(context, "Numero de ont anterior incorrecto o no disponible", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void serviportAutomatico(Integer Serviport) {
        txtServiPort.getEditText().setText(Serviport+"");
    }

    @Override
    public void setUsuarioRepetido(boolean estaRepetido) {
        if (estaRepetido){
            //Math.floor(Math.random()*(N-M+1)+M);  // Valor entre M y N, ambos incluidos.
            //usuarioRepetido=Math.floor(Math.random()*(1-99+1)+99)+"";
            Random rand = new Random();
            usuarioRepetido= rand.nextInt(99)+""; // Gives n such that 0 <= n < 20
            llenarUsuario();
        }
    }
}
