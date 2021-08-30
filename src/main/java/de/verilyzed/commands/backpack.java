package de.verilyzed.commands;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class backpack implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("backpack")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                p.sendMessage(KrassAlla.PREFIX + "Opening backpack...");

                Inventory inv = Bukkit.createInventory(p, 9, "Backpack");

                JSONParser parser = new JSONParser();

                try {
                    JSONObject json = (JSONObject) parser.parse(new FileReader(KrassAlla.dataFolder + ""));

                    JSONArray backpack = (JSONArray) json.get("backpack");

                    for (Object o : backpack) {
                        inv.addItem();
                    }

                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }

                return true;
            }
        }

        return false;
    }
}
