package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Canada extends Pais{
    public Canada(){
        super(
            "Canada",
            "America del Norte",
            new ArrayList<String>(){{
                add("Terranova");
                add("Nueva York");
                add("Yukon");
            }}
        );
    }
}
