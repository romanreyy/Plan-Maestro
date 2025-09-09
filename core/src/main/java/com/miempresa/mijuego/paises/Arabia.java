package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Arabia extends Pais{
    public Arabia(){
        super(
            "Arabia",
            "Asia",
            new ArrayList<String>(){{
                add("Iran");
                add("Tuquia");
                add("Israel");
            }}
        );
    }
}
