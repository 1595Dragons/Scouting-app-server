
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class selectFile {

	static File file = new File(System.getProperty("user.dir") + "scouting_data.csv");

	public static boolean fileMissing() throws IOException {
		return !file.isFile();
	}

	public static void makeFile() {
		File csv = new File(System.getProperty("user.dir") + "scouting_data.csv");

		FileWriter write = null;

		try {
			write = new FileWriter(csv);
		} catch (IOException e) {
		}

		try {
			write.append(
					"Team number, hasAuto, autoSwitch, autoScale, teleSwitch, teleScale, cubeNumber, endClimb, endClimbAssist\n");
			write.flush();
			write.close();
		} catch (IOException e) {

		}
		file = csv;
	}

}
