package com.miempresa.mijuego.paises;

import java.util.ArrayList;
import java.util.Arrays;

public class Saavedra extends Pais {
    public Saavedra() {
        super(
            "Saavedra",
            "Rico",
            new ArrayList<>(Arrays.asList(
                "Nordelta", "San Isidro", "Palermo",
                "Nuñez", "Recoleta", "Belgrano", "Olivos"
            )),
            new float[]{
                360, 700,  // vértice 1
                460, 720,  // vértice 2
                500, 680,  // vértice 3
                420, 660   // vértice 4
            }
        );
    }
}
