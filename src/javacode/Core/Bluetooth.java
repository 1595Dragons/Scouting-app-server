package javacode.Core;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;

import javacode.UI.MainPanel;

public class Bluetooth {

	private LocalDevice server;
	
	
	public boolean isEnabled() {
		try {
			Debugger.d(getClass(), "Bluetooth is on: " + Boolean.toString(LocalDevice.isPowerOn()));
			return LocalDevice.isPowerOn();
		} catch (Exception e) {
			e.printStackTrace();
			new MainPanel().log(e.getMessage(), true);
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

}
