package javacode.UI;

import javacode.Core.Debugger;
import javacode.Core.DeviceCatalog;
import javacode.Wireless.BlueThread;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.StringWriter;
import java.util.ArrayList;

public class MainPanel {

	private static Label macAddressHeader, console;

	public static ArrayList<DeviceCatalog> Devices = new ArrayList<>();

	private TeamNumberDialog dialog = new TeamNumberDialog();
	private ViewData window = new ViewData();

	public Scene loadMainPanel() {

		javafx.scene.layout.VBox root;

		// Get the path of the main panel's FXML file
		java.net.URL path = getClass().getClassLoader().getResource("javacode/fxml/MainPanel.fxml");

		// If the path is null, don't load
		if (path == null) {
			return null;
		}
		javacode.Core.Debugger.d(getClass(), "Path: " + path.toString());

		// Load the FXML from the layout file
		try {
			root = javafx.fxml.FXMLLoader.load(path);
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return null;
		}

		// Assign the MAC address
		macAddressHeader = (Label) root.getChildren().get(0);

		// Assign the 'console'
		MainPanel.console = ((Label) ((javafx.scene.control.ScrollPane) root.getChildren().get(2)).getContent());

		// Setup the bluetooth device stuff
		HBox deviceBank = (HBox) root.getChildren().get(4);
		for (int i = 0; i < deviceBank.getChildren().size(); i++) {
			VBox container = (VBox) deviceBank.getChildren().get(i);
			Label deviceName = (Label) container.getChildren().get(0);
			Label latencyLabel = (Label) container.getChildren().get(1);
			Button disconnectButton = (Button) container.getChildren().get(2);
			MainPanel.Devices.add(new DeviceCatalog(deviceName, latencyLabel, disconnectButton));
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

		return new Scene(root);

	}

	public void setMacAddress(String address) {
		MainPanel.macAddressHeader.setText(String.format("MAC Address: %s", address));
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
		System.err.println(String.format("Error: %s\n%s", e.toString(), sw.toString()));
		MainPanel.console.setText(String.format("%s\n%s", e.toString(), sw.toString()));
	}

	public static void connectDevice(BlueThread deviceThread) {
		Debugger.d(MainPanel.class, "Device connected: " + deviceThread.name);
		MainPanel.log(String.format("%s connected", deviceThread.name), false);
		for (DeviceCatalog device : MainPanel.Devices) {
			if (device.getName().equals("None")) {
				device.setName(deviceThread.name);
				device.setupDisconnectButton(deviceThread);
				break;
			}
		}
	}

	public static void disconnectDevice(String deviceName) {
		Debugger.d(MainPanel.class, "Device disconnected: " + deviceName);
		MainPanel.log(String.format("%s disconnected", deviceName), false);
		for (DeviceCatalog device : MainPanel.Devices) {
			if (device.getName().equals(deviceName)) {
				device.setName("None");
				device.setLatency(0);
				device.disableDisconnectButton();
				break;
			}
		}
	}

	public static void updatePing(String deviceName, int latency) {
		for (DeviceCatalog device : MainPanel.Devices) {
			if (device.getName().equals(deviceName)) {
				device.setLatency(latency);
				break;
			}
		}
	}
}
