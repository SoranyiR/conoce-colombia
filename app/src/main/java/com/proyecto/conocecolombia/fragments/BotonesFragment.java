package com.proyecto.conocecolombia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.proyecto.conocecolombia.R;

public class BotonesFragment extends Fragment {

    private TextView txtDestinoActual;
    private TextView txtVisitas;

    private Button btnCambiarDestino;
    private Button btnMostrarMensaje;
    private Button btnSumarVisita;
    private Button btnReiniciarContador;

    private final String[] destinos = {
            "Cartagena",
            "Eje Cafetero",
            "Caño Cristales",
            "Santa Marta",
            "Medellín"
    };

    private int indiceDestino = 0;
    private int contadorVisitas = 0;

    public BotonesFragment() {
        // Constructor vacío obligatorio
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_botones, container, false);

        vincularVistas(view);
        configurarEventos();
        actualizarDestino();
        actualizarContador();

        return view;
    }

    private void vincularVistas(View view) {
        txtDestinoActual = view.findViewById(R.id.txtDestinoActual);
        txtVisitas = view.findViewById(R.id.txtVisitas);

        btnCambiarDestino = view.findViewById(R.id.btnCambiarDestino);
        btnMostrarMensaje = view.findViewById(R.id.btnMostrarMensaje);
        btnSumarVisita = view.findViewById(R.id.btnSumarVisita);
        btnReiniciarContador = view.findViewById(R.id.btnReiniciarContador);
    }

    private void configurarEventos() {

        btnCambiarDestino.setOnClickListener(v -> {
            indiceDestino++;
            if (indiceDestino >= destinos.length) {
                indiceDestino = 0;
            }
            actualizarDestino();
        });

        btnMostrarMensaje.setOnClickListener(v -> {
            Toast.makeText(
                    requireContext(),
                    "Explorando " + destinos[indiceDestino] + " 🇨🇴",
                    Toast.LENGTH_SHORT
            ).show();
        });

        btnSumarVisita.setOnClickListener(v -> {
            contadorVisitas++;
            actualizarContador();
        });

        btnReiniciarContador.setOnClickListener(v -> {
            contadorVisitas = 0;
            actualizarContador();

            Toast.makeText(
                    requireContext(),
                    "Contador reiniciado",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }

    private void actualizarDestino() {
        txtDestinoActual.setText(destinos[indiceDestino]);
    }

    private void actualizarContador() {
        txtVisitas.setText("Visitas: " + contadorVisitas);
    }
}