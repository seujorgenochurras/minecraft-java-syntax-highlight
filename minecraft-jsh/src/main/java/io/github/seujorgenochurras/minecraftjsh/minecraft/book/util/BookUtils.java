package io.github.seujorgenochurras.minecraftjsh.minecraft.book.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class BookUtils {
    private BookUtils() {
    }

    public static String getAllBookText(BookMeta bookMeta) {
        return String.join(" ", bookMeta.getPages());
    }

    

    public static void addTextToBook(String text, BookMeta bookMeta) {
        final int MAX_CHAR_PAGE = 255;
        for (int i = 0; i < text.length() / MAX_CHAR_PAGE + 1; i++) {
            int firstChar = i * MAX_CHAR_PAGE;

            if (firstChar + MAX_CHAR_PAGE > text.length()) {
                bookMeta.addPage(text.substring(firstChar));
            } else {
                bookMeta.addPage(text.substring(firstChar, firstChar + MAX_CHAR_PAGE));
            }
        }
    }

    public static boolean isCompiledJavaBook(ItemStack itemStack, JavaPlugin javaPlugin) {
        if (!itemStack.hasItemMeta()) {
            return false;
        }
        if (itemStack.getItemMeta() instanceof BookMeta bookMeta) {
            return bookMeta.getTitle().equals("JavaCode") &&
                    bookMeta.getAuthor().equals("Server") &&
                    bookMeta.getPersistentDataContainer()
                            .has(new NamespacedKey(javaPlugin, "bytecode"), PersistentDataType.BYTE_ARRAY);

        }
        return false;
    }
}
