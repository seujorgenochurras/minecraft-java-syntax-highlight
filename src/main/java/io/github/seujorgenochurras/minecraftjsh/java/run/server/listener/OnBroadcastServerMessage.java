package io.github.seujorgenochurras.minecraftjsh.java.run.server.listener;

import io.github.seujorgenochurras.minecraftjsh.java.run.server.event.LocalServerEvent;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class OnBroadcastServerMessage implements LocalServerListener{
    private final Player sender;

    public OnBroadcastServerMessage(Player sender) {
        this.sender = sender;
    }

    @Override
    public Consumer<LocalServerEvent> getEventHandler() {
        return (localServerEvent -> {
            if(sender == null) {
                System.out.println("sender was null");
                localServerEvent.eventOutput().println(localServerEvent.eventArg());
                return;
            }
            sender.sendMessage(localServerEvent.eventArg());
        });
    }

    @Override
    public String getCommandName() {
        return "execBytecode";
    }
}
