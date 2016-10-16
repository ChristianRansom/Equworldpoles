package ransom.minecraft.plugin.development;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

public class FenceBar {

	private ArrayList<FenceTop> fenceTops = new ArrayList<FenceTop>();
	private Location resetSign = new Location(null, 0, 0, 0);
	private boolean set;
	private Plugin plugin;//This is here to be able to communicate with the consol for debugging
	private World world;
	
	public FenceBar(Plugin thePlugin, World theWorld){
		this(true, thePlugin, theWorld); 
	}
	
	//Default constructor sort of - Other constructors lead to this one. 
	public FenceBar(boolean setter, Plugin thePlugin, World theWorld){
		this.set = setter;
		this.plugin = thePlugin;
		this.world = theWorld;

	}
	//Called on Player Move
	public boolean isHit(Location playerLoc, EquworldPoles equworldPoles){
		for(FenceTop fence : fenceTops){
			if(fence.isHit(playerLoc, equworldPoles)){
				fall();
				return true;
				
			}
		}
		return false;
	}
	
	
	/*Method abstracted from isHit() for better code blocking 
	and clear logic flow*/
	private void fall(){
		for(FenceTop block : fenceTops){
			block.fall(world);
		}
		this.set = false;
	}
	
	@Override
	public String toString(){
		
		//Get each Line.class Split it. First is name, each consecutive 3 are a location. 
		return "" + this.fenceTops;
		
	}

	public boolean addFenceTop(Location location) {
		FenceTop fence = new FenceTop(location.getBlockX(), location.getBlockY(), location.getBlockZ(), plugin);
		if(!this.contains(fence)){
			fenceTops.add(new FenceTop(location, plugin));
			return true;
		}
		return false;		
	}
	
	public void addFenceTop(int x, int y, int z) {
		fenceTops.add(new FenceTop(x, y, z, plugin));
	}
	
	public void addFenceTop(int x, int y, int z, int fallLoc) {
		fenceTops.add(new FenceTop(x, y, z, fallLoc, plugin));
	}
	
	public ArrayList<FenceTop> getFenceTops(){
		return fenceTops;
	}

	public boolean removeFenceTop(int x, int y, int z){
		FenceTop fence = new FenceTop(x,y,z, plugin);
		if(this.contains(fence)){
			this.fenceTops.remove(fence);
			return true;
		}
		return false;
	}
	
	public boolean contains(FenceTop fence){
		for(int i = 0; i < fenceTops.size(); i++){
			if(fence.equals(fenceTops.get(i))){
				return true;
			}
		}

		return false;
	}
	
	public boolean clickIsOnFallenFence(Block clickedBlock) {
		for(FenceTop fence : fenceTops){
			if(fence.isClicked(clickedBlock)){
				this.reset();
				return true;
			}
		}
		
		
		return false;
	}

	private void reset() {
		for(FenceTop fence : fenceTops){
			fence.reset(this.world);
		}
		this.set = true;
	}

	public boolean removeFenceTop(Location location) {
		return this.removeFenceTop(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}

	public boolean isSet() {
		return set;
	}
	
	//Create command to set a jump through command. 
	public void setSet(boolean set) {
		this.set = set;
	}

	public World getWorld() {
		return this.world;
	}
}
