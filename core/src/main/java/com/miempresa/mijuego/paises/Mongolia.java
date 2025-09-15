package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Mongolia extends Pais{
    public Mongolia(){
        super(
            "Mongolia",
            "Asia",
            new ArrayList<String>(){{
                add("Siberia");
                add("Iran");
                add("Gobi");
                add("China");
            }}
        );
    }
}
