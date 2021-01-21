package me.t3sl4.snowball.listeners;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.t3sl4.snowball.T3SL4Bomb;
import me.t3sl4.snowball.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class BombListener implements Listener {

    @EventHandler
    public void p(ProjectileHitEvent e) {
        Projectile nesne = e.getEntity();
        float range = MessageUtil.RANGE;
        Iterator<ProtectedRegion> rgs = this.getWorldGuard().getRegionManager(nesne.getWorld()).getApplicableRegions(nesne.getLocation()).iterator();

        if(nesne instanceof Snowball) {
            Snowball kartopu = (Snowball) nesne;
            LivingEntity livingEntity = (LivingEntity) kartopu.getShooter();
            if(livingEntity instanceof Player) {
                Player p = (Player) livingEntity;
                String displayName = p.getItemInHand().getItemMeta().getDisplayName();
                if(p.getItemInHand().getItemMeta().hasDisplayName()) {
                    if(displayName.equalsIgnoreCase(T3SL4Bomb.item.kartopuMeta.getDisplayName())) {
                        if(p.getItemInHand().getItemMeta().getEnchants().equals(T3SL4Bomb.item.kartopuMeta.getEnchants())) {
                            if(p.getItemInHand().getItemMeta().getLore().equals(T3SL4Bomb.item.kartopuMeta.getLore())) {
                                if(MessageUtil.ENABLED_WORLDS.contains(nesne.getWorld().getName())) {
                                    if(!(rgs.hasNext())) {
                                        kartopu.getWorld().createExplosion(kartopu.getLocation(), range, false);
                                    }
                                } else {
                                    p.sendMessage((MessageUtil.WORLD).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin pl = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if(pl == null || !(pl instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin) pl;
    }
}
