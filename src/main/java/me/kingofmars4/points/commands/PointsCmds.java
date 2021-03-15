package me.kingofmars4.points.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.kingofmars4.points.Main;
import me.kingofmars4.points.utils.CurrencyManager;
import me.kingofmars4.points.utils.U;

public class PointsCmds implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!sender.hasPermission("points.see")) { sender.sendMessage(U.color("&cYou have no permission!")); return true;}
		CurrencyManager cm = new CurrencyManager();
		
		
		if (args.length == 0) {
			
			if (sender instanceof Player) {
				Player p = (Player) sender;
				p.sendMessage(Main.pluginPrefix+U.color("&5Your currently have &e"+cm.getPlayerMoney(p.getName())+" points&5!"));
			} else { sender.sendMessage(U.color("&cOnly players can execute this command!")); }
			
			
			
		} else if (args.length == 1) {

			
				if (cm.playerExists(args[0])) {
					sender.sendMessage(Main.pluginPrefix+U.color("&e"+args[0]+" &5currently has &e"+cm.getPlayerMoney(args[0])+" points&5!"));
					
				} else if (args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("set")){
					sender.sendMessage(Main.pluginPrefix+U.color("&5Please use the correct syntax: &e/points <add|remove|clear|set> <player>")); return true;
					
				} else { invalidPlayer(args[0], sender); }
			
				
				
		} else if (args.length == 2) {
			
			if (args[0].equalsIgnoreCase("clear")) {
				if (!sender.hasPermission("points.clear")) { sender.sendMessage(U.color("&cYou have no permission!")); return true;}
				if (cm.playerExists(args[1])) {
						cm.resetPoints(args[1]);
						sender.sendMessage(Main.pluginPrefix+U.color("&5Succefully cleared &e"+args[1]+" &5points!"));
						OfflinePlayer offp = Main.getPlugin().getServer().getOfflinePlayer(args[1]);
						if (offp.isOnline()) { Player p = (Player) offp; p.sendMessage(Main.pluginPrefix+U.color("&5Your points where &ecleared&5!"));}
				}
			} else if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("set")){
				sender.sendMessage(Main.pluginPrefix+U.color("&5Please use the correct syntax: &e/points <add|remove|set> <player> <quantity>")); return true;
				
			} else { invalidPlayer(args[1], sender); }
			
			
			
		} else if (args.length == 3) {
			
			if (args[0].equalsIgnoreCase("add")) {
				if (!sender.hasPermission("points.add")) { sender.sendMessage(U.color("&cYou have no permission!")); return true;}
				if (!cm.playerExists(args[1])) {invalidPlayer(args[1], sender); return true;}
				if (!U.isDouble(args[2])) { sender.sendMessage(Main.pluginPrefix+U.color("&5Please insert a valid numeric quantity!")); return true; }
				
				cm.addPlayerMoney(args[1], Double.parseDouble(args[2]));
				sender.sendMessage(Main.pluginPrefix+U.color("&5You added &e"+args[2]+" points &5to &e"+args[1]+"&5!"));
				sender.sendMessage(U.color("&e"+args[1]+" &5now has &e"+cm.getPlayerMoney(args[1])+" points&5!"));
				
				OfflinePlayer offp = Main.getPlugin().getServer().getOfflinePlayer(args[1]);
				if (offp.isOnline()) { Player p = (Player) offp; p.sendMessage(Main.pluginPrefix+U.color("&5You just received &e"+args[2]+" points&5!"));}
			}
			
			if (args[0].equalsIgnoreCase("remove")) {
				if (!sender.hasPermission("points.remove")) { sender.sendMessage(U.color("&cYou have no permission!")); return true;}
				if (!cm.playerExists(args[1])) {invalidPlayer(args[1], sender); return true;}
				if (!U.isDouble(args[2])) { sender.sendMessage(Main.pluginPrefix+U.color("&5Please insert a valid numeric quantity!")); return true; }
				
				cm.removePlayerMoney(args[1], Double.parseDouble(args[2]));
				sender.sendMessage(Main.pluginPrefix+U.color("&5You removed &e"+args[2]+" points &5from &e"+args[1]+"&5!"));
				sender.sendMessage(U.color("&e"+args[1]+" &5now has &e"+cm.getPlayerMoney(args[1])+" points&5!"));
				
			}
			
			if (args[0].equalsIgnoreCase("set")) {
				if (!sender.hasPermission("points.set")) { sender.sendMessage(U.color("&cYou have no permission!")); return true;}
				if (!cm.playerExists(args[1])) {invalidPlayer(args[1], sender); return true;}
				if (!U.isDouble(args[2])) { sender.sendMessage(Main.pluginPrefix+U.color("&5Please insert a valid numeric quantity!")); return true; }
				if (Double.parseDouble(args[2])<0) { sender.sendMessage(Main.pluginPrefix+U.color("&5Please insert number above 0!")); return true; }
				
				cm.setPlayerMoney(args[1], Double.parseDouble(args[2]));
				sender.sendMessage(Main.pluginPrefix+U.color("&5You setted &e"+args[2]+" points &5to &e"+args[1]+"&5!"));
				sender.sendMessage(U.color("&e"+args[1]+" &5now has &e"+cm.getPlayerMoney(args[1])+" points&5!"));
				
				OfflinePlayer offp = Main.getPlugin().getServer().getOfflinePlayer(args[1]);
				if (offp.isOnline()) { Player p = (Player) offp; p.sendMessage(Main.pluginPrefix+U.color("&5Your points were setted to &e"+cm.getPlayerMoney(args[1])+"&5!"));}
			}
		}
		
		return true;
	}
	
	public void invalidPlayer(String nick, CommandSender sender) {
		sender.sendMessage(Main.pluginPrefix+U.color("&e"+nick+"&c is not a valid player!"));
	}
}
