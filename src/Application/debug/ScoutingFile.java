package Application.debug;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import Application.Debugger;

public class ScoutingFile {

	private static final File ScoutingFile = new File(System.getProperty("user.dir") + "/scouting_data.csv");
	
	public static boolean FileExists() {
		Debugger.d(ScoutingFile.getClass(), Boolean.toString(ScoutingFile.exists()));
		return ScoutingFile.exists();
	}
	
	public static void makeFile() {
		Debugger.d(ScoutingFile.getClass(), "File path: " + ScoutingFile.getAbsolutePath());
		try {
			ScoutingFile.createNewFile();
		} catch (IOException e) {
			Debugger.d(ScoutingFile.getClass(), "Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void setFileText() {
		InfoTest.FileLocationText.setText(ScoutingFile.getPath().replace(System.getProperty("user.home") + "\\", "").replace("\\scouting_data.csv", ""));
	}
	
	public static void setupButton() {
		InfoTest.GoToFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Runtime.getRuntime().exec("explorer.exe /select," + ScoutingFile.getAbsoluteFile());
				} catch (IOException e) {
					Debugger.d(ScoutingFile.getClass(), "Error: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
		InfoTest.GoToFileButton.setEnabled(true);
	}
	
}
