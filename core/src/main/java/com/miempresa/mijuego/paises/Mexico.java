package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Mexico extends Pais{
    public Mexico(){
        super(
            "Mexico",
            "America del Norte",
            new ArrayList<String>(){{
                add("California");
                add("Colombia");
            }}
        );
    }
}
