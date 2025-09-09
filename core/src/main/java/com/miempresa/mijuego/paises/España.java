package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class España extends Pais{
    public España(){
        super(
            "España",
            "Europa",
            new ArrayList<String>(){{
                add("Gran Betraña");
                add("Francia");
                add("Sahara");
            }}
        );
    }
}
