package me.xemor.enchantedTeleporters.events;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.xemor.enchantedTeleporters.EnchantedTeleporters;
import me.xemor.enchantedTeleporters.comparators.TeleporterComparator;
import me.xemor.enchantedTeleporters.comparators.VanillaTeleporterComparator;
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
    @Inject
    private TeleporterComparator comparator;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onElevatorPlace(BlockPlaceEvent event) {
        if (!(comparator instanceof VanillaTeleporterComparator comparator)) return;
        if (event.isCancelled()) return;
        if (!comparator.isTeleporter(event.getItemInHand())) return;
        Block block = event.getBlock();
        if (block.getState() instanceof Beacon beacon) {
            beacon.getPersistentDataContainer().set(comparator.getTeleporterKey(), PersistentDataType.BOOLEAN, true);
            beacon.update();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onElevatorBreak(BlockBreakEvent event) {
        if (!(comparator instanceof VanillaTeleporterComparator comparator)) return;
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.CREATIVE)) return;
        Block block = event.getBlock();
        if (comparator.isTeleporter(block)) {
            block.setType(Material.AIR);
            block.getWorld().dropItemNaturally(block.getLocation(), configHandler.getTeleporter(comparator));
            event.setCancelled(true);
        }
    }



}
