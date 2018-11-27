package javacode.UI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javacode.Core.Debugger;
import javacode.Core.NodeHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewData {

	private static Stage stage;
	
	public Scene createDataCollectionPage() throws IOException {
		
		Parent root = null;

		// Get the path of the main panel's FXML file
		URL path = getClass().getClassLoader().getResource("javacode/fxml/ViewData.fxml");
		Debugger.d(getClass(), "Path: " + path.toString());

		// Load the FXML from the layout file
		try {
			root = FXMLLoader.load(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (root != null) {
			
			ArrayList<Node> Nodes = new NodeHelper().getAllNodes(root.getChildrenUnmodifiable());
			for (Node node : Nodes) {
				if (node.getId() != null) {
					Debugger.d(getClass(), String.format("Unused node: (%s) %s", node.getClass(), node.getId()));
				}
			}
			
			Scene scene = new Scene(root);
			return scene;
			
		} else {
			throw new IOException("Cannot view data dialog FXML");
		}
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		ViewData.stage = stage;
	}

}
