package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Olivos extends Pais{
    public Olivos(){
        super(
            "Olivos",
            "Rico",
            new ArrayList<String>(){{
                add("Devoto");
                add("Saavedra");
                add("Belgrano");
            }}
        );
    }
}
