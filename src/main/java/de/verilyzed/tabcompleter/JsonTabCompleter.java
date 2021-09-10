package de.verilyzed.tabcompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class JsonTabCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("json")) {
            List<String> tabList = new ArrayList<>();
            tabList.add("modify");
            tabList.add("read");

            List<String> tabSender = new ArrayList<>();

            if (args.length == 1) {
                for (String tab : tabList) {
                    if (tab.toLowerCase().contains(args[0].toLowerCase())) {
                        tabSender.add(tab);
                    }
                }

            } else if (args.length == 2) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getName().toLowerCase().contains(args[1].toLowerCase())) {
                        tabSender.add(p.getName());
                    }
                }
            } else if (args.length == 3) {
                tabSender.add("<key>");
            } else if (args.length == 4 && args[0].equalsIgnoreCase("modify")) {
                tabSender.add("<value>");
            }

            if (args.length == 0) {
                tabSender = tabList;
            }

            return tabSender;
        }

        return null;
    }
}
