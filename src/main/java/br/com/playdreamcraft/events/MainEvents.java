package br.com.playdreamcraft.events;


import br.com.playdreamcraft.FactionsPower;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;

public class MainEvents implements Listener {

    @EventHandler
    public void onClickInventory(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player){
            Player p = (Player) event.getWhoClicked();
            if(event.getInventory() instanceof AnvilInventory){
                if(FactionsPower.getMain().getConf().getBoolean("RenomarNaBigorna")){

                }
            }
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        MPlayer player = MPlayer.get(event.getPlayer());
    }
}
