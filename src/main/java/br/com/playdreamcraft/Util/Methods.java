package br.com.playdreamcraft.Util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Methods{

    void setPower(ItemStack powerItem, Player player);

    void sendActionBar(Player player, String json);


}
