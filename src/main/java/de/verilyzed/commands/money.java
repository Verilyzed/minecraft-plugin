package de.verilyzed.commands;

import de.verilyzed.exceptions.MoneyFetchException;
import de.verilyzed.exceptions.UpdateFailedException;
import de.verilyzed.krassalla.KrassAlla;
import de.verilyzed.service.UserService;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class money {

    public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String[] args) {

        if (!command.getName().equalsIgnoreCase("money") || !(sender instanceof Player)) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(KrassAlla.plugin, () -> {
            Player p = (Player) sender;
            // code for give-command
            if (args.length == 3)
                if (args[0].equalsIgnoreCase("give") || args[0].equals("send")) {
                    try {
                        UserService.sendMoney(p.getName(), args[1], Integer.parseInt(args[2]));
                    } catch (UpdateFailedException e) {
                        p.sendMessage("Es wurde kein Geld gesendet, es ist ein Fehler aufgetreten.");
                    } catch (MoneyFetchException e) {
                        p.sendMessage("Der aktuelle Kontostand konnte nicht abgerufen werden.");
                    }
                    return;
                }
            int moneySender = 0;
            try {
                moneySender = UserService.getMoney(p.getName());
            } catch (MoneyFetchException e) {
                p.sendMessage(KrassAlla.PREFIX + "Dein Geld konnte nicht geladen werden.");
                e.printStackTrace();
            }
            if (args.length == 0) {
                p.sendMessage(KrassAlla.PREFIX + "Du hast " + moneySender + " Geld");
            }
        });
    }

}
