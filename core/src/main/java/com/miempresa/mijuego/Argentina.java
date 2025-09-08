package com.miempresa.mijuego;

import java.util.ArrayList;

class Argentina extends Pais{
    public Argentina(){
        super(
            "Argentina",
            new ArrayList<String>(){{
                add("Peru");
                add("Chile");
                add("Brasil");
                add("Uruguay");
            }}
        );
    }
}
