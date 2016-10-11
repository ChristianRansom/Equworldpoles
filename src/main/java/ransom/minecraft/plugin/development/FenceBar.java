package ransom.minecraft.plugin.development;

import java.util.ArrayList;
import org.bukkit.Location;

public class FenceBar {

	private ArrayList<FenceTop> fenceTops = new ArrayList<FenceTop>();
	private Location resetSign = new Location(null, 0, 0, 0);
	boolean set;
	
	public FenceBar(){
		this.set = true; 
	}
	
	public void addFenceTop(){
		
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
	
	
	
}
