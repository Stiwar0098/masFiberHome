package com.brasmapi.masfiberhome.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.brasmapi.masfiberhome.CrearClientesFragment;
import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.UsuariosDAO;
import com.brasmapi.masfiberhome.entidades.Usuario;
import com.brasmapi.masfiberhome.ui.crear.CrearClienteFragment;
import com.brasmapi.masfiberhome.ui.crear.CrearServicioFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.brasmapi.masfiberhome.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UsuariosDAO.usuarioBaseDeDatos{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private TextView lblNombre_menu,lblRol_menu;
    Context context;
    DrawerLayout drawer;
    NavigationView navigationView;
    UsuariosDAO usuariosDAO=new UsuariosDAO(MainActivity.this);
    NavController navController;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=this;
        if(Procesos.user==null){
            SharedPreferences sp= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
            usuariosDAO.buscarUsuario(sp.getString("Usuario",""),context);
        }

        setSupportActionBar(binding.appBarMain.toolbar);
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.\

        obtenerPermisosDeUbicacion();

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_administrar,R.id.nav_pendiente, R.id.nav_crearServicio, R.id.nav_migrarCliente,R.id.nav_CerrarSesion_menu)
                .setDrawerLayout(drawer)
                .build();


        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.botones));
        }
        lblNombre_menu=findViewById(R.id.lblNombre_menu);
        lblRol_menu=findViewById(R.id.lblRol_menu);
        validadRolUsuario();
    }

    private void obtenerPermisosDeUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);
        }else{
            //Toast.makeText(context, "permisos obtenidos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        //Toast.makeText(context, "holaaaaaaaaaaaaa", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private void logOut() {
        SharedPreferences sp= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.putString("active","false");
        editor.commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
        //drawer.openDrawer(GravityCompat.START);

    // return true;
    }
    private int itemMenuSeleccionadoAnterior=0;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_administrar:
               //cambiarFragment(new HomeFragment(),"Administrar",itemMenuSeleccionadoAnterior,0);
                /*NavigationUI.onNavDestinationSelected(item,navController);
                ocultarMenu();
                return true;*/
            case R.id.nav_pendiente:
            case R.id.nav_migrarCliente:
            case R.id.nav_crearServicio:
              NavigationUI.onNavDestinationSelected(item,navController);
                ocultarMenu();
                //cambiarFragment(new CrearClienteFragment(), "Crear Cliente",itemMenuSeleccionadoAnterior,2);
                //MenuItem menuItem = navigationView.getMenu().getItem(item.getItemId());
                //onNavigationItemSelected(menuItem);
                //menuItem.setChecked(true);
                return true;
            case R.id.nav_CerrarSesion_menu:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void validadRolUsuario(){
        if(Procesos.user!=null){
            View header=navigationView.getHeaderView(0);
            //View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
            String rol="";
            if (Procesos.user.getRol()==1){
                rol="ADMINISTRADOR";
            }else{
                rol="TÉCNICO";
            }
            ((TextView) header.findViewById(R.id.lblNombre_menu)).setText(Procesos.user.getNombre());
            ((TextView) header.findViewById(R.id.lblRol_menu)).setText(rol);
            if(Procesos.user.getRol()==2){// si es tecnico
                MenuItem menuItem = navigationView.getMenu().getItem(0);
                menuItem.setVisible(false);
                menuItem = navigationView.getMenu().getItem(1);
                menuItem.setVisible(false);
                navController.navigate(R.id.nav_crearServicio);
                //cambiarFragment(new CrearServicioFragment(),"Crear cliente",itemMenuSeleccionadoAnterior,1);
                FragmentManager fragmentManager = getSupportFragmentManager();
                // Definir una transacción
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Remplazar el contenido principal por el fragmento
                //fragmentTransaction.replace(R.id.contenedor, fragment);
                fragmentTransaction.addToBackStack(null);
                // Cambiar
                fragmentTransaction.commit();
                ocultarMenu();
            }else{
                Procesos.cargandoDetener();
            }
        }
    }
    public void ocultarMenu(){
        drawer.closeDrawers();
    }
    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void usuarioSelecionado() {
        validadRolUsuario();
    }

    @Override
    public void setListaUsuario(List<Usuario> lista) {

    }
}