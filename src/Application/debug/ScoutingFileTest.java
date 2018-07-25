package Application.debug;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Application.Debugger;

public class ScoutingFileTest {

	private static final File ScoutingFile = new File(System.getProperty("user.dir") + "/scouting_data.csv");

	public static boolean FileExists() {
		Debugger.d(ScoutingFileTest.class, Boolean.toString(ScoutingFile.exists()));
		return ScoutingFile.exists();
	}

	public static void makeFile() {
		Debugger.d(ScoutingFileTest.class, "File path: " + ScoutingFile.getAbsolutePath());
		try {
			ScoutingFile.createNewFile();
		} catch (IOException e) {
			Debugger.d(ScoutingFileTest.class, "Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void setFileText() {
		InfoTest.FileLocationText.setText(ScoutingFile.getPath().replace(System.getProperty("user.home") + "\\", "")
				.replace("\\scouting_data.csv", ""));
		// Create a writer in order to write to the file
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter(ScoutingFile, true));
		} catch (IOException e) {
			Debugger.d(ScoutingFileTest.class, "Error: " + e.getMessage());
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
			Debugger.d(ScoutingFileTest.class, "Error: " + e.getMessage());
			e.printStackTrace();
		}

	}

	public static void setupButton() {
		InfoTest.GoToFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Runtime.getRuntime().exec("explorer.exe /select," + ScoutingFile.getAbsoluteFile());
				} catch (IOException e) {
					Debugger.d(ScoutingFileTest.class, "Error: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
		InfoTest.GoToFileButton.setEnabled(true);
	}

	public static void writeToFile(String data) {
		// Write the data to a CSV file
		Debugger.d(ScoutingFileTest.class, "Writing data to file");
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(System.getProperty("user.dir") + "/scouting_data.csv").getAbsolutePath(),
					true);
		} catch (IOException e1) {
			Debugger.d(ScoutingFileTest.class, String.format("Error, cannot edit file: %s", e1.getMessage()));
			e1.printStackTrace();
			return;
		}
		boolean success = false;
		try {
			fw.append(System.getProperty("line.separator") + data);
			fw.flush();
			success = true;
		} catch (IOException e1) {
			Debugger.d(ScoutingFileTest.class, String.format("Error, cannot write to file: %s", e1.getMessage()));
			e1.printStackTrace();
			success = false;
		}

		// Try closing the writer
		try {
			fw.close();
		} catch (IOException e1) {
			Debugger.d(ScoutingFileTest.class, String.format("Error, cannot close stream: %s", e1.getMessage()));
			e1.printStackTrace();
			return;
		}

		// Report success!
		if (success) {
			Debugger.d(ScoutingFileTest.class,
					String.format("Successfully written data for team: %s", data.split(",")[0]));
		} else {
			return;
		}
	}

}
