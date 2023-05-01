package com.github.liberatemetumortis.potionblacklist;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.stream.Collectors;

public class PluginTabCompleter implements TabCompleter {
    PotionBlacklist plugin;
    public PluginTabCompleter(PotionBlacklist plugin) {
        this.plugin = plugin;
        plugin.getCommand("potion").setTabCompleter(this);
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("potion.blacklist")) return null;
        if(args.length == 1) {
            return List.of("blacklist", "unblacklist");
        }
        else if(args.length == 2) {
            /*
            String operation = args[0];
            List<Player> blacklistedPlayers = plugin.getBlacklistedPlayers().stream().map(Bukkit::getPlayer).filter(Objects::nonNull).toList();
            switch (operation) {
                case "blacklist" -> { // Show only players that are not blacklisted
                    return Bukkit.getOnlinePlayers().stream().filter(player -> !blacklistedPlayers.contains(player)).map(Player::getName).collect(Collectors.toList());
                }
                case "unblacklist" -> { // Show only players that are blacklisted
                    return blacklistedPlayers.stream().map(Player::getName).collect(Collectors.toList());
                }
            }
             */
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        }
        return null;
    }
}
