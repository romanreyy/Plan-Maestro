package com.miempresa.mijuego.objetivos;

public abstract class Objetivo {
    private String nombre;
    private String descripcion;

    public Objetivo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
