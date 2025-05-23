package me.xemor.enchantedTeleporters;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.xemor.enchantedTeleporters.configs.ConfigHandler;
import me.xemor.enchantedTeleporters.events.PlaceOrBreakTeleporter;
import me.xemor.enchantedTeleporters.events.Teleportation;
import org.bstats.bukkit.Metrics;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

public final class EnchantedTeleporters extends JavaPlugin {

    private Injector injector;
    private NamespacedKey teleporterKey = new NamespacedKey(this, "is-teleporter");

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        initializeMetrics();

        injector = Guice.createInjector(
                new EnchantedTeleportersModule(this)
        );

        PlaceOrBreakTeleporter placeOrBreakTeleporter = injector.getInstance(PlaceOrBreakTeleporter.class);
        Teleportation teleportation = injector.getInstance(Teleportation.class);
        EnchantedTeleportersCommand teleportersCommand  = injector.getInstance(EnchantedTeleportersCommand.class);

        getServer().getPluginManager().registerEvents(placeOrBreakTeleporter, this);
        getServer().getPluginManager().registerEvents(teleportation, this);

        Lamp<BukkitCommandActor> lamp = BukkitLamp.builder(this).build();
        lamp.register(teleportersCommand);
    }

    public void initializeMetrics() {
        int pluginId = 25959;
        Metrics metrics = new Metrics(this, pluginId);
    }


    public NamespacedKey getTeleporterKey() {
        return teleporterKey;
    }

    public Injector getInjector() {
        return injector;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
