package me.xemor.enchantedTeleporters.events;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.xemor.enchantedTeleporters.EnchantedTeleporters;
import me.xemor.enchantedTeleporters.configs.ConfigHandler;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

@Singleton
public class PlaceOrBreakTeleporter implements Listener {

    @Inject
    private EnchantedTeleporters plugin;
    @Inject
    private ConfigHandler configHandler;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onElevatorPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        if (!isTeleporter(event.getItemInHand())) return;
        Block block = event.getBlock();
        if (block.getState() instanceof Beacon beacon) {
            beacon.getPersistentDataContainer().set(plugin.getTeleporterKey(), PersistentDataType.BOOLEAN, true);
            beacon.update();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onElevatorBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.CREATIVE)) return;
        Block block = event.getBlock();
        if (isTeleporter(block)) {
            block.setType(Material.AIR);
            block.getWorld().dropItemNaturally(block.getLocation(), configHandler.getTeleporter());
            event.setCancelled(true);
        }
    }

    private boolean isTeleporter(ItemStack item) {
        return item.getPersistentDataContainer().has(plugin.getTeleporterKey());
    }

    private boolean isTeleporter(Block block) {
        if (block.getState() instanceof Beacon beacon) {
            return beacon.getPersistentDataContainer().has(plugin.getTeleporterKey());
        }
        return false;
    }

}
