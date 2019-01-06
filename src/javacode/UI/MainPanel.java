package javacode.UI;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;

import javacode.Core.Debugger;
import javacode.Core.NodeHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainPanel {

	private static Label macAddressHeader, console;
	private static Button startScouting, viewData;

	private TeamNumberDialog dialog = new TeamNumberDialog();
	private ViewData window = new ViewData();

	public static Stage stage;

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
			return null;
		}

		// Setup each of the nodes that are important
		ArrayList<Node> Nodes = new NodeHelper().getAllNodes(root.getChildrenUnmodifiable());
		for (Node node : Nodes) {
			if (node.getId() != null) {
				// If the given node of importance, set it to an object
				if (node.getId().equals("newScout")) {
					startScouting = ((Button) node);
				} else if (node.getId().equals("viewData")) {
					viewData = ((Button) node);
				} else if (node.getId().equals("device0")) {
					connectedDevices.add(0, ((Label) node));
				} else if (node.getId().equals("device1")) {
					connectedDevices.add(1, ((Label) node));
				} else if (node.getId().equals("device2")) {
					connectedDevices.add(2, ((Label) node));
				} else if (node.getId().equals("device3")) {
					connectedDevices.add(3, ((Label) node));
				} else if (node.getId().equals("device4")) {
					connectedDevices.add(4, ((Label) node));
				} else if (node.getId().equals("device5")) {
					connectedDevices.add(5, ((Label) node));
				} else if (node.getId().equals("console")) {
					console = ((Label) node);
				} else if (node.getId().equals("macAddressHeader")) {
					macAddressHeader = ((Label) node);
				} else {
					Debugger.d(getClass(), String.format("Unused node: (%s) %s", node.getClass(), node.getId()));
				}
			}
		}

		startScouting.setOnAction(new MainPanel.startScouting());

		viewData.setOnAction(new MainPanel.viewDataHandler());

		Scene scene = new Scene(root);
		return scene;

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

	public static void logError(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		console.setTextFill(Color.RED);
		System.err.println(String.format("Error: %s\n%s", e.getMessage(), sw.toString()));
		console.setText(String.format("%s\n%s", e.getMessage(), sw.toString()));
	}

	private class startScouting implements EventHandler<ActionEvent> {

		public void handle(ActionEvent event) {

			// Check if the window is visible (for creation reasons)
			if (!dialog.getStage().isShowing()) {

				// https://stackoverflow.com/questions/15041760/javafx-open-new-window
				// Set the scene to the FMXL layout
				dialog.getStage().setScene(dialog.createTeamNumberDialog());
				dialog.getStage().setTitle("Enter team number");
				dialog.getStage().setResizable(false);

				// Show the stage, and update the visibility
				dialog.getStage().show();
			}

			// Be sure to bring the window to front
			dialog.getStage().toFront();
		}

	}

	private class viewDataHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

			if (!window.getIsVisible()) {

				// Make a new window
				// https://stackoverflow.com/questions/15041760/javafx-open-new-window
				window.setStage(new Stage());

				// Set it so when its closed, it will set the visibility to false
				window.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent event) {
						window.setIsVisible(false);
					}

				});

				// Set the scene to the FMXL layout
				try {
					window.getStage().setScene(window.ViewDataScene());
					window.getStage().setTitle("Data");

					// Show the stage, and update the visibility
					window.getStage().show();
					window.setIsVisible(true);
				} catch (IOException e) {
					MainPanel.logError(e);
				}
			}

			// Be sure to bring the window to front
			window.getStage().toFront();
		}

	}
}
