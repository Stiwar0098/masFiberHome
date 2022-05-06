package com.brasmapi.masfiberhome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.brasmapi.masfiberhome.ui.entidades.Usuario;
import com.brasmapi.masfiberhome.ui.CrearClienteFragment;
import com.brasmapi.masfiberhome.ui.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private TextView lblNombre_menu,lblRol_menu;
    Context context;
    DrawerLayout drawer;
    NavigationView navigationView;
    static Usuario op;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context=this;

        setSupportActionBar(binding.appBarMain.toolbar);
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_administrar, R.id.nav_crearCliente, R.id.nav_migrarCliente,R.id.nav_migrarip,R.id.nav_CerrarSesion_menu)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
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
        op=new Usuario(1,"brayan","brasmapi","hola","Técntico","activo");
        validadRolUsuario(op);
        View header=navigationView.getHeaderView(0);
        //View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        ((TextView) header.findViewById(R.id.lblNombre_menu)).setText(op.getNombre());
        ((TextView) header.findViewById(R.id.lblRol_menu)).setText(op.getRol());
    }

    private void logOut() {
        SharedPreferences sp= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.putString("active","false");
        editor.commit();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        drawer.openDrawer(GravityCompat.START);

     return true;
    }
    private int itemMenuSeleccionadoAnterior=0;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_administrar:
                cambiarFragment(new HomeFragment(),"Administrar",itemMenuSeleccionadoAnterior,0);
                return true;
            case R.id.nav_crearCliente:
                cambiarFragment(new CrearClienteFragment(), "Crear Cliente",itemMenuSeleccionadoAnterior,2);
                //MenuItem menuItem = navigationView.getMenu().getItem(item.getItemId());
                //onNavigationItemSelected(menuItem);
                //menuItem.setChecked(true);
                return true;
            case R.id.nav_CerrarSesion_menu:
                logOut();
                return true;
        }
        return false;
    }
    public  void cambiarFragment(Fragment fragment, String titulo, int anterior,int actual){
        if(anterior!=actual){
            MenuItem menuItem = navigationView.getMenu().getItem(anterior);
            menuItem.setChecked(false);
        }
        itemMenuSeleccionadoAnterior=actual;
        MenuItem item = navigationView.getMenu().getItem(actual);
        item.setChecked(true);
        // Crear fragmento de tu clase
        // Obtener el administrador de fragmentos a través de la actividad
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Definir una transacción
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Remplazar el contenido principal por el fragmento
        fragmentTransaction.replace(R.id.contenedor, fragment);
        fragmentTransaction.addToBackStack(null);
        // Cambiar
        fragmentTransaction.commit();
        setTitle(titulo);
        ocultarMenu();
    }

    public void validadRolUsuario(Usuario user){
        if(user.getRol().equals("Técnico")){
            cambiarFragment(new CrearClienteFragment(),"Crear cliente",itemMenuSeleccionadoAnterior,1);
            MenuItem menuItem = navigationView.getMenu().getItem(0);
            menuItem.setVisible(false);
            menuItem = navigationView.getMenu().getItem(1);
            menuItem.setVisible(false);
        }
    }
    public void ocultarMenu(){
        drawer.closeDrawers();
    }
    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}