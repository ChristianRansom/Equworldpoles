package ransom.minecraft.plugin.development;

import org.bukkit.Location;
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
import java.util.ArrayList;
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

		/*fenceBars.put("TestJump", new FenceBar());
		fenceBars.get("TestJump").addFenceTop(new Location(null, 0.5, 0.5, 0.5));
		fenceBars.get("TestJump").addFenceTop(new Location(null, 1, 1, 1));
		fenceBars.get("TestJump").addFenceTop(new Location(null, 2, 2, 2));

		
		
		getLogger().info("Print tests: \n A location: " + new Location(null, 0.5, 0.5, 0.5)
				+ "\nA fenceBar: " + fenceBars.get("TestJump")
				+ "\nA fenceTop: " + fenceBars.get("TestJump").getFenceTops().get(0));
*/
		
		this.jumpsStore = new JumpsStore(new File(pluginFolder + File.separator + "Jumps.txt"));
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
				 
			 }
		 }
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		
	}
	
	
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
		if(args.length > 0){
			if (cmd.getName().equalsIgnoreCase("MakeJump")) {
				//fenceBars.put(args[0], new FenceBar());
				sender.sendMessage("Jump Created");
				this.jumpsStore.add(args[0]);
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
			}
			//AddBlock <JumpName>
			else if(cmd.getName().equalsIgnoreCase("AddBlock")){
				if((sender instanceof Player)){
					Player player = (Player)sender;
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
				else {
					sender.sendMessage("This command is only available in game.");
				}
				
				return true;
			}
			//TODO fix remove block and other freaking errors adding/removinig blocks from non-existant jumps
			//RemoveBlock <JumpName>
			else if(cmd.getName().equalsIgnoreCase("RemoveBlock")){
				if(sender instanceof Player){
					Player player = (Player)sender;
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
				}
				else {
					sender.sendMessage("This command is only available in game.");
				}
				return true;
			}
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
