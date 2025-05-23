package me.xemor.enchantedTeleporters.configs;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public class Config {

    @JsonProperty
    private ItemStack teleporter;
    @JsonProperty
    private Component teleported;
    @JsonProperty
    private Component sidewaysTeleportError;

    public ItemStack getTeleporter() {
        return teleporter;
    }

    public Component getTeleported() {
        return teleported;
    }

    public Component getSidewaysTeleportError() {
        return sidewaysTeleportError;
    }
}
