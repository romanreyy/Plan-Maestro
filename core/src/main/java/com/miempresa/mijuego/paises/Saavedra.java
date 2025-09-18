package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Saavedra extends Pais{
    public Saavedra(){
        super(
            "Saavedra",
            "Rico",
            new ArrayList<String>(){{
                add("Nordelta");
                add("San Isidro");
                add("Palermo");
                add("Nuñez");
                add("Recoleta");
                add("Belgrano");
                add("Olivos");
            }}
        );
    }
}
