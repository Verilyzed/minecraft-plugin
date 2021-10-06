package de.verilyzed.shop;

import ninja.amp.ampmenus.items.BackItem;
import ninja.amp.ampmenus.items.CloseItem;
import ninja.amp.ampmenus.menus.ItemMenu;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopMenu extends ItemMenu {
    public ShopMenu(JavaPlugin plugin) {
        super("Shop", Size.TWO_LINE, plugin);

        // Adding items to your ItemMenu
        setItem(17, new CloseItem());
    }

    // Useful in case you only want the back item to appear if the menu has a parent
    @Override
    public void setParent(ItemMenu parent) {
        super.setParent(parent);
        if (parent != null) {
            setItem(16, new BackItem());
        }
    }
}
