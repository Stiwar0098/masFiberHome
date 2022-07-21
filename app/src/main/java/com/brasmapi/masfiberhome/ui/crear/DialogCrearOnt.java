package com.brasmapi.masfiberhome.ui.crear;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.FragmentActivity;

import com.brasmapi.masfiberhome.CustomScannerActivity;
import com.brasmapi.masfiberhome.Procesos;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.dao.ModeloOntDAO;
import com.brasmapi.masfiberhome.entidades.ModeloOnt;
import com.brasmapi.masfiberhome.entidades.Ont;
import com.brasmapi.masfiberhome.ui.buscar.DialogBuscarModeloOnt;
import com.google.android.material.textfield.TextInputLayout;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

public class DialogCrearOnt implements DialogBuscarModeloOnt.finalizoDialogBuscarModeloOnt, ModeloOntDAO.interfazModeloOntDAO
{
    static Context context;
    static Dialog dialogo;
    static TextInputLayout txtserie,txtmodelo;
    Button btnGuardar;
    Spinner spinnerResponsable;
    ModeloOnt modeloOnt=null;
    ModeloOntDAO modeloOntDAO;
    Ont ontAux;
    String opc;
    String serie,modelo,responsable;
    finalizoDialogCrearOnt interfaz;
    ActivityResultLauncher<ScanOptions> barcodeLauncher;
    public DialogCrearOnt(Context context1,Ont ont, finalizoDialogCrearOnt actividad, ActivityResultLauncher<ScanOptions> barcodeLauncher) {
        interfaz = actividad;
        context = context1;
        ontAux =ont;
        this.barcodeLauncher=barcodeLauncher;
        dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);//false
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_crear_ont);
        modeloOntDAO=new ModeloOntDAO(DialogCrearOnt.this);
        txtserie=(TextInputLayout) dialogo.findViewById(R.id.txtSerieOnt_DialogCrearOnt);
        txtmodelo=(TextInputLayout)dialogo.findViewById(R.id.txtIdModelo_DialogCrearOnt);
        spinnerResponsable=(Spinner) dialogo.findViewById(R.id.spinnerResponsable_DialogCrearOnt);
        btnGuardar=(Button) dialogo.findViewById(R.id.btnGuardar_DialogCrearOnt);
        String [] opciones={"Selecionar el responsable","CLIENTE","EMPRESA"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,opciones);
        spinnerResponsable.setAdapter(adapter);
        if (ontAux!=null){// va a editar
            opc="editar";
            txtserie.getEditText().setText(ontAux.getSerieOnt());
            modeloOntDAO.buscarModeloOnt(ontAux.getId_modeloOnt()+"",context1);
            txtmodelo.getEditText().setText(ontAux.getNombreModelo());
            int aux=0;
            if (ontAux.getResponsable().equals("CLIENTE")){
                aux=1;
            }else if (ontAux.getResponsable().equals("EMPRESA")){
                aux=2;
            }
            spinnerResponsable.setSelection(aux);
        }else{
            opc="crear";
            spinnerResponsable.setSelection(2);
            modeloOntDAO.buscarModeloOnt("DOBLE BANDA",context);
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Confirmación");
                builder.setMessage( "Seguro desea "+opc+" ?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                serie = txtserie.getEditText().getText().toString().trim();
                                modelo = txtmodelo.getEditText().getText().toString().trim();
                                responsable = spinnerResponsable.getSelectedItem().toString();
                                if (modeloOnt != null) {
                                    if (ontAux != null) { //va a editar
                                        interfaz.setOntDialogoCrearOnt(new Ont(ontAux.getId(), serie, modeloOnt.getId_modeloOnt(), modelo, responsable, ontAux.getNumeroOnt(), ontAux.getEstado()));
                                    } else {
                                        interfaz.setOntDialogoCrearOnt(new Ont(0, serie, modeloOnt.getId_modeloOnt(), modelo, responsable, -1, "activo"));
                                    }
                                }else  {
                                    Toast.makeText(context1, "Sin modelo", Toast.LENGTH_SHORT).show();
                                }
                                dialogo.dismiss();
                            }
                        })
                        .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        txtmodelo.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogBuscarModeloOnt(context,DialogCrearOnt.this);
            }
        });
        txtserie.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpQRCode2();
            }
        });

        dialogo.show();

    }

    public void setUpQRCode2(){
        ScanOptions options = new ScanOptions().setOrientationLocked(false).setCaptureActivity(CustomScannerActivity.class);
        options.setBeepEnabled(false);
        barcodeLauncher.launch(options);
    }
    @Override
    public void ModeloOntSelecionado(ModeloOnt ModeloOnt) {
        this.modeloOnt=ModeloOnt;
        txtmodelo.getEditText().setText(modeloOnt.getNombre_modeloOnt());
    }

    @Override
    public void setModeloOnt(ModeloOnt ModeloOnt) {
        try {
            txtmodelo.getEditText().setText(ModeloOnt.getNombre_modeloOnt());
            Procesos.cargandoDetener();
            this.modeloOnt=ModeloOnt;
        }catch (Exception e){

        }
    }

    @Override
    public void setListaModeloOnt(List<ModeloOnt> lista) {

    }

    @Override
    public void limpiarModeloOnt() {

    }


    public interface finalizoDialogCrearOnt {
        void setOntDialogoCrearOnt(Ont ont2);
    }
}
