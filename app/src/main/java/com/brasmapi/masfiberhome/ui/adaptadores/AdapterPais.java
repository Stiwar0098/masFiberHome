package com.brasmapi.masfiberhome.ui.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.ui.entidades.Pais;

import java.util.List;

public class AdapterPais extends RecyclerView.Adapter<AdapterPais.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    static List<Pais> listaPaises;
    private View.OnClickListener listener;
    private View.OnLongClickListener listenerLong;

    public AdapterPais(List<Pais> listaUsuarios) {
        this.listaPaises = listaUsuarios;
    }
    public void setAdapterItemBuscarPais(List<Pais> listaUsuarios) {
        this.listaPaises = listaUsuarios;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_buscar_pais, null, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtnombreUsuario.setText(listaPaises.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return listaPaises.size();
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
        TextView txtnombreUsuario;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtnombreUsuario = (TextView) itemView.findViewById(R.id.lblPais_itemBuscarPais);
        }
    }

}