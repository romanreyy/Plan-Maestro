package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Constitucion extends Pais {
    public Constitucion(){
        super(
            "Constitucion",
            "Pobre",
            new ArrayList<String>(){{
                add("Villa Palito");
                add("Nueva Pompeya");
                add("Cuartel V");
                add("Mariano Acosta");
                add("Don Orione");
                add("Hidalgo");
            }}
        );
    }
}
