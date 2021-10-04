package de.verilyzed.commands;

import de.verilyzed.exceptions.UpdateFailedException;
import de.verilyzed.krassalla.KrassAlla;
import de.verilyzed.persistence.model.Bounty;
import de.verilyzed.service.BountyService;
import de.verilyzed.service.UserService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class bounty {

    public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("bounty")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("add")) {
                        if (args.length != 3) {
                            p.sendMessage(KrassAlla.PREFIX + "§cSyntax: /bounty add <Player> <Amount>");
                            return;
                        }
                        int amount;
                        try {
                            amount = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            p.sendMessage(KrassAlla.PREFIX + "§cSyntax: /bounty add <Player> <Amount>");
                            return;
                        }
                        p.sendMessage("A:" + args[1]);
                        String uuidReceiver = UserService.getUuidByName(args[1]);
                        if (uuidReceiver == null) {
                            p.sendMessage(KrassAlla.PREFIX + "§cDieser User war bis jetzt noch nie auf dem Server.");
                            return;
                        }

                        Bounty bounty = new Bounty(uuidReceiver, amount);
                        try {
                            BountyService.addBounty(bounty, p.getName());
                            p.sendMessage(KrassAlla.PREFIX + "Money was successfully put on the Players head.");
                        } catch (UpdateFailedException e) {
                            e.printStackTrace();
                            p.sendMessage("Bounty could not be set.");
                        }
                    }
                    if (args[0].equalsIgnoreCase("list")) {

                    }
                }

                p.sendMessage(KrassAlla.PREFIX + "§cSyntax: /bounty add/list");
            }
        }

    }
}
