package com.github.aviationtwelve.obesityplugin;

import com.github.aviationtwelve.obesityplugin.listeners.CustomBlockConsumable;
import com.github.aviationtwelve.obesityplugin.listeners.CustomEntityConsumable;
import com.github.aviationtwelve.obesityplugin.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public class ObesityPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ObesityPlugin enabled!");

        // Register listeners
        try {
            getServer().getPluginManager().registerEvents(new CustomEntityConsumable(), this);
            getServer().getPluginManager().registerEvents(new CustomBlockConsumable(), this);
            getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        } catch (Exception e) {
            getLogger().severe("Failed to register one or more event listeners: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("ObesityPlugin disabled!");
    }
}
