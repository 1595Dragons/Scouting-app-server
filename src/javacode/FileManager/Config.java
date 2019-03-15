package javacode.FileManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;

import javacode.Core.Debugger;
import javacode.Match.Match;
import javacode.UI.MainPanel;

/**
 * Manages the config file for dynamically creating the database, and data
 * collection page.
 */
public class Config {

	/**
	 * The config file location (the current app directory).
	 */
	private static final String configLocation = System.getProperty("user.dir") + "/config.json";

	/**
	 * Match object for all the data collected in a match.
	 */
	public Match matchData = new Match();

	/**
	 * Method that validates the config file.
	 *
	 * @throws IOException Thrown when there is some sort of error while validating.
	 */
	public void validateConfig() throws IOException {

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
			throw new IOException("Invalid config (See the README on config documentation): " + e.getMessage());
		}
	}

	/**
	 * Creates and populates a new config file.
	 */
	private void createNewConfig() {

		File config = new File(configLocation);

		// Create the empty file
		boolean creationSuccess;
		try {
			creationSuccess = config.createNewFile();
		} catch (IOException e) {
			MainPanel.logError(e);
			return;
		}

		if (!creationSuccess) {
			MainPanel.log("Unable to create a new config file", true);
		}

		String conf = "{\n\t\"Autonomous\" : {\n\t\t\"Do the thing\" : [\"Boolean\", false]\n\t},\n\t"
				+ "\"TeleOp\" : {\n\t\t\"Get the points\" : [\"Number\", 0, 0, 25, 1],\n\t\t\"Win\" : [\"Number\", 0, 0, 25, 1]\n\t},\n\t"
				+ "\"Endgame\" : {\n\t\t\"Info\" : [\"Text\", \"See the readme for config documentation\"]\n\t}\n}";

		// Write example config data to the config file
		try {
			FileWriter writer = new FileWriter(config);
			writer.write(conf);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			MainPanel.logError(e);
		}
	}

	/**
	 *
	 * @throws JsonParsingException
	 */
	private void loadConfig() throws JsonParsingException {

		JsonReader reader;
		try {
			reader = Json.createReader(new java.io.FileReader(configLocation));
		} catch (java.io.FileNotFoundException e) {
			MainPanel.logError(e);
			return;
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

		this.matchData.endgameData = Match.matchBaseToEndgame(Match.getMatchData(rawEndGame, endgameSize));

		// Validation, only for debug mode though
		if (javacode.ScoutingApp.debug) {
			for (Match.Autonomous autoCheck : this.matchData.autonomousData) {
				Debugger.d(this.getClass(), "Loaded autonomous name: " + autoCheck.name);
			}

			for (Match.TeleOp teleCheck : this.matchData.teleopData) {
				Debugger.d(this.getClass(), "Loaded teleop name: " + teleCheck.name);
			}

			for (Match.Endgame endgameCheck : this.matchData.endgameData) {
				Debugger.d(this.getClass(), "Loaded endgame name: " + endgameCheck.name);
			}
		}
	}

	/**
	 *
	 * @return
	 */
	public JsonObject getConfigAsJson() {

		JsonReader reader;
		try {
			reader = Json.createReader(new java.io.FileReader(configLocation));
		} catch (java.io.FileNotFoundException e) {
			MainPanel.logError(e);
			return null;
		}

		JsonObject FullObject = reader.readObject().asJsonObject();
		Debugger.d(this.getClass(), "Full Json: " + FullObject.toString());

		// Close the reader
		reader.close();

		return FullObject;
	}
}
