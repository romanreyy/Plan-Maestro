package com.miempresa.mijuego.juego;

import com.miempresa.mijuego.paises.Pais;
import com.miempresa.mijuego.personajes.Jugador;
import java.util.*;

public class Batalla {

    private final GameState state;
    private final Pais atacante;
    private final Pais defensor;
    private final Jugador jugadorAtacante;
    private final Jugador jugadorDefensor;
    private final Random random = new Random();

    public Batalla(GameState state, Pais atacante, Pais defensor) {
        if (atacante == null || defensor == null)
            throw new IllegalArgumentException("Los países no pueden ser nulos.");
        if (state == null)
            throw new IllegalArgumentException("GameState no puede ser nulo.");

        this.state = state;
        this.atacante = atacante;
        this.defensor = defensor;
        this.jugadorAtacante = atacante.getPropietario();
        this.jugadorDefensor = defensor.getPropietario();

        if (!validarAtaquePermitido()) {
            throw new IllegalArgumentException("No se puede atacar: países no son limítrofes o no hay tropas suficientes.");
        }
    }

    private boolean validarAtaquePermitido() {
        if (atacante.getTropas() <= 1) return false;
        if (atacante.puedeAtacarA(defensor)) return true;

        // 🌊 Marinero Papá
        if (jugadorAtacante.tienePersonaje() &&
            "El Marinero Papá".equals(jugadorAtacante.getPersonajeSeleccionado().getNombre()) &&
            jugadorAtacante.habilidadLista(state.getNumeroRonda())) {

            System.out.println("🌊 " + jugadorAtacante.getNombre() +
                " usa 'Navegación Experta' para atacar " + defensor.getNombre() + " sin ser limítrofe!");
            jugadorAtacante.consumirHabilidad(state.getNumeroRonda());
            return true;
        }

        return false;
    }

