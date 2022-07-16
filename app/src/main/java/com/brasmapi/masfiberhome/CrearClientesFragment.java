package com.brasmapi.masfiberhome;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.brasmapi.masfiberhome.dao.ClientesDAO;
import com.brasmapi.masfiberhome.entidades.Clientes;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearClientesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearClientesFragment extends Fragment implements ClientesDAO.interfazClientesDAO {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearClientesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearClientesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearClientesFragment newInstance(String param1, String param2) {
        CrearClientesFragment fragment = new CrearClientesFragment();
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
    TextInputLayout txtCedulaClientes,txtNombreClientes,txtApellidoClientes,txtCorreoClientes,txtTelefono1Clientes,txtTelefono2Clientes;
    ClientesDAO clientesDAO;
    public static Clientes clientes;
    public static String opc=""; // editar/crear
    String cedula,nombre,apellido,correo,telefono1,telefono2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_crear_clientes, container, false);
        context=getActivity();
        Button btnguardar=(Button)vista.findViewById(R.id.btnGuardar_CrearCliente);
        txtCedulaClientes =vista.findViewById(R.id.txtCedula_crearCliente);
        txtNombreClientes =vista.findViewById(R.id.txtNombre_crearCliente);
        txtApellidoClientes =vista.findViewById(R.id.txtApellido_crearCliente);
        txtCorreoClientes =vista.findViewById(R.id.txtCorreo_CrearCliente);
        txtTelefono1Clientes =vista.findViewById(R.id.txtTelefono1_crearCliente);
        txtTelefono2Clientes =vista.findViewById(R.id.txtTelefono2_crearCliente);
        clientesDAO =new ClientesDAO(CrearClientesFragment.this);
        ((MainActivity)getActivity()).setTitle("Crear Clientes");
        if (opc.equals("editar")){
            btnguardar.setText("Editar");
            txtCedulaClientes.getEditText().setText(clientes.getCedula());
            txtNombreClientes.getEditText().setText(clientes.getNombre());
            txtApellidoClientes.getEditText().setText(clientes.getApellido());
            txtCorreoClientes.getEditText().setText(clientes.getCorreo());
            txtTelefono1Clientes.getEditText().setText(clientes.getTelefono1());
            txtTelefono2Clientes.getEditText().setText(clientes.getTelefono2());
            ((MainActivity)getActivity()).setTitle("Editar Clientes");
        }
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cedula = txtCedulaClientes.getEditText().getText().toString().trim();
                nombre = txtNombreClientes.getEditText().getText().toString().trim();
                apellido = txtApellidoClientes.getEditText().getText().toString().trim();
                correo = txtCorreoClientes.getEditText().getText().toString().trim();
                telefono1 = txtTelefono1Clientes.getEditText().getText().toString().trim();
                telefono2 = txtTelefono2Clientes.getEditText().getText().toString().trim();
                if (opc.equals("crear")) {
                    clientesDAO.crearClientes(new Clientes(0,cedula,
                            nombre,apellido,correo,telefono1,telefono2,
                            "activo"), context);
                } else {//editar
                    clientes.setCedula(cedula);
                    clientes.setNombre(nombre);
                    clientes.setApellido(apellido);
                    clientes.setCorreo(correo);
                    clientes.setTelefono1(telefono1);
                    clientes.setTelefono2(telefono2);
                    clientesDAO.editarClientes(clientes, context, false);
                }
            }
        });
        return vista;
    }

    @Override
    public void setClientes(Clientes Clientes) {

    }


    @Override
    public void setListaClientes(List<Clientes> lista) {

    }

    @Override
    public void limpiarClientes() {
        txtCedulaClientes.getEditText().setText("");
        txtNombreClientes.getEditText().setText("");
        txtApellidoClientes.getEditText().setText("");
        txtCorreoClientes.getEditText().setText("");
        txtTelefono1Clientes.getEditText().setText("");
        txtTelefono2Clientes.getEditText().setText("");
        Procesos.cerrarTeclado(getActivity());
        Procesos.cargandoDetener();
        if (opc.equals("editar")){
            getActivity().onBackPressed();// para retrocede sin que se guarde el activiti anterior  ejemplo a b c
            // con el codigo de abajo si lo aplico en c para ir a b quedaria asi
            //a b c b al momento de dar vuelta atras se ir nuevamente a c y luego a b
            //al aplicar el codigo de arriba en el mismo ejemplo si lo aplicamos en c quedaria asi
            //a b por ende si doy atras nuevamente se iria a a y ya no a c como el en anterior
            //Navigation.findNavController(getActivity().getCurrentFocus()).navigate(R.id.listaCajasNivel2Fragment);
        }
    }
}