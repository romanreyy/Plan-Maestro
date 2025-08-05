package com.miempresa.mijuego;

public class Jugador {
    private String nombre;
    private Personaje personajeSeleccionado;

    // Constructor básico
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.personajeSeleccionado = null;
    }

    // Constructor con personaje
    public Jugador(String nombre, Personaje personaje) {
        this.nombre = nombre;
        this.personajeSeleccionado = personaje;
    }


    public void asignarPersonaje(Personaje personaje) {
        this.personajeSeleccionado = personaje;
        System.out.println(nombre + " ha seleccionado a " + personaje.getNombre());
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public Personaje getPersonajeSeleccionado() {
        return personajeSeleccionado;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public boolean tienePersonaje() {
        return personajeSeleccionado != null;
    }

    public String getInformacionJugador() {
        StringBuilder info = new StringBuilder();
        info.append("Jugador: ").append(nombre).append("\n");

        if (personajeSeleccionado != null) {
            info.append("Personaje: ").append(personajeSeleccionado.getNombre());
        } else {
            info.append("Sin personaje asignado");
        }

        return info.toString();
    }

    @Override
    public String toString() {
        return nombre + (personajeSeleccionado != null ?
            " (" + personajeSeleccionado.getNombre() + ")" : " (Sin personaje)");
    }
}
