package com.github.aviationtwelve.obesityplugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerHeightData {

    private static final HashMap<UUID, Integer> realHeight = new HashMap<>();
    private static final HashMap<UUID, Double> playerSizeData = new HashMap<>();
    private static final ThreadLocalRandom rand = ThreadLocalRandom.current();

    public static void addHeight(Player player, double amount) {
        UUID playerUUID = player.getUniqueId();
        double sizeAmount = playerSizeData.getOrDefault(playerUUID, 0.1) + amount;
        int realHeightCounter = realHeight.getOrDefault(playerUUID, 30) + randInt(20, 45);

        Bukkit.getServer().dispatchCommand(player, "scale set pehkui:base " + sizeAmount + " " + player.getName());

        PlayerStrengthModifier.initPlayerEffectOnHeight(player, getHeight(player));

        realHeight.put(playerUUID, realHeightCounter);
        playerSizeData.put(playerUUID, sizeAmount);
    }

    public static int getRealHeight(Player player) {
        return realHeight.getOrDefault(player.getUniqueId(), 30);
    }

    public static double getHeight(Player player) {
        return playerSizeData.getOrDefault(player.getUniqueId(), 0.3);
    }

    public static void defaultHeight(Player player) {
        UUID playerUUID = player.getUniqueId();
        realHeight.putIfAbsent(playerUUID, 30);
        playerSizeData.putIfAbsent(playerUUID, 0.3);
    }

    public static double randDouble(double min, double max) {
        return rand.nextDouble(min, max + 1); // Adjusted to use nextDouble(double min, double max)
    }

    public static int randInt(int min, int max) {
        return rand.nextInt(min, max + 1); // Adjusted to use nextInt(int min, int max)
    }
}
