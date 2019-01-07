package javacode.Wireless;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;

import javacode.Core.Debugger;
import javacode.UI.MainPanel;
import javafx.application.Platform;

public class Client extends Thread {

	public String name;
	
	public int latency;
	
	private BufferedReader input;
	private BufferedWriter output;
	private StreamConnection connection;
	private volatile boolean stopRequested = false;
	
	public Client(StreamConnection connection) throws IOException {
		this.connection = connection;
		this.input = new BufferedReader(new InputStreamReader(this.connection.openInputStream()));
		this.output = new BufferedWriter(new OutputStreamWriter(this.connection.openOutputStream()));
		this.name = RemoteDevice.getRemoteDevice(this.connection).getFriendlyName(false);
	}

	public void run() {
		DeviceManagement.deviceConnected(this.name);
		// TODO: Send the phone the config file
		
		while (connection != null && !stopRequested) {
			String in = null;
		
			try {
				if (input.ready()) {
					in = input.readLine();
				}
			} catch (IOException e) {
				Platform.runLater(() -> MainPanel.logError(e));
			}
			
			if (in != null || in != "") {
				Debugger.d(this.getClass(), "Input: " + in);
			}
			
			// TODO: Ping the device
			
		}
		
		DeviceManagement.deviceDisconnected(this.name);
		
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
