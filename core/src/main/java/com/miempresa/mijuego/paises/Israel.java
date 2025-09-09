package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Israel extends Pais{
    public Israel(){
        super(
            "Israel",
            "Asia",
            new ArrayList<String>(){{
                add("Turquia");
                add("Egipto");
                add("Arabia");
            }}
        );
    }
}
