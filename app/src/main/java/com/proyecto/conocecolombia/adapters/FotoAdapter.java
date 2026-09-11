package com.proyecto.conocecolombia.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.conocecolombia.R;
import com.proyecto.conocecolombia.models.Foto;

import java.util.List;

public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.FotoViewHolder> {

    private List<Foto> listaFotos;
    private OnFotoClickListener listener;

    public interface OnFotoClickListener {
        void onFotoClick(Foto foto);
    }

    public FotoAdapter(
            List<Foto> listaFotos,
            OnFotoClickListener listener) {

        this.listaFotos = listaFotos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FotoViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_foto,
                        parent,
                        false
                );

        return new FotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull FotoViewHolder holder,
            int position) {

        Foto foto = listaFotos.get(position);

        // Imagen
        holder.imgFoto.setImageResource(
                foto.getImagen()
        );

        // Título
        holder.txtTituloFoto.setText(
                foto.getTitulo()
        );

        // Descripción del lugar
        holder.txtDescripcionItem.setText(
                foto.getDescripcion()
        );

        // Evento al tocar la tarjeta
        holder.itemView.setOnClickListener(v -> {

            if (listener != null) {
                listener.onFotoClick(foto);
            }

        });
    }

    @Override
    public int getItemCount() {
        return listaFotos.size();
    }

    static class FotoViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFoto;

        TextView txtTituloFoto;
        TextView txtDescripcionItem;

        public FotoViewHolder(
                @NonNull View itemView) {

            super(itemView);

            imgFoto =
                    itemView.findViewById(
                            R.id.imgFoto
                    );

            txtTituloFoto =
                    itemView.findViewById(
                            R.id.txtTituloFoto
                    );

            txtDescripcionItem =
                    itemView.findViewById(
                            R.id.txtDescripcionItem
                    );
        }
    }
}