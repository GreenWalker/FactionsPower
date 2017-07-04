package br.com.playdreamcraft.Util;

import br.com.playdreamcraft.FactionsPower;
import br.com.playdreamcraft.Util.fireworkutil.FireWorkApiDamageable;
import com.massivecraft.factions.entity.MPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

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
        ItemType type = FactionsPower.getMain().getItemUtils().suchItemType(powerItem);
        double atual, novo;
        if (type.equals(ItemType.Max)) {
            MPlayer mp = MPlayer.get(player);
            atual = mp.getPowerBoost();
            novo = FactionsPower.getMain().getConf().getInt("Poderes.PoderMaximo.valor");
            if (atual >= FactionsPower.getMain().getConf().getLong("Poderes.PoderMaximo.maximo")) {
                player.sendMessage(FactionsPower.getMain().getLang().getStringReplaced("chegou-ao-limite"));
                return;
            }
            FactionsPower.getMain().getItemUtils().removeAmount(player.getItemInHand(), 1, player.getInventory());
            mp.setPowerBoost(atual + novo);
            String msg = FactionsPower.getMain().getLang().getStringReplaced("action-bar-mensagem.mensagem-max");
            fireWork(player.getLocation());
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.5F, 1.5F);
            msg = msg.replaceAll("&", "§").replace("%valor%", new DecimalFormat("0.##").format(atual + novo));
            this.sendActionBar(player, "{\"text\":\"%msg%\"}"
                    .replace("%msg%", msg));
        } else if (type.equals(ItemType.Add)) {
            MPlayer mp = MPlayer.get(player);
            atual = mp.getPower();
            novo = FactionsPower.getMain().getConf().getDouble("Poderes.PoderAdcional.valor");
            if (atual == mp.getPowerMax()) {
                player.sendMessage(FactionsPower.getMain().getLang().getStringReplaced("chegou-ao-poder-maximo"));
                return;
            }
            FactionsPower.getMain().getItemUtils().removeAmount(player.getItemInHand(), 1, player.getInventory());
            mp.setPower(atual + novo);
            String msg = FactionsPower.getMain().getLang().getStringReplaced("action-bar-mensagem.mensagem-adcional");
            msg = msg.replaceAll("&", "§").replace("%valor%", new DecimalFormat("0.##").format(atual + novo));
           fireWork(player.getLocation());
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.5F, 1.5F);
            this.sendActionBar(player, "{\"text\":\"%msg%\"}"
                    .replace("%msg%", msg));
        }
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

    public void sendTitle(Player player, String jsonT, String jsonST){
        try{
            PacketPlayOutTitle cht = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a(jsonT), 20, 60 , 30);
            PacketPlayOutTitle cht2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a(jsonST), 20, 60,30);
            PlayerConnection pc = ((CraftPlayer)player).getHandle().playerConnection;
            pc.sendPacket(cht);
            pc.sendPacket(cht2);
        }catch (Exception ex){
            ex.printStackTrace();
            FactionsPower.getMain().info("&c&lErro de compatibilidade! utilize a versao 1.8 ou contate o desenvolvedor &aFloydz69");
        }
    }

    public void fireWork(Location loc) {
        final Firework fw = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta fm = fw.getFireworkMeta();
        fm.addEffects(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE)
                .withColor(Color.RED)
                .withColor(Color.AQUA)
                .withColor(Color.ORANGE)
                .withColor(Color.YELLOW)
                .trail(false)
                .flicker(false)
                .build());
        fm.setPower(0);
        fw.setFireworkMeta(fm);
        FireWorkApiDamageable.addFirework(fw);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FactionsPower.getMain(), fw::detonate, 2);
    }

    public boolean isNumber(String number){
        try{
            int i = Integer.valueOf(number);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
