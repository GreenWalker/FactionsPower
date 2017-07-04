package br.com.playdreamcraft.commands;

import br.com.playdreamcraft.FactionsPower;
import br.com.playdreamcraft.Util.ItemType;
import br.com.playdreamcraft.Util.UtilMethods;
import br.com.playdreamcraft.config.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MainCommands implements CommandExecutor {

    private ConfigHandler lang;

    public MainCommands(ConfigHandler lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {
        if(!command.getName().equalsIgnoreCase("factionspower")){
            return true;
        }

        if(args.length < 1){
            cs.sendMessage("§6 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            cs.sendMessage("§7Lg: <command> = obrigatorio | [command] opcional");
            cs.sendMessage("\n§3[§7-§3]§7fc give <adicional/adc | maxima/max> [quantidade] [player/all]");
            cs.sendMessage("§a- dá um item de poder para um ou todos os jogadoresz\n");
            cs.sendMessage("§6 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        }
        if(args.length > 0) {
            if (args[0].equalsIgnoreCase("give")) {
                if (args.length < 2) {
                    cs.sendMessage("§7Você precisa especificar o tipo do item! adicional ou maxima.");
                    return true;
                }
                if (args[1].equalsIgnoreCase("adicional") || args[1].equalsIgnoreCase("adc")) {
                    if (args.length >= 3) {
                        if (!UtilMethods.getInstance().isNumber(args[2])) {
                            cs.sendMessage(lang.getStringReplaced("nao-e-numero"));
                            return true;
                        }
                    }
                    if (args.length == 2) {
                        if (!commandSenderIsPlayer(cs)) {
                            cs.sendMessage("§bEste comando pode ser utilizado apenas in-game!");
                            return true;
                        }
                        FactionsPower.getMain().getItemUtils().setItem((Player) cs, ItemType.Add, 1);
                        return true;
                    }
                    if (args.length == 3) {
                        if (!commandSenderIsPlayer(cs)) {
                            cs.sendMessage("§bEste comando pode ser utilizado apenas in-game!");
                            return true;
                        }
                        FactionsPower.getMain().getItemUtils().setItem((Player) cs, ItemType.Add, Integer.valueOf(args[2]));
                        return true;
                    }
                    if (args.length >= 4) {
                        if (args[3].equalsIgnoreCase("all")) {
                            Bukkit.getOnlinePlayers().forEach(p -> FactionsPower.getMain().getItemUtils().setItem(p, ItemType.Add, Integer.valueOf(args[2])));
                            return true;
                        }
                        if (Bukkit.getPlayerExact(args[3]) != null) {
                            Player p = Bukkit.getPlayerExact(args[3]);
                            FactionsPower.getMain().getItemUtils().setItem(p, ItemType.Add, Integer.valueOf(args[2]));
                        }
                    }
                }
                if (args[1].equalsIgnoreCase("maxima") || args[1].equalsIgnoreCase("max")) {
                    if (args.length >= 3) {
                        if (!UtilMethods.getInstance().isNumber(args[2])) {
                            cs.sendMessage(lang.getStringReplaced("nao-e-numero"));
                            return true;
                        }
                    }

                    if (args.length == 2) {
                        if (!commandSenderIsPlayer(cs)) {
                            cs.sendMessage("§bEste comando pode ser utilizado apenas in-game!");
                            return true;
                        }
                        FactionsPower.getMain().getItemUtils().setItem((Player) cs, ItemType.Max, 1);
                        return true;
                    }
                    if (args.length == 3) {
                        if (!commandSenderIsPlayer(cs)) {
                            cs.sendMessage("§bEste comando pode ser utilizado apenas in-game!");
                            return true;
                        }
                        FactionsPower.getMain().getItemUtils().setItem((Player) cs, ItemType.Max, Integer.valueOf(args[2]));
                        return true;
                    }
                    if (args.length >= 4) {
                        if (args[3].equalsIgnoreCase("all")) {
                            Bukkit.getOnlinePlayers().forEach(p -> FactionsPower.getMain().getItemUtils().setItem(p, ItemType.Max, Integer.valueOf(args[2])));
                            return true;
                        }
                        if (Bukkit.getPlayerExact(args[3]) != null) {
                            Player p = Bukkit.getPlayerExact(args[3]);
                            FactionsPower.getMain().getItemUtils().setItem(p, ItemType.Max, Integer.valueOf(args[2]));
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean commandSenderIsPlayer(CommandSender sender){
        return sender instanceof Player;

    }
}
