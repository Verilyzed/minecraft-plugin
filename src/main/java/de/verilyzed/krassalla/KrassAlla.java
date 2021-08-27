package de.verilyzed.krassalla;

import de.verilyzed.commands.test;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class KrassAlla extends JavaPlugin {

    public static String PREFIX = "§b§6[§2KrassAlla§b§6]§f ";

    @Override
    public void onEnable() {
        // Plugin startup logic
        log("DU pischer ey");

        enableCommands();
    }

    public void enableCommands() {
        getCommand("test").setExecutor(new test());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void log(String text) {
        System.out.println(PREFIX + text);
    }
}
