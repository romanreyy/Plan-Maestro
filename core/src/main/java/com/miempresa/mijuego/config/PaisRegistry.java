package com.miempresa.mijuego.config;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.miempresa.mijuego.model.dto.Pais;

import java.util.ArrayList;

public class PaisRegistry {

    private static final PaisRegistry registry = new PaisRegistry();

    private ArrayList<Pais> paises = new ArrayList<Pais>();

    private PaisRegistry() {
        //empty
    }

    public static PaisRegistry getInstance() {
        if (registry.paises.isEmpty()) {
            registry.registerAllPaises();
        }
        return PaisRegistry.registry;
    }

    private void registerAllPaises() {
        Pais bajoFlores = new Pais("Bajo Flores", "Pobre", new ArrayList<>());
        registry.paises.add(bajoFlores);

        Pais barrioPiolin = new Pais("Barrio Piolin", "Pobre", new ArrayList<>());
        registry.paises.add(barrioPiolin);

        Pais barrioSanJorge = new Pais("Barrio San Jorge", "Pobre", new ArrayList<>());
        registry.paises.add(barrioSanJorge);

        Pais belgrano = new Pais("Belgrano", "Rico", new ArrayList<>());
        registry.paises.add(belgrano);

        Pais ciudadOculta = new Pais("Ciudad Oculta", "Pobre", new ArrayList<>());
        registry.paises.add(ciudadOculta);

        Pais constitucion = new Pais("Constitucion", "Pobre", new ArrayList<>());
        registry.paises.add(constitucion);

        Pais cuartelV = new Pais("Cuartel V", "Pobre", new ArrayList<>());
        registry.paises.add(cuartelV);

        Pais devoto = new Pais("Devoto", "Rico", new ArrayList<>());
        registry.paises.add(devoto);

        Pais dockSud = new Pais("Dock Sud", "Pobre", new ArrayList<>());
        registry.paises.add(dockSud);

        Pais donOrione = new Pais("Don Orione", "Pobre", new ArrayList<>());
        registry.paises.add(donOrione);

        Pais elJardin = new Pais("El Jardin", "Pobre", new ArrayList<>());
        registry.paises.add(elJardin);

        Pais emebe04PuntaDeRielesNuevos = new Pais("Emebe 04 Punta de Rieles Nuevos", "Pobre", new ArrayList<>());
        registry.paises.add(emebe04PuntaDeRielesNuevos);

        Pais fuerteApache = new Pais("Fuerte Apache", "Pobre", new ArrayList<>());
        registry.paises.add(fuerteApache);

        Pais gonzalezCatan = new Pais("Gonzalez Catan", "Pobre", new ArrayList<>());
        registry.paises.add(gonzalezCatan);

        Pais hidalgo = new Pais("Hidalgo", "Pobre", new ArrayList<>());
        registry.paises.add(hidalgo);

        Pais ingenieroBudge = new Pais("Ingeniero Budge", "Pobre", new ArrayList<>());
        registry.paises.add(ingenieroBudge);

        Pais isidroCasanova = new Pais("Isidro Casanova", "Pobre", new ArrayList<>());
        registry.paises.add(isidroCasanova);

        Pais joseCPaz = new Pais("Jose C. Paz", "Pobre", new ArrayList<>());
        registry.paises.add(joseCPaz);

        Pais la18 = new Pais("La 18", "Pobre", new ArrayList<>());
        registry.paises.add(la18);

        Pais laCatanga = new Pais("La Catanga", "Pobre", new ArrayList<>());
        registry.paises.add(laCatanga);

        Pais laCurita = new Pais("La Curita", "Pobre", new ArrayList<>());
        registry.paises.add(laCurita);

        Pais laferrere = new Pais("Laferrere", "Pobre", new ArrayList<>());
        registry.paises.add(laferrere);

        Pais laFraga = new Pais("La Fraga", "Pobre", new ArrayList<>());
        registry.paises.add(laFraga);

        Pais laRana = new Pais("La Rana", "Pobre", new ArrayList<>());
        registry.paises.add(laRana);

        Pais laRodrigoBueno = new Pais("La Rodrigo Bueno", "Pobre", new ArrayList<>());
        registry.paises.add(laRodrigoBueno);

        Pais lasTunas = new Pais("Las Tunas", "Pobre", new ArrayList<>());
        registry.paises.add(lasTunas);

        Pais laTranquila = new Pais("La Tranquila", "Pobre", new ArrayList<>());
        registry.paises.add(laTranquila);

        Pais marianoAcosta = new Pais("Mariano Acosta", "Pobre", new ArrayList<>());
        registry.paises.add(marianoAcosta);

        Pais monteChingolo = new Pais("Monte Chingolo", "Pobre", new ArrayList<>());
        registry.paises.add(monteChingolo);

        Pais nordelta = new Pais("Nordelta", "Rico", new ArrayList<>());
        registry.paises.add(nordelta);

        Pais nuevaPompeya = new Pais("Nueva Pompeya", "Pobre", new ArrayList<>());
        registry.paises.add(nuevaPompeya);

        Pais olivos = new Pais("Olivos", "Rico", new ArrayList<>());
        registry.paises.add(olivos);

        Pais once = new Pais("Once", "Pobre", new ArrayList<>());
        registry.paises.add(once);

        Pais pabloMugica = new Pais("Pablo Mugica", "Pobre", new ArrayList<>());
        registry.paises.add(pabloMugica);

        Pais puertoMadero = new Pais("Puerto Madero", "Rico", new ArrayList<>());
        registry.paises.add(puertoMadero);

        Pais recoleta = new Pais("Recoleta", "Rico", new ArrayList<>());
        registry.paises.add(recoleta);

        Pais saavedra = new Pais("Saavedra", "Rico", new ArrayList<>());
        registry.paises.add(saavedra);

        Pais sanCayetano = new Pais("San Cayetano", "Pobre", new ArrayList<>());
        registry.paises.add(sanCayetano);

        Pais sanIsidro = new Pais("San Isidro", "Rico", new ArrayList<>());
        registry.paises.add(sanIsidro);

        Pais villa31 = new Pais("Villa 31", "Pobre", new ArrayList<>());
        registry.paises.add(villa31);

        Pais villaCarlosGardel = new Pais("Villa Carlos Gardel", "Pobre", new ArrayList<>());
        registry.paises.add(villaCarlosGardel);

        Pais villaFiorito = new Pais("Villa Fiorito", "Pobre", new ArrayList<>());
        registry.paises.add(villaFiorito);

        Pais villaItati = new Pais("Villa Itati", "Pobre", new ArrayList<>());
        registry.paises.add(villaItati);

        Pais villaLanzone = new Pais("Villa Lanzone", "Pobre", new ArrayList<>());
        registry.paises.add(villaLanzone);

        Pais villaPalito = new Pais("Villa Palito", "Pobre", new ArrayList<>());
        registry.paises.add(villaPalito);

        Pais villaSoldati = new Pais("Villa Soldati", "Pobre", new ArrayList<>());
        registry.paises.add(villaSoldati);

        Pais virreyDelPino = new Pais("Virrey del Pino", "Pobre", new ArrayList<>());
        registry.paises.add(virreyDelPino);

        Pais palermo = new Pais("Palermo", "Rico", new ArrayList<>());
        registry.paises.add(palermo);

        Pais nunez = new Pais("Nuñez", "Rico", new ArrayList<>());
        registry.paises.add(nunez);

        //paises limitrofes de Bajo Flores
        bajoFlores.addPaisLimitrofe(laTranquila);
        bajoFlores.addPaisLimitrofe(ingenieroBudge);
        bajoFlores.addPaisLimitrofe(laCatanga);

        //paises limitrofes de Barrio Piolin
        barrioPiolin.addPaisLimitrofe(joseCPaz);
        barrioPiolin.addPaisLimitrofe(villaLanzone);
        barrioPiolin.addPaisLimitrofe(laRodrigoBueno);
        barrioPiolin.addPaisLimitrofe(isidroCasanova);

        //paises limitrofes Barrio San Jorge
        barrioSanJorge.addPaisLimitrofe(villaCarlosGardel);
        barrioSanJorge.addPaisLimitrofe(laRodrigoBueno);
        barrioSanJorge.addPaisLimitrofe(isidroCasanova);

        //paises limitrofes Belgrano
        belgrano.addPaisLimitrofe(recoleta);
        belgrano.addPaisLimitrofe(saavedra);
        belgrano.addPaisLimitrofe(olivos);

        //paises limitrofes Ciudad Oculta
        ciudadOculta.addPaisLimitrofe(villaFiorito);
        ciudadOculta.addPaisLimitrofe(dockSud);
        ciudadOculta.addPaisLimitrofe(devoto);

        //paises limitrofes Constitucion
        constitucion.addPaisLimitrofe(villaPalito);
        constitucion.addPaisLimitrofe(nuevaPompeya);
        constitucion.addPaisLimitrofe(cuartelV);
        constitucion.addPaisLimitrofe(marianoAcosta);
        constitucion.addPaisLimitrofe(donOrione);
        constitucion.addPaisLimitrofe(hidalgo);

        //paises limitrofes Cuartel V
        cuartelV.addPaisLimitrofe(nuevaPompeya);
        cuartelV.addPaisLimitrofe(virreyDelPino);
        cuartelV.addPaisLimitrofe(marianoAcosta);
        cuartelV.addPaisLimitrofe(constitucion);

        //paises limitrofes Devoto
        devoto.addPaisLimitrofe(ciudadOculta);
        devoto.addPaisLimitrofe(olivos);

        //paises limitrofes Dock Sud
        dockSud.addPaisLimitrofe(ciudadOculta);
        dockSud.addPaisLimitrofe(villaFiorito);
        dockSud.addPaisLimitrofe(la18);
        dockSud.addPaisLimitrofe(fuerteApache);
        dockSud.addPaisLimitrofe(laCatanga);

        //paises limitrofes Don Orione
        donOrione.addPaisLimitrofe(virreyDelPino);
        donOrione.addPaisLimitrofe(marianoAcosta);
        donOrione.addPaisLimitrofe(hidalgo);
        donOrione.addPaisLimitrofe(constitucion);

        //paises limtrofes El Jardin
        elJardin.addPaisLimitrofe(laFraga);
        elJardin.addPaisLimitrofe(once);
        elJardin.addPaisLimitrofe(villa31);
        elJardin.addPaisLimitrofe(laferrere);

        //paises limitrofes Emebe 04 Punta de Rieles Nuevos
        emebe04PuntaDeRielesNuevos.addPaisLimitrofe(villaItati);
        emebe04PuntaDeRielesNuevos.addPaisLimitrofe(ingenieroBudge);
        emebe04PuntaDeRielesNuevos.addPaisLimitrofe(nordelta);

        //paises limtrofes Fuerte Apache
        fuerteApache.addPaisLimitrofe(la18);
        fuerteApache.addPaisLimitrofe(dockSud);
        fuerteApache.addPaisLimitrofe(villaFiorito);
        fuerteApache.addPaisLimitrofe(laferrere);

        //paises limtrofes Gonzalez Catan
        gonzalezCatan.addPaisLimitrofe(isidroCasanova);
        gonzalezCatan.addPaisLimitrofe(laCatanga);
        gonzalezCatan.addPaisLimitrofe(villaSoldati);
        gonzalezCatan.addPaisLimitrofe(pabloMugica);

        //paises limitrofes Hidalgo
        hidalgo.addPaisLimitrofe(constitucion);
        hidalgo.addPaisLimitrofe(donOrione);

        //paises limtrofes Ingeniero Budge
        ingenieroBudge.addPaisLimitrofe(bajoFlores);
        ingenieroBudge.addPaisLimitrofe(emebe04PuntaDeRielesNuevos);
        ingenieroBudge.addPaisLimitrofe(villaLanzone);

        //paises limitrofes Isidro Casanova
        isidroCasanova.addPaisLimitrofe(laRodrigoBueno);
        isidroCasanova.addPaisLimitrofe(barrioSanJorge);
        isidroCasanova.addPaisLimitrofe(barrioPiolin);
        isidroCasanova.addPaisLimitrofe(monteChingolo);
        isidroCasanova.addPaisLimitrofe(gonzalezCatan);
        isidroCasanova.addPaisLimitrofe(laCatanga);

        //paises limitrofes Jose C Paz
        joseCPaz.addPaisLimitrofe(laRana);
        joseCPaz.addPaisLimitrofe(virreyDelPino);
        joseCPaz.addPaisLimitrofe(villaItati);
        joseCPaz.addPaisLimitrofe(villaLanzone);
        joseCPaz.addPaisLimitrofe(barrioPiolin);
        joseCPaz.addPaisLimitrofe(laRodrigoBueno);

        //paises limitrofes La 18
        la18.addPaisLimitrofe(dockSud);
        la18.addPaisLimitrofe(fuerteApache);

        //paises limitros La Catanga
        laCatanga.addPaisLimitrofe(laCurita);
        laCatanga.addPaisLimitrofe(bajoFlores);
        laCatanga.addPaisLimitrofe(dockSud);
        laCatanga.addPaisLimitrofe(isidroCasanova);
        laCatanga.addPaisLimitrofe(gonzalezCatan);
        laCatanga.addPaisLimitrofe(villaSoldati);

        //paises limitrofes La Curita
        laCurita.addPaisLimitrofe(laCatanga);
        laCurita.addPaisLimitrofe(villaLanzone);
        laCurita.addPaisLimitrofe(laTranquila);

        //paises limitrofes Laferrere
        laferrere.addPaisLimitrofe(fuerteApache);
        laferrere.addPaisLimitrofe(villaFiorito);
        laferrere.addPaisLimitrofe(elJardin);

        //paises limitrofes La Fraga
        laFraga.addPaisLimitrofe(elJardin);

        //paises limitrofes La Rana
        laRana.addPaisLimitrofe(sanCayetano);
        laRana.addPaisLimitrofe(nuevaPompeya);
        laRana.addPaisLimitrofe(joseCPaz);
        laRana.addPaisLimitrofe(virreyDelPino);

        //paises limitrofes La Rodrigo Bueno
        laRodrigoBueno.addPaisLimitrofe(virreyDelPino);
        laRodrigoBueno.addPaisLimitrofe(joseCPaz);
        laRodrigoBueno.addPaisLimitrofe(barrioPiolin);
        laRodrigoBueno.addPaisLimitrofe(barrioSanJorge);
        laRodrigoBueno.addPaisLimitrofe(villaCarlosGardel);

        //paises limitrofes Las Tunas
        lasTunas.addPaisLimitrofe(villaPalito);
        lasTunas.addPaisLimitrofe(nuevaPompeya);
        lasTunas.addPaisLimitrofe(sanCayetano);

        //paises limitrofes La Tranquila
        laTranquila.addPaisLimitrofe(villaLanzone);
        laTranquila.addPaisLimitrofe(laCurita);
        laTranquila.addPaisLimitrofe(bajoFlores);

        //paises limitrofes Mariano Acosta
        marianoAcosta.addPaisLimitrofe(cuartelV);
        marianoAcosta.addPaisLimitrofe(virreyDelPino);
        marianoAcosta.addPaisLimitrofe(donOrione);
        marianoAcosta.addPaisLimitrofe(constitucion);

        //paises limitrofes Monte Chingolo
        monteChingolo.addPaisLimitrofe(isidroCasanova);
        monteChingolo.addPaisLimitrofe(villaSoldati);

        //paises limitrofes Nordelta
        nordelta.addPaisLimitrofe(emebe04PuntaDeRielesNuevos);
        nordelta.addPaisLimitrofe(puertoMadero);
        nordelta.addPaisLimitrofe(saavedra);

        //paises limitrofes Nueva Pompeya
        nuevaPompeya.addPaisLimitrofe(laRana);
        nuevaPompeya.addPaisLimitrofe(sanCayetano);
        nuevaPompeya.addPaisLimitrofe(lasTunas);
        nuevaPompeya.addPaisLimitrofe(constitucion);
        nuevaPompeya.addPaisLimitrofe(cuartelV);
        nuevaPompeya.addPaisLimitrofe(villaPalito);
        nuevaPompeya.addPaisLimitrofe(virreyDelPino);

        //paises limitrofes Nunez
        nunez.addPaisLimitrofe(saavedra);
        nunez.addPaisLimitrofe(palermo);
        nunez.addPaisLimitrofe(recoleta);

        //paises limitrofes Olivos
        olivos.addPaisLimitrofe(devoto);
        olivos.addPaisLimitrofe(belgrano);
        olivos.addPaisLimitrofe(saavedra);

        //paises limitrofes Once
        once.addPaisLimitrofe(elJardin);

        //paises limitrofes Pablo Mugica
        pabloMugica.addPaisLimitrofe(gonzalezCatan);
        pabloMugica.addPaisLimitrofe(villaSoldati);

        //paises limitrofes Palermo
        palermo.addPaisLimitrofe(saavedra);
        palermo.addPaisLimitrofe(sanIsidro);
        palermo.addPaisLimitrofe(nunez);

        //paises limitrofes Puerto Madero
        puertoMadero.addPaisLimitrofe(sanIsidro);
        puertoMadero.addPaisLimitrofe(nordelta);

        //paises limitrofes Recoleta
        recoleta.addPaisLimitrofe(belgrano);
        recoleta.addPaisLimitrofe(saavedra);
        recoleta.addPaisLimitrofe(nunez);
        recoleta.addPaisLimitrofe(villaPalito);

        //paises limitrofes Saavedra
        saavedra.addPaisLimitrofe(nordelta);
        saavedra.addPaisLimitrofe(sanIsidro);
        saavedra.addPaisLimitrofe(palermo);
        saavedra.addPaisLimitrofe(nunez);
        saavedra.addPaisLimitrofe(recoleta);
        saavedra.addPaisLimitrofe(belgrano);
        saavedra.addPaisLimitrofe(olivos);

        //paises limitrofes San Cayetano
        sanCayetano.addPaisLimitrofe(laRana);
        sanCayetano.addPaisLimitrofe(nuevaPompeya);
        sanCayetano.addPaisLimitrofe(lasTunas);

        //paises limitrofes San Isidro
        sanIsidro.addPaisLimitrofe(puertoMadero);
        sanIsidro.addPaisLimitrofe(palermo);
        sanIsidro.addPaisLimitrofe(saavedra);

        //paises limitrofes Villa 31
        villa31.addPaisLimitrofe(elJardin);

        //paises limitrofes Villa Carlos Gardel
        villaCarlosGardel.addPaisLimitrofe(laRodrigoBueno);
        villaCarlosGardel.addPaisLimitrofe(barrioSanJorge);
        villaCarlosGardel.addPaisLimitrofe(virreyDelPino);

        //paises limitrofes Villa Fiorito
        villaFiorito.addPaisLimitrofe(ciudadOculta);
        villaFiorito.addPaisLimitrofe(dockSud);
        villaFiorito.addPaisLimitrofe(fuerteApache);
        villaFiorito.addPaisLimitrofe(laferrere);

        //paises limitrofes Villa Itati
        villaItati.addPaisLimitrofe(emebe04PuntaDeRielesNuevos);
        villaItati.addPaisLimitrofe(joseCPaz);

        //paises limitrofes Villa Lanzone
        villaLanzone.addPaisLimitrofe(barrioPiolin);
        villaLanzone.addPaisLimitrofe(joseCPaz);
        villaLanzone.addPaisLimitrofe(laCurita);
        villaLanzone.addPaisLimitrofe(laTranquila);
        villaLanzone.addPaisLimitrofe(ingenieroBudge);

        //paises limitrofes Villa Palito
        villaPalito.addPaisLimitrofe(lasTunas);
        villaPalito.addPaisLimitrofe(nuevaPompeya);
        villaPalito.addPaisLimitrofe(recoleta);
        villaPalito.addPaisLimitrofe(constitucion);

        //paises limitrofes Villa Soldati
        villaSoldati.addPaisLimitrofe(gonzalezCatan);
        villaSoldati.addPaisLimitrofe(pabloMugica);
        villaSoldati.addPaisLimitrofe(laCatanga);
        villaSoldati.addPaisLimitrofe(monteChingolo);

        //paises limitrofes Virrey del Pino
        virreyDelPino.addPaisLimitrofe(laRana);
        virreyDelPino.addPaisLimitrofe(nuevaPompeya);
        virreyDelPino.addPaisLimitrofe(cuartelV);
        virreyDelPino.addPaisLimitrofe(marianoAcosta);
        virreyDelPino.addPaisLimitrofe(donOrione);
        virreyDelPino.addPaisLimitrofe(laRodrigoBueno);
        virreyDelPino.addPaisLimitrofe(villaCarlosGardel);
        virreyDelPino.addPaisLimitrofe(joseCPaz);
    }

    public ArrayList<Pais> getPaises() {
        return paises;
    }
}
