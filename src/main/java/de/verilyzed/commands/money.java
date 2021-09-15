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

import java.sql.SQLException;
import java.util.UUID;

@SuppressWarnings("unused")
public class money implements CommandExecutor {

    // TODO: Generics kommen später
    @SuppressWarnings("unchecked")
    private void setMoney(JSONObject p, long difference, UUID uuid) {
        p.put("money", difference);
        FileManager.setJSONObject(uuid, p);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!command.getName().equalsIgnoreCase("money") || !(sender instanceof Player)) {
            return false;
        }

        Player p = (Player) sender;
        int moneySender = KrassAlla.logic.getMoney(p.getName());
        if (moneySender < 0) {
            p.sendMessage(KrassAlla.PREFIX + "Die Verbindung zur Datenbank ist fehlgeschlagen oder ein anderer Fehler ist aufgetreten.");
            return false;
        }
        if (args.length == 0) {
            p.sendMessage(KrassAlla.PREFIX + "Du hast " + moneySender + " Geld");
            return true;
        }
        int betrag;
        switch (args[0].toLowerCase()) {
            case "give":
                if (args.length < 3) break;
                String empfaenger = args[1];
                betrag = Integer.parseInt(args[2]);
                if (moneySender < betrag || betrag < 0) {
                    p.sendMessage(KrassAlla.PREFIX + "Du hast nicht genügend Geld.");
                    return true;
                }
                int moneyReceiver = KrassAlla.logic.getMoney(empfaenger);
                if (moneyReceiver == -1) {
                    p.sendMessage("§sHast du dich verschrieben, oder ist der Spieler vielleicht nicht online?");
                    return true;
                }
                try {
                    KrassAlla.logic.sendMoney(p.getName(), empfaenger, betrag);
                } catch (SQLException e) {
                    p.sendMessage("Money could not be sent. There was a connection issue with the database.");

                }
                return true;
            case "add":
                if (args.length != 3) {
                    p.sendMessage("§sSyntax: /money add [Spieler] [Betrag]");
                    return true;
                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (args[1].equalsIgnoreCase(player.getName())) {
                        betrag = Integer.parseInt(args[2]);
                        KrassAlla.logic.updateEntry("money", Integer.toString(betrag + moneySender), "name", args[1], "users");
                        p.sendMessage("Du Admin hast " + args[1] + " " + args[2] + " Eugen gegeben. Frech von dir.");
                        return true;
                    }
                }

                p.sendMessage("§sSyntax: /money add [Spieler] [Betrag]");
                return true;
            default:
                p.sendMessage("§sFalscher Befehl, unterstützt wird:");
                p.sendMessage("/money");
                p.sendMessage("/money give [empfänger] [betrag]");
                p.sendMessage("/money add [betrag]");
                return true;
        }
        return true;
    }
}
