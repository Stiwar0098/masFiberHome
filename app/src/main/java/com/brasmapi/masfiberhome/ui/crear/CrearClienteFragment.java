package com.brasmapi.masfiberhome.ui.crear;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.brasmapi.masfiberhome.R;

public class CrearClienteFragment extends Fragment {

    View vista;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_crearcliente, container, false);

        return vista;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}