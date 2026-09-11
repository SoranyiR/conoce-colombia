package com.proyecto.conocecolombia;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.proyecto.conocecolombia.fragments.BotonesFragment;
import com.proyecto.conocecolombia.fragments.FotosFragment;
import com.proyecto.conocecolombia.fragments.MenuFragment;
import com.proyecto.conocecolombia.fragments.PerfilFragment;
import com.proyecto.conocecolombia.fragments.VideoFragment;
import com.proyecto.conocecolombia.fragments.WebFragment;

public class MainActivity extends AppCompatActivity
        implements MenuFragment.OnMenuOptionSelected {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            mostrarFragment(new PerfilFragment());
        }
    }

    @Override
    public void onMenuSelected(String opcion) {

        switch (opcion) {

            case "PERFIL":
                mostrarFragment(new PerfilFragment());
                break;

            case "FOTOS":
                mostrarFragment(new FotosFragment());
                break;

            case "VIDEO":
                mostrarFragment(new VideoFragment());
                break;

            case "WEB":
                mostrarFragment(new WebFragment());
                break;

            case "BOTONES":
                mostrarFragment(new BotonesFragment());
                break;
        }
    }

    private void mostrarFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorContenido, fragment)
                .commit();
    }
}