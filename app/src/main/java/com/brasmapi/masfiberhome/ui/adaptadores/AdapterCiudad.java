package com.brasmapi.masfiberhome.ui.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.entidades.Ciudad;

import java.util.List;

public class AdapterCiudad extends RecyclerView.Adapter<AdapterCiudad.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private List<Ciudad> lista;
    private View.OnClickListener listener;
    private View.OnLongClickListener listenerLong;

    public AdapterCiudad(List<Ciudad> lista) {
        this.lista = lista;
    }
    public void setAdapterItemBuscarCiudad(List<Ciudad> lista) {
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_ciudad, null, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtnombreCiudad.setText(lista.get(position).getNombre());
        holder.txtnombreProvincia.setText(lista.get(position).getNombreProvincia());
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
        TextView txtnombreCiudad,txtnombreProvincia;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtnombreProvincia = (TextView) itemView.findViewById(R.id.lblProvincia_itemCiudad);
            txtnombreCiudad = (TextView) itemView.findViewById(R.id.lblCiudad_itemCiudad);
        }
    }

}