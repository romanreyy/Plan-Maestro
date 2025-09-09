package com.miempresa.mijuego.paises;

import com.badlogic.gdx.utils.Array;
import com.miempresa.mijuego.personajes.Jugador;
import java.util.ArrayList;

public abstract class Pais {
    private String nombre;
    private String continente;
    private ArrayList<Pais> limitrofes;
    private Jugador propietario;

    public Pais(String nombre, String continente,ArrayList<String> limitrofes) {
        this.nombre = nombre;
        this.continente = continente;
        this.limitrofes = new ArrayList<>();
        this.propietario = null;
    }


    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return nombre + (propietario != null ? " (" + propietario.getNombre() + ")" : " (Sin dueño)");
    }
}
