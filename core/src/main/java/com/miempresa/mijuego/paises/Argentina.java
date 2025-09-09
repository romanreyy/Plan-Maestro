package com.miempresa.mijuego.paises;

import java.util.ArrayList;

class Argentina extends Pais{
    public Argentina(){
        super(
            "Argentina",
            "America del Sur",
            new ArrayList<String>(){{
                add("Peru");
                add("Chile");
                add("Brasil");
                add("Uruguay");
            }}

        );
    }
}
