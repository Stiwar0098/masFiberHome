package com.brasmapi.masfiberhome.ui.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.entidades.Servicios;

import java.util.List;

public class AdapterServicios extends RecyclerView.Adapter<AdapterServicios.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private List<Servicios> lista;
    private View.OnClickListener listener;
    private View.OnLongClickListener listenerLong;

    public AdapterServicios(List<Servicios> lista) {
        this.lista = lista;
    }
    public void setAdapterItemBuscarServicios(List<Servicios> lista) {
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_servicios, null, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtserviport.setText(lista.get(position).getId_servicio()+"");
        holder.txtUsuario.setText(lista.get(position).getUsuario());
        holder.txtCliente.setText(lista.get(position).getNombreCliente());
        holder.txtDireccion.setText(lista.get(position).getDireccion());
        holder.txtReferencia.setText(lista.get(position).getReferencia());
        holder.txtLatitud.setText(lista.get(position).getLatitud());
        holder.txtLongitud.setText(lista.get(position).getLongitud());
        holder.txtPlan.setText(lista.get(position).getNombrePlan()+ "megas");
        holder.txtSerieOnt.setText(lista.get(position).getSerieOnt());
        holder.txtCaja.setText(lista.get(position).getNombreCaja());
        holder.txtDireccionIp.setText(lista.get(position).getDireccionip());
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
        TextView txtserviport,txtUsuario,txtCliente,txtDireccion,txtReferencia,txtLatitud,txtLongitud,txtPlan,txtSerieOnt,txtCaja,txtDireccionIp;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtserviport= (TextView) itemView.findViewById(R.id.lblServiport_itemServicio);
            txtUsuario= (TextView) itemView.findViewById(R.id.lblUsuario_itemServicio);
            txtCliente= (TextView) itemView.findViewById(R.id.lblCliente_ItemServicio);
            txtDireccion= (TextView) itemView.findViewById(R.id.lblDireccion_ItemServicio);
            txtReferencia= (TextView) itemView.findViewById(R.id.lblReferencia_itemServicio);
            txtLatitud= (TextView) itemView.findViewById(R.id.lblLatitud_ItemServicio);
            txtLongitud= (TextView) itemView.findViewById(R.id.lblLongitud_itemServicio);
            txtSerieOnt= (TextView) itemView.findViewById(R.id.lblSerieOnt_itemServicio);
            txtPlan= (TextView) itemView.findViewById(R.id.lblPlan_itemServicio);
            txtCaja= (TextView) itemView.findViewById(R.id.lblCaja_itemServicio);
            txtDireccionIp= (TextView) itemView.findViewById(R.id.lblDireccionIp_itemServicio);
        }
    }

}