package io.github.seujorgenochurras.minecraftjsh.compile.command;

import io.github.seujorgenochurras.minecraftjsh.compile.CompiledJavaCode;
import io.github.seujorgenochurras.minecraftjsh.compile.JavaCompiler;
import io.github.seujorgenochurras.minecraftjsh.minecraft.util.BookUtils;
import org.bukkit.Material;
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


public class Javac implements CommandExecutor {
    private final JavaPlugin javaPlugin;

    public Javac(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player playerSender)) return false;

        //todo validator for this
        ItemStack itemInMainHand = playerSender.getInventory().getItemInMainHand();

        if (itemInMainHand.getType() == Material.WRITTEN_BOOK) {
            BookMeta playerJavaBook = (BookMeta) itemInMainHand.getItemMeta();
            NamespacedKey bytecodeKey = new NamespacedKey(javaPlugin, "bytecode");

            assert playerJavaBook != null;
            String javaCode = BookUtils.getAllBookText(playerJavaBook);
            CompiledJavaCode compiledJavaCode = JavaCompiler.compile(javaCode);

            String humanByteCode = compiledJavaCode.getHumanByteCode();
            byte[] binaryByteCode = compiledJavaCode.getBinaryByteCode();

            ItemStack compiledJavaBookItem = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta newCompiledJavaBook = (BookMeta) compiledJavaBookItem.getItemMeta();
            assert newCompiledJavaBook != null;

            BookUtils.addTextToBook(humanByteCode.replaceAll(" {2}", ""), newCompiledJavaBook);

            newCompiledJavaBook.getPersistentDataContainer().set(bytecodeKey, PersistentDataType.BYTE_ARRAY, binaryByteCode);
            newCompiledJavaBook.setTitle("JavaCode");
            newCompiledJavaBook.setAuthor("Server");
            compiledJavaBookItem.setItemMeta(newCompiledJavaBook);
            playerSender.getInventory().addItem(compiledJavaBookItem);
        }

        return true;
    }
}
