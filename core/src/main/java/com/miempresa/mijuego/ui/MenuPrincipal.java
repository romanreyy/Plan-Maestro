package com.miempresa.mijuego.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuPrincipal implements Screen {
    private MiJuegoPrincipal juego;
    private Texture texturaMenu;

    private Music musicaFondo;
    private Sound sonidoBoton;

    private Rectangle botonJugar;
    private Rectangle botonAjustes;
    private Rectangle botonSalir;

    private Vector3 posicionToque;
    private Vector2 posicionMundo;

    public MenuPrincipal(MiJuegoPrincipal juego) {
        this.juego = juego;
        this.posicionToque = new Vector3();
        this.posicionMundo = new Vector2();

        texturaMenu = new Texture("menu_principal.png");

        cargarAudio();

        botonJugar   = new Rectangle(669, 436, 1118, 182); // JUGAR
        botonAjustes = new Rectangle(669, 219, 1118, 182); // AJUSTES
        botonSalir   = new Rectangle(938,  83,  548, 111); // SALIR
    }

    private void cargarAudio() {
        try {
            musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("musica_menu_principal.ogg"));
            // usamos el volumen global que maneja PantallaAjustes
            musicaFondo.setVolume(PantallaAjustes.getVolumenJuego());
            musicaFondo.setLooping(true);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la música. Verifica que esté en assets/");
            System.out.println("Archivo necesario: musica_menu_principal.ogg");
        }

        try {
            sonidoBoton = Gdx.audio.newSound(Gdx.files.internal("sonido_boton.mp3"));
        } catch (Exception e) {
            System.out.println("No se pudo cargar sonido_boton.mp3");
        }
    }

    @Override
    public void show() {
        if (musicaFondo != null) {
            musicaFondo.setVolume(PantallaAjustes.getVolumenJuego()); // por si cambió
            musicaFondo.play();
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        juego.vistaVentana.apply();
        juego.loteSprites.setProjectionMatrix(juego.camara.combined);

        manejarInput();

        juego.loteSprites.begin();
        juego.loteSprites.draw(texturaMenu, 0, 0,
            MiJuegoPrincipal.ANCHO_VIRTUAL,
            MiJuegoPrincipal.ALTO_VIRTUAL);
        juego.loteSprites.end();
    }

    private void manejarInput() {
        if (Gdx.input.justTouched()) {
            posicionToque.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            juego.vistaVentana.unproject(posicionToque);
            posicionMundo.set(posicionToque.x, posicionToque.y);

            if (botonJugar.contains(posicionMundo.x, posicionMundo.y)) {
                alPresionarJugar();
            } else if (botonAjustes.contains(posicionMundo.x, posicionMundo.y)) {
                alPresionarAjustes();
            } else if (botonSalir.contains(posicionMundo.x, posicionMundo.y)) {
                alPresionarSalir();
            }
        }
    }

    private void alPresionarJugar() {
        reproducirSonidoBoton();
        System.out.println("Iniciando selección de personaje...");
        if (musicaFondo != null) {
            musicaFondo.stop();
        }
        juego.setScreen(new PantallaSeleccionPersonaje(juego));
    }

    private void alPresionarAjustes() {
        reproducirSonidoBoton();
        System.out.println("Abriendo ajustes desde menú...");

        // ⚠️ IMPORTANTE: NO parar la música.
        // Se la pasamos a PantallaAjustes para que ajuste su volumen.
        juego.setScreen(new PantallaAjustes(juego, this, musicaFondo));
    }

    private void alPresionarSalir() {
        reproducirSonidoBoton();
        System.out.println("Saliendo del juego...");
        Gdx.app.exit();
    }

    private void reproducirSonidoBoton() {
        if (sonidoBoton != null) {
            sonidoBoton.play(PantallaAjustes.getVolumenJuego()); // mismo volumen global
        }
    }

    @Override
    public void resize(int ancho, int alto) {
        juego.vistaVentana.update(ancho, alto);
    }

    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void hide() {
        // solo pausamos, no seteamos volume acá
        if (musicaFondo != null) {
            musicaFondo.pause();
        }
    }

    @Override
    public void dispose() {
        texturaMenu.dispose();
        if (musicaFondo != null) {
            musicaFondo.dispose();
        }
        if (sonidoBoton != null) {
            sonidoBoton.dispose();
        }
    }
}
