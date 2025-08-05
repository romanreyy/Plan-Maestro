package com.miempresa.mijuego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class PantallaJuego implements Screen {
    private MiJuegoPrincipal juego;
    private Texture texturaJuego;
    private Jugador jugadorActual;

    // Para manejo de input
    private Vector3 posicionToque;
    private Vector2 posicionMundo;

    // Botones invisibles de la interfaz superior
    private Circle botonTimon;         // Botón de ajustes (timón) - CÍRCULO
    private Rectangle botonObjetivo;   // Botón de objetivo - RECTÁNGULO
    private Rectangle botonDerecha;    // Rectángulo a la derecha - RECTÁNGULO
    private Circle botonCirculo;       // Círculo a la derecha del todo - CÍRCULO

    public PantallaJuego(MiJuegoPrincipal juego) {
        this(juego, new Jugador("Jugador por defecto")); // Usar constructor con parámetros
    }

    public PantallaJuego(MiJuegoPrincipal juego, Jugador jugador) {
        this.juego = juego;
        this.jugadorActual = jugador; // NUEVO
        this.posicionToque = new Vector3();
        this.posicionMundo = new Vector2();

        // Cargar la textura del juego
        texturaJuego = new Texture("pantalla_juego.png");

        // Inicializar botones de la interfaz
        inicializarBotonesInterfaz();

        // NUEVO: Mostrar información del jugador y personaje
        if (jugadorActual != null && jugadorActual.tienePersonaje()) {
            System.out.println("=== INICIANDO PARTIDA ===");
            System.out.println(jugadorActual.getInformacionJugador());
            System.out.println("Personaje: " + jugadorActual.getPersonajeSeleccionado().getNombre());
            System.out.println("Habilidad: " + jugadorActual.getPersonajeSeleccionado().getHabilidad());
            System.out.println("========================");
        }
    }

    private void inicializarBotonesInterfaz() {
        // Coordenadas exactas de los botones

        // Botón Timón (izquierda) - Ajustes - CÍRCULO
        botonTimon = new Circle(119, 1008, 53);

        // Botón Objetivo (centro) - RECTÁNGULO
        botonObjetivo = new Rectangle(855, 955, 210, 105);

        // Botón rectángulo derecha - RECTÁNGULO
        botonDerecha = new Rectangle(1159, 962, 456, 93);

        // Botón círculo (derecha del todo) - CÍRCULO
        botonCirculo = new Circle(1810, 1008, 53);
    }

    @Override
    public void show() {
        // Se ejecuta cuando se muestra esta pantalla
    }

    @Override
    public void render(float delta) {
        // Limpiar pantalla
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Actualizar viewport y cámara
        juego.vistaVentana.apply();
        juego.loteSprites.setProjectionMatrix(juego.camara.combined);

        // Manejar input
        manejarInput();

        // Dibujar el sprite del juego
        juego.loteSprites.begin();
        juego.loteSprites.draw(texturaJuego, 0, 0, MiJuegoPrincipal.ANCHO_VIRTUAL, MiJuegoPrincipal.ALTO_VIRTUAL);
        juego.loteSprites.end();
    }

    private void manejarInput() {
        // Presiona ESCAPE para volver al menú principal
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            juego.setScreen(new MenuPrincipal(juego));
        }

        // Detectar clics en botones
        if (Gdx.input.justTouched()) {
            posicionToque.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            juego.vistaVentana.unproject(posicionToque);
            posicionMundo.set(posicionToque.x, posicionToque.y);

            // Verificar qué botón fue presionado
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
        System.out.println("Abriendo ajustes desde juego...");
        // Cambiar a ajustes indicando que viene del juego (true = viene del juego)
        juego.setScreen(new PantallaAjustes(juego, true));
    }

    private void alPresionarObjetivo() {
        System.out.println("Mostrando objetivos...");
        // Aquí podrías mostrar una ventana emergente con los objetivos
        // o cambiar a una pantalla de objetivos
    }

    private void alPresionarBotonDerecha() {
        System.out.println("Botón derecha presionado...");
        // Implementar la funcionalidad que necesites
    }

    private void alPresionarCirculo() {
        System.out.println("Círculo presionado...");
        // Implementar la funcionalidad que necesites
    }

    @Override
    public void resize(int ancho, int alto) {
        // Actualizar viewport cuando se redimensiona la ventana
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
        // Se ejecuta cuando esta pantalla se oculta
    }

    @Override
    public void dispose() {
        // Liberar recursos
        texturaJuego.dispose();
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }
}
