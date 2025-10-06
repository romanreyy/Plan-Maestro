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
            ))
        );
    }
}
