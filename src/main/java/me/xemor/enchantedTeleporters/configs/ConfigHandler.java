package me.xemor.enchantedTeleporters.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.xemor.configurationdata.ConfigurationData;
import me.xemor.enchantedTeleporters.EnchantedTeleporters;
import me.xemor.enchantedTeleporters.comparators.VanillaTeleporterComparator;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;

@Singleton
public class ConfigHandler {

    private Config config;
    private EnchantedTeleporters plugin;

    @Inject
    public ConfigHandler(EnchantedTeleporters plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            ConfigurationData.setupObjectMapperForConfigurationData(objectMapper);
            config = objectMapper.readValue(new File(plugin.getDataFolder(), "config.yml"), Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ItemStack getTeleporter(VanillaTeleporterComparator comparator) {
        ItemStack clonedTeleporter = config.getTeleporter().clone();
        ItemMeta itemMeta = clonedTeleporter.getItemMeta();
        itemMeta.getPersistentDataContainer().set(comparator.getTeleporterKey(), PersistentDataType.BOOLEAN, true);
        clonedTeleporter.setItemMeta(itemMeta);
        return clonedTeleporter;
    }

    public Component getTeleported() {
        return config.getTeleported();
    }

    public Component getSidewaysTeleportError() {
        return config.getSidewaysTeleportError();
    }
}
