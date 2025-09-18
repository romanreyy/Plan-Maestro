package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class VillaPalito extends Pais{
    public VillaPalito(){
        super(
            "Villa Palito",
            "Pobre",
            new ArrayList<String>(){{
                add("Las Tunas");
                add("Nueva Pompeya");
                add("Recoleta");
                add("Constitucion");
            }}
        );
    }
}
