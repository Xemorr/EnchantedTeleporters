package me.xemor.enchantedTeleporters;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import me.xemor.enchantedTeleporters.comparators.TeleporterComparator;
import me.xemor.enchantedTeleporters.configs.ConfigHandler;
import me.xemor.enchantedTeleporters.events.PlaceOrBreakTeleporter;
import me.xemor.enchantedTeleporters.events.Teleportation;


public class EnchantedTeleportersModule extends AbstractModule {

    private final EnchantedTeleporters plugin;
    private final TeleporterComparator comparator;

    public EnchantedTeleportersModule(EnchantedTeleporters plugin, TeleporterComparator comparator) {
        this.plugin = plugin;
        this.comparator = comparator;
    }

    @Override
    protected void configure() {
        bind(EnchantedTeleporters.class).toInstance(plugin);
        bind(ConfigHandler.class).in(Singleton.class);
        bind(PlaceOrBreakTeleporter.class).in(Singleton.class);
        bind(Teleportation.class).in(Singleton.class);
        bind(TeleporterComparator.class).toInstance(comparator);
    }

}
