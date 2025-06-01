package me.xemor.enchantedTeleporters.comparators;

import com.google.inject.Inject;
import com.nexomc.nexo.api.NexoBlocks;
import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.mechanics.custom_block.CustomBlockMechanic;
import me.xemor.enchantedTeleporters.nexo.TeleporterMechanicFactory;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class NexoTeleporterComparator implements TeleporterComparator {

    private TeleporterMechanicFactory factory;

    @Inject
    public NexoTeleporterComparator(TeleporterMechanicFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean isTeleporter(ItemStack item) {
        return factory.getMechanic(NexoItems.idFromItem(item)) != null;
    }

    @Override
    public boolean isTeleporter(Block block) {
        CustomBlockMechanic customBlockMechanic = NexoBlocks.customBlockMechanic(block);
        if (customBlockMechanic == null) return false;
        return factory.getMechanic(customBlockMechanic.getItemID()) != null;
    }
}
