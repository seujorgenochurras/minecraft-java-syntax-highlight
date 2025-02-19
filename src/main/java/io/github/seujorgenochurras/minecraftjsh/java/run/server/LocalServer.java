package io.github.seujorgenochurras.minecraftjsh.java.run.server;

import io.github.seujorgenochurras.minecraftjsh.java.run.server.event.LocalServerEvent;
import io.github.seujorgenochurras.minecraftjsh.java.run.server.listener.LocalServerListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalServer {
    private final ServerSocket serverSocket;
    private final ArrayList<Integer> integers = new ArrayList<>();

    private final HashMap<String, Consumer<LocalServerEvent>> listeners = new HashMap<>();
    private static final Logger logger = Logger.getLogger(LocalServer.class.getName());

    private LocalServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static LocalServer initLocalServer(int port) {
        try {
            return new LocalServer(new ServerSocket(port));
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "could not start server");
        }
        return null;
    }

    public void addListener(LocalServerListener localServerListener) {
        listeners.put(localServerListener.getCommandName(), localServerListener.getEventHandler());
    }

    public LocalServer openConnection() {
        new Thread(() -> {
            while (!serverSocket.isClosed()) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream())) {

                    String commandText = clientInput.readLine();

                    String[] clientArgs = commandText.split(":", 2);
                    String clientCommandName = clientArgs[0];
                    String clientCommandArgs = clientArgs[1];
                    Consumer<LocalServerEvent> clientCommandListener = listeners.get(clientCommandName);

                    if (clientCommandListener == null) {
                        clientOutput.println("ERR INVALID COMMAND " + clientCommandName);
                        continue;
                    }
                    LocalServerEvent event = new LocalServerEvent(clientCommandArgs, clientOutput);
                    clientCommandListener.accept(event);

                } catch (IOException e) {
                    e.printStackTrace();
                    logger.log(Level.SEVERE, "could not start server");
                }
            }
        }).start();
        return this;
    }

}
