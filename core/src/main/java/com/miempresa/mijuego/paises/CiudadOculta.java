package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class CiudadOculta extends Pais{
    public CiudadOculta(){
        super(
            "Ciudad Oculta",
            "Pobre",
            new ArrayList<String>(){{
                add("Villa Fiorito");
                add("Dock Sud");
                add("Devoto");
            }}
        );
    }
}
