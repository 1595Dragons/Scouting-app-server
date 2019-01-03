package javacode.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;

import javacode.ScoutingApp;
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

	private static final String configLocation = System.getProperty("user.dir") + "/config.json";

	public Match matchData = new Match();

	public boolean validateConfig() throws IOException {

		File config = new File(configLocation);

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

		File config = new File(configLocation);

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

		
		final JsonObject rawAutonomous = FullObject.get("Autonomous").asJsonObject();
		final int autoSize = rawAutonomous.size();
		Debugger.d(this.getClass(), "Raw autonomous Json: " + rawAutonomous.toString() + "\nSize: " + autoSize);

		this.matchData.autonomousData = Match.matchBaseToAutonomous(Match.getMatchData(rawAutonomous, autoSize));

		final JsonObject rawTeleOp = FullObject.get("TeleOp").asJsonObject();
		final int teleSize = rawTeleOp.size();
		Debugger.d(this.getClass(), "Raw teleop Json: " + rawTeleOp.toString() + "\nSize: " + teleSize);

		this.matchData.teleopData = Match.matchBaseToTeleOp(Match.getMatchData(rawTeleOp, teleSize));

		final JsonObject rawEndGame = FullObject.get("Endgame").asJsonObject();
		final int endgameSize = rawEndGame.size();
		Debugger.d(this.getClass(), "Raw endgame json: " + rawEndGame.toString() + "\nSize: " + endgameSize);

		this.matchData.endgameData = Match.matchBaseToEndgame(Match.getMatchData(rawEndGame, endgameSize));;
		
		
		// Validation, only for debug mode though
		if (ScoutingApp.debug) {
			for (Autonomous autoCheck : this.matchData.autonomousData) {
				Debugger.d(this.getClass(), "Loaded autonomous name: " + autoCheck.name);
			}
			
			for (TeleOp teleCheck : this.matchData.teleopData) {
				Debugger.d(this.getClass(), "Loaded teleop name: " + teleCheck.name);
			}
			
			for (Endgame endgameCheck : this.matchData.endgameData) {
				Debugger.d(this.getClass(), "Loaded endgame name: " + endgameCheck.name);
			}
		}
	}
}
