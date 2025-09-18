package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class NuevaPompeya extends Pais{
    public NuevaPompeya(){
        super(
            "Nueva Pompeya",
            "Pobre",
            new ArrayList<String>(){{
                add("La Rana");
                add("San Cayetano");
                add("Las Tunas");
                add("Villa Palito");
                add("Cuartel V");
                add("Constitucion");
                add("Virrey del Pino");
            }}
        );
    }
}
