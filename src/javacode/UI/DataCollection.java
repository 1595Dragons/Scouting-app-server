package javacode.UI;

import java.io.IOException;
import java.net.URL;

import javacode.Core.Debugger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class DataCollection {
	
	private int teamNumber;
	
	public DataCollection(int number) {
		this.teamNumber = number;
	}

	public Scene createDataCollectionPage() throws IOException {
		Parent root = null;

		// Get the path of the main panel's FXML file
		URL path = getClass().getClassLoader().getResource("javacode/fxml/DataPage.fxml");
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
