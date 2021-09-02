package de.verilyzed.commands;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class json implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("json")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("modify")) {
                        if (args.length > 1) {
                            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

                            }
                        } else {
                            p.sendMessage("§cSyntax: /json modify [Username]");
                        }

                        return true;
                    }

                    if (args[0].equalsIgnoreCase("read")) {
                        if (args.length > 1) {
                            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                if (args[1].equalsIgnoreCase(onlinePlayer.getName())) {
                                    try {
                                        FileReader fileReader = new FileReader(KrassAlla.dataFolder + "/PlayerData/" + onlinePlayer.getUniqueId() + ".json");
                                        Scanner scanner = new Scanner(fileReader);

                                        String jsonString = scanner.nextLine();
                                        scanner.close();

                                        JSONParser parser = new JSONParser();
                                        JSONObject json = (JSONObject) parser.parse(jsonString);

                                        if (args.length > 2) {
                                            if (json.containsKey(args[2])) {
                                                p.sendMessage(KrassAlla.PREFIX + args[2].substring(0, 1).toUpperCase() + args[2].substring(1).toLowerCase() + ": " + json.get(args[2]));
                                            } else {
                                                p.sendMessage(KrassAlla.PREFIX + "The key " + args[2] + " does not exist.");
                                            }
                                        } else {
                                            for (Object key : json.keySet()) {
                                                p.sendMessage(KrassAlla.PREFIX + key.toString().substring(0, 1).toUpperCase() + key.toString().substring(1).toLowerCase() + ": " + json.get(key));
                                            }
                                        }

                                    } catch (IOException | ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } else {
                            p.sendMessage("§cSyntax: /json read [Username]");
                        }

                        return true;
                    }

                    p.sendMessage("§cSyntax: /json [modify|read]");
                } else {
                    p.sendMessage("§cSyntax: /json [modify|read]");
                }
            }

            return true;
        }

        return false;
    }
}
