package com.miempresa.mijuego.objetivos;

import com.miempresa.mijuego.juego.GameState;
import com.miempresa.mijuego.personajes.Jugador;

public class EquilibrioEstrategico extends Objetivo{
    public EquilibrioEstrategico(){
        super(
            "Equilibrio Estrategico",
            "Controlá al menos 5 países del Territorio Rico y 20 del Territorio Pobre."
        );
    }

    @Override
    public boolean estaCumplido(Jugador jugador, GameState state) {
    }
}
