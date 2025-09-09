package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Terranova extends Pais{
    public Terranova(){
        super(
            "Terranova",
            "America del Norte",
            new ArrayList<String>(){{
                add("Canada");
                add("Nueva York");
                add("Labrador");
            }}
        );
    }
}
