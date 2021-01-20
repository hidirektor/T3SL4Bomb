package me.t3sl4.snowball.listeners;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.t3sl4.snowball.T3SL4Bomb;
import me.t3sl4.snowball.util.MessageUtil;
import me.t3sl4.snowball.util.SettingsManager;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.Iterator;

public class BombListener implements Listener {
    T3SL4Bomb t3sl4bomb = new T3SL4Bomb();
    private static SettingsManager manager = SettingsManager.getInstance();

    @EventHandler
    public void p(ProjectileHitEvent e) {
        Player p = (Player) e.getEntity().getShooter();
        Projectile nesne = e.getEntity();
        String displayName = p.getItemInHand().getItemMeta().getDisplayName();
        float range = MessageUtil.RANGE;
        Iterator<ProtectedRegion> rgs = t3sl4bomb.getWorldGuard().getRegionManager(nesne.getWorld()).getApplicableRegions(nesne.getLocation()).iterator();

        if(!(nesne.getShooter() instanceof Wither)) {
            if(nesne instanceof Snowball) {
                if(e.getEntity().getShooter() instanceof Player) {
                    if(nesne.getType().equals(EntityType.SNOWBALL)) {
                        if(p.getItemInHand().getItemMeta().hasDisplayName()) {
                            if(displayName.equalsIgnoreCase(t3sl4bomb.kartopuMeta.getDisplayName())) {
                                if(p.getItemInHand().getItemMeta().getEnchants().equals(t3sl4bomb.kartopuMeta.getEnchants())) {
                                    if(p.getItemInHand().getItemMeta().getLore().equals(t3sl4bomb.kartopuMeta.getLore())) {
                                        if(MessageUtil.ENABLED_WORLDS.contains(nesne.getWorld().getName())) {
                                            if(!(rgs.hasNext())) {
                                                nesne.getWorld().createExplosion(nesne.getLocation(), range, false);
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
        }
    }
}
