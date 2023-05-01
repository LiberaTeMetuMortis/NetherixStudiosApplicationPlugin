package com.github.liberatemetumortis.potionblacklist;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;

public class EventListener implements Listener {
    private final PotionBlacklist plugin;
    public EventListener(PotionBlacklist plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPotionEffect(EntityPotionEffectEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if(plugin.getDb().isPlayerBlacklisted(player.getUniqueId().toString())) {
            event.setCancelled(true);
        }
    }
}
