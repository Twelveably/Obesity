package com.github.aviationtwelve.obesityplugin.listeners;

import com.github.aviationtwelve.obesityplugin.managers.PlayerHeightData;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomBlockConsumable implements Listener {

    private final Map<UUID, Integer> eatBlockTimer = new HashMap<>();
    private final Map<UUID, Location> lastMinedBlockLocation = new HashMap<>();
    private static final int INTERVAL = 15;

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        Material food = event.getItem().getType();

        if (food.equals(Material.COOKED_BEEF)) {
            // Your logic for consuming cooked beef
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        Action action = event.getAction();
        Block clickedBlock = event.getClickedBlock();

        if (action.equals(Action.RIGHT_CLICK_BLOCK) && clickedBlock != null && clickedBlock.getType().isBlock()) {
            Location lastLocation = lastMinedBlockLocation.getOrDefault(playerUUID, null);
            if (lastLocation != null && lastLocation.equals(clickedBlock.getLocation())) {
                int timer = eatBlockTimer.getOrDefault(playerUUID, 0);
                player.sendMessage("Tick : " + timer);

                if (timer < INTERVAL) {
                    eatBlockTimer.put(playerUUID, timer + 1);
                    player.getWorld().spawnParticle(
                            Particle.BLOCK_CRACK,
                            player.getLocation(),
                            12,
                            0, 1.0 + (PlayerHeightData.getHeight(player) + 0.4), 0,
                            clickedBlock.getBlockData());
                    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 5.0f, 1.0f);
                } else {
                    clickedBlock.setType(Material.AIR);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 5.0f, 1.0f);
                    PlayerHeightData.addHeight(player, PlayerHeightData.randDouble(0.1, 0.3));
                }
            } else {
                lastMinedBlockLocation.put(playerUUID, clickedBlock.getLocation());
                eatBlockTimer.put(playerUUID, 0);
            }
        }
    }
}
