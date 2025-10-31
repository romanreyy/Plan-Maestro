package com.miempresa.mijuego.juego;

import com.miempresa.mijuego.paises.Pais;
import com.miempresa.mijuego.personajes.Jugador;

import java.util.*;

/**
 * Clase que representa una batalla entre dos países.
 * Aplica las reglas de ataque/defensa según las tropas y los dados.
 */
public class Batalla {

    private final Pais atacante;
    private final Pais defensor;
    private final Jugador jugadorAtacante;
    private final Jugador jugadorDefensor;
    private final Random random = new Random();

    public Batalla(Pais atacante, Pais defensor) {
        if (atacante == null || defensor == null)
            throw new IllegalArgumentException("Los países no pueden ser nulos.");
        if (!atacante.puedeAtacarA(defensor))
            throw new IllegalArgumentException("No se puede atacar: países no son limítrofes o no hay tropas suficientes.");

        this.atacante = atacante;
        this.defensor = defensor;
        this.jugadorAtacante = atacante.getPropietario();
        this.jugadorDefensor = defensor.getPropietario();
    }

    /** Ejecuta la batalla completa y devuelve el resultado final como texto. */
    public String ejecutarBatalla() {

        if (atacante.getTropas() <= 1) {
            return "⚠️ No se puede atacar con menos de 2 tropas.";
        }

        int dadosAtacante = Math.min(atacante.getTropas() - 1, 3);
        int dadosDefensor = Math.min(defensor.getTropas(), 3);

        List<Integer> tiradasAtacante = tirarDados(dadosAtacante);
        List<Integer> tiradasDefensor = tirarDados(dadosDefensor);

        tiradasAtacante.sort(Collections.reverseOrder());
        tiradasDefensor.sort(Collections.reverseOrder());

        System.out.println("🎯 " + jugadorAtacante.getNombre() + " ataca desde " + atacante.getNombre() + " (" + atacante.getTropas() + " tropas)");
        System.out.println("🛡️  " + jugadorDefensor.getNombre() + " defiende en " + defensor.getNombre() + " (" + defensor.getTropas() + " tropas)");
        System.out.println("Dados atacante: " + tiradasAtacante);
        System.out.println("Dados defensor: " + tiradasDefensor);

        int comparaciones = Math.min(dadosAtacante, dadosDefensor);
        int bajasAtacante = 0;
        int bajasDefensor = 0;

        for (int i = 0; i < comparaciones; i++) {
            int a = tiradasAtacante.get(i);
            int d = tiradasDefensor.get(i);
            if (a > d) {
                bajasDefensor++;
            } else {
                bajasAtacante++;
            }
        }

        atacante.agregarTropas(-bajasAtacante);
        defensor.agregarTropas(-bajasDefensor);

        StringBuilder resultado = new StringBuilder();
        resultado.append("\n⚔️  Resultado de la batalla:\n");
        resultado.append(" - ").append(jugadorAtacante.getNombre()).append(" perdió ").append(bajasAtacante).append(" tropas.\n");
        resultado.append(" - ").append(jugadorDefensor.getNombre()).append(" perdió ").append(bajasDefensor).append(" tropas.\n");

        // Si el defensor perdió todas las tropas → conquista
        if (defensor.getTropas() <= 0) {
            defensor.setPropietario(jugadorAtacante);
            defensor.setTropas(1); // tropa mínima se mueve
            atacante.agregarTropas(-1);

            jugadorDefensor.getPaisesControlados().remove(defensor);
            jugadorAtacante.getPaisesControlados().add(defensor);

            resultado.append("🏴‍☠️ ").append(jugadorAtacante.getNombre())
                .append(" conquistó ").append(defensor.getNombre()).append("!\n");
        }

        return resultado.toString();
    }

    /** Simula la tirada de N dados (1–6). */
    private List<Integer> tirarDados(int cantidad) {
        List<Integer> resultados = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            resultados.add(random.nextInt(6) + 1);
        }
        return resultados;
    }
}
