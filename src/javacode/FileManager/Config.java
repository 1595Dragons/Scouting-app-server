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

	public boolean validateConfig() throws IOException {

		File config = new File(this.configLocation);

		if (!config.exists()) {
			this.createNewConfig();
			throw new IOException("Config file was not found, so a new one was created. Please modify the file and restart the app.");
		}
		
		
		if (!config.canRead()) {
			
			throw new IOException("Cannot read config file. Please modify the read permissions and restart the app.");
		}
		
		
		// TODO: Compare files and check if they are the same

		return true;
	}
	
	
	private void createNewConfig() {
		
		File config = new File(this.configLocation);
		
	
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
			reader = new FileReader(new File(this.getClass().getResource("configExample.txt").getFile()));
			reader.transferTo(writer);
			reader.close();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			MainPanel.logError(e);;
		}
	}

}
