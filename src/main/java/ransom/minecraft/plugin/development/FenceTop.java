package ransom.minecraft.plugin.development;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

import com.avaje.ebeaninternal.server.transaction.TransactionMap.State;

public class FenceTop {
	//mins and maxes are stored so they only have to be calculated once
	//These are the max boundaries within the collision box. 
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	private double minZ;
	private double maxZ;

	private int x;
	private int y;
	private int z;
	private int fallLoc; //Recalculates every time it falls. 
	private String fallenSlab;
	
	private final int[] slabs = {44, 182, 126};
	private final int[] doubleSlabs = {43, 125};
	//44:0-7 bottom, 44:8-15 top
	//182:0 bottom,  44:8 top
	//126:0-5 bottom, 126:8-13 top
	//Double slabs 43:0-15
	//Double slabs 125:0-5
	private Plugin plugin;//This is here to be able to communicate with the console for debugging	

	public FenceTop(int x, int y, int z, Plugin thePlugin) {
		this.x = x;
		this.y = y;
		this.z = z;
		//TODO check if fence top is a slab?  
		//These values are due to the hit box size of the horse.
		this.maxX = x + 1.699999;
		this.minX = x - 0.699; 
		this.maxY = y + 0.00001;
		this.minY = y - .5;//This is arbitrary. They'd have to be inside of the block to be here. 
		this.maxZ = z + 1.699999;
		this.minZ = z - 0.699;
		
		plugin = thePlugin;
	}

	public FenceTop(Location playerLoc, Plugin thePlugin){
		this(playerLoc.getBlockX(), playerLoc.getBlockY(), playerLoc.getBlockZ(), thePlugin);
	}
		
	public FenceTop(int x, int y, int z, int fallLoc, Plugin plugin) {
		this(x, y, z, plugin);
		this.fallLoc = fallLoc;
	}

	public boolean equals(FenceTop other){
		if(other.getX() == this.x && other.getY() == this.y && other.getZ() == this.z){
			return true; 
		}
		return false;
	}
//no
	public boolean isHit(Location playerLoc, EquworldPoles equworldPoles) {
		/*plugin.getLogger().info("playerLocation: " + 
				playerLoc.getX() + " " + playerLoc.getY() + " " + playerLoc.getZ());
		plugin.getLogger().info("MaxX: " + 
				this.maxX + " MinX: " + this.minX + " maxY: " 
				+ this.maxY + " MaxZ: " + this.maxZ + " MinZ: " + this.minZ);
		*/
		if(playerLoc.getX() < this.maxX && playerLoc.getX() > this.minX &&
				playerLoc.getY() < this.maxY && playerLoc.getY() > this.minY &&
				playerLoc.getZ() < this.maxZ && playerLoc.getZ() > this.minZ){
			
			/*plugin.getLogger().info("HIIIIIIITTTTTTTTTTTTTTT");
			plugin.getLogger().info("HIIIIIIITTTTTTTTTTTTTTT");
			plugin.getLogger().info("HIIIIIIITTTTTTTTTTTTTTT");*/
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void fall(World world){
		//This is the actual fence block. Its 1 below where you stand in to create it
		Block fenceBlock = world.getBlockAt(this.x, this.y - 1, this.z);
		BlockState fenceState = fenceBlock.getState();
		Material fenceType = fenceState.getType();
		byte fenceData = fenceBlock.getData();
		//plugin.getLogger().info("Attempting to 'fall()");
		boolean keepFalling = true;
		
		//The first fall location is 2 below the block you stand in to create it 
		fallLoc = this.y - 2;
		while(keepFalling && fallLoc > 1){
			Block aBlock = world.getBlockAt(this.x, fallLoc, this.z);
			Material aBlockType = aBlock.getType();
			//if its not air, stop loop, we've found ground. 
			if(aBlockType != Material.AIR){
				//plugin.getLogger().info("Block material: " + aBlockType);
				keepFalling = false;
				//plugin.getLogger().info("We've Hit the ground");
			}
			else{
				//plugin.getLogger().info("Air Block found");
				fallLoc--;
			}
		}
		//if there is no air blocks beneath the fence 
		if(fallLoc == this.y){
			plugin.getLogger().info("There are no air block underneath the jump.");
			if(fenceBlock.getType().equals(Material.STEP)){
				boolean topSlab = false;
				plugin.getLogger().info("The block is a 'step': " + fenceBlock.getType());
				if(fenceBlock.getData() >= 8){
					plugin.getLogger().info("The is a top slab': " + fenceBlock.getData());
					/*This represents its fallen state. This is what will happen to the block 
					 *
					 * 
					 * 
					 E*/
					this.fallenSlab = "bottomslab";
				}
			}
		}
		Block fallBlock = world.getBlockAt(this.x, (fallLoc + 1), this.z); //Not sure why it needs fallLoc + 1 but it does. 
		//plugin.getLogger().info("This is the fallLoc where I put the block: " + fallLoc);
		fallBlock.setType(fenceBlock.getType());
		fallBlock.setData(fenceData);
		fenceBlock.setType(Material.AIR);

		
	}
	
	public boolean isClicked(Block clickedBlock) {
		/*plugin.getLogger().info("Clicked Block: " + clickedBlock.getX() + " " + clickedBlock.getY() + " " + clickedBlock.getZ());
		plugin.getLogger().info("Fallen Block: " + this.x + " " + this.fallLoc + " " + this.z);
		plugin.getLogger().info("Thats not a fallen fenceBlock");*/
		plugin.getLogger().info(clickedBlock.toString());
		if(clickedBlock.getX() == this.x && clickedBlock.getY() == this.fallLoc  + 1 && clickedBlock.getZ() == this.z){	
			//plugin.getLogger().info("THATS A FALLEN BLOCK OMG!!!");
			return true;
		}
		/*plugin.getLogger().info("Clicked Block: " + clickedBlock.getX() + " " + clickedBlock.getY() + " " + clickedBlock.getZ());
		plugin.getLogger().info("Fallen Block: " + this.x + " " + this.fallLoc + " " + this.z);
		plugin.getLogger().info("Thats not a fallen fenceBlock");*/
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void reset(World world) {
		//TODO what on earth do i do if a block is added while its down?!?
		//plugin.getLogger().info("Reseting block");
		Block fallenBlock = world.getBlockAt(this.x, fallLoc + 1, this.z);
		Material fallType = fallenBlock.getType(); 
		//plugin.getLogger().info("Fallen block type: " + fallType);//This is throwing a null pointer exception. I cri
		Block fenceBlock = world.getBlockAt(this.x, this.y - 1, this.z);
		fenceBlock.setType(fallType);
		fenceBlock.setData(fallenBlock.getData());
		fallenBlock.setType(Material.AIR);
		
	}
	
	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return this.z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	public String toString(){
		//Location blockCorner = new Location(null, (int)temp.getX(), (int)temp.getY(), (int)temp.getZ());
		return "" + this.x + " " + this.y + " " + this.z;
	}

	public int getFallLoc() {
		return this.fallLoc;
	}
	
	public void setFallLoc(int loc) {
		this.fallLoc = loc;
	}

}
