package javacode.UI;

import java.io.StringWriter;
import java.util.ArrayList;

import javacode.Core.Debugger;
import javacode.ScoutingApp;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class MainPanel {

	private Label macAddressHeader;
	private static Label console;
	public static ArrayList<Label> connectedDevices = new ArrayList<Label>();

	private TeamNumberDialog dialog = new TeamNumberDialog();
	private ViewData window = new ViewData();

	public Scene loadMainPanel() {

		javafx.scene.layout.VBox root = null;

		// Get the path of the main panel's FXML file
		java.net.URL path = getClass().getClassLoader().getResource("javacode/fxml/MainPanel.fxml");
		javacode.Core.Debugger.d(getClass(), "Path: " + path.toString());

		// Load the FXML from the layout file
		try {
			root = javafx.fxml.FXMLLoader.load(path);
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return null;
		}

		// TODO: On close have popup box making sure you want to close. If true, request all connected devices to leave

		// Assign the MAC address
		macAddressHeader = (Label) root.getChildren().get(0);

		// Assign the 'console'
		MainPanel.console = ((Label) ((javafx.scene.control.ScrollPane) root.getChildren().get(2)).getContent());

		// Setup the device headers
		HBox deviceBank = (HBox) root.getChildren().get(4);
		for (int i = 0; i < deviceBank.getChildren().size(); i++) {
			connectedDevices.add(i, (Label) deviceBank.getChildren().get(i));
		}

		// Get the buttons to scout/view data
		HBox buttonBank = (HBox) root.getChildren().get(5);

		((Button) buttonBank.getChildren().get(0)).setOnAction((event) -> {
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
		});

		((Button) buttonBank.getChildren().get(1)).setOnAction((event) -> {
			// Check if the window is visible (for creation reasons)
			if (!window.getStage().isShowing()) {

				// https://stackoverflow.com/questions/15041760/javafx-open-new-window
				// Set the scene to the FMXL layout
				window.getStage().setScene(window.ViewDataScene());
				window.getStage().setTitle("Data");

				// Show the stage, and update the visibility
				window.getStage().show();

			}

			// Be sure to bring the window to front
			window.getStage().toFront();
		});

		Scene scene = new Scene(root);
		return scene;

	}

	public void setMacAddress(String address) {
		macAddressHeader.setText(String.format("MAC Address: %s", address));
	}

	public static void log(String message, boolean error) {
		if (error) {
			MainPanel.console.setTextFill(Color.RED);
			System.err.println(message);
		} else {
			MainPanel.console.setTextFill(Color.BLACK);
		}
		MainPanel.console.setText(message);
	}

	public static void logError(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new java.io.PrintWriter(sw));
		MainPanel.console.setTextFill(Color.RED);
		System.err.println(String.format("Error: %s\n%s", e.getMessage(), sw.toString()));
		MainPanel.console.setText(String.format("%s\n%s", e.getMessage(), sw.toString()));
	}

	public static void addConnectedDevices(String deviceName) {
		Debugger.d(MainPanel.class, "Device connected: " + deviceName);
		MainPanel.log(String.format("%s connected", deviceName), false);
		for (Label deviceText : MainPanel.connectedDevices) {
			if (deviceText.getText().equals("None")) {
				deviceText.setText(deviceName);
				break;
			}
		}
	}

	public static void removeConnectedDevices(String deviceName) {
		Debugger.d(MainPanel.class, "Device disconnected: " + deviceName);
		MainPanel.log(String.format("%s disconnected", deviceName), false);
		for (Label deviceText : MainPanel.connectedDevices) {
			if (deviceText.getText().equals(deviceName)) {
				deviceText.setText("None");
				break;
			}
		}
	}
}
