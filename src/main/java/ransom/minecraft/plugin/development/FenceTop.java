package ransom.minecraft.plugin.development;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

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
	private int fallenBlock; //Recalculates every time it falls. 
	
	private Plugin plugin;//This is here to be able to communicate with the consol for debugging	

	public FenceTop(int x, int y, int z, Plugin thePlugin) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		//These values are due to the hit box size of the horse.
		this.maxX = x + 1.688;
		this.minX = x - 0.699; //This value of 700 doesn't match f3 coords... should be .719
		this.maxY = y + 0.8500001;//Little be more than exact horse height being: .8500000178814 
		this.minY = y - .5;//This is arbitrary. They'd have to be inside of the fencetop block to be here. 
		this.maxZ = z + 1.688;
		this.minZ = z - 0.699;
		
		plugin = thePlugin;
	}

	public FenceTop(Location playerLoc, Plugin thePlugin){
		this(playerLoc.getBlockX(), playerLoc.getBlockY(), playerLoc.getBlockZ(), thePlugin);
	}
	
	//TODO figure out how to account for high change when horse is 'kicking legs up'
	
	public boolean equals(FenceTop other){
		if(other.getX() == this.x && other.getY() == this.y && other.getZ() == this.z){
			return true; 
		}
		return false;
	}

	public boolean isHit(Location playerLoc, EquworldPoles equworldPoles) {
		plugin.getLogger().info("playerLocation: " + 
				playerLoc.getX() + " " + playerLoc.getY() + " " + playerLoc.getZ());
		plugin.getLogger().info("MaxX: " + 
				this.maxX + " MinX: " + this.minX + " maxY: " 
				+ this.maxY + " MaxZ: " + this.maxZ + " MinZ: " + this.minZ);
		
		if(playerLoc.getX() < this.maxX && playerLoc.getX() > this.minX &&
				playerLoc.getY() < this.maxY && playerLoc.getY() > this.minY &&
				playerLoc.getZ() < this.maxZ && playerLoc.getZ() > this.minZ){
			
			plugin.getLogger().info("HIIIIIIITTTTTTTTTTTTTTT");
			plugin.getLogger().info("HIIIIIIITTTTTTTTTTTTTTT");
			plugin.getLogger().info("HIIIIIIITTTTTTTTTTTTTTT");
			return true;
		}
		return false;
	}
	
	public void fall(){
		
	}
	
	public int getX() {
		return x;
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
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	public String toString(){
		//Location blockCorner = new Location(null, (int)temp.getX(), (int)temp.getY(), (int)temp.getZ());
		return "" + this.x + " " + this.y + " " + this.z;
	}
}
