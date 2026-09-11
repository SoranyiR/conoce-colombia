package com.proyecto.conocecolombia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.proyecto.conocecolombia.R;

public class MenuFragment extends Fragment {

    private TextView txtTituloMenu;

    private Button btnPerfil;
    private Button btnFotos;
    private Button btnVideo;
    private Button btnWeb;
    private Button btnBotones;

    private OnMenuOptionSelected listener;

    public interface OnMenuOptionSelected {
        void onMenuSelected(String opcion);
    }

    public MenuFragment() {
        // Constructor vacío obligatorio
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnMenuOptionSelected) {
            listener = (OnMenuOptionSelected) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_menu,
                container,
                false
        );

        vincularVistas(view);
        configurarEventos();

        return view;
    }

    private void vincularVistas(View view) {

        txtTituloMenu = view.findViewById(R.id.txtTituloMenu);

        btnPerfil = view.findViewById(R.id.btnPerfil);
        btnFotos = view.findViewById(R.id.btnFotos);
        btnVideo = view.findViewById(R.id.btnVideo);
        btnWeb = view.findViewById(R.id.btnWeb);
        btnBotones = view.findViewById(R.id.btnBotones);
    }

    private void configurarEventos() {

        btnPerfil.setOnClickListener(v ->
                seleccionarOpcion("PERFIL"));

        btnFotos.setOnClickListener(v ->
                seleccionarOpcion("FOTOS"));

        btnVideo.setOnClickListener(v ->
                seleccionarOpcion("VIDEO"));

        btnWeb.setOnClickListener(v ->
                seleccionarOpcion("WEB"));

        btnBotones.setOnClickListener(v ->
                seleccionarOpcion("BOTONES"));
    }

    private void seleccionarOpcion(String opcion) {

        if (listener != null) {
            listener.onMenuSelected(opcion);
        }
    }
}