package me.xemor.enchantedTeleporters.events;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.xemor.enchantedTeleporters.CooldownHandler;
import me.xemor.enchantedTeleporters.EnchantedTeleporters;
import me.xemor.enchantedTeleporters.configs.ConfigHandler;
import org.bukkit.Location;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

@Singleton
public class Teleportation implements Listener {

    @Inject
    private ConfigHandler configHandler;
    @Inject
    private EnchantedTeleporters plugin;
    private final CooldownHandler cooldownHandler = new CooldownHandler("");

    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        Location location = event.getFrom();
        Block beacon = location.add(0, -1, 0).getBlock();
        Player player = event.getPlayer();
        if (isTeleporter(beacon)) {
            for (int i = location.getBlockY() + 1; i < player.getWorld().getMaxHeight(); i++) {
                location.setY(i);
                Block block = location.getBlock();
                if (isTeleporter(block)) {
                    teleport(player, location);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent event) {
        if (event.isSneaking()) {
            Player player = event.getPlayer();
            Location location = player.getLocation();
            Block beacon = location.add(0, -1, 0).getBlock();
            if (isTeleporter(beacon)) {
                for (int i = beacon.getLocation().getBlockY() - 1; i >= player.getWorld().getMinHeight(); i--) {
                    location.setY(i);
                    Block block = location.getBlock();
                    if (isTeleporter(block)) {
                        teleport(player, location);
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPunch(PlayerAnimationEvent event) {
        if (event.getAnimationType().equals(PlayerAnimationType.ARM_SWING)) {
            Player player = event.getPlayer();
            Location location = player.getLocation();
            location = location.add(0, -1, 0);
            Block block = location.getBlock();
            if (!cooldownHandler.isCooldownOver(player.getUniqueId())) return;
            if (isTeleporter(block)) {
                float rotation = player.getEyeLocation().getYaw();
                Vector direction = simplifyYaw(rotation);
                for (int i = 0; i <= 90; i++) {
                    location = location.add(direction);
                    if (isTeleporter(location.getBlock())) {
                        cooldownHandler.startCooldown(0.5, player.getUniqueId());
                        teleport(player, location);
                        return;
                    }
                }
                player.sendActionBar(configHandler.getSidewaysTeleportError());
            }
        }
    }

    private boolean isTeleporter(Block block) {
        if (block.getState() instanceof Beacon beacon) {
            return beacon.getPersistentDataContainer().has(plugin.getTeleporterKey());
        }
        return false;
    }

    private void teleport(Player player, Location tpLocation) {
        Location eyeLocation = player.getEyeLocation();
        tpLocation.setYaw(eyeLocation.getYaw());
        tpLocation.setPitch(eyeLocation.getPitch());
        player.teleport(tpLocation.add(0, 1, 0));
        player.sendActionBar(configHandler.getTeleported());
    }

    public Vector simplifyYaw(float rotation) {
        if (rotation < 0) rotation += 360.0F;
        if (rotation < 45.0F) return new Vector(0, 0, 1);
        else if (rotation < 135.0F) return new Vector(-1, 0, 0);
        else if (rotation < 225.0F) return new Vector(0, 0, -1);
        else if (rotation < 315.0F) return new Vector(1, 0, 0);
        else if (rotation < 360.0D) return new Vector(0, 0, 1);
        return new Vector(0, 0, 0);
    }
}
