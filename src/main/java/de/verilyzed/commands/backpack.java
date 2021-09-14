package de.verilyzed.commands;

import de.verilyzed.generic.FileManager;
import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class backpack implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("backpack")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                p.sendMessage(KrassAlla.PREFIX + "Opening backpack...");

                Inventory inv = Bukkit.createInventory(p, InventoryType.CHEST, "Backpack");

                JSONObject jsonObject = FileManager.getJSONObject(p.getUniqueId());

                JSONArray jsonArray = (JSONArray) Objects.requireNonNull(jsonObject).get("backpack");

                for (Object object : jsonArray) {
                    JSONArray itemArray = (JSONArray) object;

                    ItemStack itemStack = new ItemStack(Material.valueOf((String) itemArray.get(1)));
                    itemStack.setAmount(Integer.parseInt(itemArray.get(2).toString()));
                    inv.setItem(Integer.parseInt(itemArray.get(0).toString()), itemStack);
                }

                p.openInventory(inv);
                return true;
            }
        }

        return false;
    }
}
