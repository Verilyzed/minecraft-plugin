package de.verilyzed.commands;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class echo implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender instanceof Player) {
            Player p = ((Player) sender).getPlayer();
            if (command.getName().equalsIgnoreCase("echo")) {
                if (strings.length != 0){
                    for (int i = 0; i < strings.length; i++) {
                        sender.sendMessage(KrassAlla.PREFIX + strings[i].toUpperCase());
                    }

                    return true;
                }
            }
        }

        return false;
    }
}
