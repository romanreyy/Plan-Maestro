package com.miempresa.mijuego;

public abstract class Personaje {
    protected String nombre;
    protected String descripcion;
    protected String habilidad;

    public Personaje(String nombre, String descripcion, String habilidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.habilidad = habilidad;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getHabilidad() {
        return habilidad;
    }

    public String getInformacionCompleta() {
        return "Nombre: " + nombre + "\n" +
            "Descripción: " + descripcion + "\n" +
            "Habilidad: " + habilidad;
    }
}
