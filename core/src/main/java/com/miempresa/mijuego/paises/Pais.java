package com.miempresa.mijuego.paises;

import com.badlogic.gdx.math.Polygon;
import com.miempresa.mijuego.personajes.Jugador;
import java.util.ArrayList;

public abstract class Pais {
    private String nombre;
    private String continente;
    private ArrayList<String> nombresLimitrofes;
    private Jugador propietario;

    public Pais(String nombre, String continente, ArrayList<String> nombresLimitrofes) {
        this.nombre = nombre;
        this.continente = continente;
        this.nombresLimitrofes = (nombresLimitrofes != null) ? nombresLimitrofes : new ArrayList<>();
        this.propietario = null;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContinente() {
        return continente;
    }

    public ArrayList<String> getNombresLimitrofes() {
        return nombresLimitrofes;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }


    @Override
    public String toString() {
        return nombre + (propietario != null ? " (" + propietario.getNombre() + ")" : " (Sin dueño)");
    }
}
