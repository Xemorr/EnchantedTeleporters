package me.xemor.enchantedTeleporters;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.UUID;

public class CooldownHandler {
    private final HashMap<UUID, Double> cooldownMap = new HashMap<>();
    private final String cooldownMessage;

    public CooldownHandler(String cooldownMsg) {
        cooldownMessage = ChatColor.translateAlternateColorCodes('&', cooldownMsg);
    }

    public void startCooldown(double cooldown, UUID uuid) {
        cooldownMap.put(uuid, (cooldown * 1000) + System.currentTimeMillis());
    }

    public boolean isCooldownOver(UUID uuid) {
        if (cooldownMap.containsKey(uuid)) {
            if (cooldownMap.get(uuid) <= System.currentTimeMillis()) return true;
            double seconds = ((cooldownMap.get(uuid) - System.currentTimeMillis()) / 1000);
            if (!cooldownMessage.equals("")) Bukkit.getPlayer(uuid).sendActionBar(String.format(cooldownMessage, seconds));
            return false;
        }
        else return true;
    }

}

