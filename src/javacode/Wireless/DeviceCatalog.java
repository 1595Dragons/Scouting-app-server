package javacode.Wireless;

import javacode.UI.MainPanel;
import javacode.Wireless.BlueThread;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class DeviceCatalog {
	private Label deviceNameLabel, latencyLabel;
	private Button disconnectButton;

	public DeviceCatalog(Label deviceNameLabel, Label latencyLabel, Button disconnectButton) {
		this.deviceNameLabel = deviceNameLabel;
		this.latencyLabel = latencyLabel;
		this.disconnectButton = disconnectButton;
	}

	public String getName() {
		return this.deviceNameLabel.getText();
	}

	public void setName(String name) {
		this.deviceNameLabel.setText(name);
	}

	public void setupDisconnectButton(BlueThread bt) {
		this.disconnectButton.setOnAction((event) -> {
			try {
				bt.close(true);
			} catch (IOException e) {
				Platform.runLater(() -> MainPanel.logError(e));
			}
		});
		this.disconnectButton.setDisable(false);
	}

	public void setLatency(int latency) {
		if (latency == -1) {
			Platform.runLater(() -> this.latencyLabel.setText("--- ms ping"));
		} else {
			Platform.runLater(() -> this.latencyLabel.setText(String.format("%s ms ping", Integer.toString(latency))));
		}
	}

	public void disableDisconnectButton() {
		this.disconnectButton.setOnAction(null);
		this.disconnectButton.setDisable(true);
	}

}