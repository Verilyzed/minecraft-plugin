package de.verilyzed.commands;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class test {

    public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = ((Player) sender).getPlayer();

            if (command.getName().equalsIgnoreCase("test")) {


            }
        } else {
            KrassAlla.log.log(Level.ALL,"You can't execute this command in console.");
        }

    }
}
