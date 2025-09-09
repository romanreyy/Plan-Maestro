package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Uruguay extends Pais{
    public Uruguay(){
        super(
            "Uruguay",
            "America del Sur",
            new ArrayList<String>(){{
                add("Argentina");
                add("Brasil");
            }}
        );
    }
}
