package javacode.FileManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javacode.UI.MainPanel;

/**
 * Manages the config file for dynamically creating the database, and data
 * collection page.
 * 
 * @author stephenogden
 *
 */
public class Config {

	private final String configLocation = System.getProperty("user.dir") + "/config.json";

	public void validateConfig() throws IOException {

		File config = new File(configLocation);

		if (!config.exists()) {
			
			// TODO: Create the new config file
			
			throw new IOException("A config file was not found, so a new one was created. Please modify the file and restart the app.");
		}
		
		
		if (!config.canRead()) {
			
			throw new IOException("Cannot read config file. Please modify the read permissions and restart the app.");
		}
		
		
		// TODO: Compare files and check if they are the same

	}
	
	
	public void createNewConfig() {
		
		File config = new File(configLocation);
		
	
		// Create the empty file
		try {
			config.createNewFile();
		} catch (IOException e) {
			MainPanel.logError(e);;
		}
		
		
		// Write example config data to the config file
		FileWriter writer = null;
		FileReader reader = null;
		try {
			writer = new FileWriter(config);
			reader = new FileReader(new File(this.getClass().getClassLoader() + "configExample.txt"));
			reader.transferTo(writer);
			reader.close();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			MainPanel.logError(e);;
		}
	}

}
