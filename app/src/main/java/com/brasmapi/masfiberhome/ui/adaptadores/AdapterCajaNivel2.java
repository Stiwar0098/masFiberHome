package com.brasmapi.masfiberhome.ui.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.entidades.CajaNivel2;

import java.util.List;

public class AdapterCajaNivel2 extends RecyclerView.Adapter<AdapterCajaNivel2.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private List<CajaNivel2> lista;
    private View.OnClickListener listener;
    private View.OnLongClickListener listenerLong;

    public AdapterCajaNivel2(List<CajaNivel2> lista) {
        this.lista = lista;
    }
    public void setAdapterItemBuscarCajaNivel2(List<CajaNivel2> lista) {
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_caja_nivel2, null, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtnombreCajaNivel2.setText(lista.get(position).getNombre_CajaNivel2());
        holder.txtDireccion.setText(lista.get(position).getDireccion_CajaNivel2());
        holder.txtReferencia.setText(lista.get(position).getReferencia_CajaNivel2());
        holder.txtNombreCajaNivel1.setText(lista.get(position).getNombreCajaNivel1());
        holder.txtAbreviatura.setText(lista.get(position).getAbreviatura());
        holder.txtCantidadHilos.setText(lista.get(position).getCantidadHilos());
        holder.txtHiloCaja1.setText(lista.get(position).getHiloCaja1());
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
        TextView txtnombreCajaNivel2, txtDireccion, txtReferencia, txtNombreCajaNivel1,txtAbreviatura,txtHiloCaja1,txtCantidadHilos;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtnombreCajaNivel2 = (TextView) itemView.findViewById(R.id.lblNombreCajaNivel2_itemCajaNivel2);
            txtDireccion = (TextView) itemView.findViewById(R.id.lblDireccion_ItemCajaNivel2);
            txtReferencia = (TextView) itemView.findViewById(R.id.lblReferencia_ItemCajaNivel2);
            txtAbreviatura = (TextView) itemView.findViewById(R.id.lblAbreviatura_itemCajaNivel2);
            txtNombreCajaNivel1 = (TextView) itemView.findViewById(R.id.lblNombreCajaNivel1_ItemCajaNivel2);
            txtHiloCaja1 = (TextView) itemView.findViewById(R.id.lblHiloCaja1_itemCajaNivel2);
            txtCantidadHilos = (TextView) itemView.findViewById(R.id.lblCantidadHilos_itemCajaNivel2);
        }
    }

}