package com.brasmapi.masfiberhome.ui.crear;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.entidades.Clientes;
import com.google.android.material.textfield.TextInputLayout;

public class DialogCrearCliente
{
    static Dialog dialogo;
    Context context;
    TextInputLayout txtCedulaClientes,txtNombreClientes,txtApellidoClientes,txtCorreoClientes,txtTelefono1Clientes,txtTelefono2Clientes;
    static Clientes clientes;
    public static String opc=""; // editar/crear
    String cedula,nombre,apellido,correo,telefono1,telefono2;
    public static finalizoDialogCrearClientes interfaz;
    Button btnguardar;
    public DialogCrearCliente(Context context1, Clientes clientes2, finalizoDialogCrearClientes actividad) {
        interfaz = actividad;
        context = context1;
        clientes=clientes2;
        dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);//false
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_crear_cliente);
        btnguardar=dialogo.findViewById(R.id.btnGuardar_DialogCrearCliente);
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
            if (!CrearServicioFragment.isCrearCliente){
                txtCedulaClientes.getEditText().setEnabled(false);
            }
        }
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Confirmación");
                builder.setMessage( "Seguro desea "+opc+" ?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cedula = txtCedulaClientes.getEditText().getText().toString().trim();
                                nombre = txtNombreClientes.getEditText().getText().toString().trim();
                                apellido = txtApellidoClientes.getEditText().getText().toString().trim();
                                correo = txtCorreoClientes.getEditText().getText().toString().trim();
                                telefono1 = txtTelefono1Clientes.getEditText().getText().toString().trim();
                                telefono2 = txtTelefono2Clientes.getEditText().getText().toString().trim();
                                if (opc.equals("crear")) {
                                    CrearServicioFragment.isCrearCliente= true;
                                    clientes=new Clientes(0,cedula,nombre,apellido,correo,telefono1,telefono2,"activo");
                                } else {//editar
                                    CrearServicioFragment.isEditarCliente=true;
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
        dialogo.show();
    }

    public interface finalizoDialogCrearClientes {
        void setClientesDialogoCrearClientes(Clientes cliente);
    }
}
