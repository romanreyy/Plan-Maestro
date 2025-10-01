package com.miempresa.mijuego.personajes;

import com.badlogic.gdx.graphics.Color;

public class RatonDelGrupo extends Personaje {
    public RatonDelGrupo() {
        super(
            "El Ratón del Grupo",
            "El Ratón del Grupo, nacido en una casa llena de ratones donde se acostumbró a no pagar nada a menos que sea muy necesario, tiene la habilidad de hacer que los demás tampoco se gastan.",
            "Ahorro Forzoso: Reduce los costos de acciones para todos los jugadores.",
            Color.GREEN // 🔹 color característico del Ratón
        );
    }
}
