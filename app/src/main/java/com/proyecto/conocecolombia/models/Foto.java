package com.proyecto.conocecolombia.models;

public class Foto {

    private int imagen;
    private String titulo;
    private String descripcion;

    public Foto(int imagen, String titulo, String descripcion) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}