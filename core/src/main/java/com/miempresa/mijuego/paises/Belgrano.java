package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Belgrano extends Pais{
    public Belgrano(){
        super(
            "Belgrano",
            "Rico",
            new ArrayList<String>(){{
                add("Recoleta");
                add("Saavedra");
                add("Olivos");
            }}
        );
    }
}
