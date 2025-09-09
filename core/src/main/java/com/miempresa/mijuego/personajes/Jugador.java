package com.miempresa.mijuego.personajes;

import com.miempresa.mijuego.objetivos.*;
import com.miempresa.mijuego.paises.Pais;

import java.util.*;

public class Jugador {
    private String nombre;
    private Personaje personajeSeleccionado;
    private Objetivo objetivoAsignado; // NUEVO
    private List<Pais> paisesControlados; // NUEVO

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.personajeSeleccionado = null;
        this.objetivoAsignado = null;
        this.paisesControlados = new ArrayList<>();
    }

    public Jugador(String nombre, Personaje personaje) {
        this.nombre = nombre;
        this.personajeSeleccionado = personaje;
        this.objetivoAsignado = null;
        this.paisesControlados = new ArrayList<>();
    }

    public void asignarPersonaje(Personaje personaje) {
        this.personajeSeleccionado = personaje;
        System.out.println(nombre + " ha seleccionado a " + personaje.getNombre());
    }

    public void asignarObjetivo(Objetivo objetivo) {
        this.objetivoAsignado = objetivo;
        System.out.println(nombre + " ha recibido el objetivo: " + objetivo.getNombre());
    }

    public void asignarPaises(List<Pais> paises) {
        this.paisesControlados = paises;
        for (Pais pais : paises) {
            pais.setPropietario(this);
        }
        System.out.println(nombre + " ha recibido " + paises.size() + " países");
    }

    public String getNombre() {
        return nombre;
    }

    public Personaje getPersonajeSeleccionado() {
        return personajeSeleccionado;
    }

    public Objetivo getObjetivoAsignado() {
        return objetivoAsignado;
    }

    public List<Pais> getPaisesControlados() {
        return paisesControlados;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean tienePersonaje() {
        return personajeSeleccionado != null;
    }

    public boolean tieneObjetivo() {
        return objetivoAsignado != null;
    }

    public String getInformacionJugador() {
        StringBuilder info = new StringBuilder();
        info.append("Jugador: ").append(nombre).append("\n");

        if (personajeSeleccionado != null) {
            info.append("Personaje: ").append(personajeSeleccionado.getNombre()).append("\n");
        } else {
            info.append("Sin personaje asignado\n");
        }

        if (objetivoAsignado != null) {
            info.append("Objetivo: ").append(objetivoAsignado.getNombre()).append("\n");
        } else {
            info.append("Sin objetivo asignado\n");
        }


        return info.toString();
    }

    @Override
    public String toString() {
        return nombre +
            (personajeSeleccionado != null ? " (" + personajeSeleccionado.getNombre() + ")" : " (Sin personaje)") +
            (objetivoAsignado != null ? " - " + objetivoAsignado.getNombre() : " (Sin objetivo)");
    }

    public static void asignarObjetivoAleatorio(Jugador jugador) {
        Objetivo[] objetivosDisponibles = {
            new PlanDominacion()
        };
        Random random = new Random();
        Objetivo objetivoAleatorio = objetivosDisponibles[random.nextInt(objetivosDisponibles.length)];
        jugador.asignarObjetivo(objetivoAleatorio);
    }
}
