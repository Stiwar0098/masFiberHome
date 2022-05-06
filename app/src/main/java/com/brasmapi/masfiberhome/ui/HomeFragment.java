package com.brasmapi.masfiberhome.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.brasmapi.masfiberhome.ListaProvinciasFragment;
import com.brasmapi.masfiberhome.ListaUsuariosFragment;
import com.brasmapi.masfiberhome.MainActivity;
import com.brasmapi.masfiberhome.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    View vista;
    Context context;
    CardView btnListarUsuario,btnListarProvincia;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_home, container, false);
        context=getActivity();
        btnListarUsuario =vista.findViewById(R.id.btnCrearusuario_administrador);
        btnListarProvincia =vista.findViewById(R.id.btnListarProvincia_administrador);
        btnListarUsuario.setOnClickListener(this);
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
                fragmentTransaction.replace(R.id.contenedor, new ListaUsuariosFragment());
                titulo="Listar usuarios";
                break;
            case R.id.btnListarProvincia_administrador:
                fragmentTransaction.replace(R.id.contenedor, new ListaProvinciasFragment());
                titulo="Listar provincias";
                break;
        }
        fragmentTransaction.addToBackStack(null);
        // Cambiar
        fragmentTransaction.commit();
        ((MainActivity)getActivity()).setTitle(titulo);
    }
}