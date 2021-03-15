package me.kingofmars4.points.commands;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.kingofmars4.points.Main;
import me.kingofmars4.points.utils.PointsAPI;
import me.kingofmars4.points.utils.U;

public class Pay implements CommandExecutor, Listener {
	
	public static HashMap<Player, Integer> counter = new HashMap<Player, Integer>(); 
	public static Inventory gui;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) { sender.sendMessage(U.color("&cYou must be a player in order to use this command!")); return true;}
		Player p = (Player) sender;
		if (!p.hasPermission("points.pay")) { sender.sendMessage(U.color("&cYou have no permission!")); return true;}
		
		
		if (Bukkit.getServer().getOnlinePlayers().size() < 27) {
			gui = Bukkit.createInventory(null, 27, U.color("&5Select a player to pay!"));
		} else {
			gui = Bukkit.createInventory(null, 54, U.color("&5Select a player to pay!"));
		}
		
		ArrayList<Player> players = new ArrayList<Player>(Bukkit.getOnlinePlayers());
		
		for (int i = 0; i<players.size(); i++) {
			Player pl = players.get(i);
			
			
			ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
	        SkullMeta sm = (SkullMeta) skull.getItemMeta();
	        sm.setOwningPlayer(pl);
	        sm.setDisplayName(U.color("&e"+pl.getName()));
	        
	        ArrayList<String> lore = new ArrayList<String>();
			lore.add(U.color("&5Click to select this player"));
			sm.setLore(lore);
			
	        skull.setItemMeta(sm);
			 
			gui.addItem(skull);
			
			if (i == 53) {
				ItemStack item = new ItemStack(Material.ARROW);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(U.color("&5Go to the next page"));
				item.setItemMeta(meta);
				gui.setItem(53, item);
				
				counter.replace(p, 53);
				break;
			}
		}

		
		p.openInventory(gui);
		
		
		return true;
	}
	
	private void continuePayList(Player opener, int quantity) {
		ArrayList<Player> players = new ArrayList<Player>(Bukkit.getOnlinePlayers());
		for (int i = quantity; i<players.size(); i++) {
			
			Inventory gui = Bukkit.createInventory(null, 54, U.color("&5Select a player to pay!"));
			Player pl = players.get(i);
			
			ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
	        SkullMeta sm = (SkullMeta) skull.getItemMeta();
	        sm.setOwningPlayer(pl);
	        sm.setDisplayName(U.color("&e"+pl.getName()));
	        
	        ArrayList<String> lore = new ArrayList<String>();
			lore.add(U.color("&5Click to select this player"));
			sm.setLore(lore);
			
	        skull.setItemMeta(sm);
			 
			gui.addItem(skull);
			
			counter.replace(opener, counter.get(opener)+1);
			
			if (i == quantity+53) {
				counter.replace(opener, quantity+53);
				break;
			}
			
			
		}
	}

	HashMap<Player, Player> playerTarget = new HashMap<Player, Player>();
	
	@EventHandler
	public void onClickEvent (InventoryClickEvent e) {
		if (e.getInventory().equals(gui)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) { return; }
			
			Player p = (Player) e.getWhoClicked();
			if (e.getCurrentItem().getType().equals(Material.ARROW)) {
				continuePayList(p, counter.get(p));
			}
			if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
				for (Player players : Bukkit.getOnlinePlayers()) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().contains(players.getName())) {
						if (players == p) { p.closeInventory(); p.sendMessage(U.color("&cYou can not pay yourself!")); return;}
						playerTarget.put(p, players);
						p.closeInventory();
						
						p.sendMessage(Main.pluginPrefix+U.color("&5Please insert the amount you wish to pay to &e"+players.getName()+"&5!"));
						p.sendMessage(U.color("&5Or type &cCANCEL &5to cancel the transfer!"));
						return;
					} else {
						p.sendMessage(Main.pluginPrefix+U.color("&cThis player is no longer online!")); p.closeInventory(); return;
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onChat (AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (playerTarget.containsKey(p)) {
			 if (e.getMessage().equalsIgnoreCase("cancel")) {
				playerTarget.remove(p);
				p.sendMessage(Main.pluginPrefix+U.color("&cCanceled the payment operation!"));
			} else if (U.isDouble(e.getMessage())) {
				PointsAPI.getCurrencyManager().addPlayerMoney(playerTarget.get(p).getName(), Double.parseDouble(e.getMessage()));
				p.sendMessage(Main.pluginPrefix+U.color("&5Succefully sent &e%n points &5to &e%p".replace("%n", e.getMessage()).replace("%p", playerTarget.get(p).getName())));
				playerTarget.get(p).sendMessage(Main.pluginPrefix+U.color("&5You received &e%n points &5from &e%p".replace("%n", e.getMessage()).replace("%p", p.getName())));
				playerTarget.remove(p);
			} else {
				p.sendMessage(U.color("&cYou must insert a valid number or type CANCEL!"));
			}
			 e.setCancelled(true);
		}
	}
}
