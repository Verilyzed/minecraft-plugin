package de.verilyzed.commands;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class echo implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (command.getName().equalsIgnoreCase("echo")) {
                if (strings.length != 0){
                    for (String string : strings) {
                        p.sendMessage(KrassAlla.PREFIX + string.toUpperCase());
                    }

                    return true;
                }
            }
        }

        return false;
    }
}
