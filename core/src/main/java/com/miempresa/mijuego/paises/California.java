package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class California extends Pais{
    public California(){
        super(
            "California",
            "America del Norte",
            new ArrayList<String>(){{
                add("Nueva York");
                add("Oregon");
                add("Mexico");
            }}
        );
    }
}
