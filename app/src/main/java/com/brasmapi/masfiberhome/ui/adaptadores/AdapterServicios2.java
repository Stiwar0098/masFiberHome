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

public class AdapterServicios2 extends RecyclerView.Adapter<AdapterServicios2.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private List<Servicios> lista;
    private View.OnClickListener listener;
    private View.OnLongClickListener listenerLong;

    public AdapterServicios2(List<Servicios> lista) {
        this.lista = lista;
    }
    public void setAdapterItemBuscarServicios(List<Servicios> lista) {
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_servicios2, null, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtserviport2.setText(lista.get(position).getId_servicio()+"");
        holder.txtopcion2.setText(lista.get(position).getOpcion_cliente());
        holder.txtUsuario2.setText(lista.get(position).getUsuario());
        holder.txtCliente2.setText(lista.get(position).getNombreCliente());
        holder.txtDireccion2.setText(lista.get(position).getDireccion());
        holder.txtReferencia2.setText(lista.get(position).getReferencia());
        holder.txtLatitud2.setText(lista.get(position).getLatitud());
        holder.txtLongitud2.setText(lista.get(position).getLongitud());
        holder.txtPlan2.setText(lista.get(position).getNombrePlan()+ "megas");
        holder.txtSerieOnt2.setText(lista.get(position).getSerieOnt());
        holder.txtCaja2.setText(lista.get(position).getNombreCaja());
        holder.txtDireccionIp2.setText(lista.get(position).getDireccionip());
        holder.txtDate.setText(lista.get(position).getDate2());
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
        TextView txtserviport2,txtopcion2, txtUsuario2, txtCliente2, txtDireccion2, txtReferencia2, txtLatitud2, txtLongitud2, txtPlan2, txtSerieOnt2, txtCaja2, txtDireccionIp2,txtDate;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtserviport2 = (TextView) itemView.findViewById(R.id.lblServiport_itemServicio2);
            txtopcion2 = (TextView) itemView.findViewById(R.id.lblOpcion_itemServicio2);
            txtUsuario2 = (TextView) itemView.findViewById(R.id.lblUsuario_itemServicio2);
            txtCliente2 = (TextView) itemView.findViewById(R.id.lblCliente_ItemServicio2);
            txtDireccion2 = (TextView) itemView.findViewById(R.id.lblDireccion_ItemServicio2);
            txtReferencia2 = (TextView) itemView.findViewById(R.id.lblReferencia_itemServicio2);
            txtLatitud2 = (TextView) itemView.findViewById(R.id.lblLatitud_ItemServicio2);
            txtLongitud2 = (TextView) itemView.findViewById(R.id.lblLongitud_itemServicio2);
            txtSerieOnt2 = (TextView) itemView.findViewById(R.id.lblSerieOnt_itemServicio2);
            txtPlan2 = (TextView) itemView.findViewById(R.id.lblPlan_itemServicio2);
            txtCaja2 = (TextView) itemView.findViewById(R.id.lblCaja_itemServicio2);
            txtDireccionIp2 = (TextView) itemView.findViewById(R.id.lblDireccionIp_itemServicio2);
            txtDate = (TextView) itemView.findViewById(R.id.lbldate_itemServicio2);
        }
    }

}