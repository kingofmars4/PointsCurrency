package me.kingofmars4.points.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.kingofmars4.points.Main;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

public class CurrencyManager implements Economy {
	
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

	public boolean isEnabled() {
		return true;
	}

	public String getName() {
		return "PointsSystem";
	}

	public boolean hasBankSupport() {
		
		return false;
	}

	public int fractionalDigits() {
		
		return 2;
	}

	public String format(double amount) {
		return amount+" points";
	}

	public String currencyNamePlural() {
		return "points";
	}

	public String currencyNameSingular() {
		
		return "point";
	}

	public boolean hasAccount(String playerName) {
		
		try {
    		Statement statement = Main.getPlugin().getConnection().createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM points;");
	        
			if (result.next()) {
				if (result.getString("identifier").equalsIgnoreCase(playerName)) {
					return true;
				}
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean hasAccount(OfflinePlayer player) {
		
		try {
    		Statement statement = Main.getPlugin().getConnection().createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM points;");
	        
			if (result.next()) {
				if (result.getString("identifier").equalsIgnoreCase(player.getName())) {
					return true;
				}
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean hasAccount(String playerName, String worldName) {
		
		return false;
	}

	public boolean hasAccount(OfflinePlayer player, String worldName) {
		
		return false;
	}

	public double getBalance(String playerName) {
		
		try {
    		Statement statement = Main.getPlugin().getConnection().createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM points;");
	        
			if (result.next()) {
				if (result.getString("identifier").equalsIgnoreCase(playerName)) {
					return result.getDouble("points");
				}
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public double getBalance(OfflinePlayer player) {
		
		try {
    		Statement statement = Main.getPlugin().getConnection().createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM points;");
	        
			if (result.next()) {
				if (result.getString("identifier").equalsIgnoreCase(player.getName())) {
					return result.getDouble("points");
				}
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public double getBalance(String playerName, String world) {
		
		return 0;
	}

	public double getBalance(OfflinePlayer player, String world) {
		
		return 0;
	}

	public boolean has(String playerName, double amount) {
		
		try {
    		Statement statement = Main.getPlugin().getConnection().createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM points;");
	        
			if (result.next()) {
				if (result.getString("identifier").equalsIgnoreCase(playerName)) {
					if (result.getDouble("points") > amount) {
						return true;
					} else { return false; }
				}
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean has(OfflinePlayer player, double amount) {
		
		try {
    		Statement statement = Main.getPlugin().getConnection().createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM points;");
	        
			if (result.next()) {
				if (result.getString("identifier").equalsIgnoreCase(player.getName())) {
					if (result.getDouble("points") > amount) {
						return true;
					} else { return false; }
				}
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean has(String playerName, String worldName, double amount) {
		
		return false;
	}

	public boolean has(OfflinePlayer player, String worldName, double amount) {
		
		return false;
	}

	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		
		try {
			
			Statement statement = Main.getPlugin().getConnection().createStatement();
			if (getPlayerMoney(playerName)-amount < 0) {
				statement.executeUpdate("REPLACE INTO points (identifier, points) VALUES ('"+playerName+"', 0)"); 
				
				EconomyResponse sucess= new EconomyResponse(amount, getBalance(playerName), ResponseType.SUCCESS, null);
				return sucess;
			}
			statement.executeUpdate("REPLACE INTO points (identifier, points) VALUES ('"+playerName+"', "+(getPlayerMoney(playerName)-amount)+")");
			EconomyResponse sucess= new EconomyResponse(amount, getBalance(playerName), ResponseType.SUCCESS, null);
			return sucess;
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return null;
		
	}

	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		
		try {
			
			Statement statement = Main.getPlugin().getConnection().createStatement();
			if (getPlayerMoney(player.getName())-amount < 0) {
				statement.executeUpdate("REPLACE INTO points (identifier, points) VALUES ('"+player.getName()+"', 0)"); 
				
				EconomyResponse sucess= new EconomyResponse(amount, getBalance(player.getName()), ResponseType.SUCCESS, null);
				return sucess;
			}
			statement.executeUpdate("REPLACE INTO points (identifier, points) VALUES ('"+player.getName()+"', "+(getPlayerMoney(player.getName())-amount)+")");
			EconomyResponse sucess= new EconomyResponse(amount, getBalance(player.getName()), ResponseType.SUCCESS, null);
			return sucess;
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return null;
	}

	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
		
		return null;
	}

	public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
		
		return null;
	}

	public EconomyResponse depositPlayer(String playerName, double amount) {
		
		try {
			Statement statement = Main.getPlugin().getConnection().createStatement();
			statement.executeUpdate("REPLACE INTO points (identifier, points) VALUES ('"+playerName+"', "+(getPlayerMoney(playerName)+amount)+")");
			EconomyResponse sucess= new EconomyResponse(amount, getBalance(playerName), ResponseType.SUCCESS, null);
			return sucess;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null; 
	}

	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		
		try {
			Statement statement = Main.getPlugin().getConnection().createStatement();
			statement.executeUpdate("REPLACE INTO points (identifier, points) VALUES ('"+player.getName()+"', "+(getPlayerMoney(player.getName())+amount)+")");
			EconomyResponse sucess= new EconomyResponse(amount, getBalance(player.getName()), ResponseType.SUCCESS, null);
			return sucess;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null; 
	}

	public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
		
		return null;
	}

	public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
		
		return null;
	}

	public EconomyResponse createBank(String name, String player) {
		
		return null;
	}

	public EconomyResponse createBank(String name, OfflinePlayer player) {
		
		return null;
	}

	public EconomyResponse deleteBank(String name) {
		
		return null;
	}

	public EconomyResponse bankBalance(String name) {
		
		return null;
	}

	public EconomyResponse bankHas(String name, double amount) {
		
		return null;
	}

	public EconomyResponse bankWithdraw(String name, double amount) {
		
		return null;
	}

	public EconomyResponse bankDeposit(String name, double amount) {
		
		return null;
	}

	public EconomyResponse isBankOwner(String name, String playerName) {
		
		return null;
	}

	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
		
		return null;
	}

	public EconomyResponse isBankMember(String name, String playerName) {
		
		return null;
	}

	public EconomyResponse isBankMember(String name, OfflinePlayer player) {
		
		return null;
	}

	public List<String> getBanks() {
		
		return null;
	}

	public boolean createPlayerAccount(String playerName) {
		
		try {
			Statement statement = Main.getPlugin().getConnection().createStatement();
			statement.executeUpdate("REPLACE INTO points (identifier, points) VALUES ('"+playerName+"', 0)");
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		return false;
	}

	public boolean createPlayerAccount(OfflinePlayer player) {
		
		try {
			Statement statement = Main.getPlugin().getConnection().createStatement();
			statement.executeUpdate("REPLACE INTO points (identifier, points) VALUES ('"+player.getName()+"', 0)");
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		return false;
	}

	public boolean createPlayerAccount(String playerName, String worldName) {
		
		return false;
	}

	public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
		
		return false;
	}
	
	
}
