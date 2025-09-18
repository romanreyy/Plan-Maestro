package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class LasTunas extends Pais{
    public LasTunas(){
        super(
            "Las Tunas",
            "Pobre",
            new ArrayList<String>(){{
                add("Villa Palito");
                add("Nueva Pompeya");
                add("San Cayetano");
            }}
        );
    }
}
