package com.miempresa.mijuego.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.miempresa.mijuego.paises.Pais;
import com.miempresa.mijuego.personajes.*;

public class PantallaSeleccionPersonaje implements Screen {
    private MiJuegoPrincipal juego;
    private Texture texturaSeleccion;
    private ShapeRenderer shapeRenderer;
    private Music musicaPersonajeActual;


    private Jugador jugador;
    private Personaje[] personajesDisponibles;
    private int personajeSeleccionado = -1;

    private Rectangle[] botonesPersonajes;
    private Rectangle botonAceptar;

    private Vector3 posicionToque;
    private Vector2 posicionMundo;

    public PantallaSeleccionPersonaje(MiJuegoPrincipal juego) {
        this.juego = juego;
        this.posicionToque = new Vector3();
        this.posicionMundo = new Vector2();
        this.jugador = new Jugador("Jugador 1");

        texturaSeleccion = new Texture("seleccion_personaje.png");
        shapeRenderer = new ShapeRenderer();
        inicializarPersonajes();
        inicializarBotones();
    }

    private void inicializarPersonajes() {
        personajesDisponibles = new Personaje[5];
        personajesDisponibles[0] = new MarineroPapa();
        personajesDisponibles[1] = new PibePiola();
        personajesDisponibles[2] = new Villero();
        personajesDisponibles[3] = new MentirosoRey();
        personajesDisponibles[4] = new RatonDelGrupo();
    }

    private void inicializarBotones() {
        botonesPersonajes = new Rectangle[5];

        botonesPersonajes[0] = new Rectangle(480, 510, 321, 330);  // Marinero Papá
        botonesPersonajes[1] = new Rectangle(840, 510, 321, 330);  // Pibe Piola
        botonesPersonajes[2] = new Rectangle(1200, 510, 325, 330); // Villero
        botonesPersonajes[3] = new Rectangle(480, 150, 321, 330);  // Mentiroso Rey
        botonesPersonajes[4] = new Rectangle(840, 150, 321, 330);  // Ratón del Grupo

        botonAceptar = new Rectangle(668, 30, 425, 100);           // Botón Aceptar
    }


    @Override
    public void show() {
        System.out.println("Selecciona tu personaje para Plan Maestro");
        personajeSeleccionado = 0;
        mostrarInformacionPersonaje();
    }

    private float tiempoLuz = 0;

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        tiempoLuz += delta;

        juego.vistaVentana.apply();
        juego.loteSprites.setProjectionMatrix(juego.camara.combined);

        manejarInput();

        juego.loteSprites.begin();
        juego.loteSprites.draw(texturaSeleccion, 0, 0,
            MiJuegoPrincipal.ANCHO_VIRTUAL,
            MiJuegoPrincipal.ALTO_VIRTUAL);
        juego.loteSprites.end();

        if (personajeSeleccionado != -1) {
            shapeRenderer.setProjectionMatrix(juego.camara.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            float alpha = (float) (0.5f + 0.5f * Math.sin(tiempoLuz * 4)); // parpadeo
            shapeRenderer.setColor(1f, 1f, 0f, alpha); // amarillo brillante

            Rectangle seleccionado = botonesPersonajes[personajeSeleccionado];
            shapeRenderer.rect(seleccionado.x, seleccionado.y, seleccionado.width, seleccionado.height);

            shapeRenderer.end();
        }
    }

    private void manejarInput() {
        if (Gdx.input.justTouched()) {
            posicionToque.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            juego.vistaVentana.unproject(posicionToque);
            posicionMundo.set(posicionToque.x, posicionToque.y);


            for (int i = 0; i < botonesPersonajes.length; i++) {
                if (botonesPersonajes[i].contains(posicionMundo.x, posicionMundo.y)) {
                    seleccionarPersonaje(i);
                    break;
                }
            }

            if (botonAceptar.contains(posicionMundo.x, posicionMundo.y)) {
                confirmarSeleccion();
            }
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            volverAlMenu();
        }
    }

    private void seleccionarPersonaje(int indice) {
        if (indice >= 0 && indice < personajesDisponibles.length) {
            personajeSeleccionado = indice;
            mostrarInformacionPersonaje();
            reproducirMusicaPorPersonaje(indice);
        }
    }

    private void reproducirMusicaPorPersonaje(int indice) {
        if (musicaPersonajeActual != null) {
            musicaPersonajeActual.stop();
            musicaPersonajeActual.dispose();
        }

        String rutaMusica = "";

        switch (indice) {
            case 0:
                rutaMusica = "musica_marinero.ogg";
                break;
            case 1:
                rutaMusica = "musica_pibepiola.ogg";
                break;
            case 2:
                rutaMusica = "musica_villero.ogg";
                break;
            case 3:
                rutaMusica = "musica_mentiroso.ogg";
                break;
            case 4:
                rutaMusica = "musica_raton.ogg";
                break;
        }

        musicaPersonajeActual = Gdx.audio.newMusic(Gdx.files.internal(rutaMusica));
        musicaPersonajeActual.setLooping(true);
        musicaPersonajeActual.setVolume(0.5f);
        musicaPersonajeActual.play();
    }

    private void mostrarInformacionPersonaje() {
        Personaje personaje = personajesDisponibles[personajeSeleccionado];
        System.out.println("=== PERSONAJE SELECCIONADO ===");
        System.out.println("Nombre: " + personaje.getNombre());
        System.out.println("Descripción: " + personaje.getDescripcion());
        System.out.println("Habilidad: " + personaje.getHabilidad());
        System.out.println("===============================");
    }

    private void confirmarSeleccion() {
        if (personajeSeleccionado == -1) return;

        if (musicaPersonajeActual != null) {
            musicaPersonajeActual.stop();
        }

        jugador.asignarPersonaje(personajesDisponibles[personajeSeleccionado]);
        Jugador.asignarPaisesAleatorios(jugador);
        Jugador.asignarObjetivoAleatorio(jugador);

        // Debug (opcional, para consola)
        System.out.println(jugador.getInformacionJugador());
        for (Pais p : jugador.getPaisesControlados()) {
            System.out.println(" - " + p.getNombre());
        }

        juego.setScreen(new PantallaJuego(juego, jugador));
    }

    private void volverAlMenu() {
        juego.setScreen(new MenuPrincipal(juego));
    }


    public Jugador getJugador() {
        return jugador;
    }

    @Override
    public void resize(int ancho, int alto) {
        juego.vistaVentana.update(ancho, alto);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        texturaSeleccion.dispose();
        shapeRenderer.dispose();
        if (musicaPersonajeActual != null) {
            musicaPersonajeActual.dispose();
        }
    }
}
