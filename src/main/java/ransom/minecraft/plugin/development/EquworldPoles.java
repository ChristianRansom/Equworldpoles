package ransom.minecraft.plugin.development;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;

public class EquworldPoles extends JavaPlugin implements Listener {
	
	public final String prefix = "EquworldPoles";
	
	@Override
	public void onEnable() {

		getLogger().info(prefix + "is now enabled!");
		getServer().getPluginManager().registerEvents(this, this);

		// Register defaults
		ArrayList<Object> defaults = new ArrayList<Object>();
	}
	
	
	@Override
	public void onDisable() {
		getLogger().info(prefix + "is now disabled.");

		// Save the list
		try {
			// Serialize and save rewards
			/*ArrayList<ItemStack> rewards = (ArrayList<ItemStack>) list.get(26);
			ArrayList<Map<String, Object>> serializedRewards = new ArrayList<Map<String, Object>>();
			for (ItemStack is : rewards) {
				serializedRewards.add(is.serialize());
			}
			list.set(26, serializedRewards);
			getConfig().set("com.wenikalla.anvilgame", ObjectString.objectToString(list));*/
		} catch (Exception e) {
			getLogger().info(prefix + "CRITICAL ERROR: CORRUPTED DATA. PLEASE DELETE ANVILGAME DATA FILES.");
			e.printStackTrace();
		}
		saveConfig();
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

		if (cmd.getName().equalsIgnoreCase("thecommand")) {
			if (args[0].equalsIgnoreCase("TheFenceNameee")) {
				
			}
		}
		return false;
	}

}
