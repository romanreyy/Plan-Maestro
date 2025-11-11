package com.miempresa.mijuego.model.dto;

import java.util.ArrayList;
import java.util.List;

public class Pais {
    private String nombre;
    private String continente;
    private List<Pais> limitrofes;
    private Jugador propietario;
    private int tropas = 0; // estado mínimo para jugar

    // === Constructores auxiliares (para onPaisUpdate) ===
    public Pais(String nombre) {
        this(nombre, "Desconocido", new ArrayList<>());
    }

    public Pais(String nombre, String continente) {
        this(nombre, continente, new ArrayList<>());
    }

    // === Constructor completo ===
    public Pais(String nombre, String continente, List<Pais> limitrofes) {
        this.nombre = nombre;
        this.continente = continente;
        this.limitrofes = (limitrofes != null) ? new ArrayList<>(limitrofes) : new ArrayList<>();
    }

    // === Getters/Setters básicos ===
    public String getNombre() { return nombre; }
    public String getContinente() { return continente; }

    /** Lista mutable pero propia (copia defensiva en ctor). */
    public List<Pais> getLimitrofes() { return limitrofes; }

    public Jugador getPropietario() { return propietario; }
    public void setPropietario(Jugador propietario) { this.propietario = propietario; }

    // === Tropas ===
    public int getTropas() { return tropas; }

    public void setTropas(int n) {
        // Sin tope, respeta al servidor:
        this.tropas = Math.max(0, n);

        // Si preferís tope 4, usá esto en su lugar:
        // this.tropas = Math.min(4, Math.max(0, n));
    }

    /** Suma o resta tropas (sin bajar de 0). */
    public void agregarTropas(int n) {
        setTropas(this.tropas + n);
    }

    // === Vecindad / adyacencias ===
    public void addPaisLimitrofe(Pais pais) {
        if (pais != null && !this.limitrofes.contains(pais)) {
            this.limitrofes.add(pais);
        }
    }

    public void removeLimitrofe(Pais pais) {
        if (pais != null) this.limitrofes.remove(pais);
    }

    /** ¿puede atacar? Regla mínima: limítrofe y al menos 2 tropas. */
    public boolean puedeAtacarA(Pais otro) {
        return otro != null && this.tropas > 1 && limitrofes.contains(otro);
    }

    // === Igualdad por nombre (clave para contains/remove en listas) ===
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pais)) return false;
        Pais pais = (Pais) o;
        return nombre != null && nombre.equalsIgnoreCase(pais.nombre);
    }

    @Override
    public int hashCode() {
        return (nombre == null) ? 0 : nombre.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        String dueno = (propietario != null ? propietario.getNombre() : "Sin dueño");
        return nombre + " — " + dueno + " — tropas: " + tropas;
    }
}
