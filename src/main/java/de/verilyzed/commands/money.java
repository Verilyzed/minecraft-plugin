package de.verilyzed.commands;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;


public class money {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!command.getName().equalsIgnoreCase("money") || !(sender instanceof Player)) {
            return true;
        }
        Bukkit.getScheduler().runTaskAsynchronously(KrassAlla.plugin, () -> {
            Player p = (Player) sender;
            int moneySender = KrassAlla.logic.getMoney(p.getName());
            if (moneySender < 0) {
                p.sendMessage(KrassAlla.PREFIX + "Die Verbindung zur Datenbank ist fehlgeschlagen oder ein anderer Fehler ist aufgetreten.");
                return;
            }
            if (args.length == 0) {
                p.sendMessage(KrassAlla.PREFIX + "Du hast " + moneySender + " Geld");
                return;
            }
            int amount;
            switch (args[0].toLowerCase()) {
                case "give":
                    if (args.length < 3) break;
                    String receiver = args[1];
                    amount = Integer.parseInt(args[2]);
                    if (moneySender < amount || amount <= 0) {
                        p.sendMessage(KrassAlla.PREFIX + "Du hast nicht genügend Geld.");
                        return;
                    }
                    int moneyReceiver = KrassAlla.logic.getMoney(receiver);
                    if (moneyReceiver == -1) {
                        p.sendMessage("§sHast du dich verschrieben, oder ist der Spieler vielleicht nicht online?");
                        return;
                    }
                    try {
                        KrassAlla.logic.sendMoney(p.getName(), receiver, amount);
                    } catch (SQLException e) {
                        p.sendMessage("Money could not be sent. There was a connection issue with the database.");

                    }
                    return;
                case "add":
                    if (args.length != 3) {
                        p.sendMessage("§sSyntax: /money add [Spieler] [Betrag]");
                        return;
                    }

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (args[1].equalsIgnoreCase(player.getName())) {
                            amount = Integer.parseInt(args[2]);
                            if (amount <= 0)
                                return;
                            KrassAlla.logic.updateEntry("money", Integer.toString(amount + moneySender), "name", args[1], "users");
                            p.sendMessage("Du Admin hast " + args[1] + " " + args[2] + " Eugen gegeben. Frech von dir.");
                        }
                    }
                    p.sendMessage("§sSyntax: /money add [Spieler] [Betrag]");
                    break;
                case "remove":
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (args[1].equalsIgnoreCase(player.getName())) {
                            amount = Integer.parseInt(args[2]);
                            if (amount <= 0)
                                return;
                            KrassAlla.logic.updateEntry("money", Integer.toString(amount + moneySender), "name", args[1], "users");
                            p.sendMessage("Du Admin hast " + args[1] + " " + args[2] + " Eugen gegeben. Frech von dir.");
                        }
                    }
                    break;
                default:
                    p.sendMessage("§sFalscher Befehl, unterstützt wird:");
                    p.sendMessage("/money");
                    p.sendMessage("/money give [empfänger] [amount]");
                    p.sendMessage("/money add [amount]");
            }
        });
        return true;
    }
}
