package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class VillaLanzone extends Pais{
    public VillaLanzone(){
        super(
            "Villa Lanzone",
            "Pobre",
            new ArrayList<String>(){{
                add("Barrio Piolin");
                add("Jose C. Paz");
                add("La Curita");
                add("La Tranquila");
                add("Ingeniero Budge");
            }}
        );
    }
}
