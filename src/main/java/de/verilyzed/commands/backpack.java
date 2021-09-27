package de.verilyzed.commands;

import de.verilyzed.krassalla.KrassAlla;
import de.verilyzed.service.UserService;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import java.util.Objects;

public class backpack implements Listener {

    public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("backpack")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                p.sendMessage(KrassAlla.PREFIX + "Opening backpack...");
                Inventory inv = Bukkit.createInventory(p, InventoryType.CHEST, Component.text("Backpack "));
                JSONArray jsonArray = UserService.getBackpack(p.getUniqueId().toString());

                for (Object object : jsonArray) {
                    JSONArray itemArray = (JSONArray) object;

                    ItemStack itemStack = new ItemStack(Material.valueOf((String) itemArray.get(1)));
                    itemStack.setAmount(Integer.parseInt(itemArray.get(2).toString()));
                    inv.setItem(Integer.parseInt(itemArray.get(0).toString()), itemStack);
                }

                p.openInventory(inv);
            }
        }

    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getView().title().toString().equalsIgnoreCase("Backpack")) {
            e.getPlayer().sendMessage(KrassAlla.PREFIX + "Closing Backpack.");
            Inventory inv = e.getInventory();

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
            UserService.setBackpack(e.getPlayer().getUniqueId().toString(), jsonArray);
        }
    }
}
