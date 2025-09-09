package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Groenladia extends Pais{
    public Groenladia(){
        super(
            "Groenlandia",
            "America del Norte",
            new ArrayList<String>(){{
                add("Islandia");
                add("Nueva York");
                add("Labrador");
            }}
        );
    }
}
