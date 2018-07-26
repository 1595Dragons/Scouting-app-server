package Application.debug;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;

import Application.Debugger;

public class BluetoothTest {

	public static boolean isEnabled() {
		Debugger.d(BluetoothTest.class, Boolean.toString(LocalDevice.isPowerOn()));
		return LocalDevice.isPowerOn();
	}
	
	public static void changeMACDisplay() {
		try {
			InfoTest.MACAddressText.setText(LocalDevice.getLocalDevice().getBluetoothAddress().replaceAll("..(?!$)", "$0:").toUpperCase());
		} catch (BluetoothStateException e) {
			Debugger.d(BluetoothTest.class, e.getMessage());
			e.printStackTrace();
		}
	}
	
}
