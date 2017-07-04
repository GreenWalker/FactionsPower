package br.com.playdreamcraft;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;


public class Cooldown {

    private Map<String, Long> delay;
    private static Cooldown instance;

    private Cooldown() {
        this.delay = new HashMap<String, Long>();
    }

    public static Cooldown getInstance(){
        if(null == instance){
            instance = new Cooldown();
        }
        return instance;
    }

    public void putPlayerOnDelay(Player player){
        delay.put(player.getName(), System.currentTimeMillis());
    }

    public void removePlayerOfDelay(Player player){
        if(containsPlayerInDelay(player)){
            delay.remove(player.getName());
        }
    }

    public boolean containsPlayerInDelay(Player player){
        return delay.containsKey(player.getName());
    }
}
