package br.com.playdreamcraft.Util;

import br.com.playdreamcraft.FactionsPower;
import com.massivecraft.factions.entity.MPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;

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

    public void setPower(ItemStack powerItem, Player player) {
        double atual;
        MPlayer mp = MPlayer.get(player);
        atual = mp.getPowerBoost();
        double novo = FactionsPower.getMain().getConf().getInt("Poderes.PoderMaximo.valor");
        if(atual >= FactionsPower.getMain().getConf().getLong("Poderes.PoderMaximo.maximo")){
            player.sendMessage(FactionsPower.getMain().getLang().getStringReplaced("chegou-ao-limite"));
            return;
        }
        FactionsPower.getMain().getItemUtils().removeAmount(player.getInventory(), 1);
        mp.setPowerBoost(atual + novo);
        String msg = FactionsPower.getMain().getLang().getStringReplaced("action-bar-mensagem.mensagem");
        msg = msg.replaceAll("&", "§").replace("%valor%", new DecimalFormat("0.##").format(atual + novo));
        this.sendActionBar(player, "{\"text\":\"%msg%\"}"
                .replace("%msg%", msg));
    }

    public void sendActionBar(Player player, String json) {
        try {
        IChatBaseComponent baseComponent = IChatBaseComponent.ChatSerializer.a(json);
        PacketPlayOutChat cht = new PacketPlayOutChat(baseComponent, (byte)2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(cht);
        }catch (Throwable ex){
            ex.printStackTrace();
            FactionsPower.getMain().info("&c&lErro de compatibilidade! utilize a versao 1.8 ou contate o desenvolvedor &aFloydz69");
        }
    }


}
