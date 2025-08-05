# 🗺️ Proyecto: Plan Maestro

## 👥 Integrantes del Grupo
- Román Rey

## 🎮 Descripción General del Proyecto

*Plan Maestro* es un videojuego de estrategia por turnos para PC, inspirado en el clásico juego de mesa argentino T.E.G. Los jugadores competirán por conquistar territorios en un mapa mundial, utilizando tácticas militares, gestión de tropas y habilidades especiales de personajes únicos. 

El juego presenta **5 personajes únicos** con trasfondos narrativos propios y habilidades especiales que afectan el gameplay. Cada jugador recibe un objetivo secreto que debe cumplir para obtener la victoria. El prototipo actual incluye un sistema completo de menús, selección de personajes, distribución automática de territorios y una interfaz de juego funcional.

**Características del universo narrativo:**
- Personajes con historias inmersivas: El Marinero Papá, El Pibe Piola, El Villero, El Mentiroso Rey y El Ratón del Grupo
- Sistema de objetivos estratégicos (actualmente implementado: Plan Dominación)
- Mapa mundial con 42 países distribuidos en 6 continentes

**Enlace a la Wiki del Proyecto (Propuesta Detallada):**  
[Ver la Propuesta Completa del Proyecto aquí](https://github.com/romanreyy/Plan-Maestro/wiki/Porpuesta-de-Tema)

## 🎥 Trailer

Podés ver una demostración en el siguiente video:  
[▶️ Ver video de avance en Google Drive](https://drive.google.com/file/d/1iaQQVuStJvs_-d3Dk3H9DJW9VwMi7bec/view?usp=sharing)

## ✨ Características Implementadas

### 🎯 Sistema de Personajes
- **5 personajes únicos** con descripciones narrativas completas
- **Habilidades especiales** definidas para cada personaje
- **Texturas individuales** para representación visual en el juego
- **Música temática** específica para cada personaje durante la selección

### 🖥️ Interfaz de Usuario Completa
- **Menú Principal** con navegación fluida entre opciones
- **Pantalla de Selección de Personajes** con selección interactiva y efectos visuales
- **Pantalla de Juego** con interfaz funcional y controles intuitivos
- **Pantalla de Ajustes** con configuración de audio y cursores personalizados

### 🌍 Sistema de Territorios
- **42 países implementados** distribuidos geográficamente en:
  - América del Norte (9 países)
  - América del Sur (6 países)
  - Europa (8 países)
  - Asia (12 países)
  - África (6 países)
  - Oceanía (4 países)
- **Distribución automática** de territorios entre jugadores
- **Sistema de propietarios** para gestión de control territorial

### 🎵 Sistema de Audio
- **Música de fondo** para menú principal
- **Efectos de sonido** para interacciones
- **Control de volumen** con barra deslizante funcional
- **Música temática** para cada personaje

### 🎮 Mecánicas de Juego Base
- **Sistema de objetivos** con clase base abstracta
- **Gestión de jugadores** con asignación de personajes y territorios
- **Interfaz adaptable** con resolución virtual 1920x1080
- **Detección de clics** precisa en botones con formas geométricas

## 🧰 Tecnologías Utilizadas

- **Lenguaje:** Java SE 17
- **Framework:** LibGDX (gráficos 2D, eventos, audio, gestión de recursos)
- **Librerías adicionales:** Ashley ECS, Box2D, FreeType
- **Arquitectura:** Cliente-Servidor para modo multijugador online (planificado)
- **Control de versiones:** Git
- **Entorno de desarrollo principal:** Eclipse IDE  
  *(compatible también con IntelliJ IDEA si se configura adecuadamente)*
- **Plataformas objetivo:** PC (escritorio), con posibilidad futura de expansión a Web o Móvil

## ⚙️ Cómo Compilar y Ejecutar

### Prerrequisitos
- **Java SE 17** instalado en el sistema
- **Git** para clonar el repositorio
- **Eclipse IDE** (recomendado) o IntelliJ IDEA

### Pasos de instalación

1. **Clonar el repositorio:**
    ```bash
    git clone https://github.com/romanreyy/Plan-Maestro.git
    cd Plan-Maestro
    ```

2. **Importar el proyecto en Eclipse:**
    - Abrí Eclipse
    - Seleccioná **File > Import > Gradle > Existing Gradle Project**
    - Navegá hasta la carpeta donde clonaste el repositorio
    - Hacé clic en **Finish**. Eclipse descargará las dependencias automáticamente

3. **Verificar configuración de Java:**
    - Asegurate de tener **Java SE 17** configurado como JDK en Eclipse
    - Verificá que el proyecto esté usando la versión correcta en **Project Properties > Java Build Path**

4. **Ejecutar el proyecto:**
    - Navegá a la carpeta: `desktop/src/main/java`
    - Buscá la clase principal del launcher (típicamente `DesktopLauncher.java`)
    - Hacé clic derecho sobre el archivo
    - Seleccioná **Run As > Java Application**

### Recursos necesarios
El juego requiere los siguientes archivos de recursos en la carpeta `assets/`:
- Texturas de interfaz (`.png`)
- Música y efectos de sonido (`.ogg`, `.mp3`)
- Texturas de personajes y elementos del juego

## 📌 Estado Actual del Proyecto

> **Prototipo funcional con sistema completo de interfaz y mecánicas base.**

### ✅ Completado
- Sistema completo de pantallas y navegación
- 5 personajes únicos implementados con características propias
- Sistema de distribución automática de 42 países
- Interfaz de usuario funcional con efectos visuales
- Sistema de audio completo con música temática
- Gestión de objetivos y jugadores
- Controles de configuración (volumen, cursores personalizados)

### 🔄 En desarrollo
- Sistema de combate entre territorios
- Mecánicas de turnos y refuerzos
- Implementación práctica de habilidades especiales de personajes
- Sistema de victoria por objetivos

### 🔮 Próximamente
- Sistema multijugador en red
- Efectos visuales y animaciones avanzadas
- Más objetivos y variantes de juego
- Optimizaciones de rendimiento y pulido visual

---

**Versión actual:** 0.2.0  
**Última actualización:** Agosto 2025
