package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Palermo extends Pais{
    public Palermo(){
        super(
            "Palermo",
            "Rico",
            new ArrayList<String>(){{
                add("Nuñez");
                add("San Isidro");
                add("Saavedra");
            }}
        );
    }
}
