package com.miempresa.mijuego.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class PantallaAjustes implements Screen {
    private MiJuegoPrincipal juego;
    private Texture texturaAjustes;

    private Music musicaFondo;
    private Sound sonidoBoton;

    private boolean vieneDeJuego;

    private int volumenActual = 70;

    private Cursor cursorEspada;
    private Cursor cursorBrujula;
    private Cursor cursorNormal;
    private String cursorSeleccionado = "normal";

    private Rectangle areaBarraSonido;
    private Rectangle botonEspada;         // Botón cursor espada
    private Rectangle botonBrujula;        // Botón cursor brújula
    private Rectangle botonVolver;         // Botón volver

    private Vector3 posicionToque;
    private Vector2 posicionMundo;

    private boolean arrastandoBarra = false;

    private ShapeRenderer shapeRenderer;


    public PantallaAjustes(MiJuegoPrincipal juego, boolean vieneDeJuego) {
        this.juego = juego;
        this.vieneDeJuego = vieneDeJuego;
        this.posicionToque = new Vector3();
        this.posicionMundo = new Vector2();

        texturaAjustes = new Texture("pantalla_ajustes.png");

        cargarAudio();

        crearCursores();

        inicializarBotones();

        shapeRenderer = new ShapeRenderer();
    }

    private void cargarAudio() {
        try {
            musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("sonido_menu_principal.mp3"));
            musicaFondo.setVolume(0.5f);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el audio de ajustes");
        }
    }

    private void crearCursores() {
        try {
            Pixmap pixmapEspada = new Pixmap(Gdx.files.internal("cursor_espada.png"));
            cursorEspada = Gdx.graphics.newCursor(pixmapEspada, 0, 0);
            pixmapEspada.dispose();

            Pixmap pixmapBrujula = new Pixmap(Gdx.files.internal("cursor_brujula.png"));
            cursorBrujula = Gdx.graphics.newCursor(pixmapBrujula, 0, 0);
            pixmapBrujula.dispose();

            cursorNormal = null;

        } catch (Exception e) {
            System.out.println("No se pudieron cargar los cursores personalizados: " + e.getMessage());
            cursorEspada = null;
            cursorBrujula = null;
            cursorNormal = null;
        }
    }

    private void inicializarBotones() {

        areaBarraSonido = new Rectangle(182, 549, 560, 43);

        botonEspada = new Rectangle(135, 250, 300, 200);

        botonBrujula = new Rectangle(509, 250, 300, 200);

        botonVolver = new Rectangle(121, 86, 540, 120);
    }

    @Override
    public void show() {
        if (musicaFondo != null && !vieneDeJuego) {
            musicaFondo.play();
        }

        aplicarCursor();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        juego.vistaVentana.apply();
        juego.loteSprites.setProjectionMatrix(juego.camara.combined);

        manejarInput();

        juego.loteSprites.begin();
        juego.loteSprites.draw(texturaAjustes, 0, 0, MiJuegoPrincipal.ANCHO_VIRTUAL, MiJuegoPrincipal.ALTO_VIRTUAL);
        juego.loteSprites.end();

    }

    private void manejarInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            salirDeAjustes();
        }

        if (Gdx.input.isTouched()) {
            posicionToque.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            juego.vistaVentana.unproject(posicionToque);
            posicionMundo.set(posicionToque.x, posicionToque.y);

            if (areaBarraSonido.contains(posicionMundo.x, posicionMundo.y)) {
                arrastandoBarra = true;
                actualizarVolumen(posicionMundo.x);
            }
        } else {
            arrastandoBarra = false;
        }

        if (Gdx.input.justTouched()) {
            posicionToque.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            juego.vistaVentana.unproject(posicionToque);
            posicionMundo.set(posicionToque.x, posicionToque.y);

            if (botonEspada.contains(posicionMundo.x, posicionMundo.y)) {
                alSeleccionarEspada();
            } else if (botonBrujula.contains(posicionMundo.x, posicionMundo.y)) {
                alSeleccionarBrujula();
            } else if (botonVolver.contains(posicionMundo.x, posicionMundo.y)) {
                alPresionarVolver();
            }
        }
    }

    private void actualizarVolumen(float posicionX) {
        float porcentaje = (posicionX - areaBarraSonido.x) / areaBarraSonido.width;
        porcentaje = Math.max(0, Math.min(1, porcentaje)); // Clamp entre 0 y 1

        volumenActual = (int)(porcentaje * 100);

        if (musicaFondo != null && musicaFondo.isPlaying()) {
            musicaFondo.setVolume(porcentaje);
        }

        System.out.println("Volumen ajustado a: " + volumenActual + "%");
    }

    private void alSeleccionarEspada() {
        reproducirSonidoBoton();
        cursorSeleccionado = "espada";
        aplicarCursor();
        System.out.println("Cursor cambiado a: ESPADA");
    }

    private void alSeleccionarBrujula() {
        reproducirSonidoBoton();
        cursorSeleccionado = "brujula";
        aplicarCursor();
        System.out.println("Cursor cambiado a: BRÚJULA");
    }

    private void aplicarCursor() {
        try {
            switch (cursorSeleccionado) {
                case "espada":
                    if (cursorEspada != null) {
                        Gdx.graphics.setCursor(cursorEspada);
                        System.out.println("Cursor espada aplicado");
                    } else {
                        System.out.println("Warning: Cursor espada es null, usando cursor por defecto");
                        Gdx.graphics.setCursor(null);
                    }
                    break;
                case "brujula":
                    if (cursorBrujula != null) {
                        Gdx.graphics.setCursor(cursorBrujula);
                        System.out.println("Cursor brújula aplicado");
                    } else {
                        System.out.println("Warning: Cursor brújula es null, usando cursor por defecto");
                        Gdx.graphics.setCursor(null);
                    }
                    break;
                default:
                    Gdx.graphics.setCursor(null);
                    System.out.println("Cursor por defecto aplicado");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error al aplicar cursor: " + e.getMessage());
            try {
                Gdx.graphics.setCursor(null);
            } catch (Exception fallbackError) {
                System.out.println("Error crítico al aplicar cursor por defecto: " + fallbackError.getMessage());
            }
        }
    }

    private void alPresionarVolver() {
        reproducirSonidoBoton();
        salirDeAjustes();
    }

    private void salirDeAjustes() {
        // Parar música de ajustes si está sonando
        if (musicaFondo != null && musicaFondo.isPlaying()) {
            musicaFondo.stop();
        }

        if (vieneDeJuego) {
            System.out.println("Volviendo al juego...");
            juego.setScreen(new PantallaJuego(juego));
        } else {
            System.out.println("Volviendo al menú principal...");
            juego.setScreen(new MenuPrincipal(juego));
        }
    }

    private void reproducirSonidoBoton() {
        if (sonidoBoton != null) {
            float volumenEfecto = volumenActual / 100.0f;
            sonidoBoton.play(volumenEfecto);
        }
    }

    public static int getVolumenJuego() {
        return 70;
    }

    public static String getCursorSeleccionado() {
        return "normal";
    }

    @Override
    public void resize(int ancho, int alto) {
        juego.vistaVentana.update(ancho, alto);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        if (musicaFondo != null) {
            musicaFondo.pause();
        }
    }

    @Override
    public void dispose() {
        if (texturaAjustes != null) {
            texturaAjustes.dispose();
        }
        if (musicaFondo != null) {
            musicaFondo.dispose();
        }
        if (sonidoBoton != null) {
            sonidoBoton.dispose();
        }
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}
