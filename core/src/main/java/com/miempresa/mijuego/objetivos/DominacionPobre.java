package com.miempresa.mijuego.objetivos;

import com.miempresa.mijuego.juego.GameState;
import com.miempresa.mijuego.personajes.Jugador;

public class DominacionPobre extends Objetivo{
    public DominacionPobre(){
        super(
            "Dominacion del pobre",
            "Conquista al menos 30 países del Territorio Pobre."
        );
    }

    @Override
    public boolean estaCumplido(Jugador jugador, GameState state) {
        if (jugador == null || state == null) return false;

        int paisesPobres = state.paisesDelJugadorEnContinente(jugador, "Pobre");
        return paisesPobres >= 30;
    }
}
