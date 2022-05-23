package com.brasmapi.masfiberhome.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    View vista;
    Context context;
    CardView btnListarUsuario,btnListarProvincia,btnListarPais, btnListarCiudad,btnListarVlans,btnListarCajaNivel1,btnListarCajaNivel2;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_home, container, false);
        context=getActivity();
        Procesos.detenerObtenerLatitudLongitud();
        Procesos.cerrarTeclado(getActivity());
        btnListarUsuario =vista.findViewById(R.id.btnCrearusuario_administrador);
        btnListarProvincia =vista.findViewById(R.id.btnListarProvincia_administrador);
        btnListarPais = vista.findViewById(R.id.btnListarPais_administrador);
        btnListarCiudad = vista.findViewById(R.id.btnListarCiudad_administrador);
        btnListarVlans = vista.findViewById(R.id.btnListarVlans_administrador);
        btnListarCajaNivel1 = vista.findViewById(R.id.btnListarCajaNivel1_administrador);
        btnListarCajaNivel2 = vista.findViewById(R.id.btnListarCajaNivel2_Administrador);
        btnListarCiudad.setOnClickListener(this);
        btnListarUsuario.setOnClickListener(this);
        btnListarPais.setOnClickListener(this);
        btnListarProvincia.setOnClickListener(this);
        btnListarVlans.setOnClickListener(this);
        btnListarCajaNivel1.setOnClickListener(this);
        btnListarCajaNivel2.setOnClickListener(this);
        return vista;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        String titulo = "";
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        // Definir una transacción
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Remplazar el contenido principal por el fragmento
        switch (v.getId()){
            case R.id.btnCrearusuario_administrador:
                Navigation.findNavController(v).navigate(R.id.listaUsuariosFragment);
                //fragmentTransaction.replace(R.id.contenedor, new ListaUsuariosFragment());
                titulo="Listar usuarios";
                break;
            case R.id.btnListarProvincia_administrador:
                Navigation.findNavController(v).navigate(R.id.listaProvinciasFragment);
                //fragmentTransaction.replace(R.id.contenedor, new ListaProvinciasFragment());
                titulo="Listar provincias";
                break;
            case R.id.btnListarPais_administrador:
                Navigation.findNavController(v).navigate(R.id.listaPaisesFragment);
                //fragmentTransaction.replace(R.id.contenedor, new ListaPaisesFragment());
                titulo="Listar paises";
                break;
            case R.id.btnListarCiudad_administrador:
                Navigation.findNavController(v).navigate(R.id.listaCiudadesFragment);
                //fragmentTransaction.replace(R.id.contenedor, new ListaCiudadesFragment());
                titulo="Listar ciudades";
                break;
            case R.id.btnListarVlans_administrador:
                Navigation.findNavController(v).navigate(R.id.listaVlansFragment);
                //fragmentTransaction.replace(R.id.contenedor, new ListaCiudadesFragment());
                titulo="Listar vlans";
                break;
            case R.id.btnListarCajaNivel1_administrador:
                Navigation.findNavController(v).navigate(R.id.listaCajasNivel1Fragment);
                //fragmentTransaction.replace(R.id.contenedor, new ListaCiudadesFragment());
                titulo="Listar cajas nivel 1";
                break;
            case R.id.btnListarCajaNivel2_Administrador:
                Navigation.findNavController(v).navigate(R.id.listaCajasNivel2Fragment);
                //fragmentTransaction.replace(R.id.contenedor, new ListaCiudadesFragment());
                titulo="Listar cajas nivel 2";
                break;
        }
        fragmentTransaction.addToBackStack(null);
        // Cambiar
        fragmentTransaction.commit();
        ((MainActivity)getActivity()).setTitle(titulo);
    }
}