package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Polonia extends Pais{
    public Polonia(){
        super(
            "Polonia",
            "Europa",
            new ArrayList<String>(){{
                add("Rusia");
                add("Alemania");
                add("Arabia");
                add("Egipto");
            }}
        );
    }
}
