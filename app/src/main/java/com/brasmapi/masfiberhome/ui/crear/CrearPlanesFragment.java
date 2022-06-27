package com.brasmapi.masfiberhome.ui.crear;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.PlanesDAO;
import com.brasmapi.masfiberhome.entidades.Planes;
import com.brasmapi.masfiberhome.ui.MainActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearPlanesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearPlanesFragment extends Fragment implements PlanesDAO.interfazPlanesDAO {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearPlanesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearPlanesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearPlanesFragment newInstance(String param1, String param2) {
        CrearPlanesFragment fragment = new CrearPlanesFragment();
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
    View vista;
    Context context;
    TextInputLayout txtNombrePlanes;
    PlanesDAO PlanesDAO;
    public static Planes planes;
    public static String opc=""; // editar/crear
    int nombre;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_crear_planes, container, false);
        context=getActivity();
        Button btnguardar=(Button)vista.findViewById(R.id.btnGuardar_CrearPlanes);
        txtNombrePlanes =vista.findViewById(R.id.txtNombrePlan_CrearPlan);
        PlanesDAO =new PlanesDAO(CrearPlanesFragment.this);
        ((MainActivity)getActivity()).setTitle("Crear Planes");
        if (opc.equals("editar")){
            btnguardar.setText("Editar");
            txtNombrePlanes.getEditText().setText(planes.getNombre()+"");
            ((MainActivity)getActivity()).setTitle("Editar Planes");
        }
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = Integer.parseInt(txtNombrePlanes.getEditText().getText().toString().trim());
                if (opc.equals("crear")) {
                    PlanesDAO.crearPlanes(new Planes(0,
                            nombre,
                            "activo"), context);
                } else {//editar
                    planes.setNombre(nombre);
                    PlanesDAO.editarPlanes(planes, context, false);
                }
            }
        });
        return vista;
    }

    @Override
    public void setPlanes(Planes Planes) {
        
    }

    @Override
    public void setListaPlanes(List<Planes> lista) {

    }

    @Override
    public void limpiarPlanes() {
        txtNombrePlanes.getEditText().setText("");
        Procesos.cerrarTeclado(getActivity());
        Procesos.cargandoDetener();
        if (opc.equals("editar")){
            getActivity().onBackPressed();// para retrocede sin que se guarde el activiti anterior  ejemplo a b c
            // con el codigo de abajo si lo aplico en c para ir a b quedaria asi
            //a b c b al momento de dar vuelta atras se ir nuevamente a c y luego a b
            //al aplicar el codigo de arriba en el mismo ejemplo si lo aplicamos en c quedaria asi
            //a b por ende si doy atras nuevamente se iria a a y ya no a c como el en anterior
            //Navigation.findNavController(getActivity().getCurrentFocus()).navigate(R.id.listaCajasNivel2Fragment);
        }
    }
}