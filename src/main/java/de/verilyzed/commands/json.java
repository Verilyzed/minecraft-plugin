package de.verilyzed.commands;

import de.verilyzed.generic.FileManager;
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
import java.util.Scanner;

public class json implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!command.getName().equalsIgnoreCase("json")) {
            return false;
        }

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("modify")) {
                    return handleModifyArgument(args, p);
                }

                if (args[0].equalsIgnoreCase("read")) {
                    return handleReadArgument(args, p);
                }

            }
            p.sendMessage(KrassAlla.PREFIX + "§cSyntax: /json [modify|read]");
        }

        return true;

    }

    private boolean handleReadArgument(@NotNull String @NotNull [] args, Player p) {
        if (args.length > 1) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (args[1].equalsIgnoreCase(onlinePlayer.getName())) {
                    JSONObject json = FileManager.getJSONObject(onlinePlayer.getUniqueId());

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
                }
            }
        } else {
            p.sendMessage(KrassAlla.PREFIX + "§cSyntax: /json read [Username]");
        }

        return true;
    }

    private boolean handleModifyArgument(@NotNull String @NotNull [] args, Player p) {
        if (args.length > 1) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (args[1].equalsIgnoreCase(onlinePlayer.getName())) {
                    JSONObject jsonObject = FileManager.getJSONObject(onlinePlayer.getUniqueId());

                    if (args.length > 2) {
                        if (jsonObject.containsKey(args[2])) {
                            if (args.length > 3) {
                                jsonObject.put(args[2], args[3]);

                                if (FileManager.setJSONObject(onlinePlayer.getUniqueId(), jsonObject)) {
                                    p.sendMessage(KrassAlla.PREFIX + "Key " + args[2] + " successfully edited.");
                                }
                            } else {
                                p.sendMessage(KrassAlla.PREFIX + "§cSyntax: /json modify [Username] [Key] [Value]");
                            }
                        } else {
                            p.sendMessage(KrassAlla.PREFIX + "The key " + args[2] + " does not exist.");
                        }
                    } else {
                        String keys = "";
                        for (Object key : jsonObject.keySet()) {
                            keys += key.toString() + ",";
                        }
                        keys = keys.substring(0, keys.length() - 1);

                        p.sendMessage(KrassAlla.PREFIX + "§cSyntax: /json modify [Username] [" + keys + "]");
                    }
                    return true;
                }
            }
        } else {
            p.sendMessage(KrassAlla.PREFIX + "§cSyntax: /json modify [Username]");
        }

        return true;
    }
}
