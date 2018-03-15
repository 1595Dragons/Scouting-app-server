package application;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Info {

	JFrame window;

	JLabel mac, output, fileLocaion, devices;
	static JLabel outputText = new JLabel("");
	static JLabel devicesList = new JLabel("None");
	
	static int deviceNumber = 0;

	GridBagConstraints layout;

	public boolean checkBT() {
		return LocalDevice.isPowerOn();
	}

	public void init() throws BluetoothStateException {
		if (checkBT()) {
			window = new JFrame("1595 Scouting App");
			layout = new GridBagConstraints();
			mac = new JLabel(String.format("Device MAC address: %s",
					LocalDevice.getLocalDevice().getBluetoothAddress().replaceAll("..(?!$)", "$0:").toUpperCase()));
			output = new JLabel("<html><br ><center>Output:</center></html>");
			fileLocaion = new JLabel(String.format("<html><br ><center>CSV file locaion: <br >%s</center></html>",
					new File(System.getProperty("user.dir") + "/scouting_data.csv").getPath()));
			devices = new JLabel();
			addComponents();
		} else {
			System.exit(0);
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
		this.layout.gridwidth = 6;
		this.layout.gridheight = 1;
		devicesList.setFont(new Font(null, Font.BOLD, 25));
		this.window.add(devicesList, this.layout);

		startGUI();

	}

	public void startGUI() {
		//this.window.pack();
		this.window.setSize(1058, 450); // 778, 1058

		this.window.setVisible(true);
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.window.setLocation((d.width / 2 - this.window.getSize().width / 2) - 500,
				(d.height / 2 - this.window.getSize().height / 2) - 300);

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
	
	public static void deviceConnect (String deviceName) {
		devicesList.setText(devicesList.getText().replace("None", ""));
		deviceNumber++;
		if (deviceNumber == 1) {
			devicesList.setText(deviceName);
		} else {
			devicesList.setText(String.format("%s, %s", devicesList.getText(), deviceName));
		}
		
	}
	
	public static void deviceDisconnect(String deviceName) {
		devicesList.setText(devicesList.getText().replace(deviceName, ""));
		if (devicesList.getText().startsWith(", ")) {
			devicesList.setText(devicesList.getText().replaceFirst(", ", ""));
		}
		deviceNumber--;
		if (deviceNumber == 0) {
			devicesList.setText("None");
		}
	}
	
	public static void writeToFileStandScouting(String data) {
		// Write the data to a CSV file
		log("Writing data to file", false);
		FileWriter fw = null;
		try {
			fw = new FileWriter(
					new File(System.getProperty("user.dir") + "/scouting_data.csv").getAbsolutePath(), true);
		} catch (IOException e1) {
			log(String.format("Error, cannot edit file: %s", e1.getMessage()), true);
			e1.printStackTrace();
			return;
		}
		boolean success = false;
		try {
			fw.append(System.getProperty("line.separator") + data);
			fw.flush();
			success = true;
		} catch (IOException e1) {
			log(String.format("Error, cannot write to file: %s", e1.getMessage()), true);
			e1.printStackTrace();
			success = false;
		}

		// Try closing the writer
		try {
			fw.close();
		} catch (IOException e1) {
			log(String.format("Error, cannot close stream: %s", e1.getMessage()), true);
			e1.printStackTrace();
			return;
		}
		
		
		// Report success!
		if (success) {
			log(String.format("Successfully written data for team: %s", data.split(",")[0]), false);
		} else {
			return;
		}
		
	}
	
	
	
}
