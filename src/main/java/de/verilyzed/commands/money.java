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
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class money implements CommandExecutor {
    private String getPlayerUUID(String Name) {
        for (Player receiver : Bukkit.getOnlinePlayers()) {
            if (!Name.equalsIgnoreCase(receiver.getName()))
                continue;
            return receiver.getUniqueId().toString();
        }
        return "";
    }

    private long getMoney(String p) {
        FileReader fr = null;
        long money = -1;
        try {
            fr = new FileReader(KrassAlla.dataFolder + "/PlayerData/" + p + ".json");
            Scanner scanner = new Scanner(fr);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(scanner.nextLine());
            money = (long) jsonObject.get("money");
            fr.close();
            scanner.close();
        } catch (IllegalStateException | ParseException | NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return money;
    }

    private void setMoney(String p, long difference) {
        FileReader fr = null;
        long money = -1;
        try {
            fr = new FileReader(KrassAlla.dataFolder + "/PlayerData/" + p + ".json");
            Scanner scanner = new Scanner(fr);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(scanner.nextLine());
            money = (long) jsonObject.get("money");
            fr.close();
            scanner.close();
            jsonObject.put("money", money);
            FileWriter fileWriter = new FileWriter(KrassAlla.dataFolder + "/PlayerData/" + p + ".json");
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
        } catch (IllegalStateException | ParseException | NumberFormatException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("money")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                long money = getMoney(p.getUniqueId().toString());
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("give") && args.length == 3) {
                        try {
                            if (money < Integer.parseInt(args[2])) {
                                p.sendMessage(KrassAlla.PREFIX + "Du hast nicht genügend Geld.");
                            }
                            String recuuid = getPlayerUUID(args[1]);
                            long moneyreceiver = getMoney(recuuid);
                            moneyreceiver += Integer.parseInt(args[2]);
                            money -= Integer.parseInt(args[2]);
                            setMoney(p.getUniqueId().toString(), money);
                            setMoney(recuuid, moneyreceiver);

                        } catch (IllegalStateException | NumberFormatException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                    return true;
                }
                p.sendMessage(KrassAlla.PREFIX + "Du hast " + money + " Geld");

                return true;
            }
        }

        return false;
    }
}
