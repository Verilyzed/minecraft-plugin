package de.verilyzed.commands;

import de.verilyzed.krassalla.KrassAlla;
import de.verilyzed.service.UserService;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class backpack {

    public void onCommand(@NotNull CommandSender sender, @NotNull Command command) {

        if (command.getName().equalsIgnoreCase("backpack")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                p.sendMessage(KrassAlla.PREFIX + "Opening backpack...");
                Inventory inv = Bukkit.createInventory(p, InventoryType.CHEST, Component.text("Backpack "));
                JSONObject jsonObject = UserService.getBackpack(p.getUniqueId().toString());
                KrassAlla.log.log(new LogRecord(Level.FINE, jsonObject.toJSONString()));
                for (String key : (Iterable<String>) jsonObject.keySet()) {
                    if (jsonObject.get(key) instanceof JSONObject) {
                        JSONObject help = (JSONObject) jsonObject.get(key);
                        ItemStack itemStack = new ItemStack(Material.valueOf((String) help.get("material")));
                        itemStack.setAmount(Integer.parseInt(help.get("amount").toString()));
                        inv.setItem(Integer.parseInt(help.get("id").toString()), itemStack);
                    }
                }
                p.openInventory(inv);
            }
        }
    }
}
