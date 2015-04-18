package me.cinematikk.fm;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

public class FasterMinecarts extends JavaPlugin implements Listener {
		
		public void onEnable() {
			this.getServer().getPluginManager().registerEvents(this, this);
			this.saveConfig();
			processConfigFile();
			System.out.println("[FasterMinecarts] has been enabled.");
		}

		public void onDisable() {
			this.saveConfig();
			System.out.println("[FasterMinecarts] has been disabled.");
		}

		
		public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
			
			if(cmd.getName().equalsIgnoreCase("cartspeed")) {
				
				if(args.length == 1){
					
					try{
						
						if(sender.hasPermission("FasterMinecarts.cartspeed")){
							setMultiplier(args[0]);
							sender.sendMessage(ChatColor.AQUA + "[FasterMinecarts] Successfully changed minecartspeed multiplier to " + args[0]);
							
						}
						
					}
					catch(Exception ex){
						
						sender.sendMessage(ChatColor.AQUA + "[FasterMinecarts] You need to put in a number!");
						
					}
					
				}
				
			}
			
			if(cmd.getName().equalsIgnoreCase("applyall")){
				
				if(args.length == 1){
					
					if(args[0] == "cartspeed"){
						
						if(sender.hasPermission("FasterMinecarts.applyall")){
							sender.sendMessage(ChatColor.AQUA + "[FasterMinecarts] This may take awhile!");
							Player p = (Player) sender;
							changeAllMinecarts(p);
							
						}
						
					}
					
				}
				
			}
			
			return false;
		}
	
		@EventHandler(priority = EventPriority.NORMAL)
		public void onVehicleCreate(VehicleCreateEvent e) {
			Vehicle vehicle=e.getVehicle();
			if (vehicle instanceof Minecart){
				Minecart minecart = (Minecart) vehicle;
				minecart.setMaxSpeed(0.4 * getSpeedMultiplier());
			}
			
		}

		public double getSpeedMultiplier() {
			double multiplier = this.getConfig().getDouble("SpeedMultiplier");
			return multiplier;
		}
		
		
		private void processConfigFile() {
			 
			  final Map<String, Object> defParams = new HashMap<String, Object>();
			  this.getConfig().options().copyDefaults(true);
			  String x = "1";
			  double multi = Double.parseDouble(x);
			  
			  // This is the default configuration
			  defParams.put("SpeedMultiplier", multi);
			 
			  // If config does not include a default parameter, add it
			  for (final Entry<String, Object> e : defParams.entrySet())
			    if (!this.getConfig().contains(e.getKey())){
			    	this.getConfig().set(e.getKey(), e.getValue());
			    }
			  // Save default values to config.yml in datadirectory
			  this.saveConfig();
			}
			
		public void setMultiplier(String multiplier) {
			
			double multi = Double.parseDouble(multiplier);
			this.reloadConfig();
			this.getConfig().set("SpeedMultiplier", multi);
			this.saveConfig();
			
		}
		
		public void changeAllMinecarts(Player player) {
			
			double multiplier = getSpeedMultiplier();
			for(Entity v: player.getWorld().getEntities()){   // You need to be a player for this!
				
				if(v instanceof Vehicle){
					
					if(v instanceof Minecart){
						
						((Minecart) v).setMaxSpeed(multiplier);
						
					}
					
				}
				
			}
			
		}
		
	
}
