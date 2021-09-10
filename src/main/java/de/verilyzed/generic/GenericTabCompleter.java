package de.verilyzed.generic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericTabCompleter {

    private boolean[] caseSensitive;
    private boolean[] suggestions;
    private String commandString = "";
    private List<String>[] tabCompleter;

    public List<String> tabCompleter(Command command, String[] args) {
        if (command.getName().equalsIgnoreCase(commandString)) {
            List<String> returnedTabs = new ArrayList<>();

            if (args.length > tabCompleter.length) {
                return null;
            } else {
                int i = args.length - 1;
                args[i] = caseSensitive[i] ? args[i].toLowerCase() : args[i];

                for (String tab : tabCompleter[i]) {
                    tab = caseSensitive[i] ? tab.toLowerCase() : tab;

                    if (suggestions[i]) {
                        if (tab.contains(args[i])) {
                            returnedTabs.add(tab);
                        }
                    } else {
                        returnedTabs.add(tab);
                    }
                }
            }

            return returnedTabs;
        }

        return null;
    }

    public void initialize(String commandString, List<String>[] tabCompleter, boolean[] caseSensitive, boolean[] suggestions) {
        this.commandString = commandString;
        this.tabCompleter = tabCompleter;
        this.caseSensitive = caseSensitive;
        this.suggestions = suggestions;
    }

    public void initialize(String commandString, List<String>[] tabCompleter, boolean caseSensitive, boolean suggestions) {
        boolean[] _caseSensitive = new boolean[tabCompleter.length];
        Arrays.fill(_caseSensitive, caseSensitive);

        boolean[] _suggestions = new boolean[tabCompleter.length];
        Arrays.fill(_suggestions, suggestions);

        initialize(commandString, tabCompleter, _caseSensitive, _suggestions);
    }
}
