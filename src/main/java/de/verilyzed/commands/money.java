package de.verilyzed.commands;

import de.verilyzed.generic.FileManager;
import de.verilyzed.generic.Other;
import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.util.UUID;

public class money implements CommandExecutor {

    private void setMoney(JSONObject p, long difference, UUID uuid) {
        p.put("money", difference);
        FileManager.setJSONObject(uuid, p);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("money")) {
            if (sender instanceof Player) {
                //get the player, his uuid, his JSON and his money
                Player p = (Player) sender;
                UUID uuidSender = p.getUniqueId();
                JSONObject senderJSON = FileManager.getJSONObject(uuidSender);
                long moneySender = (long) senderJSON.get("money");
                if (args.length > 0) {
                    switch(args[0].toLowerCase()) {
                        case "give":
                            if (args.length <3) break;
                            if (moneySender < Integer.parseInt(args[2]) || Integer.parseInt(args[2]) < 0) {
                                p.sendMessage(KrassAlla.PREFIX + "Du hast nicht genügend Geld.");
                                return true;
                            }
                            UUID uuidReceiver = Other.getPlayerUUID(args[1]);
                            JSONObject receiverJSON = FileManager.getJSONObject(uuidReceiver);
                            long moneyReceiver = (long) receiverJSON.get("money");
                            moneyReceiver += Integer.parseInt(args[2]);
                            moneySender -= Integer.parseInt(args[2]);
                            setMoney(senderJSON, moneySender, uuidSender);
                            setMoney(receiverJSON, moneyReceiver, uuidReceiver);
                            return true;
                        case "add":
                            if (args.length != 2)  {
                                p.sendMessage("§sSyntax: /money add [Betrag]");
                                return true;
                            }
                            moneySender += (long) Integer.parseInt(args[1]);
                            setMoney(senderJSON, moneySender, uuidSender);
                            p.sendMessage("Du Admin hast dir " + args[1] + " Eugen gegeben. Frech von dir.");
                            return true;
                        default:
                            p.sendMessage("§sFalscher Befehl, unterstützt wird:");
                            p.sendMessage("/money");
                            p.sendMessage("/money give [empfänger] [betrag]");
                            p.sendMessage("/money add [betrag]");
                            return true;
                    }
                }
                p.sendMessage(KrassAlla.PREFIX + "Du hast " + moneySender + " Geld");
                return true;
            }
        }
        return false;
    }
}
