package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class LaRodrigoBueno extends Pais {
    public LaRodrigoBueno() {
        super(
            "La Rodrigo Bueno",
            "Asia",
            new ArrayList<String>() {
                {
                    add("Virrey del Pino");
                    add("Jose C. Paz");
                    add("Barrio Piolin");
                    add("Barrio San Jorge");
                    add("Villa Carlos Gardel");
                }
            }
        );
    }
}
