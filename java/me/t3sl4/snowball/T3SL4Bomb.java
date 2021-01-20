package me.t3sl4.snowball;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.t3sl4.snowball.commands.BombCommand;
import me.t3sl4.snowball.listeners.BombListener;
import me.t3sl4.snowball.util.MessageUtil;
import me.t3sl4.snowball.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class T3SL4Bomb extends JavaPlugin implements Listener {
    public ItemStack kartopu;
    public ItemMeta kartopuMeta;
    private static SettingsManager manager = SettingsManager.getInstance();

    public void onEnable() {
        if(getWorldGuard() == null || getWorldEdit() == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Eklentinin Calisabilmesi Icin WorldGuard + WorldEdit Gereklidir!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Lutfen Sunucunuzla Destekli WorldGuard ve WorldEdit Eklentilerini Yukleyin !!");
        } else {
            Bukkit.getConsoleSender().sendMessage("   ");
            Bukkit.getConsoleSender().sendMessage("  ____   __   __  _   _   _____   _____   ____    _       _  _   ");
            Bukkit.getConsoleSender().sendMessage(" / ___|  \\ \\ / / | \\ | | |_   _| |___ /  / ___|  | |     | || |  ");
            Bukkit.getConsoleSender().sendMessage(" \\___ \\   \\ V /  |  \\| |   | |     |_ \\  \\___ \\  | |     | || |_ ");
            Bukkit.getConsoleSender().sendMessage("  ___) |   | |   | |\\  |   | |    ___) |  ___) | | |___  |__   _|");
            Bukkit.getConsoleSender().sendMessage(" |____/    |_|   |_| \\_|   |_|   |____/  |____/  |_____|    |_|  ");
            Bukkit.getConsoleSender().sendMessage("    ");
            enable();
        }
    }

    public void enable() {
        loadItem();
        loadCommands();
        loadListeners();
        manager.setup(this);
        MessageUtil.loadMessages();
    }

    public void loadItem() {
        kartopu = new ItemStack(Material.SNOW_BALL);
        kartopuMeta = kartopu.getItemMeta();
        kartopuMeta.setDisplayName(MessageUtil.ITEMNAME);
        kartopuMeta.setLore(MessageUtil.ITEMLORE);
        kartopuMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        kartopuMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        kartopu.setItemMeta(kartopuMeta);
    }

    private void loadCommands() {
        getCommand("tb").setExecutor((CommandExecutor)new BombCommand());
    }

    private void loadListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents((Listener)new BombListener(), (Plugin)this);
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin pl = getServer().getPluginManager().getPlugin("WorldGuard");

        if(pl == null || !(pl instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin) pl;
    }

    private WorldEditPlugin getWorldEdit() {
        Plugin pl = getServer().getPluginManager().getPlugin("WorldEdit");

        if(pl == null || !(pl instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldEditPlugin) pl;
    }
}
