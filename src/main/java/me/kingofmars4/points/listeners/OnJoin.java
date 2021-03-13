package me.kingofmars4.points.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import me.kingofmars4.points.utils.CurrencyManager;

public class OnJoin implements Listener {
	
	private CurrencyManager cm = new CurrencyManager();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (!cm.playerExists(e.getPlayer().getName())) {
			   CurrencyManager cm = new CurrencyManager();
			   
			   cm.resetPoints(e.getPlayer().getName());
		}
	}

}
