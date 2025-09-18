package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class LaRana extends Pais{
    public LaRana(){
        super(
            "La Rana",
            "Pobre",
            new ArrayList<String>(){{
                add("San Cayetano");
                add("Nueva Pompeya");
                add("Jose C. Paz");
                add("Virrey del Pino");
            }}
        );
    }
}
