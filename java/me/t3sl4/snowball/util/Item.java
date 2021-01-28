package me.t3sl4.snowball.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
    public ItemStack kartopu;
    public ItemMeta kartopuMeta;

    public static void loadItem(Item item) {
        item.kartopu = new ItemStack(Material.SNOW_BALL);
        item.kartopuMeta = item.kartopu.getItemMeta();
        item.kartopuMeta.setDisplayName(MessageUtil.ITEMNAME);
        item.kartopuMeta.setLore(MessageUtil.ITEMLORE);
        item.kartopuMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        item.kartopuMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.kartopu.setItemMeta(item.kartopuMeta);
    }

    public static boolean checkInventory(Player p) {
        int kontrol=0;
        for(ItemStack i : p.getInventory()) {
            if(i == null) {
                kontrol ++;
            }
        }
        if(kontrol != 0) {
            return true;
        }
        return false;
    }
}
