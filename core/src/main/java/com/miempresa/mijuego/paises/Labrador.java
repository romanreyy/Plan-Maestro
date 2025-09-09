package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Labrador extends Pais{
    public Labrador(){
        super(
            "Labrador",
            "America del Norte",
            new ArrayList<String>(){{
                add("Groenlandia");
                add("Terranova");
            }}
        );
    }
}
