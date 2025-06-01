package me.xemor.enchantedTeleporters.comparators;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

public interface TeleporterComparator {

    boolean isTeleporter(ItemStack item);
    boolean isTeleporter(Block block);

}
