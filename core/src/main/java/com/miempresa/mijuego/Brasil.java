package com.miempresa.mijuego;

import java.util.ArrayList;

class Brasil extends Pais{
    public Brasil(){
        super(
            "Brasil",
            new ArrayList<String>(){{
                add("Peru");
                add("Colombia");
                add("Argentina");
                add("Uruguay");
            }}
        );
    }
}
