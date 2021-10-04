package de.verilyzed.commands;

import de.verilyzed.krassalla.KrassAlla;
import de.verilyzed.menus.SuicideItem;
import ninja.amp.ampmenus.items.MenuItem;
import ninja.amp.ampmenus.menus.ItemMenu;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class buy {

    public void onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("buy")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                ItemMenu menu = new ItemMenu("Shop", ItemMenu.Size.TWO_LINE, KrassAlla.plugin);
                menu.setItem(1, new SuicideItem());
                menu.setItem(0, new MenuItem("Hello", new ItemStack(Material.DAMAGED_ANVIL)));
                menu.open(p);
            }
        }
    }
}
