package com.miempresa.mijuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.miempresa.mijuego.objetivos.Objetivo;
import com.miempresa.mijuego.paises.Pais;
import com.miempresa.mijuego.personajes.Jugador;
import com.miempresa.mijuego.personajes.Personaje;

import java.util.HashMap;

public class PantallaJuego implements Screen {
    private MiJuegoPrincipal juego;
    private Texture texturaJuego;
    private Texture texturaObjetivo;
    private Texture texturaPersonaje;
    private Jugador jugadorActual;
    private HashMap<String, Sprite> spritesPaises;

    private Vector3 posicionToque;
    private Vector2 posicionMundo;

    private boolean mostrandoObjetivo = false;
    private Rectangle areaObjetivo;

    private Circle botonTimon;
    private Rectangle botonObjetivo;
    private Rectangle botonDerecha;
    private Circle botonCirculo;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================
    public PantallaJuego(MiJuegoPrincipal juego) {
        this(juego, new Jugador("Jugador por defecto"));
    }

    public PantallaJuego(MiJuegoPrincipal juego, Jugador jugador) {
        this.juego = juego;
        this.jugadorActual = jugador;
        this.posicionToque = new Vector3();
        this.posicionMundo = new Vector2();

        texturaJuego = new Texture("pantalla_juego.png");
        texturaObjetivo = new Texture("sprite_objetivo.png");

        inicializarSpritesPaises();
        cargarTexturaPersonaje();
        inicializarBotonesInterfaz();
        inicializarAreaObjetivo();
        inicializarPartida();
    }

    // =====================================================
    // CONFIGURACIÓN INICIAL
    // =====================================================
    private void inicializarBotonesInterfaz() {
        botonTimon = new Circle(119, 1008, 53);
        botonObjetivo = new Rectangle(855, 955, 210, 105);
        botonDerecha = new Rectangle(1159, 962, 456, 93);
        botonCirculo = new Circle(1810, 1008, 53);
    }

    private void inicializarAreaObjetivo() {
        float anchoObjetivo = 600;
        float altoObjetivo = 400;
        float x = (MiJuegoPrincipal.ANCHO_VIRTUAL - anchoObjetivo) / 2;
        float y = (MiJuegoPrincipal.ALTO_VIRTUAL - altoObjetivo) / 2;

        areaObjetivo = new Rectangle(x, y, anchoObjetivo, altoObjetivo);
    }

    private void cargarTexturaPersonaje() {
        if (jugadorActual.tienePersonaje()) {
            String nombrePersonaje = jugadorActual.getPersonajeSeleccionado().getNombre();
            String archivoTextura = obtenerArchivoTexturaPersonaje(nombrePersonaje);

            try {
                texturaPersonaje = new Texture(archivoTextura);
                System.out.println("Cargada textura del personaje: " + archivoTextura);
            } catch (Exception e) {
                System.out.println("No se pudo cargar la textura: " + archivoTextura);
                texturaPersonaje = null;
            }
        } else {
            texturaPersonaje = null;
        }
    }

    private String obtenerArchivoTexturaPersonaje(String nombrePersonaje) {
        switch (nombrePersonaje) {
            case "El Marinero Papá":
                return "tropa_marinero.png";
            case "El Pibe Piola":
                return "tropa_pibe_piola.png";
            case "El Villero":
                return "tropa_villero.png";
            case "El Mentiroso Rey":
                return "tropa_mentiroso.png";
            case "El Ratón del Grupo":
                return "tropa_raton.png";
            default:
                return "tropa_marinero.png";
        }
    }

    private void inicializarPartida() {
        if (!jugadorActual.tieneObjetivo()) {
            Jugador.asignarObjetivoAleatorio(jugadorActual);
        }

        System.out.println("=== INICIANDO PARTIDA ===");
        System.out.println(jugadorActual.getInformacionJugador());
        if (jugadorActual.tienePersonaje()) {
            System.out.println("Habilidad: " + jugadorActual.getPersonajeSeleccionado().getHabilidad());
        }
        if (jugadorActual.tieneObjetivo()) {
            System.out.println("Descripción del objetivo: " + jugadorActual.getObjetivoAsignado().getDescripcion());
        }
        System.out.println("========================");
    }

    // =====================================================
    // SPRITES DE PAÍSES
    // =====================================================
    private void inicializarSpritesPaises() {
        spritesPaises = new HashMap<>();

        Sprite saavedra = new Sprite(new Texture("saavedra.png"));
        saavedra.setPosition(200, 654);
        spritesPaises.put("Saavedra", saavedra);

        Sprite palermo = new Sprite(new Texture("palermo.png"));
        palermo.setPosition(242, 868);
        spritesPaises.put("Palermo", palermo);

        Sprite belgrano = new Sprite(new Texture("belgrano.png"));
        belgrano.setPosition(67, 602);
        spritesPaises.put("Belgrano", belgrano);

        Sprite villa31 = new Sprite(new Texture("villa31.png"));
        villa31.setPosition(1752, 319);
        spritesPaises.put("Villa 31", villa31);

        Sprite lafraga = new Sprite(new Texture("lafraga.png"));
        lafraga.setPosition(1888, 390);
        spritesPaises.put("La Fraga", lafraga);
    }

