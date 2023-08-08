package io.github.seujorgenochurras.minecraftjsh.minecraft.util;

import org.bukkit.inventory.meta.BookMeta;

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
}
