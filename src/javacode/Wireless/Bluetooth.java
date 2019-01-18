package javacode.Wireless;

import javacode.Core.Debugger;
import javacode.UI.MainPanel;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;

public class Bluetooth {

	private LocalDevice server;

	public Bluetooth() throws BluetoothStateException {
		this.server = LocalDevice.getLocalDevice();
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

}
