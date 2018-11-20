package javacode.UI;

import java.io.IOException;
import java.net.URL;

import javacode.Debugger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class TeamNumberDialog {

	public Scene showTeamNumberDialog() throws IOException {
		Parent root = null;

		// Get the path of the main panel's fxml file
		URL path = getClass().getClassLoader().getResource("javacode/fxml/TeamNumberDialog.fxml");
		Debugger.d(getClass(), "Path: " + path.toString());

		// Load the FXML from the layout file
		try {
			root = FXMLLoader.load(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (root != null) {
			
			Scene scene = new Scene(root);
			return scene;
			
		} else {
			throw new IOException("Cannot load team number dialog FXML");
		}
	}
	
}
