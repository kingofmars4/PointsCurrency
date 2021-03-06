package me.kingofmars4.points.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class U {
	
	public static String color(String s) {
		
		return s.replaceAll("&", "?");
	}
	
	public static boolean isInt(String s) {
	    try {
	        Integer.parseInt(s);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean isDouble(String s) {
	    try {
	        Double.parseDouble(s);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public static String serializeLoc(Location l){
        return l.getWorld().getName() + "," + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ();
    }

    public static Location deserializeLoc(String s){
    	String[] st = s.split(",");
        return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]));
    }
    
    public static String serializeList(List<Integer> l){
    	Bukkit.broadcastMessage("SERIALIZED: "+l.toString());
    	return l.toString();
    }

    public static List<Material> deserializeList(String s){
    	String[] st = s.split(",");
    	List<Material> materials = new ArrayList<Material>();
    	
    	for (int i = 0; i<st.length; i++) {
    		Bukkit.broadcastMessage("I = "+i+ "    ST="+st[i]+ "    MATERIAL="+Material.getMaterial(st[i]));
    		materials.add(Material.getMaterial(st[i]));
    	}
    	Bukkit.broadcastMessage("DESERIALIZED: "+materials);
        return materials;
    }
    
    public static ItemStack createItemStack(Material m, String nome) {
		ItemStack i = new ItemStack(m);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(color(nome));
		i.setItemMeta(im);
		return i;
	}
    
    public static void createDisplay(ItemStack is, Inventory inv, int Slot, String name, String lore) {
		ItemStack item = is;
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		ArrayList<String> Lore = new ArrayList<String>();
		Lore.add(lore);
		meta.setLore(Lore);
		item.setItemMeta(meta);
		 
		inv.setItem(Slot, item); 
	}

	public static ItemStack createSkull(String owner, String name, String lore) {
		ItemStack skull;
        skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta sm = (SkullMeta) skull.getItemMeta();
        Player player = Bukkit.getPlayer(owner);
        sm.setOwningPlayer(player);
        skull.setItemMeta(sm);
        
        return skull;
	}
	
}
