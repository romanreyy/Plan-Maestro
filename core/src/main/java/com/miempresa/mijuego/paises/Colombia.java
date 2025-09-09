package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Colombia extends Pais{
    public Colombia(){
        super(
            "Colombia",
            "America del Sur",
            new ArrayList<String>(){{
                add("Peru");
                add("Brasil");
                add("Mexico");
            }}
        );
    }
}
