package me.xemor.enchantedTeleporters.comparators;

import com.google.inject.Inject;
import me.xemor.enchantedTeleporters.EnchantedTeleporters;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class VanillaTeleporterComparator implements TeleporterComparator {

    private final NamespacedKey teleporterKey;

    @Inject
    public VanillaTeleporterComparator(EnchantedTeleporters teleporters) {
        teleporterKey = new NamespacedKey(teleporters, "is-teleporter");
    }

    @Override
    public boolean isTeleporter(ItemStack item) {
        return item.getPersistentDataContainer().has(teleporterKey);
    }

    @Override
    public boolean isTeleporter(Block block) {
        if (block.getState() instanceof Beacon beacon) {
            return beacon.getPersistentDataContainer().has(teleporterKey);
        }
        return false;
    }

    public NamespacedKey getTeleporterKey() {
        return teleporterKey;
    }
}
