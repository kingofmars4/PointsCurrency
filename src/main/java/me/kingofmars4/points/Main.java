package me.kingofmars4.points;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.plugin.java.JavaPlugin;

import me.kingofmars4.points.commands.PointsCmds;
import me.kingofmars4.points.listeners.OnJoin;
import me.kingofmars4.points.utils.U;

public class Main extends JavaPlugin {
	
	public static Main getPlugin() { return (Main)JavaPlugin.getPlugin(Main.class); }
	public static String pluginPrefix;
	
	@Override
	public void onEnable() {
		loadConfig();
		loadDatabase();
		loadCommands();
		loadListeners();
	}
	
	public void loadCommands() {
		getCommand("points").setExecutor(new PointsCmds());
	}

	
	public void loadListeners() {
		getServer().getPluginManager().registerEvents(new OnJoin(), this);
	}
	
	
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		pluginPrefix = U.color(getConfig().getString("PluginPrefix"));
		getLogger().info("Configuration file succefully loaded.");
	}
	
	
	
	
	
	
	public String host, database, username, password, port;
    private Connection connection;
    
	public Connection getConnection() {
    	return connection;
    }
    
    public void setConnection(Connection connection) {
    	this.connection = connection;
    }
    
	public void loadDatabase() {
		host = getConfig().getString("MySQL.host");
        port = getConfig().getString("MySQL.port");
        database = getConfig().getString("MySQL.database");
        username = getConfig().getString("MySQL.username");
        if (getConfig().getBoolean("MySQL.usingPassword")) {
        	password = getConfig().getString("MySQL.password");
        } else { password = null; }
        

		try {    
			if (getConnection() != null && !getConnection().isClosed()) {
			    return;
			}
			
			Class.forName("com.mysql.jdbc.Driver");
			
			setConnection(DriverManager.getConnection("jdbc:mysql://" + host+ ":" + port + "/" + database,username, password));
			
			Statement statement = getConnection().createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS points  (" + 
					"    identifier VARCHAR(255) NOT NULL," + 
					"    points DOUBLE NOT NULL," + 
					"    PRIMARY KEY (identifier)" + 
					");");
			
			
			
           getLogger().info("Suceffully connected to the MySQL database!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
	}
}
