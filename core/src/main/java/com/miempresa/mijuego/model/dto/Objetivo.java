package com.miempresa.mijuego.model.dto;

import com.miempresa.mijuego.bussiness.GameState;

public abstract class Objetivo {

    private final String nombre;
    private final String descripcion;

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

    public abstract boolean estaCumplido(Jugador jugador, GameState state);

}
