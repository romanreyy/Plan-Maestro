package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Rusia extends Pais{
    public Rusia(){
        super(
            "Rusia",
            "Europa",
            new ArrayList<String>(){{
                add("Abal");
                add("Iran");
                add("Polonia");
                add("Turquia");
                add("Alemania");
            }}
        );
    }
}
