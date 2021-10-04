package de.verilyzed.commands;

import de.verilyzed.exceptions.MoneyFetchException;
import de.verilyzed.exceptions.UpdateFailedException;
import de.verilyzed.krassalla.KrassAlla;
import de.verilyzed.persistence.model.Bounty;
import de.verilyzed.persistence.model.User;
import de.verilyzed.persistence.repository.UsersRepository;
import de.verilyzed.service.BountyService;
import de.verilyzed.service.UserService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.util.UUID;

public class bounty {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("bounty")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("add")) {
                        if (args.length != 3) {
                            p.sendMessage(KrassAlla.PREFIX + "§cSyntax: /bounty add <Player> <Amount>");
                            return true;
                        }

                        int amount = 0;
                        try {
                            amount = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();

                            p.sendMessage(KrassAlla.PREFIX + "§cSyntax: /bounty add <Player> <Amount>");
                            return true;
                        }

                        p.sendMessage("A:" + args[1]);

                        String uuidReceiver = UserService.getUuidByName(args[1]);
                        if (uuidReceiver == null) {
                            p.sendMessage(KrassAlla.PREFIX + "§cDieser User war bis jetzt noch nie auf dem Server.");
                            return true;
                        }

                        int money;
                        try {
                            money = UserService.getMoney(p.getName());
                        } catch (MoneyFetchException e) {
                            e.printStackTrace();
                            p.sendMessage(KrassAlla.PREFIX + "§cMoneyFetchException");
                            return true;
                        }

                        if (money < amount) {
                            p.sendMessage(KrassAlla.PREFIX + "§cYou don't have enough money.");
                            return true;
                        }

                        money -= amount;

                        try {
                            UserService.setMoney(money, p.getName());
                        } catch (UpdateFailedException e) {
                            e.printStackTrace();
                            p.sendMessage("Money couldnt be set.");
                        }

                        Bounty bounty = new Bounty(uuidReceiver, amount);
                        BountyService.addBounty(bounty);

                        p.sendMessage(KrassAlla.PREFIX + "Money was successfully put on the Players head.");
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