    public String ejecutarBatalla() {

        // 💥 Pibe Piola
        if (jugadorAtacante.tienePersonaje() &&
            "El Pibe Piola".equals(jugadorAtacante.getPersonajeSeleccionado().getNombre()) &&
            jugadorAtacante.habilidadLista(state.getNumeroRonda())) {

            jugadorAtacante.consumirHabilidad(state.getNumeroRonda());
            defensor.setPropietario(jugadorAtacante);
            defensor.setTropas(1);
            atacante.agregarTropas(-1);
            jugadorDefensor.getPaisesControlados().remove(defensor);
            jugadorAtacante.getPaisesControlados().add(defensor);

            return "💥 " + jugadorAtacante.getNombre() +
                " usa 'Golpe Maestro' y conquista directamente " +
                defensor.getNombre() + " sin pelear!";
        }

        // ⚙️ Ratón del Grupo
        if (jugadorAtacante.tienePersonaje() &&
            "El Ratón del Grupo".equals(jugadorAtacante.getPersonajeSeleccionado().getNombre()) &&
            jugadorAtacante.habilidadLista(state.getNumeroRonda())) {

            System.out.println("🧀 " + jugadorAtacante.getNombre() +
                " activa 'Ahorro Forzoso': " +
                defensor.getNombre() + " solo podrá defender con 1 tropa.");
            defensor.setTropas(1);
            jugadorAtacante.consumirHabilidad(state.getNumeroRonda());
        }

        // ⚔️ Batalla normal
        if (atacante.getTropas() <= 1) {
            return "⚠️ No se puede atacar con menos de 2 tropas.";
        }

        int dadosAtacante = Math.min(atacante.getTropas() - 1, 3);
        int dadosDefensor = Math.min(defensor.getTropas(), 3);

        List<Integer> tiradasAtacante = tirarDados(dadosAtacante);
        List<Integer> tiradasDefensor = tirarDados(dadosDefensor);

        // 🃏 Mentiroso Rey
        if (jugadorAtacante.tienePersonaje() &&
            "El Mentiroso Rey".equals(jugadorAtacante.getPersonajeSeleccionado().getNombre()) &&
            jugadorAtacante.habilidadLista(state.getNumeroRonda()) &&
            !tiradasAtacante.isEmpty()) {

            int idxMin = 0;
            int minVal = tiradasAtacante.get(0);
            for (int i = 1; i < tiradasAtacante.size(); i++) {
                if (tiradasAtacante.get(i) < minVal) {
                    minVal = tiradasAtacante.get(i);
                    idxMin = i;
                }
            }
            tiradasAtacante.set(idxMin, 6);
            jugadorAtacante.consumirHabilidad(state.getNumeroRonda());
            System.out.println("🃏 " + jugadorAtacante.getNombre() +
                " usa 'Mentira Perfecta': su dado más bajo pasa a 6.");
        }

        // 💰 Villero: Robo Estratégico
        if (jugadorAtacante.tienePersonaje() &&
            "El Villero".equals(jugadorAtacante.getPersonajeSeleccionado().getNombre()) &&
            jugadorAtacante.habilidadLista(state.getNumeroRonda()) &&
            !tiradasAtacante.isEmpty() && !tiradasDefensor.isEmpty()) {

            int maxDef = Collections.max(tiradasDefensor);
            int minAtq = Collections.min(tiradasAtacante);

            tiradasAtacante.set(tiradasAtacante.indexOf(minAtq), maxDef);
            tiradasDefensor.set(tiradasDefensor.indexOf(maxDef), minAtq);

            jugadorAtacante.consumirHabilidad(state.getNumeroRonda());

            System.out.println("💰 " + jugadorAtacante.getNombre() +
                " usa 'Robo Estratégico': roba el dado " + maxDef +
                " del defensor y le da su dado " + minAtq + ".");
        }

        // Ordenamos después de habilidades
        tiradasAtacante.sort(Collections.reverseOrder());
        tiradasDefensor.sort(Collections.reverseOrder());

        System.out.println("🎯 " + jugadorAtacante.getNombre() + " ataca desde " +
            atacante.getNombre() + " (" + atacante.getTropas() + " tropas)");
        System.out.println("🛡️ " + jugadorDefensor.getNombre() + " defiende en " +
            defensor.getNombre() + " (" + defensor.getTropas() + " tropas)");
        System.out.println("Dados atacante: " + tiradasAtacante);
        System.out.println("Dados defensor: " + tiradasDefensor);

        int comparaciones = Math.min(dadosAtacante, dadosDefensor);
        int bajasAtacante = 0;
        int bajasDefensor = 0;

        for (int i = 0; i < comparaciones; i++) {
            int a = tiradasAtacante.get(i);
            int d = tiradasDefensor.get(i);
            if (a > d) bajasDefensor++;
            else bajasAtacante++;
        }

        atacante.agregarTropas(-bajasAtacante);
        defensor.agregarTropas(-bajasDefensor);

        StringBuilder resultado = new StringBuilder();
        resultado.append("\n⚔️  Resultado de la batalla:\n");
        resultado.append(" - ").append(jugadorAtacante.getNombre())
            .append(" perdió ").append(bajasAtacante).append(" tropas.\n");
        resultado.append(" - ").append(jugadorDefensor.getNombre())
            .append(" perdió ").append(bajasDefensor).append(" tropas.\n");

        if (defensor.getTropas() <= 0) {
            defensor.setPropietario(jugadorAtacante);
            defensor.setTropas(1);
            atacante.agregarTropas(-1);
            jugadorDefensor.getPaisesControlados().remove(defensor);
            jugadorAtacante.getPaisesControlados().add(defensor);
            resultado.append("🏴‍☠️ ").append(jugadorAtacante.getNombre())
                .append(" conquistó ").append(defensor.getNombre()).append("!\n");
        }

        return resultado.toString();
    }

    private List<Integer> tirarDados(int cantidad) {
        List<Integer> resultados = new ArrayList<>();
        for (int i = 0; i < cantidad; i++)
            resultados.add(random.nextInt(6) + 1);
        return resultados;
    }
}
