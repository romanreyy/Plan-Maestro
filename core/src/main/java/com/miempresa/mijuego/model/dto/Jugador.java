package com.miempresa.mijuego.model.dto;

import com.miempresa.mijuego.config.ObjetivoRegistry;
import com.miempresa.mijuego.config.PaisRegistry;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Jugador {
    private String nombre;
    private Personaje personajeSeleccionado;
    private Objetivo objetivoAsignado;
    private List<Pais> paisesControlados;

    /** Ronda en la que la habilidad vuelve a estar disponible (incluida). */
    private Integer siguienteRondaHabilidad = null;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.personajeSeleccionado = null;
        this.objetivoAsignado = null;
        this.paisesControlados = new ArrayList<>();
    }

    public void asignarPersonaje(Personaje personaje) {
        this.personajeSeleccionado = personaje;
        System.out.println(nombre + " ha seleccionado a " + personaje.getNombre());
    }

    /** Overload que setea el cooldown inmediatamente según la ronda actual. */
    public void asignarPersonaje(Personaje personaje, int rondaActual) {
        this.personajeSeleccionado = personaje;
        System.out.println(nombre + " ha seleccionado a " + personaje.getNombre());
        inicializarCooldownHabilidad(rondaActual);
    }

    /** Llamalo cuando arranca la partida o al asignar personaje (con la ronda actual). */
    public void inicializarCooldownHabilidad(int rondaActual) {
        if (tienePersonaje()) {
            int cd = personajeSeleccionado.getCooldownRondas();
            // primera disponibilidad: rondaActual + cd
            siguienteRondaHabilidad = rondaActual + cd;
            System.out.println("⏳ Habilidad de " + nombre + " disponible desde la ronda " + siguienteRondaHabilidad);
        } else {
            siguienteRondaHabilidad = null;
        }
    }

    /** ¿La habilidad está lista para usarse en esta ronda (o ya se pasó)? */
    public boolean habilidadLista(int rondaActual) {
        return tienePersonaje()
            && siguienteRondaHabilidad != null
            && rondaActual >= siguienteRondaHabilidad;
    }

    /** Consumí la habilidad: programa la próxima disponibilidad sumando el cooldown. */
    public void consumirHabilidad(int rondaActual) {
        if (!tienePersonaje()) return;
        int cd = personajeSeleccionado.getCooldownRondas();
        siguienteRondaHabilidad = rondaActual + cd;
        System.out.println("⚡ " + nombre + " consumió su habilidad. Próxima en ronda " + siguienteRondaHabilidad);
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

    public String getNombre() { return nombre; }
    public Personaje getPersonajeSeleccionado() { return personajeSeleccionado; }
    public Objetivo getObjetivoAsignado() { return objetivoAsignado; }
    public List<Pais> getPaisesControlados() { return paisesControlados; }

    public boolean tienePersonaje() { return personajeSeleccionado != null; }
    public boolean tieneObjetivo() { return objetivoAsignado != null; }

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
        List<Objetivo> pool = ObjetivoRegistry.getInstance().getObjetivos();
        if (pool == null || pool.isEmpty()) return; // o lanzar excepción si querés

        int idx = ThreadLocalRandom.current().nextInt(pool.size());
        Objetivo objetivoAleatorio = pool.get(idx);
        jugador.asignarObjetivo(objetivoAleatorio);
    }

    public static void asignarPaisesIniciales(Jugador jugador) {
        // Toma el pool único del Registry y baraja
        List<Pais> pool = new ArrayList<>(PaisRegistry.getInstance().getPaises());
        if (pool.isEmpty()) return;

        Collections.shuffle(pool);
        int cantidad = Math.min(16, pool.size());

        // Copia propia para no quedar atado a subList
        List<Pais> seleccion = new ArrayList<>(pool.subList(0, cantidad));
        jugador.asignarPaises(seleccion);
    }
}
