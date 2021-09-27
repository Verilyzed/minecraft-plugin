package de.verilyzed.krassalla;

import co.aikar.idb.BukkitDB;
import co.aikar.idb.Database;
import de.verilyzed.commands.CommandExecuter;
import de.verilyzed.commands.backpack;
import de.verilyzed.events.onPlayerJoinEvent;
import de.verilyzed.events.onPlayerQuitEvent;
import de.verilyzed.generic.BusinessLogic;
import de.verilyzed.tabcompleter.JsonTabCompleter;
import de.verilyzed.tabcompleter.MoneyTabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public final class KrassAlla extends JavaPlugin {

    public static String PREFIX;
    public static BusinessLogic logic;
    Database db;
    public static String dataFolder;
    public static JavaPlugin plugin;

    public static void log(String text) {
        System.out.println(PREFIX + text);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadConfig();
        enableCommands();
        plugin = this;
        enableTabCompleter();
        enableListener();
        db = BukkitDB.createHikariDatabase(this, "root", "MyNewPass", "minecraft", "localhost:3306");
        logic = new BusinessLogic();
        log("Plugin loaded.");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        Path path = Paths.get(KrassAlla.getPlugin(KrassAlla.class).getDataFolder() + "/PlayerData/");
        dataFolder = getDataFolder().toString();
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PREFIX = this.getConfig().getString("Config.General.PREFIX");
    }

    public void enableCommands() {
        String[] commands = {"test", "buy", "echo", "backpack", "money", "json", "bounty"};
        for (String command : commands) {
            Objects.requireNonNull(getCommand(command)).setExecutor(new CommandExecuter());
        }
    }

    public void enableTabCompleter() {
        Objects.requireNonNull(getCommand("json")).setTabCompleter(new JsonTabCompleter());
        Objects.requireNonNull(getCommand("money")).setTabCompleter(new MoneyTabCompleter());
    }

    public void enableListener() {
        getServer().getPluginManager().registerEvents(new onPlayerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new onPlayerQuitEvent(), this);
        getServer().getPluginManager().registerEvents(new backpack(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
