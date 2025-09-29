package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class LaCatanga extends Pais{
    public LaCatanga(){
        super(
            "La Catanga",
            "Pobre",
            new ArrayList<String>(){{
                add("La Curita");
                add("Bajo Flores");
                add("Dock Sud");
                add("Isidro Casanova");
                add("Gonzalez Catan");
                add("Villa Soldati");
            }}
        );
    }
}
