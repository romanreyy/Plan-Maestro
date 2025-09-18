package com.miempresa.mijuego.paises;

import java.util.ArrayList;

class DockSud extends Pais{
    public DockSud(){
        super(
            "Dock Sud",
            "Pobre",
            new ArrayList<String>(){{
                add("Ciudad Oculta");
                add("Villa Fiorito");
                add("La 18");
                add("Fuerte Apache");
                add("La Catanga");
            }}
        );
    }
}
