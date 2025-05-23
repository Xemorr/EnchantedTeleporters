package me.xemor.enchantedTeleporters;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import me.xemor.enchantedTeleporters.configs.ConfigHandler;
import me.xemor.enchantedTeleporters.events.PlaceOrBreakTeleporter;
import me.xemor.enchantedTeleporters.events.Teleportation;


public class EnchantedTeleportersModule extends AbstractModule {

    private final EnchantedTeleporters plugin;

    public EnchantedTeleportersModule(EnchantedTeleporters plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(EnchantedTeleporters.class).toInstance(plugin);
        bind(ConfigHandler.class).in(Singleton.class);
        bind(PlaceOrBreakTeleporter.class).in(Singleton.class);
        bind(Teleportation.class).in(Singleton.class);
    }

}
