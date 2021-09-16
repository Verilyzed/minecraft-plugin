package de.verilyzed.generic;

import org.bukkit.command.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericTabCompleter {
    /**
     * Generic tab argument: Arg1/Arg2/Arg3
     */

    private boolean[] caseSensitive;
    private boolean[] suggestions;
    private String commandString = "";
    private List<List<String>> tabCompleter;

    public List<String> tabCompleter(Command command, String[] args) {
        if (command.getName().equalsIgnoreCase(commandString)) {
            List<String> returnedTabs = new ArrayList<>();

            if (args.length > tabCompleter.size()) {
                return null;
            } else {
                int i = args.length - 1;
                args[i] = caseSensitive[i] ? args[i].toLowerCase() : args[i];

                outer:
                for (String tab : tabCompleter.get(i)) {
                    tab = caseSensitive[i] ? tab.toLowerCase() : tab;
                    String[] tabs = tab.split("/");

                    if (tabs.length > 1) {
                        for (int r = 0; r < tabs.length; r++) {
                            if (r != tabs.length - 1) {
                                if (!(args[r].equals(tabs[r]) || tabs[r].equals("<Player>"))) {
                                    break outer;
                                }
                            }
                        }
                    }

                    if (suggestions[i] && !args[i].equals(" ")) {
                        if (tabs[tabs.length - 1].contains(args[i])) {
                            returnedTabs.add(tab);
                        }
                    } else {
                        returnedTabs.add(tabs[tabs.length - 1]);
                    }
                }
            }

            return returnedTabs;
        }

        return null;
    }

    public void initialize(String commandString, List<List<String>> tabCompleter, boolean[] caseSensitive, boolean[] suggestions) {
        this.commandString = commandString;
        this.tabCompleter = tabCompleter;
        this.caseSensitive = caseSensitive;
        this.suggestions = suggestions;
    }

    /**
     * public void initialize(String commandString, List<List<String>> tabCompleter, boolean caseSensitive, boolean suggestions) {
     * boolean[] _caseSensitive = new boolean[tabCompleter.size()];
     * Arrays.fill(_caseSensitive, caseSensitive);
     * <p>
     * boolean[] _suggestions = new boolean[tabCompleter.size()];
     * Arrays.fill(_suggestions, suggestions);
     * <p>
     * initialize(commandString, tabCompleter, _caseSensitive, _suggestions);
     * }
     * <p>
     * public void initialize(String commandString, List<List<String>> tabCompleter, boolean[] caseSensitive, boolean suggestions) {
     * boolean[] _suggestions = new boolean[tabCompleter.size()];
     * Arrays.fill(_suggestions, suggestions);
     * <p>
     * initialize(commandString, tabCompleter, caseSensitive, _suggestions);
     * }
     */
    public void initialize(String commandString, List<List<String>> tabCompleter, boolean caseSensitive, boolean[] suggestions) {
        boolean[] _caseSensitive = new boolean[tabCompleter.size()];
        Arrays.fill(_caseSensitive, caseSensitive);

        initialize(commandString, tabCompleter, _caseSensitive, suggestions);
    }
}
