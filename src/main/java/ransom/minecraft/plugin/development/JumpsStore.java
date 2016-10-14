package ransom.minecraft.plugin.development;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class JumpsStore {
	private File storageFile;
	private HashMap<String, FenceBar> fenceBars; //Stores name of the jump as a key
	private Plugin plugin;//This is here so we can communicate with the consol for debugging. 

	
	public JumpsStore(File file, Plugin thePlugin){
		this.storageFile = file;
		this.fenceBars = new HashMap<String, FenceBar>();
		
		if(this.storageFile.exists() == false){
			try {
				this.storageFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.plugin = thePlugin;
	}
	
	
	public void load(){
		DataInputStream stream;
		try {
			stream = new DataInputStream(new FileInputStream(this.storageFile));
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line;
			//TODO run checks to make sure new Jump has any fencetops in it. 
			
			//Line Format: Name x y z x y z x y z...
			while((line = reader.readLine()) != null){
				String[] values = line.split(" "); //Parse Line by spaces eh? 
				String name;
				boolean isSet;
				int x;
				int y;
				int z;
				name = values[0];
				isSet = Boolean.valueOf(values[1]);
				//Not checking if it already exists. Should only load once
				//Checks if the gate was dropped or not. /....................
				if(isSet){
					fenceBars.put(name, new FenceBar(true, plugin)); 
				}
				else{
					fenceBars.put(name, new FenceBar(false, plugin));
				}
				for(int i = 2; i < values.length;){ //No auto increment because it happens in the loop
					x = Integer.parseInt(values[i++]);
					y = Integer.parseInt(values[i++]);
					z = Integer.parseInt(values[i++]);
					fenceBars.get(name).addFenceTop(x, y, z);
				}
			}
			reader.close();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save(){
		Iterator it = this.fenceBars.entrySet().iterator();
		
		try {
			FileWriter stream = new FileWriter(this.storageFile);
			BufferedWriter out = new BufferedWriter(stream);
			
			while(it.hasNext()){
				Map.Entry pair = (Map.Entry)it.next();
				String name = (String)pair.getKey();
				FenceBar jump = (FenceBar) pair.getValue();
				String isSet = "" + jump.isSet();
				
				//Writes name at beginning of line followed by isSet. 
				//Adding spaces to end of strings
				String jumpString = name + " " + isSet + " "; 
				
				ArrayList<FenceTop> fenceTops = jump.getFenceTops();
				for(FenceTop fence : fenceTops){
					jumpString = jumpString + fence.getX() + " ";
					jumpString = jumpString + fence.getY() + " ";
					jumpString = jumpString + fence.getZ() + " ";
				}
				out.write(jumpString);
				out.newLine();
			}
			out.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public boolean contains(String value){
		return this.fenceBars.containsKey(value);
	}
	
	public void add(String value){
		if(this.contains(value) == false){
			this.fenceBars.put(value, new FenceBar(plugin));
		}
	}
	
	public boolean remove(String value){
		if(this.contains(value)){
			this.fenceBars.remove(value);
			return true;
		}
		else{
			return false;
		}
	}
	
	public HashMap<String, FenceBar> getValues(){
		return this.fenceBars;
	}


	public FenceBar get(String name) {
		return this.fenceBars.get(name);
	}
	
	//used to return all the names of stored jumps 
	public Set<String> getJumpNames(){
		return this.fenceBars.keySet();
	}
	
	//This method is only  used for debugging purposes
	public ArrayList<String> getAllData(){
		ArrayList<String> data = new ArrayList<String>();
		
		Iterator it = this.fenceBars.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			String name = (String)pair.getKey();
			FenceBar jump = (FenceBar) pair.getValue();
			String jumpString = name + " "; //Writes name at beginning of line. Adding spaces to end of strings
			
			ArrayList<FenceTop> fenceTops = jump.getFenceTops();
			for(FenceTop fence : fenceTops){
				jumpString = jumpString + fence.getX() + " ";
				jumpString = jumpString + fence.getY() + " ";
				jumpString = jumpString + fence.getZ() + " ";
			}
			data.add(jumpString);
		}
		return data;
		
	}


	public boolean jumpHit(Location location, EquworldPoles equworldPoles) {

		
		Iterator it = this.fenceBars.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			FenceBar jump = (FenceBar) pair.getValue();
			if(jump.isHit(location, equworldPoles)){
				return true;
			}
		}
		return false;

	}


	

}
