
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class selectFile {

	File file = new File(System.getProperty("user.dir") + "/scouting_data.csv");

	public static boolean fileMissing() throws IOException {
		selectFile file = new selectFile();
		return !file.file.isFile();
	}

	public static void makeFile() throws IOException {
		selectFile file = new selectFile();
		File csv = new File(file.file.getAbsolutePath());

		BufferedWriter write = new BufferedWriter(new FileWriter(csv, true));

		write.append(
				"Team number, hasAuto, autoSwitch, autoScale, teleSwitch, teleScale, cubeNumber, endClimb, endClimbAssist");
		write.newLine();
		write.flush();
		write.close();
	}

}
