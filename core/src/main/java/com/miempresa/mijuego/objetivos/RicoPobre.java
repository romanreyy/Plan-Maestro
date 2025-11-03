package com.miempresa.mijuego.objetivos;

import com.miempresa.mijuego.juego.GameState;
import com.miempresa.mijuego.personajes.Jugador;

public class RicoPobre extends Objetivo{
    public RicoPobre(){
        super(
            "Rico y Pobre",
            "Tener al mismo tiempo 7 países del Territorio Rico y 15 del Territorio Pobre."
        );
    }

    @Override
    public boolean estaCumplido(Jugador jugador, GameState state) {
    }
}
