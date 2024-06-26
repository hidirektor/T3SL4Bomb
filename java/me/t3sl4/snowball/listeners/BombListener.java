package me.t3sl4.snowball.listeners;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.t3sl4.snowball.T3SL4Bomb;
import me.t3sl4.snowball.util.BlockLocation;
import me.t3sl4.snowball.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class BombListener implements Listener {
    private final Map<BlockLocation, Integer> obsidianBlocks = new HashMap<>();
    boolean heldKartopu = false;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR) {
            Player p = e.getPlayer();
            ItemStack clicked = p.getItemInHand();
            if(clicked.getType() == Material.SNOW_BALL) {
                if(clicked.getItemMeta().hasDisplayName()) {
                    if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(MessageUtil.ITEMNAME)) {
                        if(clicked.getItemMeta().getEnchants().equals(T3SL4Bomb.item.kartopuMeta.getEnchants())) {
                            if(clicked.getItemMeta().getLore().equals(T3SL4Bomb.item.kartopuMeta.getLore())) {
                                heldKartopu = true;
                            } else {
                                heldKartopu = false;
                            }
                        } else {
                            heldKartopu = false;
                        }
                    } else {
                        heldKartopu = false;
                    }
                } else {
                    heldKartopu = false;
                }
            } else {
                heldKartopu = false;
            }
        }
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e) {
        Projectile nesne = (Projectile) e.getEntity();
        if(nesne instanceof Snowball) {
            Snowball kartopu = (Snowball) nesne;
            LivingEntity livingEntity = (LivingEntity) kartopu.getShooter();
            if(livingEntity instanceof Player) {
                Player p = (Player) nesne.getShooter();
                if(heldKartopu) {
                    if(MessageUtil.ENABLED_WORLDS.contains(nesne.getWorld().getName())) {
                        if (Cooldown.tryCooldown(p, "kartopu", MessageUtil.COOLDOWN*1000) == false) {
                            p.getInventory().addItem(T3SL4Bomb.item.kartopu);
                            e.setCancelled(true);
                            p.sendMessage((MessageUtil.COOLDOWNERROR).replaceAll("%bomba%", MessageUtil.ITEMNAME).replaceAll("%time%", String.valueOf((Cooldown.getCooldown(p, "kartopu") / 1000))));
                        }
                    } else {
                    }
                }
            }
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        Projectile nesne = e.getEntity();
        Iterator<ProtectedRegion> rgs = this.getWorldGuard().getRegionManager(nesne.getWorld()).getApplicableRegions(nesne.getLocation()).iterator();

        if(nesne instanceof Snowball) {
            Snowball kartopu = (Snowball) nesne;
            LivingEntity livingEntity = (LivingEntity) kartopu.getShooter();
            if(livingEntity instanceof Player) {
                Player p = (Player) livingEntity;
                if(heldKartopu) {
                    if(MessageUtil.ENABLED_WORLDS.contains(nesne.getWorld().getName())) {
                        if(!(rgs.hasNext())) {
                            Cooldown.tryCooldown(p, "kartopu", MessageUtil.COOLDOWN*1000);
                            Entity tntPrimed = kartopu.getWorld().spawn(kartopu.getLocation(), TNTPrimed.class);
                            ((TNTPrimed)tntPrimed).setFuseTicks(0);
                            kartopu.remove();
                        }
                    } else {
                        p.sendMessage((MessageUtil.WORLD).replaceAll("%kartopu%", MessageUtil.ITEMNAME));
                    }
                }
            }
        }
    }

    @EventHandler
    public void tntRadius(ExplosionPrimeEvent event){
        if(event.getEntity().getType() == EntityType.PRIMED_TNT){
            event.setRadius(MessageUtil.RANGE);
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void tntExplodeLow(EntityExplodeEvent e) {
        int radius = (int)((TNTPrimed)e.getEntity()).getYield();
        int amount = MessageUtil.AMOUNT;
        Block block = e.getLocation().getBlock();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block relativeBlock = block.getRelative(x, y, z);
                    if (relativeBlock.getType() == Material.OBSIDIAN) {
                        BlockLocation blockLocation = new BlockLocation(relativeBlock);
                        if(MessageUtil.OBSIDIAN) {
                            int explosions = ((Integer)this.obsidianBlocks.getOrDefault(blockLocation, Integer.valueOf(0))).intValue() + 1;
                            if (explosions < amount) {
                                this.obsidianBlocks.put(blockLocation, Integer.valueOf(explosions));
                            } else {
                                this.obsidianBlocks.remove(blockLocation);
                                e.blockList().add(relativeBlock);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void tntExplodeHigh(EntityExplodeEvent e) {
        Iterator<Block> blockIterator = e.blockList().iterator();
        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();
            if (block.getType() == Material.OBSIDIAN) {
                if (e.getYield() > 0.0F) {
                    block.breakNaturally();
                    continue;
                }
                block.setType(Material.AIR);
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

    public boolean stepControl(Player p) {
        if(p.getItemInHand().getItemMeta().hasDisplayName()) {
            String displayName = p.getItemInHand().getItemMeta().getDisplayName();
            if(displayName.equalsIgnoreCase(T3SL4Bomb.item.kartopuMeta.getDisplayName())) {
                if(p.getItemInHand().getItemMeta().getEnchants().equals(T3SL4Bomb.item.kartopuMeta.getEnchants())) {
                    if(p.getItemInHand().getItemMeta().getLore().equals(T3SL4Bomb.item.kartopuMeta.getLore())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
