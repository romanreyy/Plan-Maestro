package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Alemania extends Pais{
    public Alemania(){
        super(
            "Alemania",
            "Europa",
            new ArrayList<String>(){{
                add("Polonia");
                add("Rusia");
                add("Italia");
                add("Francia");
                add("Gran Bretaña");
            }}
        );
    }
}
