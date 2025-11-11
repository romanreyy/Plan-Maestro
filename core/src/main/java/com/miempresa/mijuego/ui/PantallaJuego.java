package com.miempresa.mijuego.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.miempresa.mijuego.model.enums.FaseJuegoEnum;
import com.miempresa.mijuego.model.dto.Objetivo;
import com.miempresa.mijuego.model.dto.Pais;
import com.miempresa.mijuego.model.dto.Jugador;
import com.miempresa.mijuego.model.dto.Personaje;
import com.miempresa.mijuego.network.ClientThread;
import com.miempresa.mijuego.bussiness.GameController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PantallaJuego implements Screen, GameController {

    private final MiJuegoPrincipal juego;
    private Texture texturaJuego;
    private Texture texturaObjetivo;
    private Texture texturaPersonaje;

    // Placeholders (evitan NPE cuando llegan los primeros "Pais:")
    private final Jugador jugador1 = new Jugador("Jugador 1");
    private final Jugador jugador2 = new Jugador("Jugador 2");
    private final Jugador jugador3 = new Jugador("Jugador 3");

    // El jugador que abrió esta pantalla (para icono/objetivo propio)
    private final Jugador jugadorActual;

    // “Estado mínimo” del cliente
    private final Map<String, Pais> paisesPorNombre = new HashMap<>();
    private FaseJuegoEnum faseActual = FaseJuegoEnum.COLOCACION_INICIAL; // se actualiza con "Phase:"
    private int turnoActualId = 1;                                       // se actualiza con "Turn:"

    // Red
    private ClientThread clientThread;
    private int numPlayer = -1;
    private boolean startGame = false;

    // UI
    private HashMap<String, Sprite> spritesPaises;
    private final Vector3 posicionToque = new Vector3();
    private final Vector2 posicionMundo = new Vector2();

    private boolean mostrandoObjetivo = false;
    private Rectangle areaObjetivo;

    private Circle botonTimon;
    private Rectangle botonObjetivo;
    private Rectangle botonDerecha;
    private Circle botonCirculo;
    private Rectangle botonAtacar;
    private Rectangle botonAgrupar;
    private Rectangle areaContadorPaises;

    private boolean modoAtaque = false;
    private Pais paisSeleccionadoAtacante = null;

    private boolean victoriaActiva = false;
    private Texture fondoVictoria = null;
    private Jugador ganadorPartida = null;

    private boolean modoAgrupar = false; // “mover 1” durante fase ATAQUE
    private Pais paisSeleccionadoOrigenAgrupar = null;

    private static final boolean DEBUG_HITBOXES = true;
    private com.badlogic.gdx.graphics.glutils.ShapeRenderer debugShapes;

    private final BitmapFont font = new BitmapFont();

    public PantallaJuego(MiJuegoPrincipal juego) {
        this(juego, new Jugador("Jugador por defecto"));
    }

    public PantallaJuego(MiJuegoPrincipal juego, Jugador jugador) {
        this.juego = juego;
        this.jugadorActual = jugador;

        texturaJuego = new Texture("pantalla_juego.png");
        texturaObjetivo = new Texture("sprite_objetivo.png");

        inicializarSpritesPaises();
        cargarTexturaPersonaje();
        inicializarBotonesInterfaz();
        inicializarAreaObjetivo();
    }

    // ======== Init UI ========
    private void inicializarBotonesInterfaz() {
        botonTimon    = new Circle(119, 1008, 53);
        botonAgrupar  = new Rectangle(502, 960, 260, 95);
        botonObjetivo = new Rectangle(855, 955, 210, 105);
        botonAtacar   = new Rectangle(1159, 960, 300, 95);
        areaContadorPaises = new Rectangle(1489, 950, 118, 85);
        botonDerecha  = new Rectangle(1642, 960, 68, 95);
        botonCirculo  = new Circle(1810, 1008, 53);
        if (DEBUG_HITBOXES) debugShapes = new com.badlogic.gdx.graphics.glutils.ShapeRenderer();
    }

    private void inicializarAreaObjetivo() {
        float anchoObjetivo = 532;
        float altoObjetivo = 633;
        float x = (MiJuegoPrincipal.ANCHO_VIRTUAL - anchoObjetivo) / 2f;
        float y = (MiJuegoPrincipal.ALTO_VIRTUAL - altoObjetivo) / 2f;
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

    private void prepararOverlayVictoria(Jugador ganador) {
        if (ganador == null) return;
        String archivoVictoria = "pantalla_victoria.png";
        if (ganador.tienePersonaje()) {
            String nom = ganador.getPersonajeSeleccionado().getNombre();
            switch (nom) {
                case "El Villero":         archivoVictoria = "victoria_villero.png"; break;
                case "El Pibe Piola":      archivoVictoria = "victoria_pibe_piola.png"; break;
                case "El Mentiroso Rey":   archivoVictoria = "victoria_mentiroso.png"; break;
                case "El Marinero Papá":   archivoVictoria = "victoria_marinero.png"; break;
                case "El Ratón del Grupo": archivoVictoria = "victoria_raton.png"; break;
            }
        }
        if (fondoVictoria != null) fondoVictoria.dispose();
        fondoVictoria = new Texture(archivoVictoria);
        ganadorPartida = ganador;
        victoriaActiva = true;
    }

    private String obtenerArchivoTexturaPersonaje(String nombrePersonaje) {
        switch (nombrePersonaje) {
            case "El Marinero Papá":   return "tropa_marinero.png";
            case "El Pibe Piola":          return "tropa_pibe_piola.png";
            case "El Villero":         return "tropa_villero.png";
            case "El Mentiroso Rey":   return "tropa_mentiroso.png";
            case "El Ratón del Grupo": return "tropa_raton.png";
            default:                   return "tropa_marinero.png";
        }
    }

    private void inicializarSpritesPaises() {
        spritesPaises = new HashMap<>();

        Sprite bajoFlores = new Sprite(new Texture("bajoFlores.png"));
        bajoFlores.setPosition(960, 420);
        bajoFlores.setScale(1.5f);
        spritesPaises.put("Bajo Flores", bajoFlores);

        Sprite pabloMugica = new Sprite(new Texture("pabloMugica.png"));
        pabloMugica.setPosition(1355, 135);
        pabloMugica.setScale(1.7f);
        spritesPaises.put("Pablo Mugica", pabloMugica);

        Sprite laRodrigoBueno = new Sprite(new Texture("laRodrigoBueno.png"));
        laRodrigoBueno.setPosition(1450, 485);
        laRodrigoBueno.setScale(1.5f);
        spritesPaises.put("La Rodrigo Bueno", laRodrigoBueno);

        Sprite virreyDelPino = new Sprite(new Texture("virreyDelPino.png"));
        virreyDelPino.setPosition(1545, 580);
        virreyDelPino.setScale(1.5f);
        spritesPaises.put("Virrey del Pino", virreyDelPino);

        Sprite gonzalezCatan = new Sprite(new Texture("gonzalezCatan.png"));
        gonzalezCatan.setPosition(1375, 215);
        gonzalezCatan.setScale(1.8f);
        spritesPaises.put("Gonzalez Catan", gonzalezCatan);

        Sprite isidroCasanova = new Sprite(new Texture("isidroCasanova.png"));
        isidroCasanova.setPosition(1350, 290);
        isidroCasanova.setScale(1.8f);
        spritesPaises.put("Isidro Casanova", isidroCasanova);

        Sprite laferrere = new Sprite(new Texture("laferrere.png"));
        laferrere.setPosition(480, 180);
        laferrere.setScale(1.5f);
        spritesPaises.put("Laferrere", laferrere);

        Sprite villaItati = new Sprite(new Texture("villaItati.png"));
        villaItati.setPosition(1160, 740);
        villaItati.setScale(1.4f);
        spritesPaises.put("Villa Itati", villaItati);

        Sprite monteChingolo = new Sprite(new Texture("monteChingolo.png"));
        monteChingolo.setPosition(1500, 100);
        spritesPaises.put("Monte Chingolo", monteChingolo);

        Sprite dockSud = new Sprite(new Texture("dockSud.png"));
        dockSud.setPosition(670, 340);
        dockSud.setScale(1.5f);
        spritesPaises.put("Dock Sud", dockSud);

        Sprite laTranquila = new Sprite(new Texture("laTranquila.png"));
        laTranquila.setPosition(1100, 480);
        laTranquila.setScale(1.5f);
        spritesPaises.put("La Tranquila", laTranquila);

        Sprite laRana = new Sprite(new Texture("laRana.png"));
        laRana.setPosition(1435, 770);
        laRana.setScale(1.5f);
        spritesPaises.put("La Rana", laRana);

        Sprite villaCarlosGardel = new Sprite(new Texture("villaCarlosGardel.png"));
        villaCarlosGardel.setPosition(1585, 390);
        villaCarlosGardel.setScale(1.4f);
        spritesPaises.put("Villa Carlos Gardel", villaCarlosGardel);

        Sprite fuerteApache = new Sprite(new Texture("fuerteApache.png"));
        fuerteApache.setPosition(545, 160);
        fuerteApache.setScale(1.8f);
        spritesPaises.put("Fuerte Apache", fuerteApache);

        Sprite cuartelV = new Sprite(new Texture("cuartelV.png"));
        cuartelV.setPosition(1605, 690);
        cuartelV.setScale(1.6f);
        spritesPaises.put("Cuartel V", cuartelV);

        Sprite joseCPaz = new Sprite(new Texture("joseCPaz.png"));
        joseCPaz.setPosition(1325, 670);
        joseCPaz.setScale(1.5f);
        spritesPaises.put("Jose C. Paz", joseCPaz);

        Sprite lasTunas = new Sprite(new Texture("lasTunas.png"));
        lasTunas.setPosition(1595, 825);
        lasTunas.setScale(1.5f);
        spritesPaises.put("Las Tunas", lasTunas);

        Sprite laCatanga = new Sprite(new Texture("laCatanga.png"));
        laCatanga.setPosition(1130, 255);
        laCatanga.setScale(1.6f);
        spritesPaises.put("La Catanga", laCatanga);

        Sprite la18 = new Sprite(new Texture("la18.png"));
        la18.setPosition(660, 250);
        la18.setScale(1.8f);
        spritesPaises.put("La 18", la18);

        Sprite barrioPiolin = new Sprite(new Texture("barrioPiolin.png"));
        barrioPiolin.setPosition(1340, 540);
        barrioPiolin.setScale(1.7f);
        spritesPaises.put("Barrio Piolin", barrioPiolin);

        Sprite villaLanzone = new Sprite(new Texture("villaLanzone.png"));
        villaLanzone.setPosition(1230, 500);
        villaLanzone.setScale(1.5f);
        spritesPaises.put("Villa Lanzone", villaLanzone);

        Sprite hidalgo = new Sprite(new Texture("hidalgo.png"));
        hidalgo.setPosition(1795, 540);
        hidalgo.setScale(0.8f);
        spritesPaises.put("Hidalgo", hidalgo);

        Sprite villa31 = new Sprite(new Texture("villa31.png"));
        villa31.setPosition(1735, 285);
        villa31.setScale(1.5f);
        spritesPaises.put("Villa 31", villa31);

        Sprite laCurita = new Sprite(new Texture("laCurita.png"));
        laCurita.setPosition(1240, 380);
        laCurita.setScale(1.5f);
        spritesPaises.put("La Curita", laCurita);

        Sprite villaSoldati = new Sprite(new Texture("villaSoldati.png"));
        villaSoldati.setPosition(1280, 190);
        villaSoldati.setScale(1.8f);
        spritesPaises.put("Villa Soldati", villaSoldati);

        Sprite nuevaPompeya = new Sprite(new Texture("nuevaPompeya.png"));
        nuevaPompeya.setPosition(1580, 745);
        nuevaPompeya.setScale(1.5f);
        spritesPaises.put("Nueva Pompeya", nuevaPompeya);

        Sprite constitucion = new Sprite(new Texture("constitucion.png"));
        constitucion.setPosition(1730, 640);
        constitucion.setScale(0.9f);
        spritesPaises.put("Constitucion", constitucion);

        Sprite once = new Sprite(new Texture("once.png"));
        once.setPosition(1810, 365);
        once.setScale(1.5f);
        spritesPaises.put("Once", once);

        Sprite donOrione = new Sprite(new Texture("donOrione.png"));
        donOrione.setPosition(1700, 430);
        donOrione.setScale(0.9f);
        spritesPaises.put("Don Orione", donOrione);

        Sprite emebe04PuntaDeRielesNuevos = new Sprite(new Texture("emebe04PuntaDeRielesNuevos.png"));
        emebe04PuntaDeRielesNuevos.setPosition(730, 610);
        emebe04PuntaDeRielesNuevos.setScale(1.1f);
        spritesPaises.put("Emebe 04 Punta de Rieles Nuevos", emebe04PuntaDeRielesNuevos);

        Sprite ciudadOculta = new Sprite(new Texture("ciudadOculta.png"));
        ciudadOculta.setPosition(450, 390);
        ciudadOculta.setScale(1.4f);
        spritesPaises.put("Ciudad Oculta", ciudadOculta);

        Sprite laFraga = new Sprite(new Texture("lafraga.png"));
        laFraga.setPosition(1880, 355);
        laFraga.setScale(1.5f);
        spritesPaises.put("La Fraga", laFraga);

        Sprite villaFiorito = new Sprite(new Texture("villaFiorito.png"));
        villaFiorito.setPosition(480, 300);
        villaFiorito.setScale(1.9f);
        spritesPaises.put("Villa Fiorito", villaFiorito);

        Sprite marianoAcosta = new Sprite(new Texture("marianoAcosta.png"));
        marianoAcosta.setPosition(1655, 595);
        marianoAcosta.setScale(1.5f);
        spritesPaises.put("Mariano Acosta", marianoAcosta);

        Sprite sanCayetano = new Sprite(new Texture("sanCayetano.png"));
        sanCayetano.setPosition(1515, 825);
        sanCayetano.setScale(1.5f);
        spritesPaises.put("San Cayetano", sanCayetano);

        Sprite villaPalito = new Sprite(new Texture("villaPalito.png"));
        villaPalito.setPosition(1680, 805);
        villaPalito.setScale(0.8f);
        spritesPaises.put("Villa Palito", villaPalito);

        Sprite elJardin = new Sprite(new Texture("elJardin.png"));
        elJardin.setPosition(1825, 210);
        elJardin.setScale(1.8f);
        spritesPaises.put("El Jardin", elJardin);

        Sprite ingenieroBudge = new Sprite(new Texture("ingenieroBudge.png"));
        ingenieroBudge.setPosition(970, 545);
        ingenieroBudge.setScale(0.8f);
        spritesPaises.put("Ingeniero Budge", ingenieroBudge);

        Sprite barrioSanJorge = new Sprite(new Texture("barrioSanJorge.png"));
        barrioSanJorge.setPosition(1420, 400);
        spritesPaises.put("Barrio San Jorge", barrioSanJorge);

        Sprite nordelta = new Sprite(new Texture("nordelta.png"));
        nordelta.setPosition(490, 830);
        nordelta.setScale(1.8f);
        spritesPaises.put("Nordelta", nordelta);

        Sprite palermo = new Sprite(new Texture("palermo.png"));
        palermo.setPosition(210, 810);
        palermo.setScale(1.6f);
        spritesPaises.put("Palermo", palermo);

        Sprite devoto = new Sprite(new Texture("devoto.png"));
        devoto.setPosition(300, 445);
        devoto.setScale(1.5f);
        spritesPaises.put("Devoto", devoto);

        Sprite olivos = new Sprite(new Texture("olivos.png"));
        olivos.setPosition(150, 550);
        olivos.setScale(1.6f);
        spritesPaises.put("Olivos", olivos);

        Sprite sanIsidro = new Sprite(new Texture("sanIsidro.png"));
        sanIsidro.setPosition(285, 655);
        sanIsidro.setScale(1.6f);
        spritesPaises.put("San Isidro", sanIsidro);

        Sprite saavedra = new Sprite(new Texture("saavedra.png"));
        saavedra.setPosition(225, 595);
        saavedra.setScale(1.7f);
        spritesPaises.put("Saavedra", saavedra);

        Sprite puertoMadero = new Sprite(new Texture("puertoMadero.png"));
        puertoMadero.setPosition(375, 715);
        puertoMadero.setScale(1.6f);
        spritesPaises.put("Puerto Madero", puertoMadero);

        Sprite nunez = new Sprite(new Texture("nunez.png"));
        nunez.setPosition(130, 760);
        nunez.setScale(1.5f);
        spritesPaises.put("Nuñez", nunez);

        Sprite belgrano = new Sprite(new Texture("belgrano.png"));
        belgrano.setPosition(35, 570);
        belgrano.setScale(1.6f);
        spritesPaises.put("Belgrano", belgrano);

        Sprite recoleta = new Sprite(new Texture("recoleta.png"));
        recoleta.setPosition(45, 710);
        recoleta.setScale(1.7f);
        spritesPaises.put("Recoleta", recoleta);
    }

    // ======== Dibujo / Colores ========
    public void actualizarColoresPaises() {
        if (spritesPaises == null) return;

        for (Map.Entry<String, Sprite> e : spritesPaises.entrySet()) {
            String nombre = e.getKey();
            Sprite sprite = e.getValue();
            Pais p = paisesPorNombre.get(nombre);

            if (p == null || p.getPropietario() == null) {
                sprite.setColor(1f, 1f, 1f, 0f);
                continue;
            }

            com.badlogic.gdx.graphics.Color c;
            Jugador dueño = p.getPropietario();
            if (dueño.tienePersonaje() && dueño.getPersonajeSeleccionado().getColor() != null) {
                c = dueño.getPersonajeSeleccionado().getColor();
            } else {
                int id = (dueño == jugador1) ? 1 : (dueño == jugador2) ? 2 : 3;
                c = (id == 1) ? com.badlogic.gdx.graphics.Color.RED
                    : (id == 2) ? com.badlogic.gdx.graphics.Color.BLUE
                    : com.badlogic.gdx.graphics.Color.GREEN;
            }
            sprite.setColor(c.r, c.g, c.b, 0.85f);
        }
    }

    private String detectarPaisPorClick(float x, float y) {
        for (Map.Entry<String, Sprite> e : spritesPaises.entrySet()) {
            if (e.getValue().getBoundingRectangle().contains(x, y)) return e.getKey();
        }
        return null;
    }

    // ======== Render ========
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        juego.vistaVentana.apply();
        juego.loteSprites.setProjectionMatrix(juego.camara.combined);

        manejarInput();
        actualizarColoresPaises();

        juego.loteSprites.begin();

        // Fondo UI
        juego.loteSprites.draw(texturaJuego, 0, 0,
            MiJuegoPrincipal.ANCHO_VIRTUAL,
            MiJuegoPrincipal.ALTO_VIRTUAL);

        // Países + numerito de tropas
        for (Map.Entry<String, Sprite> e : spritesPaises.entrySet()) {
            String nombre = e.getKey();
            Sprite s = e.getValue();
            s.draw(juego.loteSprites);

            Pais p = paisesPorNombre.get(nombre);
            if (p != null && p.getTropas() > 0) {
                font.draw(juego.loteSprites,
                    String.valueOf(p.getTropas()),
                    s.getX() + s.getWidth() / 2f,
                    s.getY() + s.getHeight() + 18f);
            }
        }

        // Ícono personaje del jugador local (decorativo)
        if (texturaPersonaje != null) {
            float xPersonaje = botonCirculo.x - botonCirculo.radius;
            float yPersonaje = botonCirculo.y - botonCirculo.radius;
            float diametro = botonCirculo.radius * 2;
            juego.loteSprites.draw(texturaPersonaje, xPersonaje, yPersonaje, diametro, diametro);
        }

        // HUD básico (según mensajes del server)
        Jugador turnoJugador = getJugadorPorId(turnoActualId);
        font.draw(juego.loteSprites, "Fase: " + faseActual, 30, 1030);
        font.draw(juego.loteSprites, "Turno: " + (turnoJugador != null ? turnoJugador.getNombre() : "-"), 30, 1000);

        // Overlay objetivo propio
        if (mostrandoObjetivo) {
            juego.loteSprites.draw(texturaObjetivo,
                areaObjetivo.x, areaObjetivo.y,
                areaObjetivo.width, areaObjetivo.height);
        }

        // Contador de países del jugador local (decorativo)
        int cantPaises = (jugadorActual.getPaisesControlados() != null)
            ? jugadorActual.getPaisesControlados().size() : 0;

        float oldScaleX = font.getData().scaleX, oldScaleY = font.getData().scaleY;
        font.getData().setScale(1.8f);
        GlyphLayout layoutNumero = new GlyphLayout(font, String.valueOf(cantPaises));
        float numeroX = areaContadorPaises.x + (areaContadorPaises.width - layoutNumero.width) / 2f;
        float numeroY = areaContadorPaises.y + (areaContadorPaises.height - layoutNumero.height) / 2f + layoutNumero.height;
        font.draw(juego.loteSprites, layoutNumero, numeroX, numeroY);
        font.getData().setScale(oldScaleX, oldScaleY);

        juego.loteSprites.end();

        // Debug hitboxes
        if (DEBUG_HITBOXES && debugShapes != null) {
            debugShapes.setProjectionMatrix(juego.camara.combined);
            debugShapes.begin(com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Line);
            debugShapes.setColor(1, 0, 0, 1);
            debugShapes.circle(botonTimon.x, botonTimon.y, botonTimon.radius);
            debugShapes.rect(botonAgrupar.x,  botonAgrupar.y,  botonAgrupar.width,  botonAgrupar.height);
            debugShapes.rect(botonObjetivo.x, botonObjetivo.y, botonObjetivo.width, botonObjetivo.height);
            debugShapes.rect(botonAtacar.x,   botonAtacar.y,   botonAtacar.width,   botonAtacar.height);
            debugShapes.rect(areaContadorPaises.x, areaContadorPaises.y, areaContadorPaises.width, areaContadorPaises.height);
            debugShapes.rect(botonDerecha.x,  botonDerecha.y,  botonDerecha.width,  botonDerecha.height);
            debugShapes.circle(botonCirculo.x, botonCirculo.y, botonCirculo.radius);
            debugShapes.end();
        }

        // Overlay victoria
        if (victoriaActiva) {
            juego.loteSprites.begin();
            juego.loteSprites.draw(
                fondoVictoria,
                0, 0,
                MiJuegoPrincipal.ANCHO_VIRTUAL,
                MiJuegoPrincipal.ALTO_VIRTUAL
            );
            if (ganadorPartida != null) {
                String msg = "¡" + ganadorPartida.getNombre().toUpperCase() + " GANA!";
                float oldX = font.getData().scaleX, oldY = font.getData().scaleY;
                font.getData().setScale(2f);
                GlyphLayout lay = new GlyphLayout(font, msg);
                float x = (MiJuegoPrincipal.ANCHO_VIRTUAL - lay.width) / 2f;
                float y = 160;
                font.draw(juego.loteSprites, lay, x, y);
                font.getData().setScale(oldX, oldY);
            }
            juego.loteSprites.end();

            if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
                if (fondoVictoria != null) { fondoVictoria.dispose(); fondoVictoria = null; }
                juego.setScreen(new MenuPrincipal(juego));
            }
        }
    }

    // ======== Input ========
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

            // Botones
            if (botonTimon.contains(posicionMundo.x, posicionMundo.y))    { alPresionarTimon(); return; }
            if (botonObjetivo.contains(posicionMundo.x, posicionMundo.y)) { alPresionarObjetivo(); return; }
            if (botonDerecha.contains(posicionMundo.x, posicionMundo.y))  { alPresionarBotonDerecha(); return; }
            if (botonCirculo.contains(posicionMundo.x, posicionMundo.y))  { alPresionarCirculo(); return; }
            if (botonAgrupar.contains(posicionMundo.x, posicionMundo.y))  { alPresionarAgrupar(); return; }
            if (botonAtacar.contains(posicionMundo.x, posicionMundo.y))   { alPresionarAtacar(); return; }

            // Click en mapa
            String nombrePaisClick = detectarPaisPorClick(posicionMundo.x, posicionMundo.y);
            if (nombrePaisClick == null) return;

            Jugador turnoJugador = getJugadorPorId(turnoActualId);
            if (turnoJugador == null) return;

            switch (faseActual) {
                case COLOCACION_INICIAL: {
                    enviarColocacionInicial(nombrePaisClick); // server valida
                    System.out.println("➡️ Colocación inicial solicitada en " + nombrePaisClick);
                    break;
                }
                case AGRUPAR: {
                    // En AGRUPAR se colocan refuerzos (+1 por click) sobre países propios
                    Pais pLocal = paisesPorNombre.get(nombrePaisClick);
                    if (pLocal != null && pLocal.getPropietario() == turnoJugador) {
                        if (clientThread != null) clientThread.sendReinforce(nombrePaisClick);
                        System.out.println("➕ Refuerzo solicitado en " + nombrePaisClick);
                    } else {
                        System.out.println("⚠️ Solo podés reforzar tus países.");
                    }
                    break;
                }
                case ATAQUE: {
                    Pais paisSel = paisesPorNombre.get(nombrePaisClick);
                    if (paisSel == null) return;

                    // ----- MODO ATAQUE -----
                    if (modoAtaque) {
                        if (paisSeleccionadoAtacante == null) {
                            if (paisSel.getPropietario() != turnoJugador) {
                                System.out.println("⚠️ Ese país no te pertenece.");
                                return;
                            }
                            if (paisSel.getTropas() <= 1) {
                                System.out.println("⚠️ Necesitás al menos 2 tropas para atacar.");
                                return;
                            }
                            paisSeleccionadoAtacante = paisSel;
                            System.out.println("✅ Atacante: " + paisSeleccionadoAtacante.getNombre() + ". Elegí un país enemigo.");
                            return;
                        }

                        if (paisSel == paisSeleccionadoAtacante) {
                            System.out.println("⚠️ No podés atacar el mismo país.");
                            return;
                        }
                        if (paisSel.getPropietario() == turnoJugador) {
                            System.out.println("⚠️ Ese país también es tuyo. Elegí uno enemigo.");
                            return;
                        }

                        enviarAtaque(paisSeleccionadoAtacante.getNombre(), paisSel.getNombre());
                        modoAtaque = false;
                        paisSeleccionadoAtacante = null;
                        return;
                    }

                    // ----- MODO AGRUPAR (mover 1 durante fase ATAQUE) -----
                    if (modoAgrupar) {
                        if (paisSeleccionadoOrigenAgrupar == null) {
                            if (paisSel.getPropietario() != turnoJugador) {
                                System.out.println("⚠️ Solo desde países propios.");
                                return;
                            }
                            if (paisSel.getTropas() <= 1) {
                                System.out.println("⚠️ " + paisSel.getNombre() + " necesita al menos 2 tropas para ceder una.");
                                return;
                            }
                            paisSeleccionadoOrigenAgrupar = paisSel;
                            System.out.println("✅ Origen: " + paisSel.getNombre() + ". Elegí destino propio (server valida límite).");
                            return;
                        }

                        if (paisSel == paisSeleccionadoOrigenAgrupar) {
                            System.out.println("⚠️ Mismo país, no válido.");
                            return;
                        }
                        if (paisSel.getPropietario() != turnoJugador) {
                            System.out.println("⚠️ Solo entre países propios.");
                            return;
                        }

                        enviarAgrupar(paisSeleccionadoOrigenAgrupar.getNombre(), paisSel.getNombre());
                        paisSeleccionadoOrigenAgrupar = null;
                        modoAgrupar = false;
                        return;
                    }

                    System.out.println("💬 Estás en fase de ATAQUE. Toca 'Atacar' o 'Agrupar' para actuar.");
                    break;
                }
                default:
                    break;
            }
        }
    }

    private void alPresionarTimon() {
        juego.setScreen(new PantallaAjustes(juego, true));
    }

    private void alPresionarObjetivo() {
        mostrandoObjetivo = true;

        String archivo = "sprite_objetivo.png";
        if (jugadorActual.tieneObjetivo()) {
            Objetivo objetivo = jugadorActual.getObjetivoAsignado();
            archivo = obtenerSpriteObjetivo(objetivo);
            System.out.println("Objetivo: " + objetivo.getNombre());
            System.out.println("Descripción: " + objetivo.getDescripcion());
        }

        if (texturaObjetivo != null) texturaObjetivo.dispose();
        try {
            texturaObjetivo = new Texture(archivo);
        } catch (Exception e) {
            System.out.println("No se pudo cargar " + archivo + ", usando fallback.");
            texturaObjetivo = new Texture("sprite_objetivo.png");
        }
    }

    private String obtenerSpriteObjetivo(Objetivo obj) {
        if (obj == null) return "sprite_objetivo.png";
        String nombre = obj.getNombre();
        if (nombre == null) return "sprite_objetivo.png";
        switch (nombre) {
            case "Plan Dominacion": return "objetivo1.png";
            case "Control Total del Territorio Rico": return "objetivo2.png";
            case "Dominacion del pobre": return "objetivo3.png";
            case "Equilibrio Estrategico": return "objetivo6.png";
            case "Expansion Mixta": return "objetivo5.png";
            case "Rico y Pobre": return "objetivo4.png";
            default: return "sprite_objetivo.png";
        }
    }

    private void alPresionarBotonDerecha() {
        if (clientThread == null) return;

        // Si estás en ATAQUE → cerrar ataque del jugador actual
        if (faseActual == FaseJuegoEnum.ATAQUE) {
            clientThread.sendMessage("EndAttack");
            System.out.println("✅ Fin de ATAQUE solicitado.");
            return;
        }
        // Si estás en AGRUPAR → cerrar refuerzos del jugador actual
        if (faseActual == FaseJuegoEnum.AGRUPAR) {
            clientThread.sendMessage("EndGroup");
            System.out.println("✅ Fin de AGRUPAR (refuerzos) solicitado.");
            return;
        }
        // En otras fases, no hace nada
    }

    private void alPresionarCirculo() {
        if (jugadorActual.tienePersonaje()) {
            Personaje personaje = jugadorActual.getPersonajeSeleccionado();
            System.out.println("Personaje: " + personaje.getNombre());
        }
    }

    private void alPresionarAgrupar() {
        if (faseActual != FaseJuegoEnum.ATAQUE) {
            System.out.println("⚠️ Solo se puede agrupar durante la fase de ATAQUE.");
            return;
        }
        modoAgrupar = true;
        modoAtaque = false;
        paisSeleccionadoOrigenAgrupar = null;
        System.out.println("🔄 MODO AGRUPAR ACTIVADO: toca un país tuyo (con más de 1 tropa) para mover tropas.");
    }

    private void alPresionarAtacar() {
        if (faseActual != FaseJuegoEnum.ATAQUE) {
            System.out.println("⚠️ No podés atacar fuera de la fase de ATAQUE.");
            return;
        }
        modoAtaque = true;
        paisSeleccionadoAtacante = null;
        System.out.println("🎯 MODO ATAQUE ACTIVADO: toca un país propio (con más de 1 tropa) para comenzar.");
    }

    private void alPresionarPaises()  {
        System.out.println("PAISES presionado");
    }

    // ======== GameController callbacks (desde ClientThread) ========
    @Override
    public void connect(int numPlayer) {
        Gdx.app.postRunnable(() -> {
            this.numPlayer = numPlayer;
            System.out.println("Conectado como jugador #" + numPlayer);
        });
    }

    @Override
    public void start() {
        Gdx.app.postRunnable(() -> {
            this.startGame = true;
            System.out.println("Start recibido. ¡A jugar!");
        });
    }

    @Override
    public void endGame(int winner) {
        Gdx.app.postRunnable(() -> {
            Jugador win = getJugadorPorId(winner);
            this.victoriaActiva = true;
            this.ganadorPartida = win;
            prepararOverlayVictoria(win);
        });
    }

    @Override
    public void backToMenu() {
        Gdx.app.postRunnable(() -> juego.setScreen(new MenuPrincipal(juego)));
    }

    @Override
    public void onTurn(int playerId) {
        Gdx.app.postRunnable(() -> {
            this.turnoActualId = playerId;
            System.out.println("Turno remoto de jugador #" + playerId);
        });
    }

    @Override
    public void onPhase(FaseJuegoEnum fase) {
        Gdx.app.postRunnable(() -> {
            this.faseActual = fase;
            System.out.println("Fase remota: " + fase);
        });
    }

    @Override
    public void onPaisUpdate(String name, int ownerId, int troops) {
        Gdx.app.postRunnable(() -> {
            if (ownerId < 1 || ownerId > 3) {
                System.err.println("onPaisUpdate: ownerId inválido " + ownerId + " para " + name);
                return;
            }

            Jugador nuevo = getJugadorPorId(ownerId);

            // país local (crear si no existe)
            Pais p = paisesPorNombre.get(name);
            if (p == null) {
                p = new Pais(name, "", new ArrayList<>()); // tu ctor real
                paisesPorNombre.put(name, p);
            }

            // sacar de dueño anterior si cambia
            Jugador anterior = p.getPropietario();
            if (anterior != null && anterior != nuevo) {
                anterior.getPaisesControlados().remove(p);
            }

            // asignar dueño y mantener lista del dueño
            p.setPropietario(nuevo);
            if (!nuevo.getPaisesControlados().contains(p)) {
                nuevo.getPaisesControlados().add(p);
            }

            // tropas
            p.setTropas(troops);

            // refresco colores
            actualizarColoresPaises();
        });
        System.out.println("📨 Mensaje recibido: Pais:" + name + ":" + ownerId + ":" + troops);
    }

    @Override
    public void onInfo(String msg) {
        Gdx.app.postRunnable(() -> System.out.println("INFO: " + msg));
    }

    @Override
    public void onError(String msg) {
        Gdx.app.postRunnable(() -> System.err.println("ERROR: " + msg));
    }

    // ======== Red: envíos ========
    private void enviarColocacionInicial(String nombrePais) {
        if (clientThread != null) clientThread.sendMessage("Click:" + nombrePais);
    }
    private void enviarAtaque(String origen, String destino) {
        if (clientThread != null) clientThread.sendMessage("Attack:" + origen + ":" + destino);
    }
    private void enviarAgrupar(String origen, String destino) {
        if (clientThread != null) clientThread.sendMessage("Group:" + origen + ":" + destino + ":1");
    }

    // ======== Util ========
    private Jugador getJugadorPorId(int id) {
        switch (id) {
            case 1: return jugador1;
            case 2: return jugador2;
            case 3: return jugador3;
            default: return null;
        }
    }

    // ======== Ciclo de vida Screen ========
    @Override
    public void show() {
        System.out.println("PantallaJuego mostrada");

        // Iniciar cliente de red y handshake
        clientThread = new ClientThread(this);
        clientThread.start();
        clientThread.sendConnect(); // "Connect"
    }

    @Override
    public void resize(int width, int height) {
        juego.vistaVentana.update(width, height);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        if (texturaJuego != null) texturaJuego.dispose();
        if (texturaObjetivo != null) texturaObjetivo.dispose();
        if (texturaPersonaje != null) texturaPersonaje.dispose();
        if (fondoVictoria != null) fondoVictoria.dispose();
        if (spritesPaises != null) {
            for (Sprite s : spritesPaises.values())
                if (s != null && s.getTexture() != null) s.getTexture().dispose();
        }
        font.dispose();
        if (debugShapes != null) debugShapes.dispose();
        if (clientThread != null) clientThread.terminate();
    }

    public Jugador getJugadorActual() { return jugadorActual; }
}
