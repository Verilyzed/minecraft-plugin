package de.verilyzed.events;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class onInventoryCloseEvent implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("Backpack")) {
            e.getPlayer().sendMessage(KrassAlla.PREFIX + "Closing Backpack.");
        }
    }
}
