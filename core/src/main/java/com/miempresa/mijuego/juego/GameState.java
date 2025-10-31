package com.miempresa.mijuego.juego;

import com.miempresa.mijuego.enums.FaseJuego;
import com.miempresa.mijuego.objetivos.Objetivo;
import com.miempresa.mijuego.personajes.Jugador;
import com.miempresa.mijuego.paises.Pais;

import java.util.*;

public class GameState {

    private final List<Jugador> jugadores;
    private int turno;
    private FaseJuego fase = FaseJuego.COLOCACION_INICIAL;

    private static final int TROPAS_INICIALES_POR_JUGADOR = 8;
    private int colocacionesPendientesDelTurno = TROPAS_INICIALES_POR_JUGADOR;
    private int jugadoresQueYaColocaron = 0;

    private int jugadoresQueYaAtacaron = 0;
    private boolean rondaCompletada = false;

    private final Map<Jugador, Integer> refuerzosPendientes = new HashMap<>();

    // 🏆 NUEVO: control de victoria
    private Jugador ganador = null;

    public GameState(List<Jugador> jugadores, int indiceQueEmpieza) {
        if (jugadores == null || jugadores.size() != 3) {
            throw new IllegalArgumentException("Se requieren exactamente 3 jugadores.");
        }
        for (Jugador j : jugadores)
            Objects.requireNonNull(j, "Jugador nulo en la lista de jugadores");

        this.jugadores = jugadores;
        this.turno = Math.floorMod(indiceQueEmpieza, jugadores.size());
        for (Jugador j : jugadores) refuerzosPendientes.put(j, 0);
    }

    // --- Getters ---
    public FaseJuego getFase() { return fase; }
    public Jugador getJugadorActual() { return jugadores.get(turno); }
    public int getIndiceTurno() { return turno; }
    public int getColocacionesPendientesDelTurno() { return colocacionesPendientesDelTurno; }
    public int getJugadoresQueYaColocaron() { return jugadoresQueYaColocaron; }
    public int getTropasInicialesPorJugador() { return TROPAS_INICIALES_POR_JUGADOR; }
    public int getRefuerzosPendientesDelTurno() { return refuerzosPendientes.getOrDefault(getJugadorActual(), 0); }
    public Jugador getGanador() { return ganador; }
    public boolean hayGanador() { return ganador != null; }

    // =====================================================
    // ===============  COLOCACIÓN INICIAL  ================
    // =====================================================

    public boolean colocarTropaInicial(Pais pais) {
        if (fase != FaseJuego.COLOCACION_INICIAL) return false;
        if (pais == null) return false;
        if (pais.getPropietario() != getJugadorActual()) return false;
        if (colocacionesPendientesDelTurno <= 0) return false;

        pais.agregarTropas(1);
        colocacionesPendientesDelTurno--;
        if (colocacionesPendientesDelTurno == 0) finalizarTurnoDeColocacion();
        return true;
    }

    public void finalizarTurnoDeColocacion() {
        if (fase != FaseJuego.COLOCACION_INICIAL) return;

        jugadoresQueYaColocaron++;
        if (jugadoresQueYaColocaron >= jugadores.size()) {
            fase = FaseJuego.ATAQUE;
            System.out.println("=== ¡Fase de ATAQUE iniciada! ===");
        } else {
            turno = (turno + 1) % jugadores.size();
            colocacionesPendientesDelTurno = TROPAS_INICIALES_POR_JUGADOR;
            System.out.println("Turno del siguiente jugador: " + getJugadorActual().getNombre());
        }
    }

    // =====================================================
    // ==================  FASE DE ATAQUE  =================
    // =====================================================

    public String atacar(Pais origen, Pais destino) {
        if (fase != FaseJuego.ATAQUE) return "⚠️ No se puede atacar fuera de la fase ATAQUE.";
        if (origen == null || destino == null) return "⚠️ Error: país atacante o defensor nulo.";
        if (origen.getPropietario() != getJugadorActual()) return "⚠️ No podés atacar con un país que no te pertenece.";
        if (!origen.puedeAtacarA(destino)) return "⚠️ Ataque inválido: no son limítrofes o no hay tropas suficientes.";

        Batalla batalla = new Batalla(origen, destino);
        String resultado = batalla.ejecutarBatalla();

        // 🧩 Evaluar si el jugador ganó tras la batalla
        evaluarVictoria();
        return resultado;
    }

