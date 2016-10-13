package ransom.minecraft.plugin.development;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

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
		
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		
	}
	
	
	@Override
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player)sender;
		
		//MakeJump <JumpName>
		if (cmd.getName().equalsIgnoreCase("MakeJump")) {
			//fenceBars.put(args[0], new FenceBar());
			sender.sendMessage("Jump Created");
			this.jumpsStore.add(args[0]);
			saveData();
			return true;
		}
		//DeleteJump <JumpName>
		else if(cmd.getName().equalsIgnoreCase("DeleteJump")){
			//fenceBars.remove(args[0]);
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
			this.jumpsStore.get(args[0]).addFenceTop(player.getLocation());
			return true;
		}
		//RemoveBlock <JumpName>
		else if(cmd.getName().equalsIgnoreCase("RemoveBlock")){
			if(this.jumpsStore.get(args[0]).removeFenceTop(player.getLocation())){
				sender.sendMessage("Block removed from jump: " + args[0]);
			}
			else{
				sender.sendMessage("That block is not part of jump: " + args[0]);
			}
			return true;
		}
		else if(cmd.getName().equalsIgnoreCase("ListJumps")){
			sender.sendMessage(this.jumpsStore.getJumpNames().toString());
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
