package javacode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScoutingFile {

	private static final File ScoutingFile = new File(System.getProperty("user.dir") + "/scouting_data.csv");

	public static boolean FileExists() {
		return ScoutingFile.exists();
	}

	public static void makeFile() {
		try {
			ScoutingFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create a writer in order to write to the file
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter(ScoutingFile, true));
		} catch (IOException e) {
			e.printStackTrace();

		}

		// Try writing to said file, but report if anything is wrong!
		try {
			write.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", "Team #", "Basic auto",
					"Switch auto", "Scale auto", "Switch cube", "Scale cube", "Exchange cube", "Side climb (Single)",
					"Center climb (Single)", "Side climb (Double)", "Center climb (Double)", "Side climb (Tripple)",
					"Center climb (Tripple)", "Ramp climb", "Comments"));
			write.newLine();
			write.flush();
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeToFile(String data) {
		// Write the data to a CSV file
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(System.getProperty("user.dir") + "/scouting_data.csv").getAbsolutePath(),
					true);
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		boolean success = false;
		try {
			fw.append(System.getProperty("line.separator") + data);
			fw.flush();
			success = true;
		} catch (IOException e1) {
			e1.printStackTrace();
			success = false;
		}

		// Try closing the writer
		try {
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
	}
}
