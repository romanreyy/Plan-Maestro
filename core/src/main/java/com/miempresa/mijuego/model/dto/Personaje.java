package com.miempresa.mijuego.model.dto;

import com.badlogic.gdx.graphics.Color;

public class Personaje {
    private final String nombre;
    private final String lore;
    private final String habilidadDescripcion;
    private final Color color;
    private final int cooldownRondas; // 🔹 cada cuántas rondas se habilita la habilidad

    public Personaje(String nombre, String lore, String habilidadDescripcion, Color color, int cooldownRondas) {
        this.nombre = nombre;
        this.lore = lore;
        this.habilidadDescripcion = habilidadDescripcion;
        this.color = color;
        this.cooldownRondas = cooldownRondas;
    }


    public String getNombre() { return nombre; }
    public String getHabilidad() { return habilidadDescripcion; }
    public Color getColor() { return color; }
    public int getCooldownRondas() { return cooldownRondas; }
}
