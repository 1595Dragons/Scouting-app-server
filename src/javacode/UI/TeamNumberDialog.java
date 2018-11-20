package javacode.UI;

import java.io.IOException;
import java.net.URL;

import javacode.Debugger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TeamNumberDialog {
	
	private static boolean isVisible = false;
	
	private static Stage stage;

	public Scene createTeamNumberDialog() throws IOException {
		Parent root = null;

		// Get the path of the main panel's FXML file
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
	
	
	public boolean getIsVisible() {
		Debugger.d(getClass(), "Is team number dialog visible: " + isVisible);
		return isVisible;
	}
	

	public void setIsVisible(boolean value) {
		Debugger.d(getClass(), "Setting team number dialog visibility: " + value);
		isVisible = value;
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public void setStage(Stage stage) {
		TeamNumberDialog.stage = stage;
	}
}
