package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Oregon extends Pais{
    public Oregon(){
        super(
            "Oregon",
            "America del Norte",
            new ArrayList<String>(){{
                add("Nueva York");
                add("California");
                add("Alaska");
            }}
        );
    }
}
