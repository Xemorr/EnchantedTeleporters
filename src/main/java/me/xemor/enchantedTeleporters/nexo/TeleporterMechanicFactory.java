package me.xemor.enchantedTeleporters.nexo;

import com.nexomc.nexo.mechanics.Mechanic;
import com.nexomc.nexo.mechanics.MechanicFactory;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class TeleporterMechanicFactory extends MechanicFactory {

    public TeleporterMechanicFactory() {
        super("teleporter");
    }

    @Override @Nullable
    public TeleporterMechanic getMechanic(String itemId) {
        return (TeleporterMechanic) super.getMechanic(itemId);
    }

    @Override @Nullable
    public TeleporterMechanic getMechanic(ItemStack itemStack) {
        return (TeleporterMechanic) super.getMechanic(itemStack);
    }

    @Override
    public @Nullable Mechanic parse(@NotNull ConfigurationSection configurationSection) {
        TeleporterMechanic mechanic = new TeleporterMechanic(this, configurationSection);
        addToImplemented(mechanic);
        return mechanic;
    }

}
