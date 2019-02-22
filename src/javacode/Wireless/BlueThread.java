package javacode.Wireless;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;

import javacode.Core.Debugger;
import javacode.ScoutingApp;
import javacode.UI.MainPanel;
import javafx.application.Platform;
import sun.applet.Main;

public class BlueThread extends Thread {

	public String name;
	
	public int latency;
	
	private BufferedReader input;
	private BufferedWriter output;
	private StreamConnection connection;
	
	public BlueThread(StreamConnection connection) throws IOException {
		this.connection = connection;
		this.input = new BufferedReader(new InputStreamReader(this.connection.openInputStream()));
		this.output = new BufferedWriter(new OutputStreamWriter(this.connection.openOutputStream()));
		this.name = RemoteDevice.getRemoteDevice(this.connection).getFriendlyName(false);
	}

	public void run() {
		MainPanel.addConnectedDevices(this.name);
		// TODO: Send the phone the config file
		Request config = new Request(Request.Requests.CONFIG, ScoutingApp.config.getConfigAsJson());
		try {
			output.write(String.format("%s:%s", config.requests.name(), config.data.toString()));
			output.flush();
		} catch (IOException e) {
			Platform.runLater(() -> MainPanel.logError(e));
		}

		while (connection != null) {
			String in = null;
		
			try {
				if (input.ready()) {
					in = input.readLine();
				}
			} catch (IOException e) {
				Platform.runLater(() -> MainPanel.logError(e));
			}

			if (in != null && !in.equals("")) {
				Debugger.d(this.getClass(), "Input: " + in);
			}

			// TODO: Ping the device
			
		}

		Platform.runLater(() -> MainPanel.removeConnectedDevices(this.name));
		
		try {
			this.output.flush();
			this.output.close();
			this.input.close();
			this.connection.close();
		} catch (IOException e) {
			Platform.runLater(() -> MainPanel.logError(e));
		}
	}
	
}
