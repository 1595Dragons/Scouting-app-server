package Application.debug;

import Application.Debugger;

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
		MainPanel.Device1.setText(connectedDevices[0]);
		MainPanel.Device2.setText(connectedDevices[1]);
		MainPanel.Device3.setText(connectedDevices[2]);
		MainPanel.Device4.setText(connectedDevices[3]);
		MainPanel.Device5.setText(connectedDevices[4]);
		MainPanel.Device6.setText(connectedDevices[5]);
	}

	public static void deviceDisconnected(String deviceName) {
		Debugger.d(DeviceManagement.class, "Device name: " + deviceName);
		for (int i = 0; i < connectedDevices.length; i++) {
			if (connectedDevices[i].equals(deviceName)) {
				connectedDevices[i] = "None";
				break;
			}
		}
		MainPanel.Device1.setText(connectedDevices[0]);
		MainPanel.Device2.setText(connectedDevices[1]);
		MainPanel.Device3.setText(connectedDevices[2]);
		MainPanel.Device4.setText(connectedDevices[3]);
		MainPanel.Device5.setText(connectedDevices[4]);
		MainPanel.Device6.setText(connectedDevices[5]);
	}

	public static void reset() {
		for (int i = 0; i < connectedDevices.length; i++) {
			connectedDevices[i] = "None";
		}
		MainPanel.Device1.setText(connectedDevices[0]);
		MainPanel.Device2.setText(connectedDevices[1]);
		MainPanel.Device3.setText(connectedDevices[2]);
		MainPanel.Device4.setText(connectedDevices[3]);
		MainPanel.Device5.setText(connectedDevices[4]);
		MainPanel.Device6.setText(connectedDevices[5]);
	}

}
