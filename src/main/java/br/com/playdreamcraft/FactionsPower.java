package br.com.playdreamcraft;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class FactionsPower extends JavaPlugin {

    private static FactionsPower main;

    @Override
    public void onEnable() {
       main = this;
       info("&c[" + getName() + "] acaba de iniciar!");
    }

    @Override
    public void onDisable() {

    }

    public static FactionsPower getMain(){
        return main;
    }

    public void info(String info){
        this.getServer().getConsoleSender().sendMessage(info.replaceAll("&", "§"));
    }

    private void setupConfig(){
        if(!new File(getDataFolder().getPath(), "config.yml").exists()){
            saveDefaultConfig();
        }
    }

    public FileConfiguration getConf(){
        return getConfig();
    }
}
