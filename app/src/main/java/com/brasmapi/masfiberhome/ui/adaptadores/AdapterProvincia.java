package com.brasmapi.masfiberhome.ui.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.ui.entidades.Provincia;

import java.util.List;

public class AdapterProvincia extends RecyclerView.Adapter<AdapterProvincia.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    static List<Provincia> listaProvincias;
    private View.OnClickListener listener;
    private View.OnLongClickListener listenerLong;

    public AdapterProvincia(List<Provincia> listaProvincias) {
        this.listaProvincias = listaProvincias;
    }
    public void setAdapterItemBuscarProvincia(List<Provincia> listaProvincias) {
        this.listaProvincias = listaProvincias;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_provincia, null, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtnombreProvincia.setText(listaProvincias.get(position).getNombre());
        holder.txtPais.setText(listaProvincias.get(position).getNombre_pais());
    }

    @Override
    public int getItemCount() {
        return listaProvincias.size();
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
        TextView txtnombreProvincia,txtPais;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtnombreProvincia = (TextView) itemView.findViewById(R.id.lblProvincia_itemProvincia);
            txtPais = (TextView) itemView.findViewById(R.id.lblPais_itemProvincia);
        }
    }

}