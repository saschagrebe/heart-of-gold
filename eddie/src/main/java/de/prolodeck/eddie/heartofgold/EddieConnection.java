package de.prolodeck.eddie.heartofgold;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.prolodeck.eddie.configuration.CollectorConfig;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by grebe on 11.11.2016.
 */
@Singleton
public class EddieConnection {

    private final String serverIP;

    private final int serverPort;

    private Socket clientSocket;

    @Inject
    EddieConnection(final CollectorConfig config) {
        this.serverIP = config.getServerIp();
        this.serverPort = config.getServerPort();
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
