package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class VillaFiorito extends Pais{
    public VillaFiorito(){
        super(
            "Villa Fiorito",
            "Pobre",
            new ArrayList<String>(){{
                add("Ciudad Oculta");
                add("Dock Sud");
                add("Fuerte Apache");
                add("Laferrere");
            }}
        );
    }
}
