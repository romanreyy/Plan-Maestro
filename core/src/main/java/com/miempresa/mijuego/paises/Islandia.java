package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Islandia extends Pais{
    public Islandia(){
        super(
            "Islandia",
            "Europa",
            new ArrayList<String>(){{
                add("Groenlandia");
                add("Suecia");
                add("Gran Bretaña");
            }}
        );
    }
}
