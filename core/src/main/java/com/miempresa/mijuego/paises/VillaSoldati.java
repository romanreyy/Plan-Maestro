package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class VillaSoldati extends Pais{
    public VillaSoldati(){
        super(
            "Villa Soldati",
            "Pobre",
            new ArrayList<String>(){{
                add("Gonzalez Catan");
                add("Pablo Mugica");
                add("La Catanga");
                add("Monte Chingolo");
            }}
        );
    }
}
