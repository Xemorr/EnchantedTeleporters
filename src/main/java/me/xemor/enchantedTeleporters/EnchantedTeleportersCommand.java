package me.xemor.enchantedTeleporters;

import jakarta.inject.Inject;
import me.xemor.enchantedTeleporters.comparators.TeleporterComparator;
import me.xemor.enchantedTeleporters.comparators.VanillaTeleporterComparator;
import me.xemor.enchantedTeleporters.configs.ConfigHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Default;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.command.CommandActor;

import java.util.HashMap;
import java.util.Map;

@Command("teleporter")
public class EnchantedTeleportersCommand {

    @Inject
    private ConfigHandler configHandler;
    @Inject
    private TeleporterComparator comparator;

    @Subcommand("reload")
    @CommandPermission("enchantedteleporters.reload")
    public void reload(CommandActor actor) {
        configHandler.reload();
        actor.reply("Reloaded");
    }

    @Subcommand("give")
    @CommandPermission("enchantedteleporters.give")
    public void give(CommandActor actor, Player target, @Default("1") int amount) {
        if (!(comparator instanceof VanillaTeleporterComparator comparator)) {
            actor.reply("This server is not using EnchantedTeleporters in vanilla mode, please use the respective plugin hook to acquire teleporters.");
            return;
        }
        ItemStack teleporter = configHandler.getTeleporter(comparator).clone();
        if (amount > 64) {
            actor.reply("You can't give more than 64 teleporters");
            return;
        }
        teleporter.setAmount(amount);
        HashMap<Integer, ItemStack> leftovers = target.getInventory().addItem(teleporter);
        for (Map.Entry<Integer, ItemStack> leftover : leftovers.entrySet()) {
            target.getWorld().dropItemNaturally(target.getLocation(), leftover.getValue());
        }
        actor.reply("%s has been successfully given %d teleporters".formatted(target.getName(), amount));
    }

}
