package br.com.playdreamcraft;

import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class Cooldown {

    private Map<String, Long> delayed;
    private static Cooldown instance;

    private Cooldown() {
        this.delayed = new HashMap<>();
    }

    public static Cooldown getInstance(){
        if(null == instance){
            instance = new Cooldown();
        }
        return instance;
    }

    public void putPlayerOnDelay(Player player){
        delayed.put(player.getName(), System.currentTimeMillis() + FactionsPower.getMain().getConf().getInt("tempo-de-delay-para-usar-poder.delay"));
    }

    public void removePlayerOfDelay(Player player){
        if(containsPlayerInDelay(player)){
            delayed.remove(player.getName());
        }
    }

    public boolean containsPlayerInDelay(Player player){
        return delayed.containsKey(player.getName()) && !afterLong(player);
    }

    public long getPlayerDelay(Player player){
        if(containsPlayerInDelay(player)){
            return this.delayed.get(player.getName());
        }
        return 0;
    }

    public boolean afterLong(Player p){
        Date vehement = new Date(getPlayerDelay(p));
        Date atual = new Date();
        return atual.after(vehement);
    }

    public String convertMillisToSeconds(long l){
        long seconds = (l / 1000) % 60;
        return String.valueOf(seconds).replaceAll("-", "");
    }

    public String convertMillisToMinutes(long l){
        long minutes = (l / 60000) % 60;
        return String.valueOf(minutes).replaceAll("-", "");
    }

    public long getVariation(Player p){
        return getPlayerDelay(p) - System.currentTimeMillis();
    }
}
