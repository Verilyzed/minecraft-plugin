package de.verilyzed.krassalla;

import co.aikar.idb.BukkitDB;
import co.aikar.idb.Database;
import de.verilyzed.commands.CommandExecuter;
import de.verilyzed.events.onInventoryCloseEvent;
import de.verilyzed.events.onPlayerDeathEvent;
import de.verilyzed.events.onPlayerJoinEvent;
import de.verilyzed.events.onPlayerQuitEvent;
import de.verilyzed.persistence.repository.BountyRepository;
import de.verilyzed.persistence.repository.UsersRepository;
import de.verilyzed.service.BountyService;
import de.verilyzed.service.UserService;
import de.verilyzed.tabcompleter.JsonTabCompleter;
import de.verilyzed.tabcompleter.MoneyTabCompleter;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;

public final class KrassAlla extends JavaPlugin {
    public static PluginLogger log;
    public static String PREFIX;
    Database db;
    public static String dataFolder;
    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadConfig();
        enableCommands();
        plugin = this;
        enableTabCompleter();
        enableListener();
        db = BukkitDB.createHikariDatabase(this, "root", "MyNewPass", "minecraft", "mariadb:3306");
        UserService.setUsersRepository(new UsersRepository());
        BountyService.setBountyRepository(new BountyRepository());
        log = new PluginLogger(this);
        log.log(Level.ALL,"Plugin loaded.");
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
        getServer().getPluginManager().registerEvents(new onInventoryCloseEvent(), this);
        getServer().getPluginManager().registerEvents(new onPlayerDeathEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
