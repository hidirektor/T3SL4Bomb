package me.t3sl4.snowball.util;

import java.util.ArrayList;
import java.util.List;

public class MessageUtil {
    public static String PREFIX;
    public static List<String> ENABLED_WORLDS = new ArrayList<>();
    public static float RANGE;
    public static String ITEMNAME;
    public static String ITEMLORE;
    public static String HELP1;
    public static String HELP2;
    public static String HELP3;
    public static String HELP4;
    public static String RELOAD;
    public static String PERM;
    public static String WORLD;
    public static String ADD;
    public static String GIVE;
    public static String ARGGIVE;
    public static String ARGTAKE;
    public static String PLAYER;
    public static String CONSOLE;
    public static String ONLINE;

    static SettingsManager manager = SettingsManager.getInstance();

    public static void loadMessages() {
        PREFIX = colorize(manager.getConfig().getString("Prefix"));
        ENABLED_WORLDS = manager.getConfig().getStringList("Settings.enabled-worlds");
        RANGE = manager.getConfig().getInt("Settings.explosive-range");
        ITEMNAME = colorize(manager.getConfig().getString("Item.name"));
        ITEMLORE = colorize(manager.getConfig().getString("Item.lore"));
        HELP1 = colorize(manager.getConfig().getString("Commands.help1"));
        HELP2 = colorize(manager.getConfig().getString("Commands.help2"));
        HELP3 = colorize(manager.getConfig().getString("Commands.help3"));
        HELP4 = colorize(manager.getConfig().getString("Commands.help4"));
        RELOAD = PREFIX + colorize(manager.getConfig().getString("Messages.reload"));
        PERM = PREFIX + colorize(manager.getConfig().getString("Messages.perm"));
        WORLD = PREFIX + colorize(manager.getConfig().getString("Messages.world"));
        ADD = PREFIX + colorize(manager.getConfig().getString("Messages.add"));
        GIVE = PREFIX + colorize(manager.getConfig().getString("Messages.give"));
        ARGGIVE = PREFIX + colorize(manager.getConfig().getString("Messages.arg-give"));
        ARGTAKE = PREFIX + colorize(manager.getConfig().getString("Messages.arg-take"));
        PLAYER = PREFIX + colorize(manager.getConfig().getString("Messages.player"));
        CONSOLE = PREFIX + colorize(manager.getConfig().getString("Messages.console"));
        ONLINE = PREFIX + colorize(manager.getConfig().getString("Messages.online"));
    }

    public static String colorize(String str) {
        return str.replace("&", "§");
    }
}
