package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class BajoFlores extends Pais{
    public BajoFlores(){
        super(
            "Bajo Flores",
            "Pobre",
            new ArrayList<String>(){{
                add("La Tranquila");
                add("Ingeniero Budge");
                add("La Catanga");
            }}
        );
    }
}
