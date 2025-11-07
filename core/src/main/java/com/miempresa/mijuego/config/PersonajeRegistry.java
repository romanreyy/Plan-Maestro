package com.miempresa.mijuego.config;

import com.badlogic.gdx.graphics.Color;
import com.miempresa.mijuego.model.dto.Personaje;

import java.util.ArrayList;
import java.util.List;

public class PersonajeRegistry {

    private static PersonajeRegistry registry;

    private List<Personaje> personajes = new ArrayList<Personaje>();

    private PersonajeRegistry() {
        this.registerAllPersonaje();
    }

    public static PersonajeRegistry getInstance() {
        if (PersonajeRegistry.registry == null) {
            PersonajeRegistry.registry = new PersonajeRegistry();
        }
        return PersonajeRegistry.registry;
    }

    private void registerAllPersonaje() {
        Personaje marineroPapa = new Personaje( "El Marinero Papá",
            "El marinero papá, nacido en los mares, un conocedor de todo el mundo y con la capacidad de ir a cualquier sitio sin ningún mapa, la única herramienta que necesita es la brújula ubicada en su cabeza.",
            "Navegación Experta",
            Color.BROWN,
            5
        );
        personajes.add(marineroPapa);

        Personaje pibePiola = new Personaje( "El Pibe Piola",
            "El Pibe Piola, nacido en la cima...",
            "Recuperación Rápida: Puede recuperar territorios perdidos con mayor facilidad.",
            Color.BLUE,
            6
        );
        personajes.add(pibePiola);

        Personaje mentirosoRey = new Personaje( "El Mentiroso Rey",
            "El Mentiroso Rey, nacido en una infancia llena de mentiras donde nadie puede confiar en él, la habilidad que desarrolló fue mentir a todo lo que lo delata busca la forma de convencer lo que quiere a toda costa.",
            "Engaño Maestro: Puede confundir a los enemigos y alterar sus estrategias.",
            Color.VIOLET,
            6
        );
        personajes.add(mentirosoRey);

        Personaje ratonDelGrupo = new Personaje( "El Ratón del Grupo",
            "El Ratón del Grupo, nacido en una casa llena de ratones donde se acostumbró a no pagar nada a menos que sea muy necesario, tiene la habilidad de hacer que los demás tampoco se gastan.",
            "Ahorro Forzoso: Reduce los costos de acciones para todos los jugadores.",
            Color.GREEN,
            5
        );
        personajes.add(ratonDelGrupo);

        Personaje villero = new Personaje( "El Villero",
            "El Villero, nacido en el Fuerte Barracas, un pirata pero con la habilidad de robar que ningún otro tiene, él no roba pasa, a lo únicos enteros con la habilidades aprendidas en su barrio.",
            "Robo Estratégico: Puede apoderarse de recursos enemigos durante el combate.",
            Color.RED,
            5
        );
        personajes.add(villero);
    }
    public List<Personaje> getPersonajes() {
        return personajes;
    }
}
