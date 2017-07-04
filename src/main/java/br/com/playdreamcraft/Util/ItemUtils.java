package br.com.playdreamcraft.Util;

import br.com.playdreamcraft.FactionsPower;
import br.com.playdreamcraft.config.ConfigHandler;
import com.massivecraft.factions.Factions;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ItemUtils {

    private FileConfiguration config;
    private ConfigHandler lang;

    public ItemUtils(FileConfiguration config, ConfigHandler lang) {
        this.config = config;
        this.lang = lang;
    }

    public void removeAmount(@Nonnull ItemStack item, int amount, Inventory inventory) {
        int currentAmount = item.getAmount();
        int newAmount = currentAmount - amount;
        if (item.getAmount() > 1 && newAmount > 0) {
            item.setAmount(newAmount);
        } else if (item.getAmount() == 1) {
            inventory.removeItem(item);
        }
    }

    public ItemStack getItem(final Inventory inventory) {
        List<ItemStack> items = Arrays.asList(inventory.getContents());
        for (ItemStack it : items) {
            if(it == null || it.getType().equals(Material.AIR)){
                continue;
            }
            if(suchItemType(it) != null) {
                if (suchItemType(it).equals(ItemType.Max)) {
                    return it;
                } else if (suchItemType(it).equals(ItemType.Add)) {
                    return it;
                }
            }
        }
        return null;
    }

    public void setItem(Player player, ItemType type, int value){
        Inventory playerinv = player.getInventory();
        switch (type){
            case Add:{
               if(playerinv.firstEmpty() == -1){
                   player.sendMessage(lang.getStringReplaced("inventario-cheio"));
                   return;
               }
               playerinv.setItem(playerinv.firstEmpty(), suchItem("PoderAdcional", value).addEnchantment(Enchantment.DURABILITY, 5).build());
               player.sendMessage(lang.getStringReplaced("item-recebido", "%item%", config.getString("Poderes.PoderAdcional.nome-msg").replaceAll("&", "§")
                       , "%quantidade%", String.valueOf(value)));
               break;
            }
            case Max:{
                if(playerinv.firstEmpty() == -1){
                    player.sendMessage(lang.getStringReplaced("inventario-cheio"));
                    return;
                }
                playerinv.setItem(playerinv.firstEmpty(), suchItem("PoderMaximo", value).addEnchantment(Enchantment.DURABILITY, 10).build());
                player.sendMessage(lang.getStringReplaced("item-recebido", "%item%", config.getString("Poderes.PoderMaximo.nome-msg").replaceAll("&", "§")
                        , "%quantidade%", String.valueOf(value)));
                break;
            }
            default:{
                return;
            }
        }


    }

    public ItemType suchItemType(ItemStack it){
        String maxItem = config.getString("Poderes.PoderMaximo.item");
        String addItem = config.getString("Poderes.PoderAdcional.item");
        if (it != null && it.hasItemMeta()) {
            if (it.getItemMeta().getEnchants().containsKey(Enchantment.DURABILITY)) {
                int enchlvl = it.getItemMeta().getEnchants().get(Enchantment.DURABILITY);
                if (it.getType().toString().equals(maxItem) && enchlvl == 10) {
                    return ItemType.Max;
                } else if (it.getType().toString().equals(addItem) && enchlvl == 5) {
                    return ItemType.Add;
                }
            }
        }
        return ItemType.Normal;
    }

    /**
     *
     * @param path caminho para do item na config
     * @return the item
     */
    public Item suchItem(String path, int value){
        Item it = new Item(config.getString("Poderes." + path + ".item"));
        it.setName(config.getString("Poderes." + path + ".nome"))
                .setLore(lang.getStringList("Poderes." + path + ".lore".replace("%valor%", String.valueOf(config.getDouble("Poderes." + path + "valor")))))
                .setValue(value > 0 ? value : 1).hideAttibutes()
                ;
        return it;
    }

    public boolean isValidItem(ItemStack item){
        return (item.getType().toString().equals(FactionsPower.getMain().getConf().getString("Poderes.PoderMaximo.item")) ||
                item.getType().toString().equals(FactionsPower.getMain().getConf().getString("Poderes.PodesAdcional.item")));
    }
}
