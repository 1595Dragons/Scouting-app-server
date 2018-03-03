package application;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Info {

	JFrame window;

	JLabel mac, output, fileLocaion, devices, deviceListT;
	static JLabel outputText = new JLabel("");

	int deviceHeight;

	GridBagConstraints layout;

	public boolean checkBT() {
		return LocalDevice.isPowerOn();
	}

	public void init() throws BluetoothStateException {
		if (checkBT()) {
			window = new JFrame("1595 Scouting App Version 1.6");
			layout = new GridBagConstraints();
			mac = new JLabel(String.format("Device MAC address: %s",
					LocalDevice.getLocalDevice().getBluetoothAddress().replaceAll("..(?!$)", "$0:").toUpperCase()));
			output = new JLabel("<html><br ><center>Output:</center></html>");
			fileLocaion = new JLabel(String.format("<html><br ><center>CSV file locaion: <br >%s</center></html>",
					new File(System.getProperty("user.dir") + "/scouting_data.csv").getPath()));
			devices = new JLabel();
			deviceListT = new JLabel();
			getDevices();
			addComponents();
		} else {
			System.exit(0);
		}
	}

	public void getDevices() {
		String[] device;
		boolean canGetDevices = false;
		try {
			this.deviceHeight = LocalDevice.getLocalDevice().getDiscoveryAgent()
					.retrieveDevices(DiscoveryAgent.PREKNOWN).length;
			device = new String[this.deviceHeight];
			canGetDevices = true;
		} catch (BluetoothStateException e) {
			device = new String[2];
			device[0] = "Error: Cannot get devices!";
			device[1] = e.getMessage();
			canGetDevices = false;
		}
		if (canGetDevices) {
			for (int i = 0; i < this.deviceHeight; i++) {
				try {
					device[i] = (LocalDevice.getLocalDevice().getDiscoveryAgent()
							.retrieveDevices(DiscoveryAgent.PREKNOWN))[i].getFriendlyName(false);
				} catch (BluetoothStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.devices.setText("<html><br ><center>List of devices:<br ></html>");
			this.deviceListT.setText(Arrays.toString(device).replace("[", "").replace("]", ""));
		} else {
			this.devices.setText(String.format("<html><center>%s</center></html>", Arrays.toString(device).replace("[", "").replace("]", "")));
		}

	}

	public void addComponents() {
		this.window.setLayout(new GridBagLayout());
		this.layout.weighty = 1;
		this.layout.anchor = GridBagConstraints.NORTH;
		this.layout.ipady = 5;

		this.layout.gridx = 0;
		this.layout.gridy = 0;
		this.layout.gridwidth = 1;
		this.layout.gridheight = 1;
		this.mac.setFont(new Font(null, Font.BOLD, 25));
		this.window.add(this.mac, this.layout);

		this.layout.gridx = 0;
		this.layout.gridy = 1;
		this.layout.gridwidth = 1;
		this.layout.gridheight = 2;
		this.fileLocaion.setFont(new Font(null, Font.BOLD, 25));
		this.window.add(this.fileLocaion, this.layout);

		this.layout.gridx = 0;
		this.layout.gridy = 3;
		this.layout.gridwidth = 1;
		this.layout.gridheight = 1;
		this.output.setFont(new Font(null, Font.BOLD, 25));
		this.window.add(this.output, this.layout);

		this.layout.gridx = 0;
		this.layout.gridy = 4;
		this.layout.gridwidth = 1;
		this.layout.gridheight = 1;
		outputText.setFont(new Font(null, Font.BOLD, 25));
		this.window.add(outputText, this.layout);

		this.layout.gridx = 0;
		this.layout.gridy = 5;
		this.layout.gridwidth = 1;
		this.layout.gridheight = 1;
		this.devices.setFont(new Font(null, Font.BOLD, 25));
		this.window.add(this.devices, this.layout);
		
		this.layout.gridx = 0;
		this.layout.gridy = 6;
		this.layout.gridwidth = this.deviceHeight;
		this.layout.gridheight = 1;
		this.deviceListT.setFont(new Font(null, Font.BOLD, 25));
		this.window.add(this.deviceListT, this.layout);

		startGUI();

	}

	public void startGUI() {
		//this.window.pack();
		this.window.setSize(1058, 450); // 778, 1058

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.window.setLocation((d.width / 2 - this.window.getSize().width / 2) - 500,
				(d.height / 2 - this.window.getSize().height / 2) - 300);

		this.window.setVisible(true);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	
	public static void log(String message, boolean error) {
		//Info infoWindow = new Info();
		if (error) {
			outputText.setText(message);
			outputText.setForeground(Color.RED);
		} else {
			outputText.setText(message);
			outputText.setForeground(Color.BLACK);
			
		}
	}

}
