package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class BarrioPiolin extends Pais{
    public BarrioPiolin(){
        super(
            "Barrio Piolin",
            "Pobre",
            new ArrayList<String>(){{
                add("Jose C. Paz");
                add("Villa Lanzone");
                add("La Rodrigo Bueno");
                add("Isidro Casanova");
            }}
        );
    }
}
