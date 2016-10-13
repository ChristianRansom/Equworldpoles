package ransom.minecraft.plugin.development;

import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.Line;

import org.bukkit.Location;

public class FenceBar {

	private ArrayList<FenceTop> fenceTops = new ArrayList<FenceTop>();
	private Location resetSign = new Location(null, 0, 0, 0);
	private boolean set;
	
	public FenceBar(){
		this.set = true; 
	}
	
	//Called on Player Move
	public boolean isHit(Location playerLoc){
		/*whenPlayerMove()
	     when riding horse
	          for each fencebar
	               if fencebar.isHit()
	                    fencebar.fall()
	                    this.set = false*/
						fall();
		
		
		return false;
	}
	
	
	/*Method abstracted from isHit() for better code blocking 
	and clear logic flow*/
	private void fall(){
		
	}
	
	public boolean set(){
		
		
		return false;
	}
	
	@Override
	public String toString(){
		
		//Get each Line.class Split it. First is name, each consecutive 3 are a location. 
		return "" + this.fenceTops;
		
	}

	public void addFenceTop(Location location) {
		fenceTops.add(new FenceTop(location));
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<FenceTop> getFenceTops(){
		return fenceTops;
	}

	public void addFenceTop(int x, int y, int z) {
		fenceTops.add(new FenceTop(x, y, z));
	}
	
	public boolean removeFenceTop(int x, int y, int z){
		FenceTop fence = new FenceTop(x,y,z);
		if(this.contains(fence)){
			this.fenceTops.remove(fence);
			return true;
		}
		return false;
	}
	
	public boolean contains(FenceTop fence){
		if(fenceTops.contains(fence)){
			return true;
		}
		return false;
	}

	public boolean removeFenceTop(Location location) {
		return this.removeFenceTop(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}


}
