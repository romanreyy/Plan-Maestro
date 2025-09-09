package com.miempresa.mijuego.paises;

import java.util.ArrayList;

class Brasil extends Pais{
    public Brasil(){
        super(
            "Brasil",
            "America del Sur",
            new ArrayList<String>(){{
                add("Peru");
                add("Colombia");
                add("Argentina");
                add("Uruguay");
            }}
        );
    }
}
