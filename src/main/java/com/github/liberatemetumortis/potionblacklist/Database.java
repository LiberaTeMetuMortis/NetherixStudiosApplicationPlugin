package com.github.liberatemetumortis.potionblacklist;

import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;

public class Database {
    private final PotionBlacklist plugin;
    private final Connection connection;
    public Database(String path, PotionBlacklist plugin) {
        this.plugin = plugin;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS blacklists (uuid TEXT PRIMARY KEY)");
            ResultSet rs = statement.executeQuery("SELECT * FROM blacklists");
            while(rs.next()) {
                plugin.getBlacklistedPlayers().add(rs.getString("uuid"));
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void close() {
        try {
            connection.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isPlayerBlacklisted(String uuid) {
        return plugin.getBlacklistedPlayers().contains(uuid);
    }
    public void blacklistPlayer(String uuid) {
        new BukkitRunnable(){
            @Override
            public void run() {
                try(PreparedStatement statement = connection.prepareStatement("INSERT INTO blacklists (uuid) VALUES (?)")) {
                    statement.setString(1, uuid);
                    statement.executeUpdate();
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskAsynchronously(plugin);

        plugin.getBlacklistedPlayers().add(uuid);
    }

    public void unblacklistPlayer(String uuid) {
        new BukkitRunnable(){
            @Override
            public void run() {
                try(PreparedStatement statement = connection.prepareStatement("DELETE FROM blacklists WHERE uuid = ?")) {
                    statement.setString(1, uuid);
                    statement.executeUpdate();
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskAsynchronously(plugin);

        plugin.getBlacklistedPlayers().remove(uuid);
    }
}
