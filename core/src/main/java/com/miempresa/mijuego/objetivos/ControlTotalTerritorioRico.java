package com.miempresa.mijuego.objetivos;

import com.miempresa.mijuego.juego.GameState;
import com.miempresa.mijuego.personajes.Jugador;

public class ControlTotalTerritorioRico extends Objetivo{
    public ControlTotalTerritorioRico(){
        super(
            "Control Total del Territorio Rico",
        "Conquista los 10 países del Territorio Rico."
        );
    }

    @Override
    public boolean estaCumplido(Jugador jugador, GameState state) {
    }

}
