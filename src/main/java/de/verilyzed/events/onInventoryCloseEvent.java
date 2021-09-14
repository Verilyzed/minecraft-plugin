package de.verilyzed.events;

import de.verilyzed.generic.FileManager;
import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class onInventoryCloseEvent implements Listener {
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("Backpack")) {
            e.getPlayer().sendMessage(KrassAlla.PREFIX + "Closing Backpack.");

            Inventory inv = e.getInventory();

            JSONObject jsonObject = FileManager.getJSONObject(e.getPlayer().getUniqueId());

            Objects.requireNonNull(jsonObject).remove("backpack");

            JSONArray jsonArray = new JSONArray();

            for (int i = 0; i < inv.getSize(); i++) {
                if (inv.getItem(i) != null) {
                    ItemStack item = inv.getItem(i);

                    JSONArray itemArray = new JSONArray();
                    itemArray.add(i);
                    itemArray.add(Objects.requireNonNull(item).getType().toString());
                    itemArray.add(item.getAmount());

                    jsonArray.add(itemArray);
                }
            }

            jsonObject.put("backpack", jsonArray);

            FileManager.setJSONObject(e.getPlayer().getUniqueId(), jsonObject);
        }
    }
}
