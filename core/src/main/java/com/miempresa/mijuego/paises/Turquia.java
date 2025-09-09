package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Turquia extends Pais {
    public Turquia() {
        super(
            "Turquia",
            "Asia",
            new ArrayList<String>() {
                {
                    add("Rusia");
                    add("Polonia");
                    add("Iran");
                    add("Arabia");
                    add("Israel");
                }
            }
        );
    }
}
