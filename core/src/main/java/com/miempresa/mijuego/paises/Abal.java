package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Abal extends Pais{
    public Abal(){
        super(
            "Abal",
            "Europa",
            new ArrayList<String>(){{
                add("Tartaria");
                add("Siberia");
                add("Rusia");
                add("Iran");
            }}
        );
    }
}
