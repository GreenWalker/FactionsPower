package br.com.playdreamcraft.Util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;


public class Item {

    private ItemStack item;

    public Item(){
        item = new ItemStack(Material.AIR);
    }

    public Item(Material mat){
        item = new ItemStack(mat);
    }

    public Item(String mat){
        item = new ItemStack(Material.valueOf(mat));
    }

    public ItemStack build(){
        return this.item;
    }

    public Item setName(String name){
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name.replaceAll("&", "§"));
        this.item.setItemMeta(meta);
        return this;
    }

    public Item setLore(String[] args){
        ItemMeta meta = this.item.getItemMeta();
        for(int i = 0; i < args.length; i++){
            args[i] = args[i].replaceAll("&", "§");
        }
        meta.setLore(Arrays.asList(args));
        this.item.setItemMeta(meta);
        return this;
    }

    public Item setLore(List<String> args){
        ItemMeta meta = this.item.getItemMeta();
        for (String arg : args) {
            arg.replaceAll("&", "§");
        }
        meta.setLore(args);
        this.item.setItemMeta(meta);
        return this;
    }

    public Item addEnchantment(Enchantment enchantment, int lvl){
        this.item.addUnsafeEnchantment(enchantment, lvl);
        return this;
    }

    public Item hideAttibutes(){
        ItemMeta meta = this.item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS);
        this.item.setItemMeta(meta);
        return this;
    }

    public Item setValue(int value){
        if(this.item.getAmount() > 64){
            return this;
        }
        this.item.setAmount(value);
        return this;
    }
}
