package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class SanIsidro extends Pais{
    public SanIsidro(){
        super(
            "San Isidro",
            "Rico",
            new ArrayList<String>(){{
                add("Puerto Madero");
                add("Saavedra");
                add("Palermo");
            }}
        );
    }
}
