package com.github.aviationtwelve.obesityplugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerStrengthModifier {

    public static void initPlayerEffectOnHeight(Player player, double height) {
        Bukkit.getServer().dispatchCommand(player, "scale set pehkui:attack " + height/2 + " " + player.getName());
        Bukkit.getServer().dispatchCommand(player, "scale set pehkui:health " + height/4 + " " + player.getName());
    }

}
