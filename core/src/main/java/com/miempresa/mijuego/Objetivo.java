package com.miempresa.mijuego;

public abstract class Objetivo {
    protected String nombre;
    protected String descripcion;

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
