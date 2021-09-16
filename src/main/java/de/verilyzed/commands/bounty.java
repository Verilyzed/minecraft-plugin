package de.verilyzed.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class bounty {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("bounty")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("add")) {

                }

                if (args[0].equalsIgnoreCase("list")) {

                }
            }
        }

        return false;
    }
}
