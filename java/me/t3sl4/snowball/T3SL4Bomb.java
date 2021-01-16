package me.t3sl4.snowball;

import me.t3sl4.snowball.util.MessageUtil;
import me.t3sl4.snowball.util.SettingsManager;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class T3SL4Bomb extends JavaPlugin implements Listener {
    ItemStack kartopu;
    ItemMeta kartopuMeta;
    private static SettingsManager manager = SettingsManager.getInstance();

    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("   ");
        Bukkit.getConsoleSender().sendMessage("  ____   __   __  _   _   _____   _____   ____    _       _  _   ");
        Bukkit.getConsoleSender().sendMessage(" / ___|  \\ \\ / / | \\ | | |_   _| |___ /  / ___|  | |     | || |  ");
        Bukkit.getConsoleSender().sendMessage(" \\___ \\   \\ V /  |  \\| |   | |     |_ \\  \\___ \\  | |     | || |_ ");
        Bukkit.getConsoleSender().sendMessage("  ___) |   | |   | |\\  |   | |    ___) |  ___) | | |___  |__   _|");
        Bukkit.getConsoleSender().sendMessage(" |____/    |_|   |_| \\_|   |_|   |____/  |____/  |_____|    |_|  ");
        Bukkit.getConsoleSender().sendMessage("    ");
        getServer().getPluginManager().registerEvents(this, this);
        manager.setup(this);
        MessageUtil.loadMessages();
        loadItem();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        TextComponent msg = new TextComponent("§e§lAuthor §7|| §e§lYapımcı");
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("§7Eklenti Yapımcısı:\n   §8§l» §eSYN_T3SL4 \n   §8§l» §7Discord: §eHalil#4439")).create()));

        if(cmd.getName().equalsIgnoreCase("tb")) {
            if(args.length == 0) {
                Player hover = (Player) sender;
                if(sender.isOp() || sender.hasPermission("t3sl4bomb.genel")) {
                    hover.sendMessage(MessageUtil.HELP1);
                    hover.sendMessage((MessageUtil.HELP2).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                    hover.sendMessage((MessageUtil.HELP3).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                    hover.sendMessage(MessageUtil.HELP4);
                    hover.spigot().sendMessage(msg);
                } else if(!(sender instanceof Player)) {
                    sender.sendMessage(MessageUtil.HELP1);
                    sender.sendMessage((MessageUtil.HELP2).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                    sender.sendMessage((MessageUtil.HELP3).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                    sender.sendMessage(MessageUtil.HELP4);
                } else {
                    sender.sendMessage(MessageUtil.PERM);
                }
            } else if(args[0].equalsIgnoreCase("take") && sender.hasPermission("t3sl4bomb.take")) {
                if(sender instanceof Player) {
                    if(args.length < 2 || args.length > 2) {
                        sender.sendMessage(MessageUtil.ARGTAKE);
                    } else {
                        Player oyuncu = (Player) sender;
                        int adet = Integer.parseInt(args[1]);
                        for(int i=0; i<adet; i++) {
                            oyuncu.getInventory().addItem(kartopu);
                        }
                        sender.sendMessage((MessageUtil.ADD).replaceAll("%adet%", String.valueOf(adet)).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage(MessageUtil.CONSOLE);
                }
            } else if(args[0].equalsIgnoreCase("give") && sender.hasPermission("t3sl4bomb.give")) {
                if(args.length < 3 || args.length > 3) {
                    sender.sendMessage(MessageUtil.ARGGIVE);
                } else {
                    int adet = Integer.parseInt(args[2]);
                    if(Bukkit.getPlayer(args[1]).isOnline()) {
                        for(int i=0; i<adet; i++) {
                            Bukkit.getPlayer(args[1]).getInventory().addItem(kartopu);
                        }
                        Bukkit.getPlayer(args[1]).sendMessage((MessageUtil.ADD).replaceAll("%adet%", String.valueOf(adet)).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                        sender.sendMessage((MessageUtil.GIVE).replaceAll("%player%", Bukkit.getPlayer(args[1]).getName()).replaceAll("%adet%", String.valueOf(adet)).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                    } else {
                        sender.sendMessage((MessageUtil.ONLINE).replaceAll("%player%", Bukkit.getPlayer(args[1]).getName()));
                    }
                }
            } else if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("t3sl4bomb.reload")) {
                manager.reloadConfig();
                sender.sendMessage(MessageUtil.RELOAD);
            }
        }
        return true;
    }

    @EventHandler
    public void p(ProjectileHitEvent e) {
        Player p = (Player) e.getEntity().getShooter();
        Projectile nesne = e.getEntity();
        String displayName = p.getItemInHand().getItemMeta().getDisplayName();
        float range = MessageUtil.RANGE;

        if(nesne instanceof Snowball) {
            if(MessageUtil.ENABLED_WORLDS.contains(nesne.getWorld().getName())) {
                if(e.getEntity().getShooter() instanceof Player) {
                    if(nesne.getType().equals(EntityType.SNOWBALL)) {
                        if(p.getItemInHand().getItemMeta().hasDisplayName()) {
                            if(displayName.equalsIgnoreCase(kartopuMeta.getDisplayName())) {
                                if(p.getItemInHand().getItemMeta().getEnchants().equals(kartopuMeta.getEnchants())) {
                                    if(p.getItemInHand().getItemMeta().getLore().equals(kartopuMeta.getLore())) {
                                        nesne.getWorld().createExplosion(nesne.getLocation(), range, false);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                p.sendMessage((MessageUtil.WORLD).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
            }
        }
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
}
