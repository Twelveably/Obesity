package com.github.aviationtwelve.obesityplugin.listeners;

import com.github.aviationtwelve.obesityplugin.managers.PlayerHeightData;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomEntityConsumable implements Listener {

    private final Map<UUID, Integer> eatEntityTimer = new HashMap<>();
    private final Map<UUID, Integer> lastEntityInteracted = new HashMap<>();

    private static final int INTERVAL = 20;

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        Entity target = event.getRightClicked();

        eatEntityTimer.putIfAbsent(playerUUID, 0);
        lastEntityInteracted.put(playerUUID, target.getEntityId());

        int timer = eatEntityTimer.getOrDefault(playerUUID, 0);
        int lastInteractedEntityId = lastEntityInteracted.getOrDefault(playerUUID, -1);

        player.sendMessage("Tick : " + timer);

        if (target.getEntityId() == lastInteractedEntityId) {
            if (timer < INTERVAL) {
                eatEntityTimer.put(playerUUID, timer + 1);
                player.getWorld().spawnParticle(
                        Particle.BLOCK_CRACK,
                        player.getLocation(),
                        12,
                        0, 1.0 + (PlayerHeightData.getHeight(player) + 0.4), 0,
                        Material.PORKCHOP);
                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 5.0f, 1.0f);
            } else {
                target.remove();
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 5.0f, 1.0f);
                PlayerHeightData.addHeight(player, PlayerHeightData.randDouble(0.1, 0.3));
                lastEntityInteracted.remove(playerUUID);
                eatEntityTimer.put(playerUUID, 0);
            }
        } else {
            lastEntityInteracted.remove(playerUUID);
            eatEntityTimer.put(playerUUID, 0);
        }
    }
}
