package com.miempresa.mijuego.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MiJuegoPrincipal extends Game {
    public SpriteBatch loteSprites;
    public OrthographicCamera camara;
    public Viewport vistaVentana;

    public static final float ANCHO_VIRTUAL = 1920f;
    public static final float ALTO_VIRTUAL = 1080f;

    @Override
    public void create() {
        loteSprites = new SpriteBatch();

        camara = new OrthographicCamera();
        vistaVentana = new FitViewport(ANCHO_VIRTUAL, ALTO_VIRTUAL, camara);

        camara.position.set(ANCHO_VIRTUAL / 2, ALTO_VIRTUAL / 2, 0);
        camara.update();

        setScreen(new MenuPrincipal(this));
    }

    @Override
    public void dispose() {
        loteSprites.dispose();
        super.dispose();
    }
}
