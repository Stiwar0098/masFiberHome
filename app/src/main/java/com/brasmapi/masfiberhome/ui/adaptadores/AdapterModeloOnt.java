package com.brasmapi.masfiberhome.ui.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.entidades.ModeloOnt;

import java.util.List;

public class AdapterModeloOnt extends RecyclerView.Adapter<AdapterModeloOnt.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private List<ModeloOnt> lista;
    private View.OnClickListener listener;
    private View.OnLongClickListener listenerLong;

    public AdapterModeloOnt(List<ModeloOnt> lista) {
        this.lista = lista;
    }
    public void setAdapterItemBuscarModeloOnt(List<ModeloOnt> lista) {
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_modelos_ont, null, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtnombreModeloOnt.setText(lista.get(position).getNombre_modeloOnt());
        holder.txtTipoModeloOnt.setText(lista.get(position).getTipo_modeloOnt());
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
        TextView txtnombreModeloOnt,txtTipoModeloOnt;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtnombreModeloOnt = (TextView) itemView.findViewById(R.id.lblNombre_itemModeloOnt);
            txtTipoModeloOnt = (TextView) itemView.findViewById(R.id.lblTipo_itemModeloOnt);
        }
    }

}