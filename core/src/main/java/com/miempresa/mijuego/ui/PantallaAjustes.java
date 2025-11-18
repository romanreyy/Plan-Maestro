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

    // 🔊 Música que se está reproduciendo (menú o juego)
    private Music musicaGlobal;

    private Sound sonidoBoton;

    // 🔙 Pantalla desde la que entré (MenuPrincipal, PantallaJuego, etc.)
    private Screen pantallaAnterior;

    // ================== VOLUMEN GLOBAL ==================
    // 0..100
    private static int volumenJuego = 70;
    private int volumenActual = 70;

    // ================== CURSORES GLOBALES ==================
    private static String cursorGlobalSeleccionado = "normal";

    private Cursor cursorEspada;
    private Cursor cursorBrujula;
    private Cursor cursorNormal;      // null = por defecto del SO
    private String cursorSeleccionado;

    // ================== UI ==================
    private Rectangle areaBarraSonido;
    private Rectangle botonEspada;
    private Rectangle botonBrujula;
    private Rectangle botonVolver;

    private Vector3 posicionToque;
    private Vector2 posicionMundo;
    private boolean arrastandoBarra = false;

    private ShapeRenderer shapeRenderer;

    // ================== CONSTRUCTOR ==================
    public PantallaAjustes(MiJuegoPrincipal juego, Screen pantallaAnterior, Music musicaGlobal) {
        this.juego = juego;
        this.pantallaAnterior = pantallaAnterior;
        this.musicaGlobal = musicaGlobal;

        this.posicionToque = new Vector3();
        this.posicionMundo = new Vector2();

        texturaAjustes = new Texture("pantalla_ajustes.png");

        cargarSonidos();
        crearCursores();
        inicializarBotones();

        shapeRenderer = new ShapeRenderer();

        // Sincronizar con valores globales
        this.volumenActual = volumenJuego;
        this.cursorSeleccionado = cursorGlobalSeleccionado;

        // Aseguramos que la música que ya está sonando use el volumen global actual
        if (musicaGlobal != null) {
            musicaGlobal.setVolume(getVolumenJuego());
        }
    }

    // ================== AUDIO ==================
    private void cargarSonidos() {
        try {
            sonidoBoton = Gdx.audio.newSound(Gdx.files.internal("sonido_boton.mp3"));
        } catch (Exception e) {
            System.out.println("No se pudo cargar el sonido del botón: " + e.getMessage());
        }
    }

    // ================== CURSORES ==================
    private void crearCursores() {
        try {
            Pixmap pixmapEspada = new Pixmap(Gdx.files.internal("cursor_espada.png"));
            cursorEspada = Gdx.graphics.newCursor(pixmapEspada, 0, 0);
            pixmapEspada.dispose();

            Pixmap pixmapBrujula = new Pixmap(Gdx.files.internal("cursor_brujula.png"));
            cursorBrujula = Gdx.graphics.newCursor(pixmapBrujula, 0, 0);
            pixmapBrujula.dispose();

            cursorNormal = null; // usa cursor default
        } catch (Exception e) {
            System.out.println("No se pudieron cargar los cursores personalizados: " + e.getMessage());
            cursorEspada = null;
            cursorBrujula = null;
            cursorNormal = null;
        }
    }

    // ================== BOTONES / AREAS ==================
    private void inicializarBotones() {
        // Ajustá estos números a tu PNG (son para la imagen que mandaste)
        areaBarraSonido = new Rectangle(182, 549, 560, 43);
        botonEspada     = new Rectangle(135, 250, 300, 200);
        botonBrujula    = new Rectangle(509, 250, 300, 200);
        botonVolver     = new Rectangle(121, 86, 540, 120);
    }

    // ================== CICLO SCREEN ==================
    @Override
    public void show() {
        aplicarCursor();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        juego.vistaVentana.apply();
        juego.loteSprites.setProjectionMatrix(juego.camara.combined);

        manejarInput();

        // Fondo ajustes
        juego.loteSprites.begin();
        juego.loteSprites.draw(texturaAjustes, 0, 0,
            MiJuegoPrincipal.ANCHO_VIRTUAL,
            MiJuegoPrincipal.ALTO_VIRTUAL);
        juego.loteSprites.end();

        // Barra de sonido encima
        dibujarBarraSonido();
    }

    // ================== INPUT ==================
    private void manejarInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            salirDeAjustes();
        }

        // Arrastre en la barra
        if (Gdx.input.isTouched()) {
            posicionToque.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            juego.vistaVentana.unproject(posicionToque);
            posicionMundo.set(posicionToque.x, posicionToque.y);

            if (areaBarraSonido.contains(posicionMundo.x, posicionMundo.y) || arrastandoBarra) {
                arrastandoBarra = true;
                actualizarVolumen(posicionMundo.x);
            }
        } else {
            arrastandoBarra = false;
        }

        // Clicks
        if (Gdx.input.justTouched()) {
            posicionToque.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            juego.vistaVentana.unproject(posicionToque);
            posicionMundo.set(posicionToque.x, posicionToque.y);

            if (areaBarraSonido.contains(posicionMundo.x, posicionMundo.y)) {
                actualizarVolumen(posicionMundo.x);
            } else if (botonEspada.contains(posicionMundo.x, posicionMundo.y)) {
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
        porcentaje = Math.max(0f, Math.min(1f, porcentaje)); // clamp 0..1

        volumenActual = (int) (porcentaje * 100);
        volumenJuego  = volumenActual;                       // 🔁 guardamos global

        if (musicaGlobal != null) {
            musicaGlobal.setVolume(getVolumenJuego());       // 🔊 baja/sube la música que ya suena
        }

        System.out.println("Volumen ajustado a: " + volumenActual + "%");
    }

    private void alSeleccionarEspada() {
        reproducirSonidoBoton();
        cursorSeleccionado = "espada";
        cursorGlobalSeleccionado = cursorSeleccionado;
        aplicarCursor();
    }

    private void alSeleccionarBrujula() {
        reproducirSonidoBoton();
        cursorSeleccionado = "brujula";
        cursorGlobalSeleccionado = cursorSeleccionado;
        aplicarCursor();
    }

    private void aplicarCursor() {
        try {
            switch (cursorSeleccionado) {
                case "espada":
                    if (cursorEspada != null) {
                        Gdx.graphics.setCursor(cursorEspada);
                    } else {
                        Gdx.graphics.setCursor(null);
                    }
                    break;
                case "brujula":
                    if (cursorBrujula != null) {
                        Gdx.graphics.setCursor(cursorBrujula);
                    } else {
                        Gdx.graphics.setCursor(null);
                    }
                    break;
                default:
                    Gdx.graphics.setCursor(cursorNormal);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error al aplicar cursor: " + e.getMessage());
            try { Gdx.graphics.setCursor(null); } catch (Exception ignored) {}
        }
    }

    private void alPresionarVolver() {
        reproducirSonidoBoton();
        salirDeAjustes();
    }

    private void salirDeAjustes() {
        if (pantallaAnterior != null) {
            juego.setScreen(pantallaAnterior);  // ✅ vuelve a la MISMA instancia (menu o juego)
        } else {
            juego.setScreen(new MenuPrincipal(juego));
        }
    }

    private void reproducirSonidoBoton() {
        if (sonidoBoton != null) {
            sonidoBoton.play(getVolumenJuego());
        }
    }

    // ================== DIBUJO BARRA ==================
    private void dibujarBarraSonido() {
        if (shapeRenderer == null || areaBarraSonido == null) return;

        float porcentaje = volumenActual / 100f;

        float xBar = areaBarraSonido.x;
        float yBar = areaBarraSonido.y;
        float wBar = areaBarraSonido.width;
        float hBar = areaBarraSonido.height;

        float knobWidth = 60f;
        float knobX = xBar + porcentaje * (wBar - knobWidth);
        float knobY = yBar;

        shapeRenderer.setProjectionMatrix(juego.camara.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // barra
        shapeRenderer.setColor(0.6f, 0.45f, 0.15f, 1f); // dorado oscuro
        shapeRenderer.rect(xBar, yBar + hBar * 0.3f, wBar, hBar * 0.4f);
        // knob
        shapeRenderer.setColor(0.9f, 0.7f, 0.3f, 1f);  // dorado claro
        shapeRenderer.rect(knobX, knobY, knobWidth, hBar);
        shapeRenderer.end();
    }

    // ================== GETTERS GLOBALES ==================
    /** Volumen global 0..1 para usar en Music/Sound. */
    public static float getVolumenJuego() {
        return volumenJuego / 100f;
    }

    /** Cursor global ("normal", "espada" o "brujula"). */
    public static String getCursorSeleccionado() {
        return cursorGlobalSeleccionado;
    }

    // ================== MÉTODOS Screen ==================
    @Override
    public void resize(int ancho, int alto) {
        juego.vistaVentana.update(ancho, alto);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        if (texturaAjustes != null) texturaAjustes.dispose();
        if (sonidoBoton != null) sonidoBoton.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
    }
}
