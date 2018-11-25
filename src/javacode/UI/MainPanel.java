package javacode.UI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javacode.Core.Debugger;
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
import javafx.stage.WindowEvent;

public class MainPanel {

	private static Label macAddressHeader, console;
	private static Button startScouting, viewData;

	public static ArrayList<Label> connectedDevices = new ArrayList<Label>();

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

				// Check if there is a nested view (HBox)
				if (node instanceof HBox) {
					for (Node childNode : ((HBox) node).getChildren()) {
						// If the given node of importance, set it to an object
						if (childNode.getId().equals("newScout")) {
							startScouting = ((Button) childNode);
						} else if (childNode.getId().equals("viewData")) {
							viewData = ((Button) childNode);
						} else if (childNode.getId().equals("device0")) {
							connectedDevices.add(0, ((Label) childNode));
						} else if (childNode.getId().equals("device1")) {
							connectedDevices.add(1, ((Label) childNode));
						} else if (childNode.getId().equals("device2")) {
							connectedDevices.add(2, ((Label) childNode));
						} else if (childNode.getId().equals("device3")) {
							connectedDevices.add(3, ((Label) childNode));
						} else if (childNode.getId().equals("device4")) {
							connectedDevices.add(4, ((Label) childNode));
						} else if (childNode.getId().equals("device5")) {
							connectedDevices.add(5, ((Label) childNode));
						} else {
							Debugger.d(getClass(), String.format("Unused childNode: (%s) %s", childNode.getClass(),
									childNode.getId()));
						}
					}
				} else {
					// If the given node of importance, set it to an object
					if (node.getId().equals("macAddressHeader")) {
						macAddressHeader = ((Label) node);
					} else if (node.getId().equals("console")) {
						console = ((Label) node);
					} else {
						Debugger.d(getClass(), String.format("Unused node: (%s) %s", node.getClass(), node.getId()));
					}
				}
			}

			startScouting.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					TeamNumberDialog dialog = new TeamNumberDialog();

					try {

						// Check if the window is visible (for creation reasons)
						if (!dialog.getIsVisible()) {

							// Make a new window
							// https://stackoverflow.com/questions/15041760/javafx-open-new-window
							dialog.setStage(new Stage());

							// Set it so when its closed, it will set the visibility to false
							dialog.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
								@Override
								public void handle(WindowEvent event) {
									dialog.setIsVisible(false);
								}

							});

							// Set the scene to the FMXL layout
							dialog.getStage().setScene(dialog.createTeamNumberDialog());
							dialog.getStage().setTitle("Enter team number");

							// Show the stage, and update the visibility
							dialog.getStage().show();
							dialog.setIsVisible(true);
						}

						// Be sure to bring the window to front
						dialog.getStage().toFront();

					} catch (IOException e) {
						log(e.getMessage(), true);
					}

				}
			});

			// TODO: Setup data view
			viewData.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

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
