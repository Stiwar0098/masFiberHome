package com.brasmapi.masfiberhome.ui.crear;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.CajaNivel2DAO;
import com.brasmapi.masfiberhome.dao.ClientesDAO;
import com.brasmapi.masfiberhome.dao.ModeloOntDAO;
import com.brasmapi.masfiberhome.dao.OntDAO;
import com.brasmapi.masfiberhome.dao.PlanesDAO;
import com.brasmapi.masfiberhome.dao.RangoDireccionIpDAO;
import com.brasmapi.masfiberhome.dao.RangoHilosCaja2DAO;
import com.brasmapi.masfiberhome.dao.RangoOntDAO;
import com.brasmapi.masfiberhome.dao.ServiciosDAO;
import com.brasmapi.masfiberhome.dao.ServiportDAO;
import com.brasmapi.masfiberhome.dao.VlanDAO;
import com.brasmapi.masfiberhome.entidades.CajaNivel2;
import com.brasmapi.masfiberhome.entidades.Clientes;
import com.brasmapi.masfiberhome.entidades.ModeloOnt;
import com.brasmapi.masfiberhome.entidades.Ont;
import com.brasmapi.masfiberhome.entidades.Planes;
import com.brasmapi.masfiberhome.entidades.RangoDireccionIp;
import com.brasmapi.masfiberhome.entidades.RangoHilosCaja2;
import com.brasmapi.masfiberhome.entidades.RangoOnt;
import com.brasmapi.masfiberhome.entidades.Servicios;
import com.brasmapi.masfiberhome.entidades.Vlan;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.ui.buscar.DialogBuscarCajaNivel2;
import com.brasmapi.masfiberhome.ui.buscar.DialogBuscarCliente;
import com.google.android.gms.dynamic.IFragmentWrapper;
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
        ServiportDAO.interfazServiport,
        ServiciosDAO.interfazServicio,
        PlanesDAO.interfazPlanesDAO,
        RangoDireccionIpDAO.interfazRangoDireccionIp,
        OntDAO.interfazOntDAO,
        CajaNivel2DAO.interfazCajaNivel2DAO,
        ClientesDAO.interfazClientesDAO,DialogCrearCliente.finalizoDialogCrearClientes{

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
    int anio,mes,dia;

    Vlan vlan;//g
    Vlan vlanAnterior;
    VlanDAO vlanDAO;
    int banderaVlan=1;

    static Clientes clientes;
    Clientes clientesAnterior;
    //static Clientes clientesSeleccionadoAnterior;
    ClientesDAO clientesDAO;
    CajaNivel2 cajaNivel2;//g
    CajaNivel2 cajaNivel2Anterior;
    CajaNivel2DAO cajaNivel2DAO;

    Ont ont;
    Ont ontAnterior;
    OntDAO ontDAO;

    ModeloOnt modeloOnt;
    ModeloOntDAO modeloOntDAO;
    Spinner spinnerPlan;
    TextInputLayout txtServiPort,txtCliente,txtCajaNivel2,txtOnt,txtQuit,txtNumeroOnt,txtUsuario,txtDireccion,txtReferencia,txtHiloEnCaja2,txtDireccionIp,txtLatitud,txtLongitud;
    TextInputLayout txtComandoPlanes,txtInterfazPoncard,txtAgregarOnt,txtEquipoBridge,txtEliminarServicio, txtAgregarServicioAlPuerto,txtAgregarPlanCliente,txtAgregarDescripcionPuerto,txtEliminarOnt;
    Switch switchNumeroOnt,switchHiloEnCaja2,switchLatLon,switchDireccionIp;
    Button btnGuardar;
    ImageView btnIrUbicacion;
    LinearLayout groupComando;

    RangoHilosCaja2DAO rangoHilosCaja2DAO;
    RangoHilosCaja2 rangoHilosCaja2;
    RangoHilosCaja2 rangoHilosCaja2Anterior;

    RangoOntDAO rangoOntDAO;
    RangoOnt rangoOnt;
    RangoOnt rangoOntAnterior;

    RangoDireccionIpDAO rangoDireccionIpDAO;
    RangoDireccionIp rangoDireccionIp;
    RangoDireccionIp rangoDireccionIpAnterior;

    ServiportDAO serviportDAO;

    public static Servicios servicios;
    Servicios serviciosAnterior;
    ServiciosDAO serviciosDAO;

    PlanesDAO planesDAO;

    boolean validarOntEditar=false;
    boolean validarUsuarioEditar=false;
    boolean cajaSelecionadaOeditar=false;

    static boolean isCrearCliente=false;
    static boolean isEditarCliente=false;


    int numeroHiloCaja2,numeroOnt,idplanes;
    Integer ip_numero,serviport;
    String nombreCaja2,direccionIp,usuario,equipoBridge;
    String direccion,referencia,fecha,longitud,latitud,comandoPlanes,iterfazPonCard,agregarOnt,quit,eliminarServicio,agregarServicioPuerto,agregarDescripcionPuerto,eliminarOnt,agregarPlanCliente;
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
                    //Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            });

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         vista=inflater.inflate(R.layout.fragment_crear_servicio, container, false);
        context=getActivity();
        serviciosAnterior=servicios;
        clientes=null;
        cajaNivel2=null;
        try {
            ((MainActivity)getActivity()).setTitle("Crear servicio");
        }catch (Exception er){

        }
        modeloOntDAO=new ModeloOntDAO(CrearServicioFragment.this);
        clientesDAO=new ClientesDAO(CrearServicioFragment.this);
        cajaNivel2DAO = new CajaNivel2DAO(CrearServicioFragment.this);
        ontDAO=new OntDAO(CrearServicioFragment.this);
        vlanDAO=new VlanDAO(CrearServicioFragment.this);
        rangoHilosCaja2DAO=new RangoHilosCaja2DAO(CrearServicioFragment.this);
        rangoDireccionIpDAO=new RangoDireccionIpDAO(CrearServicioFragment.this);
        serviportDAO=new ServiportDAO(CrearServicioFragment.this);
        rangoOntDAO=new RangoOntDAO(CrearServicioFragment.this);
        planesDAO=new PlanesDAO(CrearServicioFragment.this);
        serviciosDAO =new ServiciosDAO(CrearServicioFragment.this);
        rangoHilosCaja2=new RangoHilosCaja2();
        rangoOnt=new RangoOnt();
        rangoDireccionIp=new RangoDireccionIp();
        groupComando=(LinearLayout) vista.findViewById(R.id.linearGrupoComando_CrearServicio);
        btnGuardar=(Button) vista.findViewById(R.id.btnGuardar_CrearServicio);
        btnIrUbicacion=(ImageView) vista.findViewById(R.id.btnUbicacion_crearServicio);
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
        txtFechaInstalacion.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Procesos.cerrarTeclado(getActivity());
                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtFechaInstalacion.getEditText().setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });
        planesDAO.filtarPlanes("",context,false);
        btnIrUbicacion.setVisibility(View.GONE);
        if (opc.equals("editar")){
            Procesos.cargandoIniciar(context);
            latLonAutomaticoApagado();
            btnGuardar.setText("editar");
            txtServiPort.getEditText().setText(servicios.getId_servicio()+"");
            txtCliente.getEditText().setText(servicios.getNombreCliente());//falta buscar la entidad cliente
            clientesDAO.buscarClientes(servicios.getId_cliente()+"",context);
            cajaNivel2DAO.buscarCajaNivel2(servicios.getId_cajanivel2()+"",context);
            txtUsuario.getEditText().setText(servicios.getUsuario());
            ontDAO.buscarOnt(servicios.getId_ont()+"",context);
            validarOntEditar=true;
            validarUsuarioEditar=true;
            banderaVlan=0;
            switchNumeroOnt.setChecked(false);
            switchNumeroOnt.setText("Manual");
            txtNumeroOnt.getEditText().setEnabled(true);
            switchHiloEnCaja2.setChecked(false);
            switchHiloEnCaja2.setText("Manual");
            txtHiloEnCaja2.getEditText().setEnabled(true);
            switchDireccionIp.setChecked(false);
            switchDireccionIp.setText("Manual");
            txtDireccionIp.getEditText().setEnabled(true);
            switchLatLon.setChecked(false);
            switchLatLon.setText("Manual");
            //spiner plan en set listaplanes
            txtDireccion.getEditText().setText(servicios.getDireccion());
            txtReferencia.getEditText().setText(servicios.getReferencia());
            String[] fecha= obtenerFechaPorDiaMesAno(servicios.getFecha());
            dia = Integer.parseInt(fecha[0]);
            mes = Integer.parseInt(fecha[1]);
            anio = Integer.parseInt(fecha[2]);
            txtFechaInstalacion.getEditText().setText(dia+"-"+(mes)+"-"+ anio);

            txtHiloEnCaja2.getEditText().setText(servicios.getHiloCajaNivel2()+"");
            txtDireccionIp.getEditText().setText(servicios.getDireccionip());
            direccionIp=servicios.getDireccionip();
            ip_numero=servicios.getIp_cliente();

            txtLatitud.getEditText().setText(servicios.getLatitud());
            txtLongitud.getEditText().setText(servicios.getLongitud());
            rangoHilosCaja2DAO.obtenerHiloCaja2Anterior(servicios.getId_cajanivel2(),servicios.getId_servicio(),context);

            //esto se lo hace en la respuesta del buscar caja nivel2
            //rangoOntDAO.obtenerNumeroOntAnterior(vlan.getId(),servicios.getId_ont(),context);
            //rangoDireccionIpDAO.obtenerDireccionIpAnterior(vlan.getId(),servicios.getId_servicio(),context);
        }else{
            dia = calendar.get(Calendar.DAY_OF_MONTH);
            mes = calendar.get(Calendar.MONTH);
            anio = calendar.get(Calendar.YEAR);
            serviportAutomatico();
            latLonAutomaticoEncendido();
            txtFechaInstalacion.getEditText().setText(dia+"-"+(mes+1)+"-"+ anio);
            groupComando.setVisibility(View.GONE);
        }
        if (Procesos.user!=null){
            if (Procesos.user.getRol()!=1){//si es tecnico
                if (opc.equals("editar")){
                    btnIrUbicacion.setVisibility(View.VISIBLE);
                }
                groupComando.setVisibility(View.GONE);
            }
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Procesos.cerrarTeclado(getActivity());
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Confirmación");
                builder.setMessage( "Seguro desea "+opc+" ?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(Procesos.validarTxtEstaLleno(txtServiPort)&&Procesos.validarTxtEstaLleno(txtCliente)&&Procesos.validarTxtEstaLleno(txtCajaNivel2)&&Procesos.validarTxtEstaLleno(txtOnt)&&Procesos.validarTxtEstaLleno(txtNumeroOnt)&&Procesos.validarTxtEstaLleno(txtUsuario)&&spinnerPlan.getSelectedItemPosition()!=0&&Procesos.validarTxtEstaLleno(txtDireccionIp)&&Procesos.validarTxtEstaLleno(txtDireccion)&&Procesos.validarTxtEstaLleno(txtReferencia)&&Procesos.validarTxtEstaLleno(txtFechaInstalacion)&&Procesos.validarTxtEstaLleno(txtHiloEnCaja2)&&Procesos.validarTxtEstaLleno(txtComandoPlanes)&&Procesos.validarTxtEstaLleno(txtInterfazPoncard)&&Procesos.validarTxtEstaLleno(txtAgregarOnt)&&Procesos.validarTxtEstaLleno(txtQuit)&&Procesos.validarTxtEstaLleno(txtEliminarServicio)&&Procesos.validarTxtEstaLleno(txtAgregarServicioAlPuerto)&&Procesos.validarTxtEstaLleno(txtAgregarPlanCliente)&&Procesos.validarTxtEstaLleno(txtAgregarDescripcionPuerto)&&Procesos.validarTxtEstaLleno(txtEliminarOnt)){
                                    Procesos.cargandoIniciar(context);
                                    equipoBridge=Procesos.obtenerTxtEnString(txtEquipoBridge);
                                    numeroOnt=Procesos.obtenerTxtEnEntero(txtNumeroOnt);
                                    //ont.setNumeroOnt(Procesos.obtenerTxtEnEntero(txtNumeroOnt));
                                    usuario=Procesos.obtenerTxtEnString(txtUsuario);
                                    idplanes=obtenerIdDePlanes();
                                    direccion=Procesos.obtenerTxtEnString(txtDireccion);
                                    referencia=Procesos.obtenerTxtEnString(txtReferencia);
                                    fecha=Procesos.obtenerTxtEnString(txtFechaInstalacion);
                                    numeroHiloCaja2 =Procesos.obtenerTxtEnEntero(txtHiloEnCaja2);
                                    nombreCaja2=Procesos.obtenerTxtEnString(txtCajaNivel2);
                                    direccionIp=Procesos.obtenerTxtEnString(txtDireccionIp);
                                    latitud=Procesos.obtenerTxtEnString(txtLatitud);
                                    longitud=Procesos.obtenerTxtEnString(txtLongitud);
                                    comandoPlanes=Procesos.obtenerTxtEnString(txtComandoPlanes);
                                    iterfazPonCard=Procesos.obtenerTxtEnString(txtInterfazPoncard);
                                    agregarOnt=Procesos.obtenerTxtEnString(txtAgregarOnt);
                                    quit=Procesos.obtenerTxtEnString(txtQuit);
                                    eliminarServicio=Procesos.obtenerTxtEnString(txtEliminarServicio);
                                    agregarServicioPuerto=Procesos.obtenerTxtEnString(txtAgregarServicioAlPuerto);
                                    agregarPlanCliente=Procesos.obtenerTxtEnString(txtAgregarPlanCliente);
                                    agregarDescripcionPuerto=Procesos.obtenerTxtEnString(txtAgregarDescripcionPuerto);
                                    eliminarOnt=Procesos.obtenerTxtEnString(txtEliminarOnt);
                                    validar0ontExistente();
                                }else {
                                    Toast.makeText(context, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
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
            }
        });
        btnIrUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Procesos.comoLlegar(getActivity(),txtLatitud.getEditText().getText().toString(),txtLongitud.getEditText().getText().toString()));
            }
        });
        addTextChangedListener();
        setOnCheckedChangeListener();
        setEndIconOnClickListener();
        return vista;
    }

    boolean bandedarGuardar=false;
    public void crearEditar(){
        bandedarGuardar=true;
        if (opc.equals("crear")){
            ont.setNumeroOnt(numeroOnt);
            if (isCrearCliente){
                clientesDAO.crearClientes(clientes,context);
            }else if(isEditarCliente){
                clientesDAO.editarClientes(clientes,context,false);
                ontDAO.crearOnt(ont,context);//primero guarda el ont una vez guardado llama al metodo limpiaront que ejecuta el guardar servicio
            }else{
                ontDAO.crearOnt(ont,context);//primero guarda el ont una vez guardado llama al metodo limpiaront que ejecuta el guardar servicio
            }
        }else if(opc.equals("editar")) {
            if (seDebeEditarValidar1Caja2 || seDebeEditarValidar2NumeroOnt || seDebeEditarValidar3DireccionIp || !Procesos.obtenerTxtEnString(txtDireccion).equals(serviciosAnterior.getDireccion())||!Procesos.obtenerTxtEnString(txtReferencia).equals(serviciosAnterior.getReferencia())||!Procesos.obtenerTxtEnString(txtFechaInstalacion).equals(serviciosAnterior.getFecha())|| !spinnerPlan.getSelectedItem().toString().equals(serviciosAnterior.getNombrePlan()+" megas")||!Procesos.obtenerTxtEnString(txtLatitud).equals(serviciosAnterior.getLatitud()) || !Procesos.obtenerTxtEnString(txtUsuario).equals(serviciosAnterior.getUsuario()) || seModificoOnt){ //si se modifico algo se debe editar
                if (isCrearCliente){
                    clientesDAO.crearClientes(clientes,context);
                }else if(isEditarCliente){
                    clientesDAO.editarClientes(clientes,context,false);
                    serviciosDAO.editarServicio(new Servicios(serviport,usuario,direccion,referencia,fecha,longitud,latitud,idplanes,"",ont.getId(),"",cajaNivel2.getId_CajaNivel2(),"",clientes.getId_cliente(),"",numeroHiloCaja2,direccionIp,ip_numero,comandoPlanes,iterfazPonCard,agregarOnt,equipoBridge,quit,eliminarServicio,agregarServicioPuerto,agregarDescripcionPuerto,eliminarOnt,Procesos.user.getId(),"editar",generarComandoCopiar(),Procesos.obtenerFechaActualConHora(),"pendiente"),context,false);
                }else{
                    serviciosDAO.editarServicio(new Servicios(serviport,usuario,direccion,referencia,fecha,longitud,latitud,idplanes,"",ont.getId(),"",cajaNivel2.getId_CajaNivel2(),"",clientes.getId_cliente(),"",numeroHiloCaja2,direccionIp,ip_numero,comandoPlanes,iterfazPonCard,agregarOnt,equipoBridge,quit,eliminarServicio,agregarServicioPuerto,agregarDescripcionPuerto,eliminarOnt,Procesos.user.getId(),"editar",generarComandoCopiar(),Procesos.obtenerFechaActualConHora(),"pendiente"),context,false);
                }
            }else{// no se edito nada
                if (!seModificoOnt){
                    Toast.makeText(context, "No se a cambiado la información", Toast.LENGTH_SHORT).show();
                }else{
                    getActivity().onBackPressed();
                }
            }
        Procesos.cargandoDetener();
        }
    }

    private String generarComandoCopiar(){
        String comandoCopiar="";
        if (opc.equals("crear")){
            comandoCopiar=iterfazPonCard+"@"+
                    agregarOnt+"@"+
                    quit+"@"+
                    agregarServicioPuerto+"@"+
                    agregarPlanCliente+"@"+
                    agregarDescripcionPuerto;
        }else if(opc.equals("editar")){
            comandoCopiar=serviciosAnterior.getEliminarServicio()+"@"+
                    serviciosAnterior.getIterfazPonCard()+"@"+
                    serviciosAnterior.getEliminarOnt()+"@"+
                    agregarOnt+"@"+
                    quit+"@"+
                    agregarServicioPuerto+"@"+
                    agregarPlanCliente+"@"+
                    agregarDescripcionPuerto;
        }
        return comandoCopiar;
    }
    String op,op2;
    private void setEndIconOnClickListener() {
        txtCliente.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op="Editar";
                if (!isCrearCliente){
                    op2="Crear";
                }else{
                    op2="Cancelar";
                }
                if(Procesos.obtenerTxtEnString(txtCliente).equals("")){
                    op="Crear";
                    op2="Cancelar";
                }
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Opciones");
                builder.setMessage("¿Que desea hacer?")
                        .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new DialogBuscarCliente(context,CrearServicioFragment.this);
                            }
                        })
                        .setNegativeButton(op, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (op.equals("Crear")){
                                    DialogCrearCliente.opc="crear";
                                }else{
                                    DialogCrearCliente.opc="editar";
                                }
                                new DialogCrearCliente(context,clientes,CrearServicioFragment.this);
                            }
                        })
                        .setNeutralButton(op2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (op2.equals("Cancelar")){
                                    Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else{
                                    DialogCrearCliente.opc="crear";
                                    new DialogCrearCliente(context,clientes,CrearServicioFragment.this);
                                }
                            }
                        })
                        .show();

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
                    Procesos.copiarEnElPortapapeles(context,Procesos.obtenerTxtEnString(txtComandoPlanes),getActivity());
                }
            }
        });
        txtInterfazPoncard.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtInterfazPoncard.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,Procesos.obtenerTxtEnString(txtInterfazPoncard),getActivity());
                }
            }
        });
        txtAgregarOnt.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtAgregarOnt.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,Procesos.obtenerTxtEnString(txtAgregarOnt),getActivity());
                }
            }
        });
        txtEquipoBridge.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtEquipoBridge.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,Procesos.obtenerTxtEnString(txtEquipoBridge),getActivity());
                }
            }
        });
        txtQuit.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtQuit.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,Procesos.obtenerTxtEnString(txtQuit),getActivity());
                }
            }
        });
        txtEliminarServicio.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtEliminarServicio.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,Procesos.obtenerTxtEnString(txtEliminarServicio),getActivity());
                }
            }
        });
        txtAgregarServicioAlPuerto.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtAgregarServicioAlPuerto.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,Procesos.obtenerTxtEnString(txtAgregarServicioAlPuerto),getActivity());
                }
            }
        });
        txtAgregarPlanCliente.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtAgregarPlanCliente.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,Procesos.obtenerTxtEnString(txtAgregarPlanCliente),getActivity());
                }
            }
        });
        txtAgregarDescripcionPuerto.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtAgregarDescripcionPuerto.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,Procesos.obtenerTxtEnString(txtAgregarDescripcionPuerto),getActivity());
                }
            }
        });
        txtEliminarOnt.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtEliminarOnt.getEditText().getText().toString().equals("")){
                    Procesos.copiarEnElPortapapeles(context,Procesos.obtenerTxtEnString(txtEliminarOnt),getActivity());
                }
            }
        });
        txtOnt.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogCrearOnt(context,ont,CrearServicioFragment.this,barcodeLauncher);
            }
        });
    }
    boolean seModificoOnt=false;
    public void validar0ontExistente(){
        Toast.makeText(context, "validar 0", Toast.LENGTH_SHORT).show();
        if (opc.equals("crear")){
            if (ont!=null){
                ontDAO.buscarOnt(ont.getSerieOnt(),context);
            }else{
                Procesos.cargandoDetener();
                Toast.makeText(context, "Ingrese una ont", Toast.LENGTH_SHORT).show();
            }
        }else{
            if (validarOntEditar){
                ontDAO.buscarOnt(ont.getSerieOnt(),context);
            }else {
                if (!ontAnterior.getSerieOnt().equals(ont.getSerieOnt())||!ontAnterior.getResponsable().equals(ont.getResponsable())||ontAnterior.getId_modeloOnt()!=ont.getId_modeloOnt()||ontAnterior.getNumeroOnt()!=numeroOnt){//si es diferente se modifico la ont
                    Ont aux = new Ont(ontAnterior.getId(),ont.getSerieOnt(),ont.getId_modeloOnt(),ont.getNombreModelo(),ont.getResponsable(),numeroOnt,ont.getEstado());
                    seModificoOnt=true;
                    ontDAO.editarOnt(aux,context,false);
                    ont=aux;
                    validar1HiloEnCaja2();
                }else{
                    validar1HiloEnCaja2();
                }
            }
        }
    }
    boolean seDebeEditarValidar1Caja2=true;
    boolean hiloModificadoCreado=true;
    public void validar1HiloEnCaja2(){
        Toast.makeText(context, "validar 1", Toast.LENGTH_SHORT).show();
        if (switchHiloEnCaja2.isChecked()){
            if (Procesos.validarTxtEstaLleno(txtHiloEnCaja2)){
                validar2NumeroOnt();
            }else{
                Toast.makeText(context, "Esta caja ya no dispone de hilos", Toast.LENGTH_SHORT).show();
            }
        }else{//manual
            if (servicios!=null){//quiere decir que opc=editar
                if(cajaNivel2Anterior.getId_CajaNivel2()!=cajaNivel2.getId_CajaNivel2()){//si es diferente es decir que si se seleciono otra caja y debemos validar el nuevo numero de hilo
                    rangoHilosCaja2DAO.verificarHiloCaja2Manual(cajaNivel2.getId_CajaNivel2(), numeroHiloCaja2,context);
                    servicios.setId_cajanivel2(cajaNivel2.getId_CajaNivel2());
                    servicios.setHiloCajaNivel2(numeroHiloCaja2);
                }else{//si los id son iguales es decir que no se seleciono otra caja
                    if (servicios.getHiloCajaNivel2()!=numeroHiloCaja2){//como no se seleciono otra caja se verifica si cambiaron el numero manualmente
                        // si los numeros son diferentes es decir que cambiaron el numero de hilo manualmente y debemos validar
                        rangoHilosCaja2DAO.verificarHiloCaja2Manual(cajaNivel2.getId_CajaNivel2(), numeroHiloCaja2,context);
                        servicios.setHiloCajaNivel2(numeroHiloCaja2);
                    }else{// los numeros de hilo son los mismos no es necesario validar nuevamente
                        hiloModificadoCreado=false;
                        seDebeEditarValidar1Caja2=false;
                        validar2NumeroOnt();//ojo en cada validar true se llama al siguiente validar
                    }
                }
            }else{//quiere decir que opc=crear por ende se debe validar
                hiloModificadoCreado=true;
                rangoHilosCaja2DAO.verificarHiloCaja2Manual(cajaNivel2.getId_CajaNivel2(), numeroHiloCaja2,context);
            }
        }
    }
    boolean seDebeEditarValidar2NumeroOnt=true;
    boolean numeroOntModificadoCreado=true;
    public void validar2NumeroOnt(){
        Toast.makeText(context, "validar 2", Toast.LENGTH_SHORT).show();
        if (switchNumeroOnt.isChecked()){
            if (Procesos.validarTxtEstaLleno(txtNumeroOnt)){
                validar3DireccionIp();
            }else{
                Toast.makeText(context, "Esta vlan ya no dispone de numeros para ont", Toast.LENGTH_SHORT).show();
            }
        }else{//manual
            if (servicios!=null){//quiere decir que opc=editar
                if(vlanAnterior.getId()!=vlan.getId()){//si es diferente es decir que si se seleciono otra caja y debemos validar el nuevo numero de hilo
                    rangoOntDAO.verificarNumeroOntManual(vlan.getId(), numeroOnt,context);
                    //servicios.setId_cajanivel2(cajaNivel2.getId_CajaNivel2());
                    //servicios.setHiloCajaNivel2(numeroHiloCaja2);
                    ont.setNumeroOnt(numeroOnt);
                }else{//si el id es igual es decir que no se seleciono otra caja/ por ende es la misma vlan
                    if (ontAnterior.getNumeroOnt()!=numeroOnt){//como no se seleciono otra caja se verifica si cambiaron el numero manualmente
                        // si los numeros son diferentes es decir que cambiaron el numero de hilo manualmente y debemos validar
                        rangoOntDAO.verificarNumeroOntManual(vlan.getId(), numeroOnt,context);
                        ont.setNumeroOnt(numeroOnt);
                        //servicios.setHiloCajaNivel2(numeroHiloCaja2);
                    }else{// los numeros de hilo son los mismos no es necesario validar nuevamente
                        numeroOntModificadoCreado=false;
                        seDebeEditarValidar2NumeroOnt=false;
                        validar3DireccionIp();//ojo en cada validar true se llama al siguiente validar
                    }
                }
            }else{//quiere decir que opc=crear por ende se debe validar
                numeroOntModificadoCreado=true;
                rangoOntDAO.verificarNumeroOntManual(vlan.getId(), numeroOnt,context);
            }
        }
    }
    boolean seDebeEditarValidar3DireccionIp =true;
    boolean direccionIpModificadoCreado=true;
    public void validar3DireccionIp(){
        Toast.makeText(context, "validar 3", Toast.LENGTH_SHORT).show();
        if (switchDireccionIp.isChecked()){
            if (Procesos.validarTxtEstaLleno(txtDireccionIp)){
                validarUsuarioASidoUtilizado();
            }else{
                Toast.makeText(context, "Esta vlan ya no dispone direcciones ip", Toast.LENGTH_SHORT).show();
            }
        }else{//manual
            if(Procesos.validarDireccionIp(direccionIp)){
                int[] ipDescompuesta=Procesos.descomponerDireccionIp(direccionIp);
                int[] ipDescompuestaIpVlanInicio=Procesos.descomponerDireccionIp(vlan.getIpInicio());
                int[] ipDescompuestaIpVlanFin=Procesos.descomponerDireccionIp(vlan.getIpFin());
                if ((ipDescompuesta[0]==ipDescompuestaIpVlanInicio[0]) && (ipDescompuesta[1]==ipDescompuestaIpVlanInicio[1]) && (ipDescompuesta[2]==ipDescompuestaIpVlanInicio[2]) && (ipDescompuesta[3]>=ipDescompuestaIpVlanInicio[3] && ipDescompuesta[3]<=ipDescompuestaIpVlanFin[3])){
                    if (servicios!=null){//quiere decir que opc=editar
                        if(vlanAnterior.getId()!=vlan.getId()){//si los id son diferentes es decir que si se seleciono otra caja y debemos validar el nuevo numero de hilo
                            rangoDireccionIpDAO.verificarDireccionIpManual(vlan.getId(), ipDescompuesta[3],context);
                        }else{//si el id es igual es decir que no se seleciono otra caja
                            if (!servicios.getDireccionip().equals(direccionIp)){//como no se seleciono otra caja se verifica si cambiaron el numero manualmente
                                // si los numeros son diferentes es decir que cambiaron el numero de direccionip manualmente y debemos validar
                                rangoDireccionIpDAO.verificarDireccionIpManual(vlan.getId(), ipDescompuesta[3],context);
                            }else{// los numeros de direccion ip son los mismos no es necesario validar nuevamente
                                direccionIpModificadoCreado=false;
                                seDebeEditarValidar3DireccionIp =false;
                                validarUsuarioASidoUtilizado();//ojo en cada validar true se llama al siguiente validar
                            }
                        }
                    }else{//quiere decir que opc=crear por ende se debe validar
                        direccionIpModificadoCreado=true;
                        rangoDireccionIpDAO.verificarDireccionIpManual(vlan.getId(), ipDescompuesta[3],context);
                    }
                }else{
                    Toast.makeText(context, "La direccion ip ingresada no esta en el rango de ips de la caja selecionada", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(context, "Extructura de la direccion ip incorrecta", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void validarUsuarioASidoUtilizado(){
        bandedarGuardar=true;
        llenarUsuario();
    }

    public void validar4ServiPortASidoUtilizado(){
        serviportAutomatico();
    }
    public void validar5NumeroOntASidoUtilizado(){
        if (switchNumeroOnt.isChecked()){
            numeroOntAutomatico();
        }else{
            validar6HiloEnCaja2ASidoUtilizado();
        }

    }
    public void validar6HiloEnCaja2ASidoUtilizado(){
        if (switchHiloEnCaja2.isChecked()){
            hiloEnCaja2Automatico();
        }else{
            validar7DireccionIpASidoUtilizado();
        }
    }
    public void validar7DireccionIpASidoUtilizado(){
        if (switchDireccionIp.isChecked()){
            direccionIpAutomatico();
        }else{
            crearEditar();
        }
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
                    if (vlan!=null){
                        int[] ip=Procesos.descomponerDireccionIp(vlan.getIpInicio());
                        txtDireccionIp.getEditText().setText(ip[0]+"."+ip[1]+"."+ip[2]+".");
                    }
                }
            }
        });
    }

    private void direccionIpAutomatico() {
        if (switchDireccionIp.isChecked()){
            txtDireccionIp.getEditText().setEnabled(false);
            if(vlan!=null){
                rangoDireccionIpDAO.obtenerDireccionIpAutomatico(vlan.getId(),context);
            }
        }
    }

    private void hiloEnCaja2Automatico() {
           txtHiloEnCaja2.getEditText().setEnabled(false);
           switchHiloEnCaja2.setChecked(true);
           switchHiloEnCaja2.setText("Automático");
           if(cajaNivel2!=null){
               rangoHilosCaja2DAO.obtenerHiloCaja2Automatico(cajaNivel2.getId_CajaNivel2(),context);
           }
    }

    private void numeroOntAutomatico() {
        if (switchNumeroOnt.isChecked()){
            txtNumeroOnt.getEditText().setEnabled(false);
            if(vlan!=null){
                rangoOntDAO.obtenerNumeroOntAutomatico(vlan.getId(),context);
            }
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
        serviportDAO.obtenerServiportAutomatico(context);
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
                    usuarioRepetido="";
                    llenarUsuario();
                    llenarComandoPlanes();
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
                    usuarioRepetido="";
                    llenarUsuario();
                    llenarInterfazPoncard();
                    llenarAgregarOnt();
                    llenarAgregarServicioAlPuerto();
                    llenarEquipoBridge();
                    llenarAgregarDescripcionAlPuerto();
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
        if (serviport!=null && !serviport.equals("") && spinnerPlan.getSelectedItemPosition()!=0 && clientes!=null && listaPlanes!=null){
            plan=obtenerPrimerApellido(spinnerPlan.getSelectedItem().toString());
            conca="service-port "+serviport+" inbound traffic-table index "+plan+" outbound traffic-table index "+plan;
            txtComandoPlanes.getEditText().setText(conca);
        }
    }
    String usuarioRepetido="";
    public void llenarUsuario(){
        if (validarUsuarioEditar==false) {
            if (clientes != null && cajaNivel2 != null) {
                String conca;
                conca = obtenerPrimerLetra(clientes.getNombre()) + obtenerPrimerApellido(clientes.getApellido()) + usuarioRepetido + "_" + cajaNivel2.getAbreviaturaCajaNivel1() + "_" + cajaNivel2.getAbreviatura();
                txtUsuario.getEditText().setText(conca);
                usuario=Procesos.obtenerTxtEnString(txtUsuario);
                serviciosDAO.validarUsuario(conca, context);
            }
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
        if(vlan!=null && modeloOnt!=null ){
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
            String[] ar = fecha.split("-");
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
        //clientesSeleccionadoAnterior=Clientes;
        txtCliente.getEditText().setText(clientes.getNombre()+" "+clientes.getApellido());
        validarUsuarioEditar=false;
        isEditarCliente=false;
        isCrearCliente=false;
        llenarUsuario();
    }
    @Override
    public void CajaNivel2Selecionado(CajaNivel2 CajaNivel2) {
        this.cajaNivel2=CajaNivel2;
        validarUsuarioEditar=false;
        cajaSelecionadaOeditar=true;
        txtCajaNivel2.getEditText().setText(cajaNivel2.getNombre_CajaNivel2());
        vlanDAO.buscarVlan(cajaNivel2.getIdvlan()+"",context);
    }

    @Override
    public void setVlan(Vlan Vlan) {
        this.vlan=Vlan;
        if (opc.equals("crear")){
            int[] ip=Procesos.descomponerDireccionIp(vlan.getIpInicio());
            if (!switchDireccionIp.isChecked()){
                txtDireccionIp.getEditText().setText(ip[0]+"."+ip[1]+"."+ip[2]+".");
            }
        }else if(banderaVlan==0){//si es 0 quiere decir que aun no entra en esta parte
            this.vlanAnterior=Vlan;
            banderaVlan=1;//al asignarle 1 ya no va a volver a entrar aqui
        }
        if (opc.equals("editar")){
            Procesos.cargandoDetener();
            if (vlanAnterior.getId()!=vlan.getId()){
                txtNumeroOnt.getEditText().setText("");
                numeroOntAutomatico();
                switchNumeroOnt.setChecked(true);
                switchNumeroOnt.setText("Automático");
                txtNumeroOnt.getEditText().setEnabled(false);

                txtDireccionIp.getEditText().setText("");
                direccionIpAutomatico();
                switchDireccionIp.setChecked(true);
                switchDireccionIp.setText("Automático");
                txtDireccionIp.getEditText().setEnabled(false);
            }
        }
        llenarInterfazPoncard();
        llenarAgregarOnt();
        llenarAgregarServicioAlPuerto();
        llenarEquipoBridge();
        llenarEliminarOnt();
        //----
        if (cajaSelecionadaOeditar){
            numeroOntAutomatico();
            hiloEnCaja2Automatico();
            direccionIpAutomatico();
        }else{
            Procesos.cargandoDetener();
        }
    }

    @Override
    public void setListaVlan(List<Vlan> lista) {

    }

    @Override
    public void limpiarVlan() {

    }

    @Override
    public void setModeloOnt(ModeloOnt ModeloOnt) {
        this.modeloOnt=ModeloOnt;
        Procesos.cargandoDetener();
        llenarEquipoBridge();
    }

    @Override
    public void setListaModeloOnt(List<ModeloOnt> lista) {

    }

    @Override
    public void limpiarModeloOnt() {

    }

    @Override
    public void setPlanes(Planes Planes) {

    }
    List<Planes> listaPlanes;
    @Override
    public void setListaPlanes(List<Planes> lista) {
        listaPlanes=lista;
        String[] a=new String[lista.size()+1];
        a[0]="Selecionar el plan";
        int i=1;
        for (Planes as:lista) {
            a[i]= String.valueOf(as.getNombre()+" megas");
            i++;
        }
        Procesos.cargandoDetener();
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,a);
        spinnerPlan.setAdapter(adapter);
        if (opc.equals("crear")){
            spinnerPlan.setSelection(1);
        }else{
            spinnerPlan.setSelection(obtenerNumeroParaSpinnerDePlanes(servicios.getId_planes()));
        }
    }

    @Override
    public void limpiarPlanes() {

    }

    public Integer obtenerIdDePlanes(){
        for (Planes ase: listaPlanes) {
            String aux=ase.getNombre()+" megas";
            if (aux.equals(spinnerPlan.getSelectedItem().toString())){
             return ase.getId();
            }
        }
        return null;
    }
    public Integer obtenerNumeroParaSpinnerDePlanes(Integer idplanes){
        int i=1;
        for (Planes ase: listaPlanes) {
            if (ase.getId()==idplanes){
                return i;
            }
            i++;
        }
        return 0;
    }
    @Override
    public void limpiarServicio() {//debe ser el limpiar de servicioDao
        if(hiloModificadoCreado){
            rangoHilosCaja2.setEstado("activo");
            rangoHilosCaja2DAO.editarRangoHilosCaja2(rangoHilosCaja2,Procesos.obtenerTxtEnString(txtUsuario),context);
            if (rangoHilosCaja2Anterior!=null){
                rangoHilosCaja2DAO.editarRangoHilosCaja2Anterior(rangoHilosCaja2Anterior.getId_rangocaja2(),context);
            }
        }
        if(numeroOntModificadoCreado){
            rangoOnt.setEstado("activo");
            rangoOntDAO.editarRangoOnt(rangoOnt, ont.getSerieOnt(),context);
            if(rangoOntAnterior!=null){
                rangoOntDAO.editarRangoOntAnterior(rangoOntAnterior.getId_rangoont(),context);
            }
        }
        if(direccionIpModificadoCreado){
            rangoDireccionIp.setEstado("activo");
            rangoDireccionIpDAO.editarRangoDireccionIp(rangoDireccionIp,usuario,context);
            if(rangoDireccionIpAnterior!=null){
                rangoDireccionIpDAO.editarRangoDireccionIpAnterior(rangoDireccionIpAnterior.getid_rangodireccionesip(),context);
            }
        }
        if (Procesos.user.getRol()==1){//administrador
            try {
                getActivity().onBackPressed();
            }catch (Exception w){

            }
        }else{
            try {
                getActivity().onBackPressed();
                MainActivity.navController.navigate(R.id.nav_pendiente_tec);
                MainActivity.navigationView.getMenu().getItem(3).setChecked(true);
            }catch (Exception w){

            }

        }
        onDestroyView();
    }

    @Override
    public void setOntDialogoCrearOnt(Ont ont2) {
        this.ont= ont2;
        validarOntEditar=false;
        txtOnt.getEditText().setText(ont2.getSerieOnt());
        modeloOntDAO.buscarModeloOnt(ont2.getId_modeloOnt()+"",context);
        llenarAgregarOnt();
        llenarAgregarServicioAlPuerto();
        llenarEliminarOnt();
        llenarEquipoBridge();
    }

    @Override
    public void setOnt(Ont ont) {
        if (opc.equals("crear")){
            if (ont!=null){
                Procesos.cargandoDetener();
                Toast.makeText(context, "La serie de la ont ya existe2", Toast.LENGTH_SHORT).show();
            }else{
                validar1HiloEnCaja2();
            }
        }else{
            if (validarOntEditar){
                validarOntEditar=false;
                ontAnterior=ont;
                this.ont=ont;
                txtOnt.getEditText().setText(this.ont.getSerieOnt());
                txtNumeroOnt.getEditText().setText(this.ont.getNumeroOnt()+"");
                modeloOntDAO.buscarModeloOnt(this.ont.getId_modeloOnt()+"",context);
            }else{
                validar1HiloEnCaja2();
            }
        }
    }

    @Override
    public void setListaOnt(List<Ont> lista) {

    }

    @Override
    public void limpiarOnt() {
        if (opc.equals("crear")){
                serviciosDAO.crearServicio(new Servicios(serviport,usuario,direccion,referencia,fecha,longitud,latitud,idplanes,"",ont.getId(),"",cajaNivel2.getId_CajaNivel2(),"",clientes.getId_cliente(),"",numeroHiloCaja2,direccionIp,ip_numero,comandoPlanes,iterfazPonCard,agregarOnt,equipoBridge,quit,eliminarServicio,agregarServicioPuerto,agregarDescripcionPuerto,eliminarOnt,Procesos.user.getId(),"crear",generarComandoCopiar(),Procesos.obtenerFechaActualConHora(),"pendiente"),ont.getSerieOnt(),context);
        }
    }

    @Override
    public void setLatitudLongitud(String latitud, String longitud) {
        txtLatitud.getEditText().setText(latitud);
        txtLongitud.getEditText().setText(longitud);
    }

    @Override
    public void hiloCaja2Automatico(RangoHilosCaja2 rangoHilosCaja2) {
        if (rangoHilosCaja2!=null ){
            txtHiloEnCaja2.getEditText().setText(rangoHilosCaja2.getNumero_rangocaja2()+"");
            numeroHiloCaja2 =Procesos.obtenerTxtEnEntero(txtHiloEnCaja2);
            this.rangoHilosCaja2=rangoHilosCaja2;
            if (bandedarGuardar==true){
                validar7DireccionIpASidoUtilizado();
            }
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
            Procesos.cargandoDetener();
            Toast.makeText(context, "Numero de hilo incorrecto o no disponible", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void hiloCaja2Anterior(RangoHilosCaja2 rangoHilosCaja2) {
        rangoHilosCaja2Anterior= rangoHilosCaja2;
    }

    @Override
    public void numeroOntAutomatico(RangoOnt rangoOnt) {
        if (rangoHilosCaja2!=null && rangoOnt!=null){
            txtNumeroOnt.getEditText().setText(rangoOnt.getNumero_rangoont()+"");
            this.rangoOnt= rangoOnt;
            numeroOnt=Procesos.obtenerTxtEnEntero(txtNumeroOnt);
            if (bandedarGuardar==true){
                validar6HiloEnCaja2ASidoUtilizado();
            }
        }else{
            Toast.makeText(context, "No hay numero de ont disponible en esta vlan", Toast.LENGTH_LONG).show();
        }
        Procesos.cargandoDetener();
    }

    @Override
    public void validarNumeroOntManual(RangoOnt rangoOnt) {
        if (rangoOnt !=null){
            this.rangoOnt= rangoOnt;
            validar3DireccionIp();
        }else{
            Procesos.cargandoDetener();
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
        llenarComandoPlanes();
        Procesos.cargandoDetener();
        serviport=Procesos.obtenerTxtEnEntero(txtServiPort);
        if (bandedarGuardar==true){
            validar5NumeroOntASidoUtilizado();
        }
    }

    @Override
    public void setUsuarioRepetido(boolean estaRepetido) {
            Procesos.cargandoDetener();
            if (estaRepetido){
                //Math.floor(Math.random()*(N-M+1)+M);  // Valor entre M y N, ambos incluidos.
                //usuarioRepetido=Math.floor(Math.random()*(1-99+1)+99)+" ";
                Random rand = new Random();
                usuarioRepetido= rand.nextInt(99)+""; // Gives n such that 0 <= n < 20
                llenarUsuario();
            }else if (bandedarGuardar==true){
                validar4ServiPortASidoUtilizado();
            }
    }

    @Override
    public void setServicio(Servicios servicios) {

    }

    @Override
    public void setListaServicio(List<Servicios> lista) {

    }

    @Override
    public void direccionIpAutomatico(RangoDireccionIp rangoDireccionIp) {
        if (rangoDireccionIp!=null && vlan!=null){
            ip_numero=rangoDireccionIp.getip_rangodireccionesip();
                int[] ipDescompuestaIpVlanInicio=Procesos.descomponerDireccionIp(vlan.getIpInicio());
                txtDireccionIp.getEditText().setText( ipDescompuestaIpVlanInicio[0]+"."+ipDescompuestaIpVlanInicio[1]+"."+ipDescompuestaIpVlanInicio[2]+"."+ip_numero);
                this.rangoDireccionIp= rangoDireccionIp;
                direccionIp=Procesos.obtenerTxtEnString(txtDireccionIp);
                if (bandedarGuardar==true){
                    crearEditar();
                }
        }else{
            Toast.makeText(context, "No hay numero de ip disponible en esta vlan", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void validarDireccionIpManual(RangoDireccionIp rangoDireccionIp) {
        if (rangoDireccionIp !=null){
            this.rangoDireccionIp= rangoDireccionIp;
            validarUsuarioASidoUtilizado();
        }else{
            Procesos.cargandoDetener();
            Toast.makeText(context, "Numero de ip incorrecto o no disponible", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void direccionIpAnterior(RangoDireccionIp rangoDireccionIp) {
        if (rangoDireccionIp !=null){
            rangoDireccionIpAnterior= rangoDireccionIp;
        }else{
            Procesos.cargandoDetener();
            Toast.makeText(context, "Numero de ip anterior incorrecto o no disponible", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setCajaNivel2(CajaNivel2 CajaNivel2) {
        this.cajaNivel2=CajaNivel2;
        this.cajaNivel2Anterior=CajaNivel2;
        txtCajaNivel2.getEditText().setText(cajaNivel2.getNombre_CajaNivel2());
        rangoOntDAO.obtenerNumeroOntAnterior(cajaNivel2.getIdvlan(),servicios.getId_ont(),context);
        rangoDireccionIpDAO.obtenerDireccionIpAnterior(cajaNivel2.getIdvlan(),servicios.getId_servicio(),context);
        vlanDAO.buscarVlan(cajaNivel2.getIdvlan()+"",context);
    }

    @Override
    public void setListaCajaNivel2(List<CajaNivel2> lista) {

    }

    @Override
    public void limpiarCajaNivel2() {

    }
int auxClie=0;
    @Override
    public void setClientes(Clientes Clientes) {
        if (Clientes != null) {
            this.clientes=Clientes;
            if(auxClie==0){
                this.clientesAnterior=Clientes;
                //clientesSeleccionadoAnterior=Clientes;
                auxClie=1;
            }
            txtCliente.getEditText().setText(clientes.getNombre()+" "+clientes.getApellido());
            if (bandedarGuardar){
                if (opc.equals("crear")){
                    ontDAO.crearOnt(ont,context);//primero guarda el ont una vez guardado llama al metodo limpiaront que ejecuta el guardar servicio
                }else if(opc.equals("editar")){
                    serviciosDAO.editarServicio(new Servicios(serviport,usuario,direccion,referencia,fecha,longitud,latitud,idplanes,"",ont.getId(),"",cajaNivel2.getId_CajaNivel2(),"",clientes.getId_cliente(),"",numeroHiloCaja2,direccionIp,ip_numero,comandoPlanes,iterfazPonCard,agregarOnt,equipoBridge,quit,eliminarServicio,agregarServicioPuerto,agregarDescripcionPuerto,eliminarOnt,Procesos.user.getId(),"editar",generarComandoCopiar(),Procesos.obtenerFechaActualConHora(),"pendiente"),context,false);
                    serviciosDAO.editarServicio(new Servicios(serviport,usuario,direccion,referencia,fecha,longitud,latitud,idplanes,"",ont.getId(),"",cajaNivel2.getId_CajaNivel2(),"",clientes.getId_cliente(),"",numeroHiloCaja2,direccionIp,ip_numero,comandoPlanes,iterfazPonCard,agregarOnt,equipoBridge,quit,eliminarServicio,agregarServicioPuerto,agregarDescripcionPuerto,eliminarOnt,Procesos.user.getId(),"editar",generarComandoCopiar(),Procesos.obtenerFechaActualConHora(),"pendiente"),context,false);
                }
            }
        }
    }


    @Override
    public void setListaClientes(List<Clientes> lista) {

    }

    @Override
    public void limpiarClientes() {
        clientesDAO.buscarClientes(clientes.getCedula(),context);
    }

    @Override
    public void setClientesDialogoCrearClientes(Clientes cliente) {
        this.clientes=cliente;
        if (cliente!=null && !cliente.getNombre().equals("")){
            txtCliente.getEditText().setText(clientes.getNombre()+" "+clientes.getApellido());
            validarUsuarioEditar=false;
            llenarUsuario();
            llenarComandoPlanes();
            llenarAgregarOnt();
            llenarEliminarServicio();
        }

    }
}
