package de.verilyzed.commands;

import de.verilyzed.krassalla.KrassAlla;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class bounty {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("bounty")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("add")) {
                        if (args.length != 2) {
                            p.sendMessage(KrassAlla.PREFIX + "§cSyntax: /bounty add <Amount>");
                            return true;
                        }

                        int amount = 0;
                        try {
                            amount = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();

                            p.sendMessage(KrassAlla.PREFIX + "§cSyntax: /bounty add <Amount>");
                            return true;
                        }

                        int money = KrassAlla.logic.getMoney(p.getName());
                        if (money < amount)
                            return true;

                        KrassAlla.logic.updateEntry("money", Integer.toString(money - amount), "name", p.getName(), "users");



                    }

                    if (args[0].equalsIgnoreCase("list")) {

                    }
                }

                p.sendMessage(KrassAlla.PREFIX + "§cSyntax: /bounty add/list");
            }
        }

        return false;
    }
}
