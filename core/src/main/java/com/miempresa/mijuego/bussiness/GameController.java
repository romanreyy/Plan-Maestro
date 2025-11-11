package com.miempresa.mijuego.bussiness;

import com.miempresa.mijuego.model.enums.FaseJuegoEnum;

public interface GameController {
    // Handshake / ciclo de vida
    void connect(int numPlayer);   // llega "Connected:<id>"
    void start();                  // llega "Start"
    void endGame(int winner);      // llega "EndGame:<id>"
    void backToMenu();             // llega "Disconnect"

    // Actualizaciones de estado en tiempo real (enviadas por el server)
    void onTurn(int playerId);                         // llega "Turn:<id>"
    void onPhase(FaseJuegoEnum fase);                  // llega "Phase:<NOMBRE>"
    void onPaisUpdate(String name, int ownerId, int tropas); // llega "Pais:<name>:<owner>:<tropas>"

    // Mensajería
    void onInfo(String msg);   // llega "Info:<texto>"
    void onError(String msg);  // llega "Error:<texto>"
}
