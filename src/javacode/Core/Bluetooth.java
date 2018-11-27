package javacode.Core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;

import javacode.UI.MainPanel;

public class Bluetooth {

	private LocalDevice server;

	public boolean isEnabled() {
		try {
			Debugger.d(getClass(), "Bluetooth is on: " + Boolean.toString(LocalDevice.isPowerOn()));
			return LocalDevice.isPowerOn();
		} catch (Exception e) {
			MainPanel.logError(e);
			return false;
		}
	}

	public String getMACAddress() throws BluetoothStateException {
		if (isEnabled()) {
			if (server != null) {
				return server.getBluetoothAddress().replaceAll("..(?!$)", "$0:").toUpperCase();
			} else {
				throw new BluetoothStateException("Bluetooth object is null");
			}
		} else {
			throw new BluetoothStateException("Bluetooth is not turned on");
		}
	}

	public class SSPServer extends Thread {

		StreamConnection connection;

		public SSPServer(StreamConnection connection) {
			this.connection = connection;
		}

		// Wait for client connection
		public void run() {

			MainPanel debug = new MainPanel();

			if (connection != null) {
				synchronized (connection) {

					RemoteDevice client = null;
					try {
						client = RemoteDevice.getRemoteDevice(connection);
					} catch (IOException e) {
						MainPanel.logError(e);
					}

					if (client != null) {
						InputStream inputStream = null;
						try {
							inputStream = connection.openInputStream();
						} catch (IOException e) {
							MainPanel.logError(e);
						}

						if (inputStream != null) {

							// Read the data from the stream
							BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
							String lineRead = null;
							try {
								lineRead = bufferedReader.readLine();
								if (lineRead == null) {
									debug.log(String.format("%s (%s) has disconnected", client.getFriendlyName(false),
											client.getBluetoothAddress()), false);
								}
							} catch (IOException e) {
								MainPanel.logError(e);
							}

							// TODO: Parse the info from the stream to data, so it can be stored in the
							// databases

							// new Database().updateDatabase(teamNumber, hasAuto, autoSwitchCube,
							// autoScaleCube, teleSwitchCube, teleScaleCube, exchangeCube, canClimb,
							// comments);

							// TODO: Don't disconnect the device until requested
							// But for now, just disconnect
							try {
								if (bufferedReader != null) {
									bufferedReader.close();
								}
								if (inputStream != null) {
									inputStream.close();
								}
								if (connection != null) {
									new DeviceManagement().deviceDisconnected(
											RemoteDevice.getRemoteDevice(connection).getFriendlyName(false));
									connection.close();
								}
							} catch (IOException e) {
								MainPanel.logError(e);
							}
						} else {
							return;
						}
					} else {
						return;
					}
				}
			} else {
				debug.log("Bluetooth connection is null!\nCannot run on null stream", true);
			}
		}
	}

}
