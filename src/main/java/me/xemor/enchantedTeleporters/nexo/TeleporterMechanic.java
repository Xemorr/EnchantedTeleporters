package me.xemor.enchantedTeleporters.nexo;

import com.nexomc.nexo.items.ItemBuilder;
import com.nexomc.nexo.mechanics.Mechanic;
import com.nexomc.nexo.mechanics.MechanicFactory;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class TeleporterMechanic extends Mechanic {
    public TeleporterMechanic(@Nullable MechanicFactory factory, @NotNull ConfigurationSection section) {
        super(factory, section);
    }
}
