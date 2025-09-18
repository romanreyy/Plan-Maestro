package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class BarrioSanJorge extends Pais{
    public BarrioSanJorge(){
        super(
            "Barrio San Jorge",
            "Pobre",
            new ArrayList<String>(){{
                add("Villa Carlos Gardel");
                add("La Rodrigo Bueno");
                add("Isidro Casanova");
            }}
        );
    }
}
