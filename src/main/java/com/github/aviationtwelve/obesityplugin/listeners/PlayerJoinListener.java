package com.github.aviationtwelve.obesityplugin.listeners;

import com.github.aviationtwelve.obesityplugin.ObesityPlugin;
import com.github.aviationtwelve.obesityplugin.managers.PlayerHeightData;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinListener implements Listener {

    ObesityPlugin plugin;

    public PlayerJoinListener(ObesityPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerHeightData.defaultHeight(player);
        Bukkit.getServer().dispatchCommand(player, "scale set pehkui:base 0.1 " + player.getName());
        Bukkit.getServer().dispatchCommand(player, "scale set pehkui:attack 0.1 " + player.getName());

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                }
                int height = (int) PlayerHeightData.getRealHeight(player);
                sendTitle(player, ChatColor.translateAlternateColorCodes(
                        '&',
                        "&bTinggi badan saat ini: &6&l" + height + " cm"));
            }
        }.runTaskTimer(this.plugin, 5L, 5L);

    }

    public void sendTitle(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }
}
