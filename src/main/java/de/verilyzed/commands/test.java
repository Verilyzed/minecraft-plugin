package de.verilyzed.commands;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class test implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = ((Player) sender).getPlayer();

            if (command.getName().equalsIgnoreCase("test")) {


                return true;
            }
        } else {
            KrassAlla.log("You can't execute this command in console.");
        }

        return false;
    }
}