    public void finalizarTurnoDeAtaque() {
        if (fase != FaseJuego.ATAQUE) return;

        jugadoresQueYaAtacaron++;

        if (jugadoresQueYaAtacaron >= jugadores.size()) {
            rondaCompletada = true;
            calcularRefuerzosDeTodos();
            fase = FaseJuego.AGRUPAR;
            jugadoresQueYaAtacaron = 0;
            turno = 0;
            System.out.println("🏁 RONDA COMPLETA → fase AGRUPAR iniciada.");
        } else {
            turno = (turno + 1) % jugadores.size();
            System.out.println("➡️ Turno de ataque de: " + getJugadorActual().getNombre());
        }
    }

    private void calcularRefuerzosDeTodos() {
        for (Jugador j : jugadores) {
            int paises = (j.getPaisesControlados() != null) ? j.getPaisesControlados().size() : 0;
            int bonus = Math.max(1, paises / 2);
            refuerzosPendientes.put(j, bonus);
            System.out.println("🧮 " + j.getNombre() + " tiene " + paises + " países → " + bonus + " refuerzos.");
        }
    }

    // =====================================================
    // ===================  FASE AGRUPAR  ==================
    // =====================================================

    public boolean asignarRefuerzo(Pais pais) {
        if (fase != FaseJuego.AGRUPAR) return false;
        if (pais == null) return false;

        Jugador actual = getJugadorActual();
        if (pais.getPropietario() != actual) return false;

        int pend = refuerzosPendientes.getOrDefault(actual, 0);
        if (pend <= 0) return false;

        if (pais.getTropas() >= 4) {
            System.out.println("⚠️ " + pais.getNombre() + " ya está en el máximo (4).");
            return false;
        }

        pais.agregarTropas(1);
        refuerzosPendientes.put(actual, pend - 1);
        System.out.println("➕ +1 en " + pais.getNombre() + ". Quedan refuerzos: " + (pend - 1));
        return true;
    }

    public void cerrarAgrupacionYPasarTurno() {
        if (fase != FaseJuego.AGRUPAR) return;

        Jugador actual = getJugadorActual();
        refuerzosPendientes.put(actual, 0);

        // 🧩 También evaluamos victoria por si el objetivo depende de tener cierto control
        evaluarVictoria();
        if (hayGanador()) return;

        boolean todosAgruparon = true;
        for (Jugador j : jugadores) {
            if (refuerzosPendientes.getOrDefault(j, 0) > 0) {
                todosAgruparon = false;
                break;
            }
        }

        if (todosAgruparon) {
            fase = FaseJuego.ATAQUE;
            rondaCompletada = false;
            turno = 0;
            System.out.println("🔁 Todos agruparon → Nueva ronda de ATAQUE iniciada.");
        } else {
            turno = (turno + 1) % jugadores.size();
            System.out.println("➡️ Ahora agrupa: " + getJugadorActual().getNombre());
        }
    }

    // =====================================================
    // ====================  VICTORIA  =====================
    // =====================================================

    private void evaluarVictoria() {
        if (ganador != null) return; // ya hay un ganador

        for (Jugador j : jugadores) {
            Objetivo obj = j.getObjetivoAsignado();
            if (obj != null && obj.estaCumplido(j, this)) {
                ganador = j;
                fase = FaseJuego.FIN_PARTIDA;
                System.out.println("🏆 ¡" + j.getNombre() + " cumplió su objetivo (" + obj.getNombre() + ")!");
                break;
            }
        }
    }

    // =====================================================
    // ===================  DEBUG / TEST  ==================
    // =====================================================

    public void forzarFase(FaseJuego nuevaFase) {
        this.fase = nuevaFase;
        System.out.println("⚙️  Fase forzada a: " + nuevaFase);
    }
}
