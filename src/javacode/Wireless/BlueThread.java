package javacode.Wireless;

import javacode.Core.Debugger;
import javacode.ScoutingApp;
import javacode.UI.MainPanel;
import javafx.application.Platform;

import javax.bluetooth.RemoteDevice;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.microedition.io.StreamConnection;
import java.io.*;

public class BlueThread extends Thread {

	public String name;

	public int latency;

	private BufferedReader input;
	private OutputStream output;
	private StreamConnection connection;

	public BlueThread(StreamConnection connection) throws IOException {
		this.connection = connection;
		this.input = new BufferedReader(new InputStreamReader(this.connection.openInputStream()));
		this.output = this.connection.openOutputStream();
		this.name = RemoteDevice.getRemoteDevice(this.connection).getFriendlyName(false);
	}

	public void run() {
		Platform.runLater(() -> MainPanel.addConnectedDevices(this.name));
		// Send the config to the phone
		this.sendData(new Request(Request.Requests.CONFIG, ScoutingApp.config.getConfigAsJson()));
		Debugger.d(this.getClass(), "Sent data" + ScoutingApp.config.getConfigAsJson().toString());
		while (this.connection != null) {

			String in = null;
			try {
				if (input.ready()) {
					in = input.readLine();
					Debugger.d(this.getClass(), "In: " + in);
				}
			} catch (IOException e) {
				Platform.runLater(() -> MainPanel.logError(e));
			}

			if (in != null && !in.equals("")) {
				Debugger.d(this.getClass(), "Input: " + in);

				// Parse input
				// https://stackoverflow.com/questions/25948000/how-to-convert-string-to-jsonobject
				JsonReader jsonReader = Json.createReader(new StringReader(in));
				JsonObject object = jsonReader.readObject();
				jsonReader.close();
				switch (this.parseRequest(object)) {
					case CONFIG:
						// Resend the config
						this.sendData(new Request(Request.Requests.CONFIG, ScoutingApp.config.getConfigAsJson()));
						break;
					case DATA:
						Platform.runLater(() -> MainPanel.log("Received data from device " + this.name + "\n" + object.getString(Request.Requests.DATA.name()), false));
						break;
					case REQUEST_PING:
						break;
					case REQUEST_CLOSE:
						try {
							this.close(true);
						} catch (IOException e) {
							Platform.runLater(() -> MainPanel.logError(e));
						}
						break;
				}
			}

			// TODO: Ping the device
		}

		try {
			this.close(false);
		} catch (IOException e) {
			Platform.runLater(() -> MainPanel.logError(e));
		}
	}

	private void sendData(Request request) {
		try {
			this.output.write(String.format("{\"%s\":%s}", request.requests.name(), request.data.toString()).getBytes());
			this.output.flush();
		} catch (IOException e) {
			Platform.runLater(() -> MainPanel.logError(e));
		}
	}

	private Request.Requests parseRequest(JsonObject input) {
		if (input.containsKey(Request.Requests.REQUEST_CLOSE.name())) {
			return Request.Requests.REQUEST_CLOSE;
		} else if (input.containsKey(Request.Requests.REQUEST_PING.name())) {
			return Request.Requests.REQUEST_PING;
		} else if (input.containsKey(Request.Requests.CONFIG.name())) {
			return Request.Requests.CONFIG;
		} else {
			return Request.Requests.DATA;
		}
	}

	private void close(boolean isRequested) throws IOException {
		Platform.runLater(() -> MainPanel.log("Disconnecting device " + this.name, false));
		if (isRequested) {
			this.sendData(new Request(Request.Requests.REQUEST_CLOSE, null));
		}
		this.output.flush();
		this.output.close();
		this.input.close();
		this.connection.close();
		Platform.runLater(() -> MainPanel.removeConnectedDevices(this.name));
	}

}
