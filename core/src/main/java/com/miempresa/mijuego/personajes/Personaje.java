package com.miempresa.mijuego.personajes;

public abstract class Personaje {
    private String nombre;
    private String descripcion;
    private String habilidad;

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
