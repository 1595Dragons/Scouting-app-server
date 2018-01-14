
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class selectFile {

	static JFileChooser fileChooser = new JFileChooser();
	static File file = new File(System.getProperty("user.dir") + "scouting_data.csv");

	@Deprecated
	public static void CSV() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV", "csv");
		fileChooser.setFileFilter(filter);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		display display = new display();

		display.feed.setText("Selecting CSV file...");
		if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
			// user selects a file
			file = fileChooser.getSelectedFile().getAbsoluteFile();
			display.feed.setText("File selected: " + file.toString());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				display.feed.setText("Error occured while waiting!");
			}
			display.feed.setText("File selected: " + file.getName());
		} else {
			// Something else happened
			display.feed.setText("Operation canceled");
		}
	}

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
