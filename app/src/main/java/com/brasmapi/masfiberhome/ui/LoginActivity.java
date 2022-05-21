package com.brasmapi.masfiberhome.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.UsuariosDAO;
import com.brasmapi.masfiberhome.entidades.Usuario;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements UsuariosDAO.usuarioBaseDeDatos{

    Context context;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarCampos();
        context=this;
        SharedPreferences sp= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        checkLogIn(sp);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //e.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                rememberUser(sp);
            }
        });
    }

    Button btnIngresar;
    TextInputLayout txtUsuario, txtContrasena;
    CheckBox checkRecordar;
    public  void inicializarCampos(){
        btnIngresar=findViewById(R.id.btnIngresar_login);
        txtContrasena=findViewById(R.id.txtContrasena_login);
        txtUsuario=findViewById(R.id.txtUsuario_login);
        checkRecordar=findViewById(R.id.checkRecordar_login);
    }
    SharedPreferences sp;
    //Permite guardar el Usuario y la contrasena para luego recordarlo ademas
    private void rememberUser(SharedPreferences ap){
        sp=ap;
        // Recuperamos el contenido de los textFields
        String usuario = txtUsuario.getEditText().getText().toString().trim();
        String contrasena = txtContrasena.getEditText().getText().toString().trim();
        // Verificamos si los campos no son vacíos
        if (!usuario.isEmpty() && !contrasena.isEmpty()){
            if(Procesos.user!=null && usuario.equals(Procesos.user.getUsuario())){
                ingresarMenuPrincipal();
            }else{
                UsuariosDAO usuariosDAO=new UsuariosDAO(LoginActivity.this);
                usuariosDAO.buscarUsuario(usuario,context);
            }

        }else{
            // En caso los datos no estén completos mostramos un Toast
            Toast.makeText(this,"Ingrese las credenciales", Toast.LENGTH_SHORT).show();
        }
    }
    public void ingresarMenuPrincipal(){
        if(Procesos.user==null){
            Toast.makeText(context, "Usuario no existe", Toast.LENGTH_SHORT).show();
            txtUsuario.getEditText().setText("");
        }else{
            if(Procesos.user.getContrasena().equals(txtContrasena.getEditText().getText().toString().trim())){
                SharedPreferences.Editor editor= sp.edit();
                if (checkRecordar.isChecked()){
                    editor.putString("Usuario",Procesos.user.getUsuario());
                    editor.putString("contrasena",Procesos.user.getContrasena());
                    editor.putString("active","true");
                    editor.putString("recordar","true");
                    editor.commit();
                }else{
                    editor.putString("active","true");
                    editor.putString("recordar","false");
                    editor.commit();
                }
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }else{
                Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                txtContrasena.getEditText().setText("");
            }
        }
    }

    //si el Usuario no cierra session no es necesario que vuelta vuelva a ingresar en el login
    private void checkLogIn(SharedPreferences sp){
        if (sp.getString("active","").equals("true")){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{
            if (sp.getString("recordar","").equals("true")){
                txtUsuario.getEditText().setText(sp.getString("Usuario",""));
                txtContrasena.getEditText().setText(sp.getString("contrasena",""));
            }
        }
    }

    @Override
    public void usuarioSelecionado() {
        ingresarMenuPrincipal();
    }

    @Override
    public void setListaUsuario(List<Usuario> lista) {

    }
}