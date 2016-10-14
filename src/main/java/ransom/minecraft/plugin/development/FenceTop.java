package ransom.minecraft.plugin.development;

import org.bukkit.Location;

public class FenceTop {
	//mins and maxes are stored so they only have to be calculated once
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	private int x;
	private int y;
	private int z;
		
	public FenceTop(double xMin, double xMax, double yMin, double yMax){
		
		
	}
	
	public FenceTop(Location playerLoc){
		this.x = playerLoc.getBlockX();
		this.y = playerLoc.getBlockY();
		this.z = playerLoc.getBlockZ();
	}
	
	public FenceTop(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public String toString(){
		//Location blockCorner = new Location(null, (int)temp.getX(), (int)temp.getY(), (int)temp.getZ());
		return "" + this.x + " " + this.y + " " + this.z;
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
	
	public boolean equals(FenceTop other){
		if(other.getX() == this.x && other.getY() == this.y && other.getZ() == this.z){
			return true; 
		}
		return false;
	}
}