    // =====================================================
    // ACTUALIZACIÓN DE COLORES
    // =====================================================
    public void actualizarColoresPaises() {
        if (spritesPaises == null || jugadorActual == null) return;

        for (String nombrePais : spritesPaises.keySet()) {
            Sprite sprite = spritesPaises.get(nombrePais);

            boolean controlado = false;
            com.badlogic.gdx.graphics.Color color = com.badlogic.gdx.graphics.Color.CLEAR;

            if (jugadorActual.getPaisesControlados() != null) {
                for (Pais p : jugadorActual.getPaisesControlados()) {
                    if (p.getNombre().equalsIgnoreCase(nombrePais)) {
                        controlado = true;
                        if (jugadorActual.tienePersonaje()) {
                            color = jugadorActual.getPersonajeSeleccionado().getColor();
                        }
                        break;
                    }
                }
            }

            if (controlado) {
                sprite.setColor(color.r, color.g, color.b, 0.45f);
            } else {
                sprite.setColor(1, 1, 1, 0f);
            }
        }
    }

    // =====================================================
    // RENDER
    // =====================================================
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        juego.vistaVentana.apply();
        juego.loteSprites.setProjectionMatrix(juego.camara.combined);
        manejarInput();

        actualizarColoresPaises();

        juego.loteSprites.begin();

        juego.loteSprites.draw(texturaJuego, 0, 0,
            MiJuegoPrincipal.ANCHO_VIRTUAL,
            MiJuegoPrincipal.ALTO_VIRTUAL);

        for (Sprite s : spritesPaises.values()) {
            s.draw(juego.loteSprites);
        }

        if (texturaPersonaje != null) {
            float xPersonaje = botonCirculo.x - botonCirculo.radius;
            float yPersonaje = botonCirculo.y - botonCirculo.radius;
            float diametro = botonCirculo.radius * 2;
            juego.loteSprites.draw(texturaPersonaje, xPersonaje, yPersonaje, diametro, diametro);
        }

        if (mostrandoObjetivo) {
            juego.loteSprites.draw(texturaObjetivo,
                areaObjetivo.x, areaObjetivo.y,
                areaObjetivo.width, areaObjetivo.height);
        }

        juego.loteSprites.end();
    }

    // =====================================================
    // INPUTS
    // =====================================================
    private void manejarInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            juego.setScreen(new MenuPrincipal(juego));
        }

        if (Gdx.input.justTouched()) {
            posicionToque.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            juego.vistaVentana.unproject(posicionToque);
            posicionMundo.set(posicionToque.x, posicionToque.y);

            if (mostrandoObjetivo) {
                if (!areaObjetivo.contains(posicionMundo.x, posicionMundo.y)) {
                    mostrandoObjetivo = false;
                    System.out.println("Cerrando pantalla de objetivo");
                }
                return;
            }

            if (botonTimon.contains(posicionMundo.x, posicionMundo.y)) {
                alPresionarTimon();
            } else if (botonObjetivo.contains(posicionMundo.x, posicionMundo.y)) {
                alPresionarObjetivo();
            } else if (botonDerecha.contains(posicionMundo.x, posicionMundo.y)) {
                alPresionarBotonDerecha();
            } else if (botonCirculo.contains(posicionMundo.x, posicionMundo.y)) {
                alPresionarCirculo();
            }
        }
    }

    private void alPresionarTimon() {
        juego.setScreen(new PantallaAjustes(juego, true));
    }

    private void alPresionarObjetivo() {
        mostrandoObjetivo = true;
        if (jugadorActual.tieneObjetivo()) {
            Objetivo objetivo = jugadorActual.getObjetivoAsignado();
            System.out.println("Objetivo: " + objetivo.getNombre());
            System.out.println("Descripción: " + objetivo.getDescripcion());
        }
    }

    private void alPresionarBotonDerecha() {
        System.out.println("Botón derecha presionado...");
    }

    private void alPresionarCirculo() {
        if (jugadorActual.tienePersonaje()) {
            Personaje personaje = jugadorActual.getPersonajeSeleccionado();
            System.out.println("Personaje: " + personaje.getNombre());
        }
    }

    // =====================================================
    // MÉTODOS OBLIGATORIOS DE SCREEN
    // =====================================================
    @Override
    public void show() {
        System.out.println("PantallaJuego mostrada");
    }

    @Override
    public void resize(int width, int height) {
        juego.vistaVentana.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        texturaJuego.dispose();
        texturaObjetivo.dispose();
        if (texturaPersonaje != null) texturaPersonaje.dispose();
        for (Sprite s : spritesPaises.values()) s.getTexture().dispose();
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }
}
