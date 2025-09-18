package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class LaCurita extends Pais{
    public LaCurita(){
        super(
            "La Curita",
            "Pobre",
            new ArrayList<String>(){{
                add("La Catanga");
                add("Villa Lanzone");
                add("La Tranquila");
            }}
        );
    }
}
