package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Alaska extends Pais{
    public Alaska(){
        super(
            "Alaska",
            "America del Norte",
            new ArrayList<String>(){{
                add("Oregon");
                add("Nueva York");
                add("Yukon");
                add("Kamchatka");
            }}
        );
    }
}
