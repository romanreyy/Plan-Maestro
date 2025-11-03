package com.miempresa.mijuego.objetivos;

import com.miempresa.mijuego.juego.GameState;
import com.miempresa.mijuego.personajes.Jugador;

public class PlanDominacion extends Objetivo {
    public PlanDominacion() {
        super("Plan Dominacion",
            "Conquista 30 países sin importar su ubicación, y manténlos simultáneamente.");
    }

    @Override
    public boolean estaCumplido(Jugador jugador, GameState state) {
        int cant = (jugador.getPaisesControlados() != null)
            ? jugador.getPaisesControlados().size() : 0;
        return cant >= 30;
    }
}
