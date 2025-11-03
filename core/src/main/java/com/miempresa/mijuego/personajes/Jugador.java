package com.miempresa.mijuego.personajes;

import com.miempresa.mijuego.objetivos.*;
import com.miempresa.mijuego.paises.Pais;
import com.miempresa.mijuego.paises.*;

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
            new PlanDominacion(),
            new ControlTotalTerritorioRico(),
            new DominacionPobre(),
            new EquilibrioEstrategico(),
            new ExpansionMixta(),
            new RicoPobre()
        };
        Random random = new Random();
        Objetivo objetivoAleatorio = objetivosDisponibles[random.nextInt(objetivosDisponibles.length)];
        jugador.asignarObjetivo(objetivoAleatorio);
    }

    public static void asignarPaisesAleatorios(Jugador jugador) {
        ArrayList<Pais> paisesDisponibles = new ArrayList<>();
        paisesDisponibles.add(new BajoFlores());
        paisesDisponibles.add(new BarrioPiolin());
        paisesDisponibles.add(new BarrioSanJorge());
        paisesDisponibles.add(new Belgrano());
        paisesDisponibles.add(new CiudadOculta());
        paisesDisponibles.add(new Constitucion());
        paisesDisponibles.add(new CuartelV());
        paisesDisponibles.add(new Devoto());
        paisesDisponibles.add(new DockSud());
        paisesDisponibles.add(new DonOrione());
        paisesDisponibles.add(new ElJardin());
        paisesDisponibles.add(new Emebe04PuntaDeRielesNuevos());
        paisesDisponibles.add(new FuerteApache());
        paisesDisponibles.add(new GonzalezCatan());
        paisesDisponibles.add(new Hidalgo());
        paisesDisponibles.add(new IsidroCasanova());
        paisesDisponibles.add(new IngenieroBudge());
        paisesDisponibles.add(new JoseCPaz());
        paisesDisponibles.add(new La18());
        paisesDisponibles.add(new LaCatanga());
        paisesDisponibles.add(new LaCurita());
        paisesDisponibles.add(new LaFraga());
        paisesDisponibles.add(new LaRana());
        paisesDisponibles.add(new LaRodrigoBueno());
        paisesDisponibles.add(new LaTranquila());
        paisesDisponibles.add(new Laferrere());
        paisesDisponibles.add(new LasTunas());
        paisesDisponibles.add(new MarianoAcosta());
        paisesDisponibles.add(new MonteChingolo());
        paisesDisponibles.add(new Nordelta());
        paisesDisponibles.add(new NuevaPompeya());
        paisesDisponibles.add(new Nunez());
        paisesDisponibles.add(new Olivos());
        paisesDisponibles.add(new Once());
        paisesDisponibles.add(new PabloMugica());
        paisesDisponibles.add(new Palermo());
        paisesDisponibles.add(new PuertoMadero());
        paisesDisponibles.add(new Recoleta());
        paisesDisponibles.add(new Saavedra());
        paisesDisponibles.add(new SanCayetano());
        paisesDisponibles.add(new SanIsidro());
        paisesDisponibles.add(new Villa31());
        paisesDisponibles.add(new VillaCarlosGardel());
        paisesDisponibles.add(new VillaFiorito());
        paisesDisponibles.add(new VillaItati());
        paisesDisponibles.add(new VillaLanzone());
        paisesDisponibles.add(new VillaPalito());
        paisesDisponibles.add(new VillaSoldati());
        paisesDisponibles.add(new VirreyDelPino());


        Collections.shuffle(paisesDisponibles);

        ArrayList<Pais> paisesAsignados = new ArrayList<>(
            paisesDisponibles.subList(0, Math.min(16, paisesDisponibles.size()))
        );

        jugador.asignarPaises(paisesAsignados);
    }
}
