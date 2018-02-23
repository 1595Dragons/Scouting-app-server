package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Deprecated
public class selectFile {

	File file = new File(System.getProperty("user.dir") + "/scouting_data.csv");

	public static boolean fileMissing() {
		selectFile file = new selectFile();
		return !file.file.isFile();
	}

	public static void makeFile() {
		selectFile file = new selectFile();
		File csv = new File(file.file.getAbsolutePath());

		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter(csv, true));
		} catch (IOException e) {
			System.out.println("Error creating file writer: " + e.getMessage());
		}

		try {
			write.append(
					"Team number, hasAuto, autoSwitch, autoScale, teleSwitch, teleScale, cubeNumber, endClimb, endClimbAssist");
			write.newLine();
			write.flush();
			write.close();
		} catch (IOException e) {
			System.out.println("Error writing to file: " + e.getMessage());
		}
		
	}

}
