package br.com.playdreamcraft.Util;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class UtilMethods implements Methods {

    private static UtilMethods instance;

    private UtilMethods(){

    }

    public static UtilMethods getInstance(){
        if(null == instance){
            instance = new UtilMethods();
        }
        return instance;
    }

    public void setPower(Player player, double quant) {
        IChatBaseComponent baseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"Você upou um level\",\"color\":\"green\"}");
        PacketPlayOutChat cht = new PacketPlayOutChat(baseComponent, (byte)2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(cht);
    }

    public double getPower() {
        return 0;
    }

    public void setActionBar(String msg) {

    }

}
