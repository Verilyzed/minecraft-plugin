package de.verilyzed.events;

import de.verilyzed.krassalla.KrassAlla;
import de.verilyzed.service.UserService;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import java.util.Objects;

public class onInventoryCloseEvent implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        TextComponent tc = (TextComponent) e.getView().title();
        System.out.println(tc.content().toCharArray());
        if (!tc.content().contains("Backpack")) {
            return;
        }
        e.getPlayer().sendMessage(KrassAlla.PREFIX + "Closing Backpack.");
        Inventory inv = e.getInventory();

        JSONObject obj = new JSONObject();
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) != null) {
                ItemStack item = inv.getItem(i);
                JSONObject itemJSON = new JSONObject();
                itemJSON.put("id", i);
                itemJSON.put("material", Objects.requireNonNull(item).getType().toString());
                itemJSON.put("amount", item.getAmount());
                obj.put(i, itemJSON);
            }
        }
        UserService.setBackpack(e.getPlayer().getUniqueId().toString(), obj);

    }
}
