package br.com.playdreamcraft.events;


import br.com.playdreamcraft.FactionsPower;
import br.com.playdreamcraft.Util.UtilMethods;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class MainEvents implements Listener {

    @EventHandler
    public void onClickInventory(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player){
            Player p = (Player) event.getWhoClicked();
            if(event.getInventory() instanceof AnvilInventory){
                if(!FactionsPower.getMain().getConf().getBoolean("RenomarNaBigorna")){
                    ItemStack item = event.getCurrentItem();
                    if(item.hasItemMeta() && item.getItemMeta().getEnchants().containsKey(Enchantment.DURABILITY) &&
                            (item.getType().toString().equals(FactionsPower.getMain().getConf().getString("Poderes.PoderMaximo.item")) ||
                                    item.getType().toString().equals(FactionsPower.getMain().getConf().getString("Poderes.PodesAdcional.item")))){
                    event.setCancelled(true);
                    p.sendMessage("você não pode renomear este item!");
                    return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        UtilMethods.getInstance().setPower(p.getItemInHand(), p);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player p = event.getPlayer();
        UtilMethods.getInstance().setPower(p.getItemInHand(), p);
    }
}
