package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class LaTranquila extends Pais{
    public LaTranquila(){
        super(
            "La Tranquila",
            "Pobre",
            new ArrayList<String>(){{
                add("Villa Lanzone");
                add("La Curita");
                add("Bajo Flores");
            }}
        );
    }
}
