package com.proyecto.conocecolombia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.proyecto.conocecolombia.R;

public class PerfilFragment extends Fragment {

    private ImageView imgPerfil;

    private TextView txtTituloPerfil;
    private TextView txtNombre;
    private TextView txtProfesion;
    private TextView txtEstudios;
    private TextView txtExperiencia;
    private TextView txtDescripcion;

    public PerfilFragment() {
        // Constructor vacío obligatorio
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_perfil,
                container,
                false
        );

        vincularVistas(view);

        return view;
    }

    private void vincularVistas(View view) {

        imgPerfil = view.findViewById(R.id.imgPerfil);

        txtTituloPerfil = view.findViewById(R.id.txtTituloPerfil);
        txtNombre = view.findViewById(R.id.txtNombre);
        txtProfesion = view.findViewById(R.id.txtProfesion);
        txtEstudios = view.findViewById(R.id.txtEstudios);
        txtExperiencia = view.findViewById(R.id.txtExperiencia);
        txtDescripcion = view.findViewById(R.id.txtDescripcion);
    }
}