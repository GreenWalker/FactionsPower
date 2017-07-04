package br.com.playdreamcraft;

import br.com.playdreamcraft.Util.ItemUtils;
import br.com.playdreamcraft.config.ConfigHandler;
import br.com.playdreamcraft.events.MainEvents;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class FactionsPower extends JavaPlugin {

    private static FactionsPower main;
    private ConfigHandler lang;
    private ItemUtils itemUtils;

    @Override
    public void onEnable() {
       main = this;
       setupConfig();
       setupInstances();
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
        try {
            lang = new ConfigHandler(this, "lang.yml");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        if(!new File(getDataFolder().getPath(), "config.yml").exists()){
            saveDefaultConfig();
        }
    }

    private void setupInstances(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new MainEvents(), this);
        itemUtils = new ItemUtils();
    }

    public FileConfiguration getConf(){
        return getConfig();
    }

    public ConfigHandler getLang() {
        return lang;
    }

    public ItemUtils getItemUtils() {
        return itemUtils;
    }
}
