package com.brasmapi.masfiberhome.ui.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.entidades.Clientes;
import com.brasmapi.masfiberhome.entidades.Ont;

import java.util.List;

public class AdapterOnt extends RecyclerView.Adapter<AdapterOnt.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private List<Ont> lista;
    private View.OnClickListener listener;
    private View.OnLongClickListener listenerLong;

    public AdapterOnt(List<Ont> lista) {
        this.lista = lista;
    }
    public void setAdapterItemBuscarOnt(List<Ont> lista) {
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_ont, null, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtSerie.setText(lista.get(position).getSerieOnt());
        holder.txtNombreModelo.setText(lista.get(position).getNombreModelo());
        holder.txtResponsable.setText(lista.get(position).getResponsable());
        holder.txtNumeroOnt.setText(lista.get(position).getNumeroOnt()+"");
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
        TextView txtSerie, txtNombreModelo,txtResponsable,txtNumeroOnt;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtSerie = (TextView) itemView.findViewById(R.id.lblSerie_itemOnt);
            txtNombreModelo = (TextView) itemView.findViewById(R.id.lblIdModeloOnt_itemOnt);
            txtResponsable = (TextView) itemView.findViewById(R.id.lblResponsable_itemOnt);
            txtNumeroOnt = (TextView) itemView.findViewById(R.id.lblNumero_itemOnt);
        }
    }

}