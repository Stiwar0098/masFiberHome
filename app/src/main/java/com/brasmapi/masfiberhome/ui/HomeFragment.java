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

import com.brasmapi.masfiberhome.ListaCiudadesFragment;
import com.brasmapi.masfiberhome.ListaProvinciasFragment;
import com.brasmapi.masfiberhome.ListaUsuariosFragment;
import com.brasmapi.masfiberhome.MainActivity;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.ListaPaisesFragment;

public class HomeFragment extends Fragment implements View.OnClickListener {

    View vista;
    Context context;
    CardView btnListarUsuario,btnListarProvincia,btnListarPais, btnListarCiudad;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_home, container, false);
        context=getActivity();
        btnListarUsuario =vista.findViewById(R.id.btnCrearusuario_administrador);
        btnListarProvincia =vista.findViewById(R.id.btnListarProvincia_administrador);
        btnListarPais = vista.findViewById(R.id.btnListarPais_administrador);
        btnListarCiudad = vista.findViewById(R.id.btnListarCiudad_administrador);
        btnListarCiudad.setOnClickListener(this);
        btnListarUsuario.setOnClickListener(this);
        btnListarPais.setOnClickListener(this);
        btnListarProvincia.setOnClickListener(this);
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
        }
        fragmentTransaction.addToBackStack(null);
        // Cambiar
        fragmentTransaction.commit();
        ((MainActivity)getActivity()).setTitle(titulo);
    }
}