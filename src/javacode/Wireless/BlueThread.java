package javacode.Wireless;

import javacode.Core.Debugger;
import javacode.UI.MainPanel;
import javafx.application.Platform;

import javax.json.JsonObject;
import javax.microedition.io.StreamConnection;
import java.io.BufferedReader;
import java.io.IOException;

public class BlueThread extends Thread {

	public String name;

	public int latency;

	private BufferedReader input;
	private java.io.OutputStream output;
	private StreamConnection connection;

	public BlueThread(StreamConnection connection) throws IOException {
		this.connection = connection;
		this.input = new BufferedReader(new java.io.InputStreamReader(this.connection.openInputStream()));
		this.output = this.connection.openOutputStream();
		this.name = javax.bluetooth.RemoteDevice.getRemoteDevice(this.connection).getFriendlyName(false);
	}

	public void run() {
		Platform.runLater(() -> MainPanel.addConnectedDevices(this.name));
		// Send the config to the phone
		this.sendData(new Request(Request.Requests.CONFIG, javacode.ScoutingApp.config.getConfigAsJson()));
		while (this.connection != null) {

			String in = null;
			try {
				in = input.readLine();
				Debugger.d(this.getClass(), "In: " + in);
			} catch (IOException e) {
				// Check if the connection wasn't closed
				if (!e.toString().contains("An established connection was aborted by the software in your host machine.")) {
					Platform.runLater(() -> MainPanel.logError(e));
				}
			}

			if (in != null && !in.equals("")) {
				Debugger.d(this.getClass(), "Input: " + in);

				// Parse input
				// https://stackoverflow.com/questions/25948000/how-to-convert-string-to-jsonobject
				javax.json.JsonReader jsonReader = javax.json.Json.createReader(new java.io.StringReader(in));
				JsonObject object = jsonReader.read().asJsonObject();
				jsonReader.close();
				switch (this.parseRequest(object)) {
					case CONFIG:
						// Resend the config
						this.sendData(new Request(Request.Requests.CONFIG, javacode.ScoutingApp.config.getConfigAsJson()));
						break;
					case DATA:
						Platform.runLater(() -> MainPanel.log("Received data from device " + this.name + "\n" + object.get(Request.Requests.DATA.name()).toString(), false));
						// TODO Parse data from device

						break;
					case REQUEST_PING:
						break;
					case REQUEST_CLOSE:
						try {
							this.close(false);
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
			if (!e.toString().equals("java.io.IOException: Stream closed")) {
				Platform.runLater(() -> MainPanel.logError(e));
			}
		}
	}

	private void sendData(Request request) {
		try {
			String data = String.format("{\"%s\":%s}\n", request.requests.name(), request.data.toString());
			Debugger.d(this.getClass(), "Sending data: " + data);
			this.output.write(data.getBytes());
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
		Platform.runLater(() -> MainPanel.removeConnectedDevices(this.name));
		if (isRequested) {
			this.sendData(new Request(Request.Requests.REQUEST_CLOSE, null));
		}
		this.output.flush();
		this.output.close();
		this.input.close();
		this.connection.close();
		this.connection = null;
	}

}
