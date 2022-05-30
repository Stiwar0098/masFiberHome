package com.brasmapi.masfiberhome.ui.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.entidades.CajaNivel1;

import java.util.List;

public class AdapterCajaNivel1 extends RecyclerView.Adapter<AdapterCajaNivel1.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private List<CajaNivel1> lista;
    private View.OnClickListener listener;
    private View.OnLongClickListener listenerLong;

    public AdapterCajaNivel1(List<CajaNivel1> lista) {
        this.lista = lista;
    }
    public void setAdapterItemBuscarCajaNivel1(List<CajaNivel1> lista) {
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_caja_nivel1, null, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtnombreCajaNivel1.setText(lista.get(position).getNombre_cajaNivel1());
        holder.txtDireccion.setText(lista.get(position).getDireccion_cajaNivel1());
        holder.txtReferencia.setText(lista.get(position).getReferencia_cajaNivel1());
        holder.txtNombreVlan.setText(lista.get(position).getNombreVlan());
        holder.txtNombreCiudad.setText(lista.get(position).getNombreCiudad());
        holder.txtAbreviatura.setText(lista.get(position).getAbreviatura_cajaNivel1());
        holder.txtCantidadHilos.setText(lista.get(position).getNumeroHilos_cajaNivel1()+"");
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
    public void setOnLongClickListener(View.OnLongClickListener listener) { this.listenerLong = listener; }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (listenerLong != null) {
            listenerLong.onLongClick(v);
        }
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtnombreCajaNivel1, txtDireccion, txtReferencia, txtNombreVlan, txtNombreCiudad,txtAbreviatura,txtCantidadHilos;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtnombreCajaNivel1 = (TextView) itemView.findViewById(R.id.lblNombreCajaNivel1_itemCajaNivel1);
            txtDireccion = (TextView) itemView.findViewById(R.id.lblDireccion_ItemCajaNivel1);
            txtReferencia = (TextView) itemView.findViewById(R.id.lblReferencia_ItemCajaNivel);
            txtNombreVlan = (TextView) itemView.findViewById(R.id.lblNombreVlan_ItemCajaNivel1);
            txtNombreCiudad = (TextView) itemView.findViewById(R.id.lblNombreCiudad_ItemCajaNivel1);
            txtAbreviatura = (TextView) itemView.findViewById(R.id.lblAbreviatura_itemCajaNivel1);
            txtCantidadHilos = (TextView) itemView.findViewById(R.id.lblCantidadHilos_ItemCajaNivel1);
        }
    }

}