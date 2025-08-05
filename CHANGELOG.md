# 📄 CHANGELOG

Todas las modificaciones importantes del proyecto serán documentadas en este archivo.

---

## [0.2.0] - 2025-08-05

### Added
- **Sistema completo de pantallas del juego:**
  - MenuPrincipal: Pantalla inicial con navegación a juego, ajustes y salir
  - PantallaSeleccionPersonaje: Selección interactiva de personajes con música temática
  - PantallaJuego: Pantalla principal de juego con interfaz funcional
  - PantallaAjustes: Configuración de audio y cursores personalizados

- **Sistema de personajes completo:**
  - 5 personajes únicos implementados: El Marinero Papá, El Pibe Piola, El Villero, El Mentiroso Rey, El Ratón del Grupo
  - Cada personaje con descripción, historia y habilidad especial única
  - Texturas individuales para cada personaje en el juego
  - Música temática específica para cada personaje durante la selección

- **Sistema de jugadores y mecánicas de juego:**
  - Clase Jugador con gestión de personajes, objetivos y países
  - Sistema de asignación automática de países (42 países del mapa mundial)
  - Distribución equitativa de territorios entre jugadores
  - Sistema de objetivos con clase base abstracta y PlanDominacion implementado

- **Interfaz de usuario avanzada:**
  - Detección de clics en botones con formas geométricas (Rectangle, Circle)
  - Sistema de viewport para resoluciones adaptables (1920x1080 virtual)
  - Efectos visuales: resaltado parpadeante para selección de personajes
  - Cursores personalizados (espada y brújula) con aplicación dinámica

- **Sistema de audio:**
  - Música de fondo para menú principal
  - Música específica para cada personaje
  - Control de volumen con barra deslizante funcional
  - Efectos de sonido para interacciones de botones

- **Gestión de países y territorios:**
  - 42 países implementados distribuidos en 6 continentes
  - Sistema de propietarios para cada país
  - Asignación automática aleatoria de territorios al inicio

### Changed
- Estructura del proyecto reorganizada con separación clara de clases
- Sistema de navegación entre pantallas mejorado con parámetros de contexto
- Gestión mejorada de recursos gráficos y de audio con dispose() apropiado

### Technical Improvements
- Implementación de patrones de diseño: herencia con clases abstractas (Personaje, Objetivo)
- Sistema de coordenadas unificado con transformación de input
- Gestión eficiente de memoria con liberación de texturas y audio
- Manejo de errores robusto para carga de recursos multimedia

### Fixed
- Corrección en la gestión de memoria para evitar memory leaks
- Manejo de excepciones para recursos de audio faltantes
- Validación de estados de objetos antes de operaciones (null checks)

---

## [0.1.0] - 2025-05-29

### Added
- Estructura inicial del proyecto creada con LibGDX.
- Configuración de módulos: core, desktop y assets.
- Documentación base agregada: README.md y CHANGELOG.md.
- Propuesta formal del juego digital basada en T.E.G. con alcance mínimo y deseable definido.
- Se definen las mecánicas principales del juego (turnos, refuerzos, ataques, poderes especiales).
- Se detalla la arquitectura: cliente-servidor, trabajo iterativo y control de versiones con Git.

---

## [Unreleased]

### Próximamente
- Sistema de combate entre territorios
- Mecánicas de turnos y refuerzos
- Implementación de habilidades especiales de personajes
- Sistema multijugador en red
- Efectos visuales y animaciones avanzadas

