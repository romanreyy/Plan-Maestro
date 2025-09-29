package com.miempresa.mijuego.paises;

import java.util.ArrayList;

public class MarianoAcosta extends Pais{
    public MarianoAcosta(){
        super(
            "Mariano Acosta",
            "Pobre",
            new ArrayList<String>(){{
                add("Cuartel V");
                add("Virrey del Pino");
                add("Don Orione");
                add("Constitucion");
            }}
        );
    }
}
