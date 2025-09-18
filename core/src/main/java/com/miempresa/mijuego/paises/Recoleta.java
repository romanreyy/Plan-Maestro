package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Recoleta extends Pais{
    public Recoleta(){
        super(
            "Recoleta",
            "Rico",
            new ArrayList<String>(){{
                add("Nuñez");
                add("Saavedra");
                add("Belgrano");
                add("Villa Palito");
            }}
        );
    }
}
