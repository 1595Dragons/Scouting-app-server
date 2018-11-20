package javacode.UI;

import java.io.IOException;
import java.net.URL;

import javacode.Debugger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainPanel {

	private Label macAddressHeader, console;
	private Button startScouting, viewData;

	public Scene loadMainPanel() throws IOException {

		Parent root = null;

		// Get the path of the main panel's FXML file
		URL path = getClass().getClassLoader().getResource("javacode/fxml/MainPanel.fxml");
		Debugger.d(getClass(), "Path: " + path.toString());

		// Load the FXML from the layout file
		try {
			root = FXMLLoader.load(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (root != null) {

			// Setup each of the nodes that are important
			for (Node node : root.getChildrenUnmodifiable()) {
				if (node instanceof HBox) {
					for (Node childNode : ((HBox) node).getChildren()) {
						
						// If the given node of importance, set it to an object
						if (childNode.getId().equals("newScout")) {
							startScouting = ((Button) childNode);
						} else if (childNode.getId().equals("viewData")) {
							viewData = ((Button) childNode);
						}
						
						Debugger.d(getClass(), "HBox node: " + childNode.getId());
					}
				} else {
					
					// If the given node of importance, set it to an object
					if (node.getId().equals("macAddressHeader")) {
						macAddressHeader = ((Label) node);
					} else if (node.getId().equals("console")) {
						console = ((Label) node);
					}
					
					
					Debugger.d(getClass(), "Node: " + node.getId());
				}
			}
			
			// TODO: Setup button functions
			startScouting.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					// TODO: Check if window is already visible
					
					TeamNumberDialog dialog = new TeamNumberDialog();
					
		            try {
		            	// https://stackoverflow.com/questions/15041760/javafx-open-new-window
		            	Stage stage = new Stage();
						stage.setScene(dialog.showTeamNumberDialog());
						stage.setTitle("Enter team number");
						stage.show();
					} catch (IOException e) {
						log(e.getMessage(), true);
					}
				}
				
			});
			
			viewData.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					
				}
				
			});

			Scene scene = new Scene(root);
			return scene;
		} else {
			throw new IOException("Cannot load main panel FXML");
		}
	}
	
	
	public void setMacAddress(String address) {
		macAddressHeader.setText(String.format("MAC Address: %s", address));
	}
	
	
	public void log(String message, boolean error) {
		if (error) {
			console.setTextFill(Color.RED);
			System.err.println(message);
		} else {
			console.setTextFill(Color.BLACK);
		}
		
		console.setText(message);
	}

}
