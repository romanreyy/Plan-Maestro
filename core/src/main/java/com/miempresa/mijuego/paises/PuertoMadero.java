package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class PuertoMadero extends Pais{
    public PuertoMadero(){
        super(
            "Puerto Madero",
            "Rico",
            new ArrayList<String>(){{
                add("San Isidro");
                add("Nordelta");
            }}
        );
    }
}
