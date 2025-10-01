package com.miempresa.mijuego.personajes;

import com.badlogic.gdx.graphics.Color;

public abstract class Personaje {
    private String nombre;
    private String descripcion;
    private String habilidad;
    private Color color; // 🔹 nuevo atributo

    public Personaje(String nombre, String descripcion, String habilidad, Color color) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.habilidad = habilidad;
        this.color = color;
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

    public Color getColor() {
        return color;
    }

    public String getInformacionCompleta() {
        return "Nombre: " + nombre + "\n" +
            "Descripción: " + descripcion + "\n" +
            "Habilidad: " + habilidad;
    }
}
