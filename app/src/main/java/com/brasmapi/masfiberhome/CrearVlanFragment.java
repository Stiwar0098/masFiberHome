package com.brasmapi.masfiberhome;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.brasmapi.masfiberhome.ui.dao.VlanDAO;
import com.brasmapi.masfiberhome.ui.entidades.RangoDireccionesIp;
import com.brasmapi.masfiberhome.ui.entidades.Vlan;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearVlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearVlanFragment extends Fragment implements VlanDAO.interfazVlanDAO {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearVlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearVlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearVlanFragment newInstance(String param1, String param2) {
        CrearVlanFragment fragment = new CrearVlanFragment();
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
    TextInputLayout txtNombreVlan, txtNumeroOlt,txtTarjetaOlt,txtPuertoOlt,txtIpInicio,txtIpFin,txtMascara,txtGateway;
    VlanDAO vlanDAO;
    public static Vlan vlan;
    public static String opc=""; // editar/crear

    String nombre,ipInicio,ipfin,mascara,gateway;
    int numeroOlt,tarjetaOlt,puertoOlt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_crear_vlan, container, false);
        context=getActivity();
        Button btnguardar=(Button)vista.findViewById(R.id.btnGuardar_CrearVlan);
        txtNombreVlan =vista.findViewById(R.id.txtNombreVlan_CrearVlan);
        txtNumeroOlt =vista.findViewById(R.id.txtNumeroOlt_CrearVlan);
        txtTarjetaOlt =vista.findViewById(R.id.txtTarjetaOlt_CrearVlan);
        txtPuertoOlt =vista.findViewById(R.id.txtPuertoOlt_CrearVlan);
        txtIpInicio =vista.findViewById(R.id.txtIpInicio_CrearVlan);
        txtIpFin =vista.findViewById(R.id.txtIpFin_CrearVlan);
        txtMascara =vista.findViewById(R.id.txtMascara_CrearVlan);
        txtGateway =vista.findViewById(R.id.txtGateway_CrearVlan);
        vlanDAO =new VlanDAO(CrearVlanFragment.this);
        ((MainActivity)getActivity()).setTitle("Crear Vlan");
        if (opc.equals("editar")){
            btnguardar.setText("Editar");
            txtNombreVlan.getEditText().setText(vlan.getNombreVlan());
            txtNumeroOlt.getEditText().setText(vlan.getNumeroOlt()+"");
            txtTarjetaOlt.getEditText().setText(vlan.getTarjetaOlt()+"");
            txtPuertoOlt.getEditText().setText(vlan.getPuertoOlt()+"");
            txtIpInicio.getEditText().setText(vlan.getIpInicio());
            txtIpFin.getEditText().setText(vlan.getIpFin());
            txtMascara.getEditText().setText(vlan.getMascara());
            txtGateway.getEditText().setText(vlan.getGateway());
            ((MainActivity)getActivity()).setTitle("Editar Vlan");
        }
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre=txtNombreVlan.getEditText().getText().toString().trim();
                numeroOlt=Integer.parseInt(txtNumeroOlt.getEditText().getText().toString().trim());
                tarjetaOlt=Integer.parseInt(txtTarjetaOlt.getEditText().getText().toString().trim());
                puertoOlt=Integer.parseInt(txtPuertoOlt.getEditText().getText().toString().trim());
                ipInicio=txtIpInicio.getEditText().getText().toString().trim();
                ipfin=txtIpFin.getEditText().getText().toString().trim();
                mascara=txtMascara.getEditText().getText().toString().trim();
                gateway=txtGateway.getEditText().getText().toString().trim();
                if(!Procesos.validarDireccionIp(ipInicio)){
                    Toast.makeText(context, "Ip inicio incorrecta", Toast.LENGTH_SHORT).show();
                    txtIpInicio.setError("Error");
                }else if(!Procesos.validarDireccionIp(ipfin)){
                    Toast.makeText(context, "Ip fin incorrecta", Toast.LENGTH_SHORT).show();
                    txtIpFin.setError("Error");
                }else if(!Procesos.validarDireccionIp(mascara)){
                    Toast.makeText(context, "Mascara incorrecta", Toast.LENGTH_SHORT).show();
                    txtMascara.setError("Error");
                }else if(!Procesos.validarDireccionIp(gateway)){
                    Toast.makeText(context, "Gateway incorrecto", Toast.LENGTH_SHORT).show();
                    txtGateway.setError("Error");
                }else{
                    if (opc.equals("crear")){
                        int[] inicio,fin;
                        int totalIps,ini;
                        inicio=Procesos.extraerNumerosDeIp(ipInicio);
                        fin=Procesos.extraerNumerosDeIp(ipfin);
                        ini=inicio[3];
                        totalIps=fin[3]-ini+1;
                        Toast.makeText(context, totalIps+"", Toast.LENGTH_SHORT).show();
                        vlanDAO.crearVlan(new Vlan(0,
                                nombre,
                                numeroOlt,
                                tarjetaOlt,
                                puertoOlt,
                                ipInicio,
                                ipfin,
                                mascara,
                                gateway,
                                "activo"),context,ini,totalIps);
                    }else{//editar
                        vlan.setNombreVlan(nombre);
                        vlan.setNumeroOlt(numeroOlt);
                        vlan.setTarjetaOlt(tarjetaOlt);
                        vlan.setPuertoOlt(puertoOlt);
                        vlan.setIpInicio(ipInicio);
                        vlan.setIpFin(ipfin);
                        vlan.setMascara(mascara);
                        vlan.setGateway(gateway);
                        vlanDAO.editarVlan(vlan,context,false);
                    }
                }
            }
        });
        return vista;
    }
    @Override
    public void setVlan(Vlan Vlan) {

    }

    @Override
    public void setListaVlan(List<Vlan> lista) {

    }

    @Override
    public void limpiar() {
        txtNombreVlan.getEditText().setText("");
        txtNumeroOlt.getEditText().setText("0");
        txtTarjetaOlt.getEditText().setText("");
        txtPuertoOlt.getEditText().setText("");
        txtIpInicio.getEditText().setText("10.170.");
        txtIpFin.getEditText().setText("10.170.");
        txtMascara.getEditText().setText("255.255.255.128");
        txtGateway.getEditText().setText("10.170.");
        txtNombreVlan.getEditText().requestFocusFromTouch();
        Procesos.cerrarTeclado(getActivity());
        txtNombreVlan.setErrorEnabled(false);
        txtNumeroOlt.setErrorEnabled(false);
        txtTarjetaOlt.setErrorEnabled(false);
        txtPuertoOlt.setErrorEnabled(false);
        txtIpInicio.setErrorEnabled(false);
        txtIpFin.setErrorEnabled(false);
        txtMascara.setErrorEnabled(false);
        txtGateway.setErrorEnabled(false);
    }
}