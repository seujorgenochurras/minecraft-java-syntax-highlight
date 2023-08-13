package io.github.seujorgenochurras.minecraftjsh.minecraft.command;

import com.google.common.util.concurrent.ClosingFuture;
import io.github.seujorgenochurras.minecraftjsh.java.compile.shell.Shell;
import io.github.seujorgenochurras.minecraftjsh.java.run.server.LocalServer;
import io.github.seujorgenochurras.minecraftjsh.java.run.server.listener.OnBroadcastServerMessage;
import io.github.seujorgenochurras.minecraftjsh.java.run.util.ByteUtils;
import io.github.seujorgenochurras.minecraftjsh.minecraft.book.util.BookUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;


public class Java implements CommandExecutor {
    private final JavaPlugin javaPlugin;
    private final NamespacedKey namespacedKey;

    private final LocalServer localServer;

    public Java(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        namespacedKey = new NamespacedKey(javaPlugin, "bytecode");


        localServer = LocalServer.initLocalServer(6969);
        assert localServer != null;

        localServer.addListener(new OnBroadcastServerMessage(null));

        localServer.openConnection();
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            ItemStack playerItemInHand = player.getInventory().getItemInMainHand();
            if (BookUtils.isCompiledJavaBook(playerItemInHand, javaPlugin)) {
                BookMeta playerBook = (BookMeta) playerItemInHand.getItemMeta();
                assert playerBook != null;

                byte[] bytecode = playerBook.getPersistentDataContainer()
                        .get(namespacedKey, PersistentDataType.BYTE_ARRAY);
                assert bytecode != null;
                char[] bytecodeHex = ByteUtils.toHexCharArray(bytecode);
                String formattedHex = formatHex(bytecodeHex);
                Shell.execute("bash", "-c", "echo -ne \"" + formattedHex + "\" > Main.class");

                var broadcastMessageListener = new OnBroadcastServerMessage(player);
                localServer.addListener(broadcastMessageListener);
                Shell.execute("bash", "-c", "echo execBytecode:$(/usr/bin/java Main) | nc 127.0.0.1 6969");
            }
        }
        return true;
    }

    private static String formatHex(char[] hexArray) {
        int currentIndex = 0;
        StringBuilder formattedHex = new StringBuilder();
        for (char c : hexArray) {
            if (currentIndex % 2 == 0) {
                formattedHex.append("\\\\x");
            }
            formattedHex.append(c);
            currentIndex++;
        }
        return formattedHex.toString();
    }
}
