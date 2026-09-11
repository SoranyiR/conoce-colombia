package com.proyecto.conocecolombia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.conocecolombia.R;
import com.proyecto.conocecolombia.adapters.FotoAdapter;
import com.proyecto.conocecolombia.models.Foto;

import java.util.ArrayList;
import java.util.List;

public class FotosFragment extends Fragment {

    private RecyclerView recyclerFotos;
    private TextView txtTituloFotos;

    private List<Foto> listaFotos;
    private FotoAdapter fotoAdapter;

    public FotosFragment() {
        // Constructor vacío obligatorio
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_fotos,
                container,
                false
        );

        vincularVistas(view);
        cargarFotos();
        configurarRecyclerView();

        return view;
    }

    /**
     * Vincula las variables Java con los elementos
     * definidos en fragment_fotos.xml.
     */
    private void vincularVistas(View view) {

        txtTituloFotos =
                view.findViewById(R.id.txtTituloFotos);

        recyclerFotos =
                view.findViewById(R.id.recyclerFotos);
    }

    /**
     * Carga los destinos turísticos que serán
     * mostrados en el RecyclerView.
     */
    private void cargarFotos() {

        listaFotos = new ArrayList<>();

        listaFotos.add(
                new Foto(
                        R.drawable.cartagena,
                        "Cartagena",
                        "Cartagena de Indias es una ciudad histórica ubicada en la costa Caribe de Colombia, reconocida por su ciudad amurallada, arquitectura colonial y riqueza cultural."
                )
        );

        listaFotos.add(
                new Foto(
                        R.drawable.eje_cafetero,
                        "Eje Cafetero",
                        "El Eje Cafetero es una región de Colombia reconocida por sus paisajes montañosos, cultivos de café, pueblos tradicionales y riqueza natural."
                )
        );

        listaFotos.add(
                new Foto(
                        R.drawable.cano_cristales,
                        "Caño Cristales",
                        "Caño Cristales es un río ubicado en la Sierra de la Macarena y es reconocido por sus diferentes tonalidades, producto de las plantas acuáticas presentes en su recorrido."
                )
        );
    }

    /**
     * Configura el RecyclerView y asigna
     * el adaptador de fotografías.
     */
    private void configurarRecyclerView() {

        recyclerFotos.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        /*
         * Ya no necesitamos mostrar la descripción
         * en un TextView externo.
         *
         * Cada tarjeta muestra su propia descripción
         * mediante FotoAdapter.
         */
        fotoAdapter = new FotoAdapter(
                listaFotos,
                null
        );

        recyclerFotos.setAdapter(fotoAdapter);
    }
}