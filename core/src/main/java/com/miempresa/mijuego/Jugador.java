package com.miempresa.mijuego;

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

        info.append("Países controlados: ").append(paisesControlados.size());

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

    public static List<Pais> crearPaisesDelMapa() {
        List<Pais> paises = new ArrayList<>();

        // América del Norte
        paises.add(new Pais("ALASKA"));
        paises.add(new Pais("CANADA"));
        paises.add(new Pais("GROENLANDIA"));
        paises.add(new Pais("OREGON"));
        paises.add(new Pais("NUEVA YORK"));
        paises.add(new Pais("LABRADOR"));
        paises.add(new Pais("TERRANOVA"));
        paises.add(new Pais("CALIFORNIA"));
        paises.add(new Pais("MEXICO"));

        // América del Sur
        paises.add(new Pais("COLOMBIA"));
        paises.add(new Pais("BRASIL"));
        paises.add(new Pais("PERU"));
        paises.add(new Pais("ARGENTINA"));
        paises.add(new Pais("URUGUAY"));
        paises.add(new Pais("CHILE"));

        // Europa
        paises.add(new Pais("GRAN BRETAÑA"));
        paises.add(new Pais("ISLANDIA"));
        paises.add(new Pais("FRANCIA"));
        paises.add(new Pais("ESPAÑA"));
        paises.add(new Pais("ALEMANIA"));
        paises.add(new Pais("ITALIA"));
        paises.add(new Pais("POLONIA"));
        paises.add(new Pais("SUECIA"));

        // Asia
        paises.add(new Pais("RUSIA"));
        paises.add(new Pais("SIBERIA"));
        paises.add(new Pais("TARTARIA"));
        paises.add(new Pais("TAYMIR"));
        paises.add(new Pais("KAMCHATKA"));
        paises.add(new Pais("ABAI"));
        paises.add(new Pais("IRAN"));
        paises.add(new Pais("MONGOLIA"));
        paises.add(new Pais("CHINA"));
        paises.add(new Pais("GOBI"));
        paises.add(new Pais("INDIA"));
        paises.add(new Pais("MALASIA"));

        // África
        paises.add(new Pais("SAHARA"));
        paises.add(new Pais("EGIPTO"));
        paises.add(new Pais("ETIOPIA"));
        paises.add(new Pais("ZAIRE"));
        paises.add(new Pais("SUDAFRICA"));
        paises.add(new Pais("MADAGASCAR"));

        // Oceanía
        paises.add(new Pais("AUSTRALIA"));
        paises.add(new Pais("SUMATRA"));
        paises.add(new Pais("BORNEO"));
        paises.add(new Pais("JAVA"));

        // Oriente Medio
        paises.add(new Pais("ARABIA"));
        paises.add(new Pais("ISRAEL"));

        return paises;
    }

    public static void distribuirPaises(List<Jugador> jugadores) {
        List<Pais> todosLosPaises = crearPaisesDelMapa();
        Collections.shuffle(todosLosPaises);

        int paisesTotal = todosLosPaises.size();
        int paisesPorJugador = paisesTotal / jugadores.size();

        System.out.println("=== DISTRIBUCIÓN DE PAÍSES ===");
        System.out.println("Total países: " + paisesTotal);
        System.out.println("Países por jugador: " + paisesPorJugador);

        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            int inicio = i * paisesPorJugador;
            int fin = (i == jugadores.size() - 1) ? paisesTotal : (i + 1) * paisesPorJugador;

            List<Pais> paisesDelJugador = todosLosPaises.subList(inicio, fin);
            jugador.asignarPaises(new ArrayList<>(paisesDelJugador));
        }
        System.out.println("===============================");
    }
}
