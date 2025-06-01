package me.xemor.enchantedTeleporters;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nexomc.nexo.api.events.NexoMechanicsRegisteredEvent;
import com.nexomc.nexo.mechanics.MechanicsManager;
import me.xemor.enchantedTeleporters.comparators.ComparatorProvider;
import me.xemor.enchantedTeleporters.comparators.NexoTeleporterComparator;
import me.xemor.enchantedTeleporters.comparators.TeleporterComparator;
import me.xemor.enchantedTeleporters.comparators.VanillaTeleporterComparator;
import me.xemor.enchantedTeleporters.events.PlaceOrBreakTeleporter;
import me.xemor.enchantedTeleporters.events.Teleportation;
import me.xemor.enchantedTeleporters.nexo.TeleporterMechanicFactory;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

public final class EnchantedTeleporters extends JavaPlugin {

    private Injector injector;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        initializeMetrics();

        TeleporterComparator comparator = setupComparator();

        injector = Guice.createInjector(
                new EnchantedTeleportersModule(this, comparator)
        );

        PlaceOrBreakTeleporter placeOrBreakTeleporter = injector.getInstance(PlaceOrBreakTeleporter.class);
        Teleportation teleportation = injector.getInstance(Teleportation.class);
        EnchantedTeleportersCommand teleportersCommand  = injector.getInstance(EnchantedTeleportersCommand.class);

        getServer().getPluginManager().registerEvents(placeOrBreakTeleporter, this);
        getServer().getPluginManager().registerEvents(teleportation, this);

        Lamp<BukkitCommandActor> lamp = BukkitLamp.builder(this).build();
        lamp.register(teleportersCommand);
    }

    public TeleporterComparator setupComparator() {
        ComparatorProvider provider = Bukkit.getPluginManager().isPluginEnabled("Nexo") ? ComparatorProvider.NEXO : ComparatorProvider.VANILLA;
        getLogger().info("EnchantedTeleporters launching in %s mode".formatted(provider.name()));
        TeleporterComparator comparator;
        TeleporterMechanicFactory teleporterMechanicFactory = new TeleporterMechanicFactory();
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void nRegister(NexoMechanicsRegisteredEvent event) {
                MechanicsManager.INSTANCE.registerMechanicFactory(teleporterMechanicFactory, true);
            }
        }, this);
        if (provider == ComparatorProvider.NEXO) comparator = new NexoTeleporterComparator(teleporterMechanicFactory);
        else comparator = new VanillaTeleporterComparator(this);
        return comparator;
    }

    public void initializeMetrics() {
        int pluginId = 25959;
        Metrics metrics = new Metrics(this, pluginId);
    }

    public Injector getInjector() {
        return injector;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
