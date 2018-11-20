package javacode;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;

import javacode.Debugger;

public class Bluetooth {

	public static boolean isEnabled() {
		Debugger.d(Bluetooth.class, Boolean.toString(LocalDevice.isPowerOn()));
		return LocalDevice.isPowerOn();
	}

	public static void changeMACDisplay() {
		try {
			OldMainPanel.MACAddress.setText(String.format("MAC Address: %s",
					LocalDevice.getLocalDevice().getBluetoothAddress().replaceAll("..(?!$)", "$0:").toUpperCase()));
		} catch (BluetoothStateException e) {
			Debugger.d(Bluetooth.class, e.getMessage());
			e.printStackTrace();
		}
	}

}
