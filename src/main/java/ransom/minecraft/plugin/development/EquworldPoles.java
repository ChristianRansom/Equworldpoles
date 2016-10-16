package ransom.minecraft.plugin.development;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.Set;

public class EquworldPoles extends JavaPlugin implements Listener {
	
	public final String prefix = "EquworldPoles";
	protected JumpsStore jumpsStore;
	
	@Override
	public void onEnable() {
		
		String pluginFolder = this.getDataFolder().getAbsolutePath();
		(new File(pluginFolder)).mkdirs();
		getLogger().info(prefix + "is now enabled!");
		getServer().getPluginManager().registerEvents(this, this);
		
		this.jumpsStore = new JumpsStore(new File(pluginFolder + File.separator + "Jumps.txt"), this);
		this.jumpsStore.load();
		
	}
	
	
	@Override
	public void onDisable() {
		getLogger().info(prefix + "is now disabled.");
		this.jumpsStore.save();
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		//Check if a horse hit the top of a jump
		 if ((player.isInsideVehicle()) || ((player.getVehicle() instanceof Horse))){
			 //player.sendMessage("You're on a horse");
			 if(jumpsStore.jumpHit(player.getLocation(), this)){
				 saveData();
				 //commenting to commit
			 }
		 }
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{	Player player = event.getPlayer();
		//player.sendMessage("You've Interacted");

		Block clickedBlock = event.getClickedBlock();
		if(jumpsStore.clickOn(clickedBlock)){
			player.sendMessage("Jump has been reset");
			//Cancels event so blocks can't be placed accidentally when resetting. 
			event.setCancelled(true);
		}
	}
	
	//TODO dont allow them to create a fence with the same name? 
	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		
		//Commands without any arguments
		if(cmd.getName().equalsIgnoreCase("ListJumps")){
			//sender.sendMessage(this.jumpsStore.getJumpNames().toString());
			Set<String> alldata = this.jumpsStore.getJumpNames();
			for(String value : alldata){
				sender.sendMessage(value);
			}
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("EquworldPoles")){
			if(args.length > 0 && args[0].equalsIgnoreCase("help")){
				sender.sendMessage("Commands: ");
				sender.sendMessage("/MakeJump <jump name> ");
				sender.sendMessage("/DeleteJump <jump name> ");
				sender.sendMessage("/AddBlock <jump name> ");
				sender.sendMessage("/RemoveBlock <jump name> ");
				sender.sendMessage("/ListJumps ");
			}
			else{
				sender.sendMessage("/EquworldPoles help");
			}
			return true;
		}
		
		//MakeJump <JumpName>
		if(!(sender instanceof Player)){
			sender.sendMessage("This command is only available in game.");
			return true;
		}
		else if(args.length > 0){
			Player player = (Player)sender;
			if (cmd.getName().equalsIgnoreCase("MakeJump")) {
				//fenceBars.put(args[0], new FenceBar());
				sender.sendMessage("Jump Created");
				this.jumpsStore.add(args[0], player.getWorld());
				saveData();
				return true;
			}
			//DeleteJump <JumpName>
			else if(cmd.getName().equalsIgnoreCase("DeleteJump")){
				if(this.jumpsStore.remove(args[0])){
					sender.sendMessage("Jump Deleted");
					saveData();
				}
				else{
					sender.sendMessage("There is no Jump by that name.");
				}
				return true;
			}			//AddBlock <JumpName>
			else if(cmd.getName().equalsIgnoreCase("AddBlock")){
				if(!this.jumpsStore.contains(args[0])){
					sender.sendMessage("There is no jump by that name");
				}
				if(this.jumpsStore.get(args[0]).addFenceTop(player.getLocation())){
					sender.sendMessage("Block added to jump: " + args[0]);
					saveData();
				}
				else {
					sender.sendMessage("That block is already part of jump: " + args[0]);
				}
			}
			//TODO fix remove block and other freaking errors adding/removinig blocks from non-existant jumps
			//RemoveBlock <JumpName>
			else if(cmd.getName().equalsIgnoreCase("RemoveBlock")){
				if(!this.jumpsStore.contains(args[0])){
					sender.sendMessage("There is no jump by that name");
				}
				if(this.jumpsStore.get(args[0]).removeFenceTop(player.getLocation())){
					sender.sendMessage("Block removed from jump: " + args[0]);
					saveData();
				}
				else{
					sender.sendMessage("That block is not part of jump: " + args[0]);
				}
				return true;
			}
		}
		else {
			sender.sendMessage("Missing arguments.");
			return true;
		}
		return false; 
	}
	
	
	public void saveData(){
		try{
			this.jumpsStore.save();
		}
		catch(Exception e){	
			e.printStackTrace();
		}
	}
}
