package javacode.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.stream.JsonParsingException;

import javacode.Core.Debugger;
import javacode.Core.Match;
import javacode.Core.Match.Autonomous;
import javacode.Core.Match.Endgame;
import javacode.Core.Match.TeleOp;
import javacode.UI.MainPanel;

/**
 * Manages the config file for dynamically creating the database, and data
 * collection page.
 * 
 */
public class Config {

	private final String configLocation = System.getProperty("user.dir") + "/config.json";

	public Match matchData = new Match();

	public boolean validateConfig() throws IOException {

		File config = new File(this.configLocation);

		if (!config.exists()) {
			this.createNewConfig();
			throw new IOException(
					"Config file was not found, so a new one was created. Please modify the file and restart the app.");
		}

		if (!config.canRead()) {
			throw new IOException("Cannot read config file. Please modify the read permissions and restart the app.");
		}

		try {
			this.loadConfig();
		} catch (JsonParsingException e) {
			throw new IOException("Invlaid config (See the README on config documentaiton): " + e.getMessage());
		}
		
		return true;
	}

	private void createNewConfig() {

		File config = new File(this.configLocation);

		// Create the empty file
		try {
			config.createNewFile();
		} catch (IOException e) {
			MainPanel.logError(e);
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
			MainPanel.logError(e);
		}
	}

	private void loadConfig() throws JsonParsingException {

		JsonReader reader = null;
		try {
			reader = Json.createReader(new FileReader(configLocation));
		} catch (FileNotFoundException e) {
			MainPanel.logError(e);
		}

		JsonObject FullObject = reader.readObject().asJsonObject();
		Debugger.d(this.getClass(), "Full Json: " + FullObject.toString());

		// Close the reader
		reader.close();

		JsonObject rawAutonomous = FullObject.get("Autonomous").asJsonObject();
		int autoSize = rawAutonomous.size();
		Debugger.d(this.getClass(), "Raw autonomous Json: " + rawAutonomous.toString() + "\nSize: " + autoSize);

		Autonomous autonomousData[] = new Autonomous[autoSize];
		String[] autonomousKeys = rawAutonomous.keySet().toArray(new String[autoSize]);
		for (int i = 0; i < autoSize; i++) {
			Debugger.d(this.getClass(), "Key: " + autonomousKeys[i]);

			JsonArray autoArray = rawAutonomous.getJsonArray(autonomousKeys[i]);
			Debugger.d(this.getClass(), "Auto array: " + autoArray.toString());

			Autonomous auto = new Match().new Autonomous();

			auto.name = autonomousKeys[i] + "(Autonomous)";
			auto.datatype = Match.DataType.valueOf(autoArray.getString(0));
			
			
			int autoArrayValues = autoArray.size();
			ArrayList<JsonValue> values = new ArrayList<JsonValue>();
			for (int k = 1; k < autoArrayValues; k++) {
				values.add(autoArray.get(k));
			}
			auto.value = values;

			
			autonomousData[i] = auto;
		}
		
		matchData.autonomousData = autonomousData;
		
		JsonObject rawTeleOp = FullObject.get("TeleOp").asJsonObject();
		int teleSize = rawTeleOp.size();
		Debugger.d(this.getClass(), "Raw teleop Json: " + rawTeleOp.toString() + "\nSize: " + teleSize);
		
		TeleOp teleopData[] = new TeleOp[teleSize];
		String[] teleopKeys = rawTeleOp.keySet().toArray(new String[teleSize]);
		for (int i = 0; i < teleSize; i++) {
			Debugger.d(this.getClass(), "Key: " + teleopKeys[i]);

			JsonArray teleopArray = rawTeleOp.getJsonArray(teleopKeys[i]);
			Debugger.d(this.getClass(), "TeleOp array: " + teleopArray.toString());
			
			TeleOp teleop = new Match().new TeleOp();
			
			teleop.name = teleopKeys[i];
			teleop.datatype = Match.DataType.valueOf(teleopArray.getString(0));
			
			
			int teleopArrayValues = teleopArray.size();
			ArrayList<JsonValue> values = new ArrayList<JsonValue>();
			for (int k = 1; k < teleopArrayValues; k++) {
				values.add(teleopArray.get(k));
			}
			teleop.value = values;
			
			
			teleopData[i] = teleop;
			
		}
		
		matchData.teleopData = teleopData;
		
		
		JsonObject rawEndGame = FullObject.get("Endgame").asJsonObject();
		int endgameSize = rawEndGame.size();
		Debugger.d(this.getClass(), "Raw endgame json: " + rawEndGame.toString() + "\nSize: " + endgameSize);
		
		Endgame endgameData[] = new Endgame[endgameSize];
		String[] endgameKeys = rawEndGame.keySet().toArray(new String[endgameSize]);
		for (int i = 0; i < endgameSize; i++) {
			Debugger.d(this.getClass(), "Key: " + endgameKeys[i]);
			
			JsonArray endgameArray = rawEndGame.getJsonArray(endgameKeys[i]);
			Debugger.d(this.getClass(), "Endgame array: " + endgameArray.toString());
			
			Endgame endgame = new Match().new Endgame();
			
			
			endgame.name = endgameKeys[i];
			endgame.datatype = Match.DataType.valueOf(endgameArray.getString(0));
			
			int endgameArrayValues = endgameArray.size();
			ArrayList<JsonValue> values = new ArrayList<JsonValue>();
			for (int k = 1; k < endgameArrayValues; k++) {
				values.add(endgameArray.get(k));
			}
			endgame.value = values;
			
			
			endgameData[i] = endgame;
		}
		
	}
	
	// TODO: Create custom view stuff for data, as well as custom database

}
