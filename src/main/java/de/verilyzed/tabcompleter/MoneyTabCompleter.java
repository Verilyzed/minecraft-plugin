package de.verilyzed.tabcompleter;

import de.verilyzed.generic.GenericTabCompleter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MoneyTabCompleter implements TabCompleter {

    GenericTabCompleter genericTabCompleter = new GenericTabCompleter();

    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {


        return genericTabCompleter.tabCompleter(command, args);
    }
}

