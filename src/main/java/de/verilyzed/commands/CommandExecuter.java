package de.verilyzed.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class CommandExecuter implements org.bukkit.command.CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch(command.getName().toLowerCase()) {
            case "money":
                new money().onCommand(sender, command, args);
                break;
            case "backpack":
                new backpack().onCommand(sender, command);
                break;
            case "buy":
                new buy().onCommand(sender, command, args);
                break;
            case "bounty":
                new bounty().onCommand(sender, command, args);
                break;
            case "echo":
                new echo().onCommand(sender, command, args);
                break;
            case "test":
                new test().onCommand(sender, command);
                break;
            case "json":
                new json().onCommand(sender, command, args);
                break;

        }
        return true;
    }
}