package com.miempresa.mijuego;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public abstract class Pais {
    private String nombre;
    private ArrayList<Pais> limitrofes;
    private Jugador propietario;

    public Pais(String nombre, ArrayList<String> limitrofes) {
        this.nombre = nombre;
        this.limitrofes = new ArrayList<>();
        this.propietario = null;
    }


    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return nombre + (propietario != null ? " (" + propietario.getNombre() + ")" : " (Sin dueño)");
    }
}
