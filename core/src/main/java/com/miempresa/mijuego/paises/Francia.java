package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Francia extends Pais{
    public Francia(){
        super(
            "Francia",
            "Europa",
            new ArrayList<String>(){{
                add("Alemania");
                add("Italia");
                add("España");
            }}
        );
    }
}
