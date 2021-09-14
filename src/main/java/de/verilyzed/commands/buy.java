package de.verilyzed.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class buy implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("buy")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                Inventory inv = Bukkit.createInventory(p, 9, "Buy");

                p.openInventory(inv);
            }

            return true;
        }

        return false;
    }
}
