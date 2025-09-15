package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Siberia extends Pais{
    public Siberia(){
        super(
            "Siberia",
            "Asia",
            new ArrayList<String>(){{
                add("Abal");
                add("Mongolia");
                add("China");
                add("Kamchatka");
                add("Taymir");
                add("Tartaria");
            }}
        );
    }
}
