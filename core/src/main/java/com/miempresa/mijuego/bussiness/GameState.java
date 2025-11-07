package com.miempresa.mijuego.bussiness;

import com.miempresa.mijuego.model.enums.FaseJuegoEnum;
import com.miempresa.mijuego.model.dto.Objetivo;
import com.miempresa.mijuego.model.dto.Jugador;
import com.miempresa.mijuego.model.dto.Pais;

import java.util.*;

public class GameState {

    private final List<Jugador> jugadores;
    private int turno;
    private FaseJuegoEnum fase = FaseJuegoEnum.COLOCACION_INICIAL;

    // 🔁 Rondas
    private int numeroRonda = 1;

    private static final int TROPAS_INICIALES_POR_JUGADOR = 8;
    private int colocacionesPendientesDelTurno = TROPAS_INICIALES_POR_JUGADOR;
    private int jugadoresQueYaColocaron = 0;

    private int jugadoresQueYaAtacaron = 0;
    private boolean rondaCompletada = false;

    private final Map<Jugador, Integer> refuerzosPendientes = new HashMap<>();

    private Jugador ganador = null;

    public GameState(List<Jugador> jugadores, int indiceQueEmpieza) {
        if (jugadores == null || jugadores.size() != 3) {
            throw new IllegalArgumentException("Se requieren exactamente 3 jugadores.");
        }
        for (Jugador j : jugadores) Objects.requireNonNull(j, "Jugador nulo en la lista de jugadores");

        this.jugadores = jugadores;
        this.turno = Math.floorMod(indiceQueEmpieza, jugadores.size());
        for (Jugador j : jugadores) refuerzosPendientes.put(j, 0);

        // ⏱️ Inicializa la primera disponibilidad de habilidades según la ronda actual
        inicializarCooldownHabilidadesParaTodos();
    }

    // --- Getters ---
    public FaseJuegoEnum getFase() { return fase; }
    public Jugador getJugadorActual() { return jugadores.get(turno); }
    public int getIndiceTurno() { return turno; }
    public int getColocacionesPendientesDelTurno() { return colocacionesPendientesDelTurno; }
    public int getJugadoresQueYaColocaron() { return jugadoresQueYaColocaron; }
    public int getTropasInicialesPorJugador() { return TROPAS_INICIALES_POR_JUGADOR; }
    public int getRefuerzosPendientesDelTurno() { return refuerzosPendientes.getOrDefault(getJugadorActual(), 0); }
    public Jugador getGanador() { return ganador; }
    public boolean hayGanador() { return ganador != null; }
    public List<Jugador> getJugadores() { return jugadores; }
    public int getNumeroRonda() { return numeroRonda; }

    // =====================================================
    // ===============  COLOCACIÓN INICIAL  ================
    // =====================================================

    public boolean colocarTropaInicial(Pais pais) {
        if (fase != FaseJuegoEnum.COLOCACION_INICIAL) return false;
        if (pais == null) return false;
        if (pais.getPropietario() != getJugadorActual()) return false;
        if (colocacionesPendientesDelTurno <= 0) return false;

        pais.agregarTropas(1);
        colocacionesPendientesDelTurno--;
        if (colocacionesPendientesDelTurno == 0) finalizarTurnoDeColocacion();
        return true;
    }

    public void finalizarTurnoDeColocacion() {
        if (fase != FaseJuegoEnum.COLOCACION_INICIAL) return;

        jugadoresQueYaColocaron++;
        if (jugadoresQueYaColocaron >= jugadores.size()) {
            fase = FaseJuegoEnum.ATAQUE;
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
        if (fase != FaseJuegoEnum.ATAQUE) return "⚠️ No se puede atacar fuera de la fase ATAQUE.";
        if (origen == null || destino == null) return "⚠️ Error: país atacante o defensor nulo.";
        if (origen.getPropietario() != getJugadorActual()) return "⚠️ No podés atacar con un país que no te pertenece.";
        if (origen.getTropas() <= 1) return "⚠️ Necesitás al menos 2 tropas para atacar.";

        // 👇 Importante: NO validamos adyacencia acá. Eso lo resuelve Batalla usando la habilidad del personaje.
        Batalla batalla = new Batalla(this, origen, destino); // pasa GameState para usar numeroRonda y habilidades
        String resultado = batalla.ejecutarBatalla();

        evaluarVictoria(); // Verifica si el ataque generó victoria
        return resultado;
    }

    public void finalizarTurnoDeAtaque() {
        if (fase != FaseJuegoEnum.ATAQUE) return;

        jugadoresQueYaAtacaron++;

        if (jugadoresQueYaAtacaron >= jugadores.size()) {
            rondaCompletada = true;
            calcularRefuerzosDeTodos();
            fase = FaseJuegoEnum.AGRUPAR;
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
        if (fase != FaseJuegoEnum.AGRUPAR) return false;
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
        if (fase != FaseJuegoEnum.AGRUPAR) return;

        Jugador actual = getJugadorActual();
        refuerzosPendientes.put(actual, 0);

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
            // 🔁 Avanza la ronda cuando TODOS terminaron de agrupar
            avanzarRonda();

            fase = FaseJuegoEnum.ATAQUE;
            rondaCompletada = false;
            turno = 0;
            System.out.println("🔁 Todos agruparon → Nueva ronda de ATAQUE iniciada.");
        } else {
            turno = (turno + 1) % jugadores.size();
            System.out.println("➡️ Ahora agrupa: " + getJugadorActual().getNombre());
        }
    }
    private void inicializarCooldownHabilidadesParaTodos() {
        for (Jugador j : jugadores) {
            j.inicializarCooldownHabilidad(numeroRonda);
        }
    }

    private void avanzarRonda() {
        numeroRonda++;
        System.out.println("🔁 Nueva ronda: " + numeroRonda);
        // No hace falta tocar jugadores acá: cada uno calcula su próxima disponibilidad al consumir habilidad.
    }

    /** Devuelve un set con todos los países del mapa (de todos los jugadores). */
    public Set<Pais> getTodosLosPaises() {
        Set<Pais> todos = new HashSet<>();
        for (Jugador j : jugadores) {
            if (j.getPaisesControlados() != null) todos.addAll(j.getPaisesControlados());
        }
        return todos;
    }

    /** Devuelve cuántos países existen en cierto continente. */
    public int totalPaisesEnContinente(String continente) {
        int total = 0;
        for (Pais p : getTodosLosPaises()) {
            if (p.getContinente().equalsIgnoreCase(continente)) total++;
        }
        return total;
    }

    /** Devuelve cuántos países controla un jugador en cierto continente. */
    public int paisesDelJugadorEnContinente(Jugador j, String continente) {
        if (j == null || j.getPaisesControlados() == null) return 0;
        int cant = 0;
        for (Pais p : j.getPaisesControlados()) {
            if (p.getContinente().equalsIgnoreCase(continente)) cant++;
        }
        return cant;
    }


    private void evaluarVictoria() {
        if (ganador != null) return; // ya hay un ganador

        for (Jugador j : jugadores) {
            Objetivo obj = j.getObjetivoAsignado();
            if (obj != null && obj.estaCumplido(j, this)) {
                ganador = j;
                fase = FaseJuegoEnum.FIN_PARTIDA;
                System.out.println("🏆 ¡" + j.getNombre() + " cumplió su objetivo (" + obj.getNombre() + ")!");
                break;
            }
        }
    }

    public void forzarFase(FaseJuegoEnum nuevaFase) {
        this.fase = nuevaFase;
        System.out.println("⚙️  Fase forzada a: " + nuevaFase);
    }
}
