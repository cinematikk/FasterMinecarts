package me.cinematikk.fm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Minecart;
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
			System.out.println("[FasterMinecarts] has been enabled.");
		}

		public void onDisable() {
			System.out.println("[FasterMinecarts] has been disabled.");
		}

		
		public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
			
			if(cmd.getName().equalsIgnoreCase("cartspeed")) {
				
				if(args.length == 1){
					
					try{
						
						if(sender.hasPermission("FasterMinecarts.cartspeed")){
							
							this.reloadConfig();
							this.getConfig().set("SpeedMultiplier", args[0]);
							this.saveConfig();
							sender.sendMessage(ChatColor.AQUA + "[FasterMinecarts] Successfully changed minecartspeed multiplier to " + args[0]);
							
						}
						
					}
					catch(Exception ex){
						
						sender.sendMessage(ChatColor.AQUA + "[FasterMinecarts] You need to put in a number!");
						
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

		private double getSpeedMultiplier() {
			
			double multiplier = 0;
			
			// Import speedmultiplier from config
			
			return multiplier;
		}
		
	
}
