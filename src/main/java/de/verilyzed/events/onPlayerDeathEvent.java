package de.verilyzed.events;

import de.verilyzed.exceptions.UpdateFailedException;
import de.verilyzed.krassalla.KrassAlla;
import de.verilyzed.persistence.model.Bounty;
import de.verilyzed.persistence.model.User;
import de.verilyzed.service.BountyService;
import de.verilyzed.service.UserService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class onPlayerDeathEvent implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();

        Bounty bounty = BountyService.getBountyByUuid(p.getUniqueId().toString());

        if (bounty == null) {
            return;
        }

        Player killer = p.getKiller();
        assert killer != null;

        try {
            User user = UserService.getUserByName(killer.getName());
            int money = user.getMoney() + bounty.getMoney();

            UserService.setMoney(money, killer.getName());

            killer.sendMessage(KrassAlla.PREFIX + "You killed " + p.getName() + " and received a reward!");
        } catch (UpdateFailedException e) {
            KrassAlla.log.log(new LogRecord(Level.WARNING, "onPlayerDeathEvent error: UpdateFailedException"));
            e.printStackTrace();
        }

        BountyService.removeBountyByName(p.getName());
    }
}
