package io.github.seujorgenochurras.minecraftjsh;

import io.github.seujorgenochurras.minecraftjsh.command.KitCommand;
import io.github.seujorgenochurras.minecraftjsh.listener.OnLecternInteraction;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftJsh extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("world").setExecutor(new KitCommand());
        getServer().getPluginManager().registerEvents(new OnLecternInteraction(), this);
    }
}
