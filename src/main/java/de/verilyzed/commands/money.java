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

import java.io.*;
import java.util.Scanner;

public class money implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("money")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if(args.length>0) {
                    if (args[0].equalsIgnoreCase("give") && args.length == 3) {
                        try {
                            FileReader fr = new FileReader(KrassAlla.dataFolder + "\\PlayerData\\" + p.getUniqueId() + ".json");
                            Scanner scanner  = new Scanner(fr);
                            JSONParser parser = new JSONParser();
                            JSONObject jsonObject = (JSONObject) parser.parse(scanner.nextLine());
                            long money = (long)jsonObject.get("money");
                            scanner.close();
                            fr.close();
                            String recuuid = "";
                            if (money<Integer.parseInt(args[2])) {
                                p.sendMessage(KrassAlla.PREFIX + "Du hast nicht genügend Geld.");
                            }
                            for (Player receiver : Bukkit.getOnlinePlayers()) {
                                if(!args[1].equalsIgnoreCase(p.getName()))
                                    continue;
                                recuuid = receiver.getUniqueId().toString();
                                break;
                            }
                            FileReader frreceiver = new FileReader(KrassAlla.dataFolder + "\\PlayerData\\" + recuuid + ".json");
                            Scanner scannerreceiver  = new Scanner(frreceiver);
                            JSONObject jsonObjectreceiver = (JSONObject) parser.parse(scanner.nextLine());
                            long moneyreceiver = (long)jsonObjectreceiver.get("money");
                            scannerreceiver.close();
                            frreceiver.close();
                            moneyreceiver += Integer.parseInt(args[2]);
                            money -= Integer.parseInt(args[2]);
                            jsonObjectreceiver.put("money", moneyreceiver);
                            jsonObject.put("money", money);
                            FileWriter fileWriter = new FileWriter(KrassAlla.dataFolder + "\\PlayerData\\" + p.getPlayer().getUniqueId() + ".json");
                            fileWriter.write(jsonObject.toJSONString());
                            fileWriter.close();
                            fileWriter = new FileWriter(KrassAlla.dataFolder + "\\PlayerData\\" + recuuid + ".json");
                            fileWriter.write(jsonObjectreceiver.toJSONString());
                            fileWriter.close();

                        } catch (IllegalStateException | ParseException | NumberFormatException | IOException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
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
