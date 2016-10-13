package ransom.minecraft.plugin.development;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JumpsStore {
	private File storageFile;
	private ArrayList<String> data;
	
	public JumpsStore(File file){
		this.storageFile = file;
		this.data = new ArrayList<String>();
		
		if(this.storageFile.exists() == false){
			try {
				this.storageFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void load(){
		DataInputStream stream;
		try {
			stream = new DataInputStream(new FileInputStream(this.storageFile));
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line;
			
			while((line = reader.readLine()) != null){
				if(this.data.contains(line) == false){
					this.data.add(line);
				}
			}
			
			reader.close();
			stream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save(){
		try {
			FileWriter stream = new FileWriter(this.storageFile);
			BufferedWriter out = new BufferedWriter(stream);
			
			for(String value : this.data){
				out.write(value);
				out.newLine();
			}
			out.close();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
