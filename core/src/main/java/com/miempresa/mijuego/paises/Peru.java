package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Peru extends Pais{
    public Peru(){
        super(
            "Peru",
            "America del Sur",
            new ArrayList<String>(){{
                add("Colombia");
                add("Chile");
                add("Brasil");
                add("Argentina");
            }}
        );
    }
}
