package com.github.liberatemetumortis.potionblacklist;

import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PotionBlacklist extends JavaPlugin {
    public ArrayList<String> getBlacklistedPlayers() {
        return blacklistedPlayers;
    }

    private final ArrayList<String> blacklistedPlayers = new ArrayList<>();

    public Database getDb() {
        return db;
    }

    private Database db;


    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        reloadConfig();
        connectToDatabase();
        new EventListener(this);
        new CommandHandler(this);
        new PluginTabCompleter(this);
    }

    @Override
    public void onDisable() {
        db.close();
    }

    public void connectToDatabase() {
        File dbFile = new File(getDataFolder(), "db.sqlite");
        if(!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        db = new Database(dbFile.getAbsolutePath(), this);
    }
}
