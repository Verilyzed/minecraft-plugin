package de.verilyzed.events;

import de.verilyzed.krassalla.KrassAlla;
import de.verilyzed.persistence.model.Bounty;
import de.verilyzed.persistence.model.User;
import de.verilyzed.service.BountyService;
import de.verilyzed.service.UserService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

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
        killer.sendMessage(KrassAlla.PREFIX + "You killed " + p.getName() + " and received a reward!");

        User user = UserService.getUserByName(killer.getName());
        int money = user.getMoney() + bounty.getMoney();

        UserService.setMoneyByName(money, killer.getName());

        BountyService.removeBountyByName(p.getName());
    }
}
