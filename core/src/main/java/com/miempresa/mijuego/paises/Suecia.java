package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Suecia extends Pais{
    public Suecia(){
        super(
            "Suecia",
            "Europa",
            new ArrayList<String>(){{
                add("Rusia");
                add("Islandia");
            }}
        );
    }
}
