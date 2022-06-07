package com.brasmapi.masfiberhome.ui.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.entidades.Clientes;

import java.util.List;

public class AdapterClientes extends RecyclerView.Adapter<AdapterClientes.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private List<Clientes> lista;
    private View.OnClickListener listener;
    private View.OnLongClickListener listenerLong;

    public AdapterClientes(List<Clientes> lista) {
        this.lista = lista;
    }
    public void setAdapterItemBuscarClientes(List<Clientes> lista) {
        this.lista = lista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_clientes, null, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtcedula.setText(lista.get(position).getCedula());
        holder.txtNombre.setText(lista.get(position).getNombre());
        holder.txtApellido.setText(lista.get(position).getApellido());
        holder.txtCorreo.setText(lista.get(position).getCorreo());
        holder.txtTelefono1.setText(lista.get(position).getTelefono1());
        holder.txtTelefono2.setText(lista.get(position).getTelefono2());

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
        TextView txtcedula, txtNombre,txtApellido,txtCorreo,txtTelefono1,txtTelefono2;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtcedula = (TextView) itemView.findViewById(R.id.lblCedula_itemClientes);
            txtNombre = (TextView) itemView.findViewById(R.id.lblNombre_itemClientes);
            txtApellido = (TextView) itemView.findViewById(R.id.lblApellidos_itemClientes);
            txtCorreo = (TextView) itemView.findViewById(R.id.lblCorreo_itemClientes);
            txtTelefono1 = (TextView) itemView.findViewById(R.id.lblTelefono1_itemClientes);
            txtTelefono2 = (TextView) itemView.findViewById(R.id.lblTelefono2_itemClientes);

        }
    }

}