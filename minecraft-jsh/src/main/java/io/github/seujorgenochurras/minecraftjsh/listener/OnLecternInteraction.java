package io.github.seujorgenochurras.minecraftjsh.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;


public class OnLecternInteraction implements Listener {

    private BookMeta cachedBookData;
    @EventHandler
    @SuppressWarnings("UnstableApiUsage")
    public void onPlayerWroteOnBookEvent(PlayerEditBookEvent bookEvent) {
        if(cachedBookData == null){
            cachedBookData = bookEvent.getNewBookMeta();
            cachedBookData.setPage(1, "FUCK YEAH");
        }
        bookEvent.setNewBookMeta(cachedBookData);
        bookEvent.getPlayer().updateInventory();
    }
}
