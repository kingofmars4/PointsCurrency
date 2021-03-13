package me.kingofmars4.points.utils;

import org.bukkit.plugin.java.JavaPlugin;

import me.kingofmars4.points.Main;

public class PointsAPI {

	public static Main getPlugin() { return (Main)JavaPlugin.getPlugin(Main.class); }
	public static CurrencyManager getCurrencyManager() { CurrencyManager cm = new CurrencyManager(); return cm; }
	
}
