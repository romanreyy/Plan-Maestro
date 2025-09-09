package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class Chile extends Pais{
    public Chile(){
        super(
          "Chile",
          "America del Sur",
            new ArrayList<String>(){{
                add("Peru");
                add("Argentina");
            }}
        );
    }
}
