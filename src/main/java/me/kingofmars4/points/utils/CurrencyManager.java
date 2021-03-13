package me.kingofmars4.points.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.kingofmars4.points.Main;

public class CurrencyManager {
	
	public double getPlayerMoney(String identifier) {
		try {
    		Statement statement = Main.getPlugin().getConnection().createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM points;");
	        
			if (result.next()) {
				if (result.getString("identifier").equalsIgnoreCase(identifier)) {
					return result.getDouble("points");
				}
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setPlayerMoney(String identifier, double quantity) {
		try {
			Statement statement = Main.getPlugin().getConnection().createStatement();
			statement.executeUpdate("REPLACE INTO points (identifier, points) VALUES ('"+identifier+"', "+quantity+")");
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
	}
	
	public void addPlayerMoney(String identifier, double quantity) {
		try {
			Statement statement = Main.getPlugin().getConnection().createStatement();
			statement.executeUpdate("REPLACE INTO points (identifier, points) VALUES ('"+identifier+"', "+(getPlayerMoney(identifier)+quantity)+")");
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
	}
	
	public void removePlayerMoney(String identifier, double quantity) {
		try {
			Statement statement = Main.getPlugin().getConnection().createStatement();
			if (getPlayerMoney(identifier)-quantity < 0) {
				double oldMoney = getPlayerMoney(identifier);
				statement.executeUpdate("REPLACE INTO points (identifier, points) VALUES ('"+identifier+"', 0)"); 
				@SuppressWarnings("deprecation")
				OfflinePlayer offp = Main.getPlugin().getServer().getOfflinePlayer(identifier);
				if (offp.isOnline()) { Player p = (Player) offp; p.sendMessage(Main.pluginPrefix+U.color("&e"+oldMoney+" points &5were taken from you!"));}
				return;
			}
			statement.executeUpdate("REPLACE INTO points (identifier, points) VALUES ('"+identifier+"', "+(getPlayerMoney(identifier)-quantity)+")");
			
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
	}
	
	public boolean playerExists(String identifier) {
		
		try {
    		Statement statement = Main.getPlugin().getConnection().createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM points;");
	        
			if (result.next()) {
				if (result.getString("identifier").equalsIgnoreCase(identifier)) {
					return true;
				}
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void resetPoints(String identifier) {
		try {
			Statement statement = Main.getPlugin().getConnection().createStatement();
			statement.executeUpdate("REPLACE INTO points (identifier, points) VALUES ('"+identifier+"', 0)");
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
	}
	
	
}
