package io.github.seujorgenochurras.minecraftjsh.listener;

import io.github.seujorgenochurras.minecraftjsh.antlr.minecraft.MinecraftSyntaxHighlight;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;


public class OnLecternInteraction implements Listener {


    @EventHandler
    @SuppressWarnings("UnstableApiUsage")
    public void onPlayerWroteOnBookEvent(PlayerEditBookEvent bookEvent) {
        BookMeta bookMeta = bookEvent.getNewBookMeta();

        int pageCount = bookMeta.getPageCount();
        for (int i = 1; i <= pageCount; i++) {
            String currentPage = bookMeta.getPage(i);
            currentPage = currentPage.replaceAll("§.", "");

            currentPage = MinecraftSyntaxHighlight.handle(currentPage, bookEvent.getPlayer());
            bookMeta.setPage(i, currentPage);
        }

        bookEvent.setNewBookMeta(bookMeta);
        bookEvent.getPlayer().updateInventory();
    }
}