package com.miempresa.mijuego.network;

import com.miempresa.mijuego.bussiness.GameController;
import com.miempresa.mijuego.model.enums.FaseJuegoEnum;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ClientThread extends Thread {

    private DatagramSocket socket;
    private final int serverPort = 5555;
    private InetAddress ipServer;         // se fijará al recibir "Connected:<id>"
    private volatile boolean end = false; // permite terminar el hilo
    private final GameController gameController;

    public ClientThread(GameController gameController) {
        this.gameController = gameController;
        try {
            this.ipServer = InetAddress.getByName("255.255.255.255");
            this.socket = new DatagramSocket();
            this.socket.setBroadcast(true);
            this.socket.setSoTimeout(600);
        } catch (IOException e) {
            throw new RuntimeException("Error inicializando cliente UDP", e);
        }
    }

    @Override
    public void run() {
        while (!end) {
            try {
                byte[] buffer = new byte[2048];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                processMessage(packet);
            } catch (SocketTimeoutException ste) {
                // Timeout: vuelve a chequear si 'end' cambió.
            } catch (IOException e) {
                if (!end) System.err.println("Error en recepción UDP: " + e.getMessage());
            }
        }
        if (socket != null && !socket.isClosed()) socket.close();
    }

    private void processMessage(DatagramPacket packet) {
        String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8).trim();
        System.out.println("Mensaje recibido: " + message);

        String[] parts = message.split(":");
        if (parts.length == 0) return;

        String kind = parts[0];

        switch (kind) {
            case "AlreadyConnected":
                System.out.println("⚠️ Ya estás conectado.");
                break;

            case "Connected": {
                this.ipServer = packet.getAddress(); // fijamos IP real del server
                int numPlayer = (parts.length > 1) ? safeInt(parts[1], 1) : 1;
                gameController.connect(numPlayer);
                break;
            }

            case "Full":
                System.out.println("Servidor lleno");
                end = true;
                break;

            case "Start":
                gameController.start();
                break;

            case "EndGame": {
                int winner = (parts.length > 1) ? safeInt(parts[1], -1) : -1;
                gameController.endGame(winner);
                break;
            }

            case "Disconnect":
                gameController.backToMenu();
                break;

            case "Turn": {
                int pid = (parts.length > 1) ? safeInt(parts[1], -1) : -1;
                if (pid >= 0) gameController.onTurn(pid);
                break;
            }

            case "Phase":
                if (parts.length > 1) {
                    try {
                        FaseJuegoEnum fase = FaseJuegoEnum.valueOf(parts[1]);
                        gameController.onPhase(fase);
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Phase inválida: " + parts[1]);
                    }
                }
                break;

            case "Pais": {
                // Formato: Pais:<name>:<ownerId>:<tropas>
                if (parts.length >= 4) {
                    String name = parts[1];
                    int ownerId = safeInt(parts[2], -1);
                    int tropas  = safeInt(parts[3], -1);
                    if (ownerId >= 0 && tropas >= 0) {
                        gameController.onPaisUpdate(name, ownerId, tropas);
                    } else {
                        System.out.println("Pais msg mal formado: " + message);
                    }
                }
                break;
            }

            case "Info": {
                String prefix = "Info:";
                String cuerpo;
                if (message.startsWith(prefix)) {
                    cuerpo = message.substring(prefix.length()).trim();
                } else if (parts.length > 1) {
                    cuerpo = message.substring(message.indexOf(':') + 1).trim();
                } else {
                    cuerpo = "";
                }

                if (!cuerpo.isEmpty()) {
                    gameController.onInfo(cuerpo);
                }
                break;
            }

            case "Error":
                if (parts.length > 1) gameController.onError(parts[1]);
                break;

            default:
                System.out.println("⚙️ Mensaje no reconocido: " + message);
                break;
        }
    }

    private int safeInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }

    // ============================
    // ====== MÉTODOS ENVÍO ======
    // ============================

    public void sendMessage(String message) {
        if (ipServer == null) return; // todavía no conectado
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(data, data.length, ipServer, serverPort);
        try {
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("Error enviando UDP: " + e.getMessage());
        }
    }

    public void sendConnect() { sendMessage("Connect"); }

    public void sendClick(String pais) { sendMessage("Click:" + pais); }

    public void sendAttack(String from, String to) {
        sendMessage("Attack:" + from + ":" + to);
    }

    public void sendGroup(String from, String to, int amount) {
        sendMessage("Group:" + from + ":" + to + ":" + amount);
    }

    public void sendEndTurn() { sendMessage("EndTurn"); }

    public void sendReinforce(String pais) { sendMessage("Reinforce:" + pais); }

    public void sendPersonaje(String nombrePersonaje) {
        sendMessage("Personaje:" + nombrePersonaje);
    }

    public void terminate() {
        end = true;
        interrupt();
        if (socket != null && !socket.isClosed()) socket.close();
    }
}
