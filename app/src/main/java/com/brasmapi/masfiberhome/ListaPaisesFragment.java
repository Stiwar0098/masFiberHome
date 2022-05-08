package com.brasmapi.masfiberhome;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.brasmapi.masfiberhome.ui.adaptadores.AdapterPais;
import com.brasmapi.masfiberhome.ui.dao.PaisesDAO;
import com.brasmapi.masfiberhome.ui.entidades.Pais;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaPaisesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaPaisesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaPaisesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaPaisesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaPaisesFragment newInstance(String param1, String param2) {
        ListaPaisesFragment fragment = new ListaPaisesFragment();
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
    static Context context;
    Button btnCrearPais;
    public static PaisesDAO paisesDAO;
    public static AdapterPais adaptadorPaises;
    public static RecyclerView recyclerViewPaises;
    public static List<Pais> listaPaises;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_lista_paises, container, false);
        paisesDAO = new PaisesDAO();
        context=getActivity();
        mostrarDatos("rehrh");
        btnCrearPais =(Button)vista.findViewById(R.id.btnCrearPais_ListaPais);
        btnCrearPais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                // Definir una transacción
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Remplazar el contenido principal por el fragmento
                fragmentTransaction.replace(R.id.contenedor, new CrearPaisFragment());
                fragmentTransaction.addToBackStack(null);
                // Cambiar
                fragmentTransaction.commit();
            }
        });
        return vista;
    }
    public void mostrarDatos(String filtrar){
        Procesos.cargandoIniciar(vista.getContext());

        // crear lista de carview dentro del recycleview
        recyclerViewPaises = (RecyclerView)vista.findViewById(R.id.recyclerView_ListaPaises);
        recyclerViewPaises.setLayoutManager(new LinearLayoutManager(context));
        listaPaises=paisesDAO.filtarPaises(filtrar, vista.getContext());
    }
    public static void cargar(){
        if(listaPaises==null){
            Toast.makeText(context, "No hay paises", Toast.LENGTH_SHORT).show();
        }else{
            adaptadorPaises = new AdapterPais(listaPaises);
            recyclerViewPaises.setAdapter(adaptadorPaises);
            adaptadorPaises.notifyDataSetChanged();
        }
        Procesos.cargandoDetener();
    }
    private void filtrar(String filtrar){
        List<Pais> aux2=new ArrayList<>();
        for (Pais aux:listaPaises) {
            if(aux.getNombre().toLowerCase().contains(filtrar.toLowerCase())){
                aux2.add(aux);
            }
        }
        adaptadorPaises.setAdapterItemBuscarPais(aux2);
        adaptadorPaises.notifyDataSetChanged();
    }

}