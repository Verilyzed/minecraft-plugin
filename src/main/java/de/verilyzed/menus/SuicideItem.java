package de.verilyzed.menus;

import ninja.amp.ampmenus.*;
import ninja.amp.ampmenus.events.ItemClickEvent;
import ninja.amp.ampmenus.items.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SuicideItem extends MenuItem {
    private static final String DISPLAY_NAME = ChatColor.BLUE + "Click for OP!";
    private static final ItemStack ICON = new ItemStack(Material.DIAMOND);

    public SuicideItem() {
        super(DISPLAY_NAME, ICON);
    }

    // This method controls what happens when the MenuItem is clicked
    @Override
    public void onItemClick(ItemClickEvent event) {
        event.getPlayer().setHealth(0);
    }

    // This method lets you modify the ItemStack before it is displayed, based on the player opening the menu
    @Override
    public ItemStack getFinalIcon(Player player) {
        ItemStack finalIcon = super.getFinalIcon(player);
        if (player.hasPermission("you.cant.fool.me")) {
            finalIcon.setType(Material.DIAMOND);
            ItemMeta meta = finalIcon.getItemMeta();
            meta.setDisplayName(ChatColor.DARK_RED + "Suicide");
            finalIcon.setItemMeta(meta);
        }
        return finalIcon;
    }
}