package com.brasmapi.masfiberhome.ui.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brasmapi.masfiberhome.R;
import com.brasmapi.masfiberhome.ui.entidades.Usuario;

import java.util.List;

public class AdapterUsuario extends RecyclerView.Adapter<AdapterUsuario.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    static List<Usuario> listaUsuarios;
    private View.OnClickListener listener;
    private View.OnLongClickListener listenerLong;

    public AdapterUsuario(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    public void setAdapterItemBuscarUsuario(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_usuario, null, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtnombreUsuario.setText(listaUsuarios.get(position).getNombre());
        holder.txtUsuario.setText(listaUsuarios.get(position).getUsuario());
        holder.txtContra.setText(listaUsuarios.get(position).getContrasena());
        if(listaUsuarios.get(position).getRol()==1){
            holder.txtrol.setText("ADMINISTRADOR");
        }else{
            holder.txtrol.setText("TECNICO");
        }
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
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
        TextView txtnombreUsuario,txtUsuario,txtContra,txtrol;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            txtnombreUsuario = (TextView) itemView.findViewById(R.id.lblNombre_itemUsuario);
            txtUsuario = (TextView) itemView.findViewById(R.id.lblUsuario_itemUsuario);
            txtContra = (TextView) itemView.findViewById(R.id.lblContra_itemUsuario);
            txtrol = (TextView) itemView.findViewById(R.id.lblRol_itemUsuario);
        }
    }

}