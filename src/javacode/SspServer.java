package javacode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;

import javacode.Debugger;

public class SspServer extends Thread {
	StreamConnection connection = OldScoutingApp.currentConnection;

	// Wait for client connection
	public void run() {
		if (connection != null) {
			synchronized (connection) {

				InputStream inStream = null;
				try {
					inStream = connection.openInputStream();
				} catch (IOException e1) {
					Debugger.logTest(String.format("Error, cannot open stream: %s", e1.getMessage()), true);
					Debugger.d(this.getClass(), "Error: " + e1.getMessage());
					e1.printStackTrace();
				}

				// Read the data from the stream
				BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
				String lineRead = null;
				try {
					lineRead = bReader.readLine();
					if (lineRead == null) {
						Debugger.logTest("Device disconnected", false);
						Debugger.d(this.getClass(),
								String.format("Device disconnected: %s (%s)",
										RemoteDevice.getRemoteDevice(connection).getFriendlyName(false),
										RemoteDevice.getRemoteDevice(connection).getBluetoothAddress()));
						DeviceManagement
								.deviceDisconnected(RemoteDevice.getRemoteDevice(connection).getFriendlyName(false));
						return;
					}
				} catch (IOException e1) {
					Debugger.logTest(String.format("Error, cannot read stream: %s", e1.getMessage()), true);
					Debugger.d(this.getClass(), "Error: " + e1.getMessage());
					e1.printStackTrace();
				}

				ScoutingFile.writeToFile(lineRead);

				// Try closing the writer and stream
				try {
					DeviceManagement
							.deviceDisconnected(RemoteDevice.getRemoteDevice(connection).getFriendlyName(false));
					bReader.close();
					inStream.close();
				} catch (IOException e1) {
					Debugger.logTest(String.format("Error, cannot close stream: %s", e1.getMessage()), true);
					e1.printStackTrace();
				}

				// Success!
				try {
					connection.close();
				} catch (IOException e) {
					Debugger.log(String.format("Error closing connections: %s", e.getMessage()), true);
					Debugger.d(this.getClass(), "Error: " + e.getMessage());
					e.printStackTrace();
				}

			}
		} else {
			Debugger.logTest("Connection is null!", true);
			Debugger.d(this.getClass(), "Connection is null!");
		}
	}
}