package de.verilyzed.commands;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class money implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("money")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if(args.length>0) {

                 return true;
                }
                try {
                    FileReader fr = new FileReader(KrassAlla.dataFolder + "\\PlayerData\\" + p.getUniqueId() + ".json");
                    Scanner scanner  = new Scanner(fr);
                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(scanner.nextLine());

                    scanner.close();
                    fr.close();

                    p.sendMessage(KrassAlla.PREFIX + "Du hast " + jsonObject.get("money") + "Geld");
                } catch (IllegalStateException | ParseException | NumberFormatException | IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        return false;
    }
}
