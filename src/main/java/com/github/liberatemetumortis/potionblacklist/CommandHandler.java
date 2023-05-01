package com.github.liberatemetumortis.potionblacklist;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import static com.github.liberatemetumortis.potionblacklist.Utils.translateColors;

public class CommandHandler implements CommandExecutor {
    PotionBlacklist plugin;
    public CommandHandler(PotionBlacklist plugin) {
        plugin.getCommand("potion").setExecutor(this);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("potion.blacklist")) {
            sender.sendMessage(translateColors(plugin.getConfig().getString("messages.no-permission")));
            return true;
        }
        if(args.length < 2 || (!args[0].equalsIgnoreCase("blacklist") && !args[0].equalsIgnoreCase("unblacklist"))) {
            sender.sendMessage(translateColors(plugin.getConfig().getString("usages.potion")));
            return true;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if(player == null) {
            sender.sendMessage(translateColors(plugin.getConfig().getString("messages.player-not-found")));
            return true;
        }

        String operation = args[0];
        switch (operation) {
            case "blacklist" -> {
                if(plugin.getDb().isPlayerBlacklisted(player.getUniqueId().toString())) {
                    sender.sendMessage(translateColors(plugin.getConfig().getString("messages.player-already-blacklisted")));
                    return true;
                }
                plugin.getDb().blacklistPlayer(player.getUniqueId().toString());
                sender.sendMessage(translateColors(plugin.getConfig().getString("messages.player-blacklisted")));
            }
            case "unblacklist" -> {
                if(!plugin.getDb().isPlayerBlacklisted(player.getUniqueId().toString())) {
                    sender.sendMessage(translateColors(plugin.getConfig().getString("messages.player-not-blacklisted")));
                    return true;
                }
                plugin.getDb().unblacklistPlayer(player.getUniqueId().toString());
                sender.sendMessage(translateColors(plugin.getConfig().getString("messages.player-unblacklisted")));
            }
        }


        return true;
    }
}
