package io.github.seujorgenochurras.minecraftjsh.java.run.server.listener;

import io.github.seujorgenochurras.minecraftjsh.java.run.server.event.LocalServerEvent;

import java.util.function.Consumer;

public interface LocalServerListener {

    Consumer<LocalServerEvent> getEventHandler();

    String getCommandName();
}
