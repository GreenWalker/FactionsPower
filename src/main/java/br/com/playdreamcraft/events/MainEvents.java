package br.com.playdreamcraft.events;


import br.com.playdreamcraft.Cooldown;
import br.com.playdreamcraft.FactionsPower;
import br.com.playdreamcraft.Util.UtilMethods;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class MainEvents implements Listener {

    private Cooldown cooldown;

    public MainEvents(Cooldown cooldown) {
        this.cooldown = cooldown;
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player){
            Player p = (Player) event.getWhoClicked();
            if(event.getInventory() instanceof AnvilInventory){
                if(!FactionsPower.getMain().getConf().getBoolean("RenomarNaBigorna")){
                    ItemStack item = event.getCurrentItem();
                    if(item.hasItemMeta() && item.getItemMeta().getEnchants().containsKey(Enchantment.DURABILITY) && FactionsPower.getMain().getItemUtils().isValidItem(item)){
                    event.setCancelled(true);
                    p.closeInventory();
                    p.sendMessage(FactionsPower.getMain().getLang().getStringReplaced("nao-pode-renomear"));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player p = event.getPlayer();
        ItemStack item = event.getItem();
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(event.getItem() != null && FactionsPower.getMain().getItemUtils().isValidItem(item) && item.hasItemMeta()){
                if(cooldown.containsPlayerInDelay(p)) {
                    if(!cooldown.afterLong(p)) {
                        UtilMethods.getInstance().sendTitle(p,
                                "{\"text\":\"§c§lAviso!\"}",
                                "{\"text\":\"§7Você não pode utilizar este item por §c§l%tempo%\"}".replace("%tempo%", FactionsPower.getMain().getConf().getInt("Poderes.tempo-de-delay-para-usar-poder.delay") > 60 ?
                                cooldown.convertMillisToMinutes(cooldown.getVariation(p)) + "§r §7minutos" : cooldown.convertMillisToSeconds(cooldown.getVariation(p)) + "§r §7segundos"));
                        return;
                    }
                }

                if(FactionsPower.getMain().getConf().getBoolean("Poderes.tempo-de-delay-para-usar-poder.ativo")){
                    cooldown.putPlayerOnDelay(p);
                }
                    UtilMethods.getInstance().setPower(item, p);
            }
        }
    }

}
