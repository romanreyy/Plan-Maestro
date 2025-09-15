package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Kamchatka extends Pais{
    public Kamchatka(){
        super(
            "Kamchatka",
            "Asia",
            new ArrayList<String>(){{
                add("Alaska");
                add("Siberia");
                add("Taymir");
                add("China");
            }}
        );
    }
}
