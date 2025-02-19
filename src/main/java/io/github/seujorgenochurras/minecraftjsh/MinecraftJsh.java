package io.github.seujorgenochurras.minecraftjsh;

import io.github.seujorgenochurras.minecraftjsh.minecraft.command.*;
import io.github.seujorgenochurras.minecraftjsh.listener.OnPlayerWroteBook;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftJsh extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("world").setExecutor(new KitCommand());
        getCommand("javac").setExecutor(new Javac(this));
        getCommand("java").setExecutor(new Java(this));

        getServer().getPluginManager().registerEvents(new OnPlayerWroteBook(), this);
    }
}
