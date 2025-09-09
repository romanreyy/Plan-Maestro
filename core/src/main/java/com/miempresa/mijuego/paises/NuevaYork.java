package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class NuevaYork extends Pais{
    public NuevaYork(){
        super(
            "Nueva York",
            "America del Norte",
            new ArrayList<String>(){{
                add("Oregon");
                add("California");
                add("Alaska");
                add("Yukon");
                add("Canada");
                add("Terranova");
                add("Groenladia");
            }}
        );
    }
}
