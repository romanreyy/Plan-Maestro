package com.miempresa.mijuego.objetivos;

import com.miempresa.mijuego.juego.GameState;
import com.miempresa.mijuego.personajes.Jugador;

public class ExpansionMixta extends Objetivo{
    public ExpansionMixta(){
        super(
            "Expansion Mixta",
            "Conquista 25 países en total, incluyendo al menos 3 del Territorio Rico."
        );
    }

    @Override
    public boolean estaCumplido(Jugador jugador, GameState state) {
        if (jugador == null || state == null) return false;

        int total = (jugador.getPaisesControlados() != null)
            ? jugador.getPaisesControlados().size() : 0;

        int ricos = state.paisesDelJugadorEnContinente(jugador, "Rico");

        return (total >= 25 && ricos >= 3);
    }
}
