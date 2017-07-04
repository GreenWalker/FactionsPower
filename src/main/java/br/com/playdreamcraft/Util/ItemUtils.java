package br.com.playdreamcraft.Util;

import br.com.playdreamcraft.FactionsPower;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class ItemUtils {

    public ItemUtils() {

    }

    public void removeAmount(@Nonnull Inventory inventory, int amount) {
        ItemStack item = this.getItem(inventory) != null ? this.getItem(inventory) : new ItemStack(Material.AIR);
        int currentAmount = item.getAmount();
        int newAmount = currentAmount - amount;
        if (item.getAmount() > 1) {
            item.setAmount(newAmount);
        } else if (item.getAmount() == 1) {
            inventory.removeItem(item);
        }
    }

    public ItemStack getItem(final Inventory inventory) {
        List<ItemStack> items = Arrays.asList(inventory.getContents());
        for (ItemStack it : items) {
            if(suchItemType(it).equals(ItemType.Max)){
                return it;
            }else if(suchItemType(it).equals(ItemType.Add)){
                return it;
            }
        }
        return null;
    }

    public ItemType suchItemType(ItemStack it){
        String maxItem = FactionsPower.getMain().getConf().getString("PoderMaximo.item");
        String addItem = FactionsPower.getMain().getConf().getString("PoderAdcional.item");
        if (it != null && it.hasItemMeta()) {
            if (it.getItemMeta().getEnchants().containsKey(Enchantment.DURABILITY)) {
                int enchlvl = it.getItemMeta().getEnchants().get(Enchantment.DURABILITY);
                if (it.getType().toString().equals(maxItem)  && enchlvl == 10) {
                    return ItemType.Max;
                } else if (it.getType().toString().equals(addItem) && enchlvl == 5) {
                    return ItemType.Add;
                }
            }
        }
        return null;
    }
}
