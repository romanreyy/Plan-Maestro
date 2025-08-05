package com.miempresa.mijuego;

public class Pais {
    private String nombre;
    private Jugador propietario;

    public Pais(String nombre) {
        this.nombre = nombre;
        this.propietario = null;
    }

    public String getNombre() {
        return nombre;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return nombre + (propietario != null ? " (" + propietario.getNombre() + ")" : " (Sin dueño)");
    }
}
