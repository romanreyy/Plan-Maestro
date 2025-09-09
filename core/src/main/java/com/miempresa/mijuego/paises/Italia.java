package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Italia extends Pais{
    public Italia(){
        super(
            "Italia",
            "Europa",
            new ArrayList<String>(){{
                add("Alemania");
                add("Francia");
                add("Sahara");
            }}
        );
    }
}
