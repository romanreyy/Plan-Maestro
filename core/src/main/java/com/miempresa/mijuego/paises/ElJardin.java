package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class ElJardin extends Pais{
    public ElJardin(){
        super(
            "El Jardin",
            "Pobre",
            new ArrayList<String>(){{
                add("La Fraga");
                add("Once");
                add("Villa 31");
                add("Laferrere");
            }}
        );
    }
}
