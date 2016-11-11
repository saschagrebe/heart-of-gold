package de.prolodeck.eddie.heartofgold;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by grebe on 28.10.2016.
 */
public class Eddie {

    private final String serverIP;

    private final int serverPort;

    private Socket clientSocket;

    private final StatusLight upper = new StatusLight(this, StatusLight.StatusLightPosition.UPPER);

    private final StatusLight lower = new StatusLight(this, StatusLight.StatusLightPosition.LOWER);

    public Eddie(final String serverIP, final int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public StatusLight upper() {
        return upper;
    }

    public StatusLight lower() {
        return lower;
    }

    void send(final String cmd) {
        ensureConnection();
        sendInternal(cmd);
    }

    private void sendInternal(final String cmd) {
        // do not close stream or the socket will be closed
        try {
            final DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            //sending the message
            outToServer.writeBytes(cmd + "\n");
            outToServer.flush();
        } catch(IOException e) {
            System.out.println("Cmd produced exception " + e.getMessage());
        }
    }

    private void ensureConnection() {
        if (clientSocket == null || !isConencted()) {
            try {
                clientSocket = new Socket(serverIP, serverPort);
                sendInternal("hello friends");
            } catch(IOException e) {
                System.out.println("Cmd produced exception " + e.getMessage());
            }
        }
    }

    private boolean isConencted() {
        return clientSocket.isConnected();
    }

    public void quit() {
        sendInternal("q");
        sendInternal("");
    }
}
