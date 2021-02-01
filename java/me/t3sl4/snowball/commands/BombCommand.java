package me.t3sl4.snowball.commands;

import me.t3sl4.snowball.T3SL4Bomb;
import me.t3sl4.snowball.util.Item;
import me.t3sl4.snowball.util.MessageUtil;
import me.t3sl4.snowball.util.SettingsManager;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BombCommand implements CommandExecutor {
    private static SettingsManager manager = SettingsManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        TextComponent msg = new TextComponent("§e§lAuthor §7|| §e§lYapımcı");
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("§7Eklenti Yapımcısı:\n   §8§l» §eSYN_T3SL4 \n   §8§l» §7Discord: §eHalil#4439")).create()));

        if(cmd.getName().equalsIgnoreCase("tb")) {
            if(args.length == 0) {
                Player hover = (Player) sender;
                if(sender.isOp() || sender.hasPermission("t3sl4bomb.general")) {
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
                    Player p = (Player) sender;
                    if(args.length < 2 || args.length > 2) {
                        sender.sendMessage(MessageUtil.ARGTAKE);
                    } else {
                        Player oyuncu = (Player) sender;
                        if(isInteger(args[1])) {
                            int adet = Integer.parseInt(args[1]);
                            if(Item.checkInventory(p)) {
                                for(int i=0; i<adet; i++) {
                                    oyuncu.getInventory().addItem(T3SL4Bomb.item.kartopu);
                                }
                                oyuncu.sendMessage((MessageUtil.ADD).replaceAll("%adet%", String.valueOf(adet)).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                            } else {
                                oyuncu.sendMessage((MessageUtil.INVENTORY_FULL).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                            }
                        } else {
                            oyuncu.sendMessage((MessageUtil.NUMBER).replaceAll("%sayi%", String.valueOf(args[1])));
                        }
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage(MessageUtil.CONSOLE);
                }
            } else if(args[0].equalsIgnoreCase("give") && sender.hasPermission("t3sl4bomb.give")) {
                if(args.length < 3 || args.length > 3) {
                    sender.sendMessage(MessageUtil.ARGGIVE);
                } else {
                    int adet = Integer.parseInt(args[2]);
                    if(Bukkit.getPlayer(args[1]) != null) {
                        if(Item.checkInventory(Bukkit.getPlayer(args[1]))) {
                            for(int i=0; i<adet; i++) {
                                Bukkit.getPlayer(args[1]).getInventory().addItem(T3SL4Bomb.item.kartopu);
                            }
                            Bukkit.getPlayer(args[1]).sendMessage((MessageUtil.ADD).replaceAll("%adet%", String.valueOf(adet)).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                        } else {
                            sender.sendMessage((MessageUtil.INVENTORY_IS_FULL).replaceAll("%player%", Bukkit.getPlayer(args[1]).getName()).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                            Bukkit.getPlayer(args[1]).sendMessage((MessageUtil.INVENTORY_FULL).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                        }
                        sender.sendMessage((MessageUtil.GIVE).replaceAll("%player%", Bukkit.getPlayer(args[1]).getName()).replaceAll("%adet%", String.valueOf(adet)).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                    } else {
                        sender.sendMessage((MessageUtil.ONLINE).replaceAll("%player%", Bukkit.getPlayer(args[1]).getName()));
                    }
                }
            } else if(args[0].equalsIgnoreCase("reload") && sender.hasPermission("t3sl4bomb.reload")) {
                manager.reloadConfig();
                MessageUtil.loadMessages();
                Item.loadItem(T3SL4Bomb.item);
                sender.sendMessage(MessageUtil.RELOAD);
            }
        }
        return true;
    }

    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try
        {
            Integer.parseInt(s);
            isValidInteger = true;
        }
        catch (NumberFormatException ex)
        {
        }
        return isValidInteger;
    }
}
