package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Laferrere extends Pais{
    public Laferrere(){
        super(
          "Laferrere",
          "Pobre",
            new ArrayList<String>(){{
                add("Fuerte Apache");
                add("Villa Fiorito");
                add("El Jardin");
            }}
        );
    }
}
