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

    private void inicializarSpritesPaises() {
        spritesPaises = new HashMap<>();

        Sprite villaLaCava = new Sprite(new Texture("villaLaCava.png"));
        villaLaCava.setPosition(0, 0);
        spritesPaises.put("Villa la Cava", villaLaCava);

        Sprite bajoFlores = new Sprite(new Texture("bajoFlores.png"));
        bajoFlores.setPosition(0, 0);
        spritesPaises.put("Bajo Flores", bajoFlores);

        Sprite pabloMugica = new Sprite(new Texture("pabloMugica.png"));
        pabloMugica.setPosition(0, 0);
        spritesPaises.put("Pablo Mugica", pabloMugica);

        Sprite laRodrigoBueno = new Sprite(new Texture("laRodrigoBueno.png"));
        laRodrigoBueno.setPosition(0, 0);
        spritesPaises.put("La Rodrigo Bueno", laRodrigoBueno);

        Sprite virreyDelPino = new Sprite(new Texture("virreyDelPino.png"));
        virreyDelPino.setPosition(0, 0);
        spritesPaises.put("Virrey del Pino", virreyDelPino);

        Sprite gonzalezCatan = new Sprite(new Texture("gonzalezCatan.png"));
        gonzalezCatan.setPosition(0, 0);
        spritesPaises.put("Gonzalez Catan", gonzalezCatan);

        Sprite isidroCasanova = new Sprite(new Texture("isidroCasanova.png"));
        isidroCasanova.setPosition(0, 0);
        spritesPaises.put("Isidro Casanova", isidroCasanova);

        Sprite laferrere = new Sprite(new Texture("laferrere.png"));
        laferrere.setPosition(0, 0);
        spritesPaises.put("Laferrere", laferrere);

        Sprite villaItati = new Sprite(new Texture("villaItati.png"));
        villaItati.setPosition(0, 0);
        spritesPaises.put("Villa Itati", villaItati);

        Sprite monteChingolo = new Sprite(new Texture("monteChingolo.png"));
        monteChingolo.setPosition(0, 0);
        spritesPaises.put("Monte Chingolo", monteChingolo);

        Sprite dockSud = new Sprite(new Texture("dockSud.png"));
        dockSud.setPosition(0, 0);
        spritesPaises.put("Dock Sud", dockSud);

        Sprite laTranquila = new Sprite(new Texture("laTranquila.png"));
        laTranquila.setPosition(0, 0);
        spritesPaises.put("La Tranquila", laTranquila);

        Sprite laRana = new Sprite(new Texture("laRana.png"));
        laRana.setPosition(0, 0);
        spritesPaises.put("La Rana", laRana);

        Sprite villaCarlosGardel = new Sprite(new Texture("villaCarlosGardel.png"));
        villaCarlosGardel.setPosition(0, 0);
        spritesPaises.put("Villa Carlos Gardel", villaCarlosGardel);

        Sprite fuerteApache = new Sprite(new Texture("fuerteApache.png"));
        fuerteApache.setPosition(0, 0);
        spritesPaises.put("Fuerte Apache", fuerteApache);

        Sprite cuartelV = new Sprite(new Texture("cuartelV.png"));
        cuartelV.setPosition(0, 0);
        spritesPaises.put("Cuartel V", cuartelV);

        Sprite joseCPaz = new Sprite(new Texture("joseCPaz.png"));
        joseCPaz.setPosition(0, 0);
        spritesPaises.put("Jose C. Paz", joseCPaz);

        Sprite lasTunas = new Sprite(new Texture("lasTunas.png"));
        lasTunas.setPosition(0, 0);
        spritesPaises.put("Las Tunas", lasTunas);

        Sprite laCatanga = new Sprite(new Texture("laCatanga.png"));
        laCatanga.setPosition(0, 0);
        spritesPaises.put("La Catanga", laCatanga);

        Sprite villa18 = new Sprite(new Texture("villa18.png"));
        villa18.setPosition(0, 0);
        spritesPaises.put("Villa 18", villa18);

        Sprite barrioPiolin = new Sprite(new Texture("barrioPiolin.png"));
        barrioPiolin.setPosition(0, 0);
        spritesPaises.put("Barrio Piolin", barrioPiolin);

        Sprite villaLanzone = new Sprite(new Texture("villaLanzone.png"));
        villaLanzone.setPosition(0, 0);
        spritesPaises.put("Villa Lanzone", villaLanzone);

        Sprite hidalgo = new Sprite(new Texture("hidalgo.png"));
        hidalgo.setPosition(0, 0);
        spritesPaises.put("Hidalgo", hidalgo);

        Sprite villa31 = new Sprite(new Texture("villa31.png"));
        villa31.setPosition(0, 0);
        spritesPaises.put("Villa 31", villa31);

        Sprite laCurita = new Sprite(new Texture("laCurita.png"));
        laCurita.setPosition(0, 0);
        spritesPaises.put("La Curita", laCurita);

        Sprite villaSoldati = new Sprite(new Texture("villaSoldati.png"));
        villaSoldati.setPosition(0, 0);
        spritesPaises.put("Villa Soldati", villaSoldati);

        Sprite nuevaPompeya = new Sprite(new Texture("nuevaPompeya.png"));
        nuevaPompeya.setPosition(0, 0);
        spritesPaises.put("Nueva Pompeya", nuevaPompeya);

        Sprite constitucion = new Sprite(new Texture("constitucion.png"));
        constitucion.setPosition(0, 0);
        spritesPaises.put("Constitucion", constitucion);

        Sprite once = new Sprite(new Texture("once.png"));
        once.setPosition(0, 0);
        spritesPaises.put("Once", once);

        Sprite donOrione = new Sprite(new Texture("donOrione.png"));
        donOrione.setPosition(0, 0);
        spritesPaises.put("Don Orione", donOrione);

        Sprite emebe04PuntaDeRielesNuevos = new Sprite(new Texture("emebe04PuntaDeRielesNuevos.png"));
        emebe04PuntaDeRielesNuevos.setPosition(0, 0);
        spritesPaises.put("Emebe 04 Punta de Rieles Nuevos", emebe04PuntaDeRielesNuevos);

        Sprite ciudadOculta = new Sprite(new Texture("ciudadOculta.png"));
        ciudadOculta.setPosition(0, 0);
        spritesPaises.put("Ciudad Oculta", ciudadOculta);

        Sprite laFraga = new Sprite(new Texture("lafraga.png"));
        laFraga.setPosition(0, 0);
        spritesPaises.put("La Fraga", laFraga);

        Sprite villaFiorito = new Sprite(new Texture("villaFiorito.png"));
        villaFiorito.setPosition(0, 0);
        spritesPaises.put("Villa Fiorito", villaFiorito);

        Sprite marianoAcosta = new Sprite(new Texture("marianoAcosta.png"));
        marianoAcosta.setPosition(0, 0);
        spritesPaises.put("Mariano Acosta", marianoAcosta);

        Sprite sanCayetano = new Sprite(new Texture("sanCayetano.png"));
        sanCayetano.setPosition(0, 0);
        spritesPaises.put("San Cayetano", sanCayetano);

        Sprite villaPalito = new Sprite(new Texture("villaPalito.png"));
        villaPalito.setPosition(0, 0);
        spritesPaises.put("Villa Palito", villaPalito);

        Sprite elJardin = new Sprite(new Texture("elJardin.png"));
        elJardin.setPosition(0, 0);
        spritesPaises.put("El Jardin", elJardin);

        Sprite ingenieroBudge = new Sprite(new Texture("ingenieroBudge.png"));
        ingenieroBudge.setPosition(0, 0);
        spritesPaises.put("Ingeniero Budge", ingenieroBudge);

        Sprite barrioSanJorge = new Sprite(new Texture("barrioSanJorge.png"));
        barrioSanJorge.setPosition(0, 0);
        spritesPaises.put("Barrio San Jorge", barrioSanJorge);

        Sprite nordelta = new Sprite(new Texture("nordelta.png"));
        nordelta.setPosition(0, 0);
        spritesPaises.put("Nordelta", nordelta);

        Sprite palermo = new Sprite(new Texture("palermo.png"));
        palermo.setPosition(0, 0);
        spritesPaises.put("Palermo", palermo);

        Sprite devoto = new Sprite(new Texture("devoto.png"));
        devoto.setPosition(0, 0);
        spritesPaises.put("Devoto", devoto);

        Sprite olivos = new Sprite(new Texture("olivos.png"));
        olivos.setPosition(0, 0);
        spritesPaises.put("Olivos", olivos);

        Sprite sanIsidro = new Sprite(new Texture("sanIsidro.png"));
        sanIsidro.setPosition(0, 0);
        spritesPaises.put("San Isidro", sanIsidro);

        Sprite saavedra = new Sprite(new Texture("saavedra.png"));
        saavedra.setPosition(0, 0);
        spritesPaises.put("Saavedra", saavedra);

        Sprite puertoMadero = new Sprite(new Texture("puertoMadero.png"));
        puertoMadero.setPosition(0, 0);
        spritesPaises.put("Puerto Madero", puertoMadero);

        Sprite nunez = new Sprite(new Texture("nunez.png"));
        nunez.setPosition(0, 0);
        spritesPaises.put("Nuñez", nunez);

        Sprite belgrano = new Sprite(new Texture("belgrano.png"));
        belgrano.setPosition(0, 0);
        spritesPaises.put("Belgrano", belgrano);
    }

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
            System.out.println("Click en posición: X=" + posicionMundo.x + ", Y=" + posicionMundo.y);
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
