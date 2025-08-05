package com.miempresa.mijuego;

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

    // Audio
    private Music musicaFondo;
    private Sound sonidoBoton;

    // Para saber desde dónde se accedió
    private boolean vieneDeJuego;

    // Volumen del juego (0-100)
    private int volumenActual = 70;

    // Cursores
    private Cursor cursorEspada;
    private Cursor cursorBrujula;
    private Cursor cursorNormal;
    private String cursorSeleccionado = "normal"; // "espada", "brujula", "normal"

    // Botones invisibles (coordenadas basadas en tu imagen)
    private Rectangle areaBarraSonido;     // Área completa de la barra
    private Rectangle botonEspada;         // Botón cursor espada
    private Rectangle botonBrujula;        // Botón cursor brújula
    private Rectangle botonVolver;         // Botón volver

    // Para convertir coordenadas
    private Vector3 posicionToque;
    private Vector2 posicionMundo;

    // Control de arrastre de la barra
    private boolean arrastandoBarra = false;

    // DEBUG: Shape renderer para visualizar botones
    private ShapeRenderer shapeRenderer;


    public PantallaAjustes(MiJuegoPrincipal juego, boolean vieneDeJuego) {
        this.juego = juego;
        this.vieneDeJuego = vieneDeJuego;
        this.posicionToque = new Vector3();
        this.posicionMundo = new Vector2();

        // Cargar la textura de ajustes
        texturaAjustes = new Texture("pantalla_ajustes.png");

        // Cargar audio
        cargarAudio();

        // Crear cursores personalizados
        crearCursores();

        // Definir botones transparentes
        inicializarBotones();

        // DEBUG: Inicializar shape renderer
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
            // Cursor espada
            Pixmap pixmapEspada = new Pixmap(Gdx.files.internal("cursor_espada.png"));
            cursorEspada = Gdx.graphics.newCursor(pixmapEspada, 0, 0);
            pixmapEspada.dispose();

            // Cursor brújula
            Pixmap pixmapBrujula = new Pixmap(Gdx.files.internal("cursor_brujula.png"));
            cursorBrujula = Gdx.graphics.newCursor(pixmapBrujula, 0, 0);
            pixmapBrujula.dispose();

            // Cursor normal se mantiene como null para usar el cursor por defecto del sistema
            cursorNormal = null;

        } catch (Exception e) {
            System.out.println("No se pudieron cargar los cursores personalizados: " + e.getMessage());
            cursorEspada = null;
            cursorBrujula = null;
            cursorNormal = null;
        }
    }

    private void inicializarBotones() {
        // Coordenadas ajustadas según la imagen que proporcionaste

        // Área de la barra de sonido (barra horizontal naranja)
        areaBarraSonido = new Rectangle(182, 549, 560, 43);

        // Botón cursor espada (cuadrado izquierdo con espada)
        botonEspada = new Rectangle(135, 250, 300, 200);

        // Botón cursor brújula (cuadrado derecho con brújula)
        botonBrujula = new Rectangle(509, 250, 300, 200);

        // Botón volver (botón inferior izquierdo)
        botonVolver = new Rectangle(121, 86, 540, 120);
    }

    @Override
    public void show() {
        if (musicaFondo != null && !vieneDeJuego) {
            // Solo reproducir música si venimos del menú principal
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
        // ESC para salir rápido
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            salirDeAjustes();
        }

        // Manejar arrastre de la barra de sonido
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

        // Detectar clics en botones
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
        // Calcular porcentaje basado en la posición X dentro de la barra
        float porcentaje = (posicionX - areaBarraSonido.x) / areaBarraSonido.width;
        porcentaje = Math.max(0, Math.min(1, porcentaje)); // Clamp entre 0 y 1

        volumenActual = (int)(porcentaje * 100);

        // Aplicar el volumen a la música actual
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
                        Gdx.graphics.setCursor(null); // Cursor por defecto del sistema
                    }
                    break;
                case "brujula":
                    if (cursorBrujula != null) {
                        Gdx.graphics.setCursor(cursorBrujula);
                        System.out.println("Cursor brújula aplicado");
                    } else {
                        System.out.println("Warning: Cursor brújula es null, usando cursor por defecto");
                        Gdx.graphics.setCursor(null); // Cursor por defecto del sistema
                    }
                    break;
                default:
                    // Para cursor "normal" o cualquier otro caso, usar cursor por defecto del sistema
                    Gdx.graphics.setCursor(null);
                    System.out.println("Cursor por defecto aplicado");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error al aplicar cursor: " + e.getMessage());
            // En caso de error, usar cursor por defecto
            try {
                Gdx.graphics.setCursor(null);
            } catch (Exception fallbackError) {
                System.out.println("Error crítico al aplicar cursor por defecto: " + fallbackError.getMessage());
            }
        }
    }

    // DEBUG: Dibujar rectángulos semitransparentes sobre los botones
    private void dibujarBotonesDebug() {
        shapeRenderer.setProjectionMatrix(juego.camara.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Botón espada - Rojo
        shapeRenderer.setColor(1, 0, 0, 0.3f); // Rojo semitransparente
        shapeRenderer.rect(botonEspada.x, botonEspada.y, botonEspada.width, botonEspada.height);

        // Botón brújula - Verde
        shapeRenderer.setColor(0, 1, 0, 0.3f); // Verde semitransparente
        shapeRenderer.rect(botonBrujula.x, botonBrujula.y, botonBrujula.width, botonBrujula.height);

        // Barra de sonido - Azul
        shapeRenderer.setColor(0, 0, 1, 0.3f); // Azul semitransparente
        shapeRenderer.rect(areaBarraSonido.x, areaBarraSonido.y, areaBarraSonido.width, areaBarraSonido.height);

        // Botón volver - Amarillo
        shapeRenderer.setColor(1, 1, 0, 0.3f); // Amarillo semitransparente
        shapeRenderer.rect(botonVolver.x, botonVolver.y, botonVolver.width, botonVolver.height);

        shapeRenderer.end();

        // Dibujar bordes para mejor visibilidad
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Bordes más gruesos
        Gdx.gl.glLineWidth(3);

        // Botón espada - Rojo
        shapeRenderer.setColor(1, 0, 0, 0.8f);
        shapeRenderer.rect(botonEspada.x, botonEspada.y, botonEspada.width, botonEspada.height);

        // Botón brújula - Verde
        shapeRenderer.setColor(0, 1, 0, 0.8f);
        shapeRenderer.rect(botonBrujula.x, botonBrujula.y, botonBrujula.width, botonBrujula.height);

        // Barra de sonido - Azul
        shapeRenderer.setColor(0, 0, 1, 0.8f);
        shapeRenderer.rect(areaBarraSonido.x, areaBarraSonido.y, areaBarraSonido.width, areaBarraSonido.height);

        // Botón volver - Amarillo
        shapeRenderer.setColor(1, 1, 0, 0.8f);
        shapeRenderer.rect(botonVolver.x, botonVolver.y, botonVolver.width, botonVolver.height);

        shapeRenderer.end();

        // Restaurar grosor de línea
        Gdx.gl.glLineWidth(1);
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
            // Volver al juego
            System.out.println("Volviendo al juego...");
            juego.setScreen(new PantallaJuego(juego));
        } else {
            // Volver al menú principal
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

    // Getter para que otras pantallas puedan acceder al volumen configurado
    public static int getVolumenJuego() {
        // En una implementación real, esto se guardaría en preferencias
        return 70; // Valor por defecto
    }

    // Getter para el cursor seleccionado
    public static String getCursorSeleccionado() {
        // En una implementación real, esto se guardaría en preferencias
        return "normal"; // Valor por defecto
    }

    @Override
    public void resize(int ancho, int alto) {
        juego.vistaVentana.update(ancho, alto);
    }

    @Override
    public void pause() {
        // Se ejecuta cuando el juego se pausa
    }

    @Override
    public void resume() {
        // Se ejecuta cuando el juego se reanuda
    }

    @Override
    public void hide() {
        if (musicaFondo != null) {
            musicaFondo.pause();
        }
    }

    @Override
    public void dispose() {
        // Liberar recursos
        if (texturaAjustes != null) {
            texturaAjustes.dispose();
        }
        if (musicaFondo != null) {
            musicaFondo.dispose();
        }
        if (sonidoBoton != null) {
            sonidoBoton.dispose();
        }
        // Dispose del shape renderer
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }
}
