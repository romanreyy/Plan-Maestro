package com.miempresa.mijuego.model.dto;

import java.util.ArrayList;

public class Pais {
    private String nombre;
    private String continente;
    private ArrayList<Pais> nombresLimitrofes;
    private Jugador propietario;
    // NUEVO: estado mínimo para jugar
    private int tropas = 0;

    public Pais(String nombre, String continente, ArrayList<Pais> nombresLimitrofes) {
        this.nombre = nombre;
        this.continente = continente;
        this.nombresLimitrofes = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getContinente() {
        return continente;
    }

    public ArrayList<Pais> getNombresLimitrofes() {
        return nombresLimitrofes;
    }

    public void addPaisLimitrofe(Pais pais) {
        if (pais != null && !this.nombresLimitrofes.contains(pais)) {
            this.nombresLimitrofes.add(pais);
        }
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
        return otro != null && this.tropas>1 && nombresLimitrofes.contains(otro);
    }

    @Override
    public String toString() {
        String dueño = (propietario != null ? propietario.getNombre() : "Sin dueño");
        return nombre + " — " + dueño + " — tropas: " + tropas;
    }
}
