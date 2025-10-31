package com.miempresa.mijuego.paises;

import com.badlogic.gdx.math.Polygon;
import com.miempresa.mijuego.personajes.Jugador;
import java.util.ArrayList;

public abstract class Pais {
    private String nombre;
    private String continente;
    private ArrayList<String> nombresLimitrofes;
    private Jugador propietario;

    // NUEVO: estado mínimo para jugar
    private int tropas = 0;

    public Pais(String nombre, String continente, ArrayList<String> nombresLimitrofes) {
        this.nombre = nombre;
        this.continente = continente;
        this.nombresLimitrofes = (nombresLimitrofes != null) ? nombresLimitrofes : new ArrayList<>();
        this.propietario = null;
        this.tropas = 0; // empiezan sin tropas colocadas (se agregan en la fase de colocación)
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

    // ====== NUEVO: TROPAS ======
    public int getTropas() {
        return tropas;
    }

    public void setTropas(int n) {
        this.tropas = Math.min(4, Math.max(0, n));
    }

    /** Suma o resta tropas (sin bajar de 0 y con tope 4). */
    public void agregarTropas(int n) {
        this.tropas = Math.min(4, Math.max(0, this.tropas + n));
    }

    /** Conveniencia: ¿puedo atacar a "otro"? (regla mínima: limítrofe y al menos 2 tropas) */
    public boolean puedeAtacarA(Pais otro) {
        if (otro == null) return false;
        if (this.tropas <= 1) return false;
        // chequeo de limítrofe por nombre, ignorando mayúsculas
        for (String lim : nombresLimitrofes) {
            if (lim.equalsIgnoreCase(otro.getNombre())) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String dueño = (propietario != null ? propietario.getNombre() : "Sin dueño");
        return nombre + " — " + dueño + " — tropas: " + tropas;
    }
}
