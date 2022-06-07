package com.brasmapi.masfiberhome.ui.crear;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearServicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearServicioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearServicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearServicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearServicioFragment newInstance(String param1, String param2) {
        CrearServicioFragment fragment = new CrearServicioFragment();
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
    Calendar calendar = Calendar.getInstance();
    Context context;
    DatePickerDialog datePickerDialog;
    View vista;
    TextInputLayout txtFechaInstalacion;
    int ano,mes,dia;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         vista=inflater.inflate(R.layout.fragment_crear_servicio, container, false);
        context=getActivity();
        ((MainActivity)getActivity()).setTitle("Crear servicio");
        Procesos.cargandoDetener();
        txtFechaInstalacion= (TextInputLayout) vista.findViewById(R.id.txtImputFechaInicio_CrearServicio);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        ano = calendar.get(Calendar.YEAR);
        txtFechaInstalacion.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtFechaInstalacion.getEditText().setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, ano, mes, dia);
                datePickerDialog.show();
            }
        });
        return vista;
    }
}