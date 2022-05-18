package com.brasmapi.masfiberhome.ui.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.ui.entidades.Vlan;

import java.util.List;

public class AdapterVlan extends RecyclerView.Adapter<AdapterVlan.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private List<Vlan> lista;
    private View.OnClickListener listener;
    private View.OnLongClickListener listenerLong;

    public AdapterVlan(List<Vlan> lista) {
        this.lista = lista;
    }
    public void setAdapterItemBuscarVlan(List<Vlan> lista) {
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_vlan, null, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtnombreVlan.setText(lista.get(position).getNombreVlan());
        holder.txtNumeroTarjetaPuertoOlt.setText(lista.get(position).getNumeroOlt()+"/"+lista.get(position).getTarjetaOlt()+"/"+lista.get(position).getPuertoOlt());
        holder.txtIpInicio.setText(lista.get(position).getIpInicio());
        holder.txtIpFin.setText(lista.get(position).getIpFin());
        holder.txtGateway.setText(lista.get(position).getGateway());
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
        TextView txtnombreVlan, txtNumeroTarjetaPuertoOlt,txtIpInicio,txtIpFin,txtGateway;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtnombreVlan = (TextView) itemView.findViewById(R.id.lblNombreVlan_itemVlan);
            txtNumeroTarjetaPuertoOlt = (TextView) itemView.findViewById(R.id.lblNumerosOlt_ItemVlan);
            txtIpInicio = (TextView) itemView.findViewById(R.id.lblIpInicio_ItemVlan);
            txtIpFin = (TextView) itemView.findViewById(R.id.lblIpFin_ItemVlan);
            txtGateway = (TextView) itemView.findViewById(R.id.lblIpGateway_ItemVlan);
        }
    }

}