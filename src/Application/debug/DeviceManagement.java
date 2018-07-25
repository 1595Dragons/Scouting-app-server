package Application.debug;

public class DeviceManagement {

	public static String[] connectedDevices = new String[6];
	
	public static void deviceConnected(String deviceName) {

		for (int i = 0; i < connectedDevices.length; i++) {
			if (connectedDevices[i].equals("None")) {
				connectedDevices[i] = deviceName;
				break;
			}
		}
		InfoTest.Device1.setText(connectedDevices[0]);
		InfoTest.Device2.setText(connectedDevices[1]);
		InfoTest.Device3.setText(connectedDevices[2]);
		InfoTest.Device4.setText(connectedDevices[3]);
		InfoTest.Device5.setText(connectedDevices[4]);
		InfoTest.Device6.setText(connectedDevices[5]);

	}

	public static void deviceDisconnected(String deviceName) {
		for (int i = 0; i < connectedDevices.length; i++) {
			if (connectedDevices[i].equals(deviceName)) {
				connectedDevices[i] = "None";
				break;
			}
		}
		InfoTest.Device1.setText(connectedDevices[0]);
		InfoTest.Device2.setText(connectedDevices[1]);
		InfoTest.Device3.setText(connectedDevices[2]);
		InfoTest.Device4.setText(connectedDevices[3]);
		InfoTest.Device5.setText(connectedDevices[4]);
		InfoTest.Device6.setText(connectedDevices[5]);
	}
	
	public static void reset() {
		for (int i = 0; i < connectedDevices.length; i++) {
			connectedDevices[i] = "None";
		}
		InfoTest.Device1.setText(connectedDevices[0]);
		InfoTest.Device2.setText(connectedDevices[1]);
		InfoTest.Device3.setText(connectedDevices[2]);
		InfoTest.Device4.setText(connectedDevices[3]);
		InfoTest.Device5.setText(connectedDevices[4]);
		InfoTest.Device6.setText(connectedDevices[5]);
	}
	
}
