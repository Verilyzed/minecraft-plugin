package de.verilyzed.tabcompleter;

import de.verilyzed.generic.GenericTabCompleter;
import de.verilyzed.generic.Other;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MoneyTabCompleter implements TabCompleter {

    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        GenericTabCompleter gtc = new GenericTabCompleter();

        List<String> tab1 = Arrays.asList("add", "give");
        List<String> tab2 = Other.getOnlinePlayerNames();
        tab2.add("add/<Player>/<Betrag>");
        List<String> tab3 = Collections.singletonList("give/<Player>/<Betrag>");

        List<List<String>> tabs = Arrays.asList(tab1, tab2, tab3);

        boolean[] suggestions = {true, true, false};

        gtc.initialize(command.getName(), tabs, false, suggestions);

        return gtc.tabCompleter(command, args);
    }
}

