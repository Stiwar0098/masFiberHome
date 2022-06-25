package com.brasmapi.masfiberhome.ui.crear;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.FragmentActivity;

import com.brasmapi.masfiberhome.CustomScannerActivity;
import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.entidades.ModeloOnt;
import com.brasmapi.masfiberhome.entidades.Ont;
import com.brasmapi.masfiberhome.ui.buscar.DialogBuscarModeloOnt;
import com.google.android.material.textfield.TextInputLayout;
import com.journeyapps.barcodescanner.ScanOptions;

public class DialogCrearOnt implements DialogBuscarModeloOnt.finalizoDialogBuscarModeloOnt
{
    static Context context;
    static Dialog dialogo;
    static TextInputLayout txtserie,txtmodelo;
    Button btnGuardar;
    Spinner spinnerResponsable;
    ModeloOnt modeloOnt=null;
    String serie,modelo,responsable;
    public static finalizoDialogCrearOnt interfaz;
    FragmentActivity fragmentActivity;
    ActivityResultLauncher<ScanOptions> barcodeLauncher;
    public DialogCrearOnt(Context context1, finalizoDialogCrearOnt actividad, ActivityResultLauncher<ScanOptions> barcodeLauncher) {
        interfaz = actividad;
        context = context1;
        this.barcodeLauncher=barcodeLauncher;
        dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);//false
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_crear_ont);
        txtserie=(TextInputLayout) dialogo.findViewById(R.id.txtSerieOnt_DialogCrearOnt);
        txtmodelo=(TextInputLayout)dialogo.findViewById(R.id.txtIdModelo_DialogCrearOnt);
        spinnerResponsable=(Spinner) dialogo.findViewById(R.id.spinnerResponsable_DialogCrearOnt);
        btnGuardar=(Button) dialogo.findViewById(R.id.btnGuardar_DialogCrearOnt);
        String [] opciones={"Selecionar el responsable","CLIENTE","EMPRESA"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,opciones);
        spinnerResponsable.setAdapter(adapter);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serie = txtserie.getEditText().getText().toString().trim();
                modelo = txtmodelo.getEditText().getText().toString().trim();
                responsable = spinnerResponsable.getSelectedItem().toString();
               if(modeloOnt!=null){
                   interfaz.setOntDialogoCrearOnt(new Ont(0, serie, modeloOnt.getId_modeloOnt(), modelo, responsable, -1, "activo"));
                   dialogo.dismiss();
               }
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


    public interface finalizoDialogCrearOnt {
        void setOntDialogoCrearOnt(Ont Ont);
    }
}
