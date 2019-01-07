package javacode.Wireless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;

import javacode.Core.Debugger;
import javacode.UI.MainPanel;

public class Bluetooth {
	
	private LocalDevice server;
	
	public Bluetooth() throws BluetoothStateException {
		this.server =  LocalDevice.getLocalDevice();
	}
	
	public static boolean isEnabled() {
		// First, check if the operating system is windows
		if (System.getProperty("os.name").startsWith("Windows")) {
			try {
				boolean isOn = LocalDevice.isPowerOn();
				Debugger.d(Bluetooth.class, "Bluetooth is on: " + isOn);
				return isOn;
			} catch (Exception e) {
				MainPanel.logError(e);
				return false;
			}
		} else {
			return false;
		}
	}

	public String getMACAddress() {
		return this.server.getBluetoothAddress().replaceAll("..(?!$)", "$0:").toUpperCase();
	}

	public class SSPServer extends Thread {

		StreamConnection connection;

		public SSPServer(StreamConnection connection) {
			this.connection = connection;
		}

		// Wait for client connection
		public void run() {

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
									MainPanel.log(String.format("%s (%s) has disconnected", client.getFriendlyName(false),
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
				MainPanel.log("Bluetooth connection is null!\nCannot run on null stream", true);
			}
		}
	}

}
