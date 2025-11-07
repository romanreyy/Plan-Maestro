package com.miempresa.mijuego.config;

import com.miempresa.mijuego.bussiness.GameState;
import com.miempresa.mijuego.model.dto.Jugador;
import com.miempresa.mijuego.model.dto.Objetivo;
import com.miempresa.mijuego.model.dto.Personaje;

import java.util.ArrayList;
import java.util.List;

public class ObjetivoRegistry {

    private static ObjetivoRegistry registry;

    private List<Objetivo> objetivos = new ArrayList<Objetivo>();

    private ObjetivoRegistry() {
        this.registerAllObjetivo();
    }

    public static ObjetivoRegistry getInstance() {
        if (ObjetivoRegistry.registry == null) {
            ObjetivoRegistry.registry = new ObjetivoRegistry();
        }
        return ObjetivoRegistry.registry;
    }

    private void registerAllObjetivo() {

        Objetivo controlTotalTerritorioRico = new Objetivo(
            "Control Total del Territorio Rico",
            "Conquista los 10 países del Territorio Rico."
        ) {
            @Override
            public boolean estaCumplido(Jugador jugador, GameState state) {
                int totalRico   = state.totalPaisesEnContinente("Rico");
                int tuyosRico   = state.paisesDelJugadorEnContinente(jugador, "Rico");
                return totalRico > 0 && tuyosRico == totalRico;
            }
        };
        objetivos.add(controlTotalTerritorioRico);

        Objetivo dominacionPobre = new Objetivo(
            "Dominacion del pobre",
            "Conquista al menos 30 países del Territorio Pobre."
        ) {
            @Override
            public boolean estaCumplido(Jugador jugador, GameState state) {
                if (jugador == null || state == null) return false;

                int paisesPobres = state.paisesDelJugadorEnContinente(jugador, "Pobre");
                return paisesPobres >= 30;
            }
        };
        objetivos.add(dominacionPobre);

        Objetivo equilibrioEstrategico = new Objetivo(
            "Equilibrio Estratégico",
            "Controla al menos 15 países en cada uno de los continentes: Rico, Pobre y Medio."
        ) {
            @Override
            public boolean estaCumplido(Jugador jugador, GameState state) {
                if (jugador == null || state == null) return false;

                int paisesRico  = state.paisesDelJugadorEnContinente(jugador, "Rico");
                int paisesMedio = state.paisesDelJugadorEnContinente(jugador, "Medio");
                int paisesPobre = state.paisesDelJugadorEnContinente(jugador, "Pobre");

                return paisesRico >= 15 && paisesMedio >= 15 && paisesPobre >= 15;
            }
        };
        objetivos.add(equilibrioEstrategico);

        Objetivo expansionMixta = new Objetivo(
            "Expansion Mixta",
            "Conquista 25 países en total, incluyendo al menos 3 del Territorio Rico."
        ) {
            @Override
            public boolean estaCumplido(Jugador jugador, GameState state) {
                if (jugador == null || state == null) return false;

                int total = (jugador.getPaisesControlados() != null)
                    ? jugador.getPaisesControlados().size() : 0;

                int ricos = state.paisesDelJugadorEnContinente(jugador, "Rico");

                return (total >= 25 && ricos >= 3);
            }
        };
        objetivos.add(expansionMixta);

        Objetivo planDominacion = new Objetivo(
            "Plan Dominacion",
            "Conquista 30 países sin importar su ubicación, y manténlos simultáneamente."
        ) {
            @Override
            public boolean estaCumplido(Jugador jugador, GameState state) {
                int cant = (jugador.getPaisesControlados() != null)
                    ? jugador.getPaisesControlados().size() : 0;
                return cant >= 30;
            }
        };
        objetivos.add(planDominacion);

        Objetivo ricoPobre = new Objetivo("Rico y Pobre",
            "Tener al mismo tiempo 7 países del Territorio Rico y 15 del Territorio Pobre."
        ) {
            @Override
            public boolean estaCumplido(Jugador jugador, GameState state) {
                if (jugador == null || state == null) return false;

                int ricos = state.paisesDelJugadorEnContinente(jugador, "Rico");
                int pobres = state.paisesDelJugadorEnContinente(jugador, "Pobre");

                return (ricos >= 7 && pobres >= 15);
            }
        };
        objetivos.add(ricoPobre);
    }

    public List<Objetivo> getObjetivos() {
        return objetivos;
    }
}
