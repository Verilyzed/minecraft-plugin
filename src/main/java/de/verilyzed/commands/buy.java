package de.verilyzed.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class buy {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("buy")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                Inventory inv = Bukkit.createInventory(p, 9, Component.text("Buy"));
                p.openInventory(inv);
            }
            return true;
        }
        return false;
    }
}
