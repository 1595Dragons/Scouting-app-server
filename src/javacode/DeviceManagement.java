package javacode;

import javacode.Debugger;

public class DeviceManagement {

	public static String[] connectedDevices = new String[6];

	public static void deviceConnected(String deviceName) {
		Debugger.d(DeviceManagement.class, "Device name: " + deviceName);
		for (int i = 0; i < connectedDevices.length; i++) {
			if (connectedDevices[i].equals("None")) {
				connectedDevices[i] = deviceName;
				break;
			}
		}
		OldMainPanel.Device1.setText(connectedDevices[0]);
		OldMainPanel.Device2.setText(connectedDevices[1]);
		OldMainPanel.Device3.setText(connectedDevices[2]);
		OldMainPanel.Device4.setText(connectedDevices[3]);
		OldMainPanel.Device5.setText(connectedDevices[4]);
		OldMainPanel.Device6.setText(connectedDevices[5]);
	}

	public static void deviceDisconnected(String deviceName) {
		Debugger.d(DeviceManagement.class, "Device name: " + deviceName);
		for (int i = 0; i < connectedDevices.length; i++) {
			if (connectedDevices[i].equals(deviceName)) {
				connectedDevices[i] = "None";
				break;
			}
		}
		OldMainPanel.Device1.setText(connectedDevices[0]);
		OldMainPanel.Device2.setText(connectedDevices[1]);
		OldMainPanel.Device3.setText(connectedDevices[2]);
		OldMainPanel.Device4.setText(connectedDevices[3]);
		OldMainPanel.Device5.setText(connectedDevices[4]);
		OldMainPanel.Device6.setText(connectedDevices[5]);
	}

	public static void reset() {
		for (int i = 0; i < connectedDevices.length; i++) {
			connectedDevices[i] = "None";
		}
		OldMainPanel.Device1.setText(connectedDevices[0]);
		OldMainPanel.Device2.setText(connectedDevices[1]);
		OldMainPanel.Device3.setText(connectedDevices[2]);
		OldMainPanel.Device4.setText(connectedDevices[3]);
		OldMainPanel.Device5.setText(connectedDevices[4]);
		OldMainPanel.Device6.setText(connectedDevices[5]);
	}

}
