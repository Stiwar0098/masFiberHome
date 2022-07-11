package com.brasmapi.masfiberhome.ui.crear;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.FragmentActivity;

import com.brasmapi.masfiberhome.CrearClientesFragment;
import com.brasmapi.masfiberhome.CustomScannerActivity;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.ClientesDAO;
import com.brasmapi.masfiberhome.dao.ModeloOntDAO;
import com.brasmapi.masfiberhome.entidades.Clientes;
import com.brasmapi.masfiberhome.entidades.ModeloOnt;
import com.brasmapi.masfiberhome.entidades.Ont;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.brasmapi.masfiberhome.ui.buscar.DialogBuscarModeloOnt;
import com.google.android.material.textfield.TextInputLayout;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

public class DialogCrearCliente
{
    static Dialog dialogo;
    Context context;
    TextInputLayout txtCedulaClientes,txtNombreClientes,txtApellidoClientes,txtCorreoClientes,txtTelefono1Clientes,txtTelefono2Clientes;
    static Clientes clientes;
    public static String opc=""; // editar/crear
    String cedula,nombre,apellido,correo,telefono1,telefono2;
    public static finalizoDialogCrearClientes interfaz;
    Button btnguardar,btnCancelar;
    public DialogCrearCliente(Context context1, Clientes clientes2, finalizoDialogCrearClientes actividad) {
        interfaz = actividad;
        context = context1;
        clientes=clientes2;
        dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);//false
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_crear_cliente);
        btnguardar=dialogo.findViewById(R.id.btnGuardar_DialogCrearCliente);
        btnCancelar=dialogo.findViewById(R.id.btnCancelar_DialogCrearCliente);
        txtCedulaClientes =dialogo.findViewById(R.id.txtCedula_DialogcrearCliente);
        txtNombreClientes =dialogo.findViewById(R.id.txtNombre_DialogcrearCliente);
        txtApellidoClientes =dialogo.findViewById(R.id.txtApellido_DialogcrearCliente);
        txtCorreoClientes =dialogo.findViewById(R.id.txtCorreo_DialogCrearCliente);
        txtTelefono1Clientes =dialogo.findViewById(R.id.txtTelefono1_DialogcrearCliente);
        txtTelefono2Clientes =dialogo.findViewById(R.id.txtTelefono2_DialogcrearCliente);
        if (opc.equals("editar")){
            btnguardar.setText("Pre Editar");
            txtCedulaClientes.getEditText().setText(clientes.getCedula());
            txtNombreClientes.getEditText().setText(clientes.getNombre());
            txtApellidoClientes.getEditText().setText(clientes.getApellido());
            txtCorreoClientes.getEditText().setText(clientes.getCorreo());
            txtTelefono1Clientes.getEditText().setText(clientes.getTelefono1());
            txtTelefono2Clientes.getEditText().setText(clientes.getTelefono2());
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
                    clientes=new Clientes(0,cedula,nombre,apellido,correo,telefono1,telefono2,"activo");
                } else {//editar
                    clientes.setCedula(cedula);
                    clientes.setNombre(nombre);
                    clientes.setApellido(apellido);
                    clientes.setCorreo(correo);
                    clientes.setTelefono1(telefono1);
                    clientes.setTelefono2(telefono2);
                }
                interfaz.setClientesDialogoCrearClientes(clientes);
                dialogo.dismiss();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (opc.equals("crear")) {
                    CrearServicioFragment.isCrearCliente=false;
                }else{
                    CrearServicioFragment.isEditarCliente=false;
                }
                dialogo.dismiss();
            }
        });
        dialogo.show();
    }

    public interface finalizoDialogCrearClientes {
        void setClientesDialogoCrearClientes(Clientes cliente);
    }
}
