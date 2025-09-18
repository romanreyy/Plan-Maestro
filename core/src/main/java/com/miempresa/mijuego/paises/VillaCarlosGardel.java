package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class VillaCarlosGardel extends Pais{
    public VillaCarlosGardel(){
        super(
            "Villa Carlos Gardel",
            "Pobre",
            new ArrayList<String>(){{
                add("Virrey del Pino");
                add("La Rodrigo Bueno");
                add("Barrio San Jorge");
            }}
        );
    }
}
