package application;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Info {

	JFrame window;

	JPanel contentPane;

	JLabel MacAddressHeader = new JLabel("MAC Address: "), MacAddressText = new JLabel("----"),
			FileLocationHeader = new JLabel("File location: "), outputHeader = new JLabel("Output:"),
			ConnectedDevicesHeader = new JLabel("Connected devices:");

	String FileLocationText;

	static JLabel output = new JLabel("-"), Device1 = new JLabel("None"), Device2 = new JLabel("None"),
			Device3 = new JLabel("None"), Device4 = new JLabel("None"), Device5 = new JLabel("None"),
			Device6 = new JLabel("None");

	static String[] connectedDevices = new String[6];

	public boolean checkBT() {
		return LocalDevice.isPowerOn();
	}

	public void init() throws BluetoothStateException {
		if (checkBT()) {
			window = new JFrame("1595 Scouting App");
			this.MacAddressText.setText(
					LocalDevice.getLocalDevice().getBluetoothAddress().replaceAll("..(?!$)", "$0:").toUpperCase());
			this.FileLocationText = (new File(System.getProperty("user.dir") + "/").getAbsolutePath());
			for (int i = 0; i < 6; i++) {
				connectedDevices[i] = "None";
			}

			addComponents();
		} else {
			System.exit(0);
		}
	}

	public void addComponents() {
		this.window.setResizable(false);
		this.window.setTitle("1595 Scouting App");
		this.window.setForeground(Color.BLACK);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setBounds(100, 100, 1294, 650);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.window.setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 70,
				70, 0 };
		gbl_contentPane.rowHeights = new int[] { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		MacAddressHeader.setForeground(Color.WHITE);

		MacAddressHeader.setFont(new Font("Verdana", Font.PLAIN, 25));
		GridBagConstraints gbc_MacAddressHeader = new GridBagConstraints();
		gbc_MacAddressHeader.anchor = GridBagConstraints.EAST;
		gbc_MacAddressHeader.gridwidth = 8;
		gbc_MacAddressHeader.insets = new Insets(0, 0, 5, 5);
		gbc_MacAddressHeader.gridx = 0;
		gbc_MacAddressHeader.gridy = 0;
		contentPane.add(MacAddressHeader, gbc_MacAddressHeader);
		MacAddressText.setForeground(Color.WHITE);

		MacAddressText.setFont(new Font("Verdana", Font.BOLD, 22));
		GridBagConstraints gbc_MacAddressText = new GridBagConstraints();
		gbc_MacAddressText.anchor = GridBagConstraints.WEST;
		gbc_MacAddressText.gridwidth = 10;
		gbc_MacAddressText.insets = new Insets(0, 0, 5, 0);
		gbc_MacAddressText.gridx = 8;
		gbc_MacAddressText.gridy = 0;
		contentPane.add(MacAddressText, gbc_MacAddressText);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridwidth = 18;
		gbc_verticalStrut.fill = GridBagConstraints.HORIZONTAL;
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		contentPane.add(verticalStrut, gbc_verticalStrut);
		FileLocationHeader.setForeground(Color.WHITE);

		FileLocationHeader.setText(String.format("<html>%s <b>%s</b></html>", FileLocationHeader.getText(), FileLocationText));
		FileLocationHeader.setFont(new Font("Verdana", Font.PLAIN, 25));
		GridBagConstraints gbc_FileLocationHeader = new GridBagConstraints();
		gbc_FileLocationHeader.gridwidth = 18;
		gbc_FileLocationHeader.insets = new Insets(0, 0, 5, 5);
		gbc_FileLocationHeader.gridx = 0;
		gbc_FileLocationHeader.gridy = 2;
		contentPane.add(FileLocationHeader, gbc_FileLocationHeader);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.gridheight = 2;
		gbc_verticalStrut_2.gridwidth = 18;
		gbc_verticalStrut_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_2.gridx = 0;
		gbc_verticalStrut_2.gridy = 3;
		contentPane.add(verticalStrut_2, gbc_verticalStrut_2);
		outputHeader.setForeground(Color.WHITE);

		outputHeader.setFont(new Font("Verdana", Font.PLAIN, 25));
		GridBagConstraints gbc_outputHeader = new GridBagConstraints();
		gbc_outputHeader.gridwidth = 18;
		gbc_outputHeader.insets = new Insets(0, 0, 5, 0);
		gbc_outputHeader.gridx = 0;
		gbc_outputHeader.gridy = 5;
		contentPane.add(outputHeader, gbc_outputHeader);
		output.setForeground(Color.WHITE);

		output.setFont(new Font("Verdana", Font.PLAIN, 22));
		GridBagConstraints gbc_output = new GridBagConstraints();
		gbc_output.gridheight = 2;
		gbc_output.gridwidth = 18;
		gbc_output.insets = new Insets(0, 0, 5, 0);
		gbc_output.gridx = 0;
		gbc_output.gridy = 6;
		contentPane.add(output, gbc_output);

		Component verticalStrut_4 = Box.createVerticalStrut(20);
		verticalStrut_4.setForeground(Color.BLACK);
		GridBagConstraints gbc_verticalStrut_4 = new GridBagConstraints();
		gbc_verticalStrut_4.gridwidth = 18;
		gbc_verticalStrut_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_verticalStrut_4.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_4.gridx = 0;
		gbc_verticalStrut_4.gridy = 8;
		contentPane.add(verticalStrut_4, gbc_verticalStrut_4);
		ConnectedDevicesHeader.setForeground(Color.WHITE);

		ConnectedDevicesHeader.setFont(new Font("Verdana", Font.PLAIN, 25));
		GridBagConstraints gbc_ConnectedDevicesHeader = new GridBagConstraints();
		gbc_ConnectedDevicesHeader.gridwidth = 18;
		gbc_ConnectedDevicesHeader.insets = new Insets(0, 0, 5, 0);
		gbc_ConnectedDevicesHeader.gridx = 0;
		gbc_ConnectedDevicesHeader.gridy = 9;
		contentPane.add(ConnectedDevicesHeader, gbc_ConnectedDevicesHeader);

		Component verticalStrut_5 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_5 = new GridBagConstraints();
		gbc_verticalStrut_5.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_5.gridx = 2;
		gbc_verticalStrut_5.gridy = 10;
		contentPane.add(verticalStrut_5, gbc_verticalStrut_5);
		Device1.setForeground(Color.WHITE);

		Device1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_Device1 = new GridBagConstraints();
		gbc_Device1.gridheight = 2;
		gbc_Device1.gridwidth = 3;
		gbc_Device1.insets = new Insets(0, 0, 0, 5);
		gbc_Device1.gridx = 0;
		gbc_Device1.gridy = 11;
		contentPane.add(Device1, gbc_Device1);
		Device2.setForeground(Color.WHITE);

		Device2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_Device2 = new GridBagConstraints();
		gbc_Device2.gridheight = 2;
		gbc_Device2.gridwidth = 3;
		gbc_Device2.insets = new Insets(0, 0, 0, 5);
		gbc_Device2.gridx = 3;
		gbc_Device2.gridy = 11;
		contentPane.add(Device2, gbc_Device2);
		Device3.setForeground(Color.WHITE);

		Device3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_Device3 = new GridBagConstraints();
		gbc_Device3.gridheight = 2;
		gbc_Device3.gridwidth = 3;
		gbc_Device3.insets = new Insets(0, 0, 0, 5);
		gbc_Device3.gridx = 6;
		gbc_Device3.gridy = 11;
		contentPane.add(Device3, gbc_Device3);
		Device4.setForeground(Color.WHITE);

		Device4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_Device4 = new GridBagConstraints();
		gbc_Device4.gridheight = 2;
		gbc_Device4.gridwidth = 3;
		gbc_Device4.insets = new Insets(0, 0, 0, 5);
		gbc_Device4.gridx = 9;
		gbc_Device4.gridy = 11;
		contentPane.add(Device4, gbc_Device4);
		Device5.setForeground(Color.WHITE);

		Device5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_Device5 = new GridBagConstraints();
		gbc_Device5.gridheight = 2;
		gbc_Device5.gridwidth = 3;
		gbc_Device5.insets = new Insets(0, 0, 0, 5);
		gbc_Device5.gridx = 12;
		gbc_Device5.gridy = 11;
		contentPane.add(Device5, gbc_Device5);
		Device6.setForeground(Color.WHITE);

		Device6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_Device6 = new GridBagConstraints();
		gbc_Device6.gridheight = 2;
		gbc_Device6.gridwidth = 3;
		gbc_Device6.gridx = 15;
		gbc_Device6.gridy = 11;
		contentPane.add(Device6, gbc_Device6);

		startGUI();

	}

	public void startGUI() {
		this.window.setVisible(true);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.window.setLocation((d.width / 2 - this.window.getSize().width / 2) - 500,
				(d.height / 2 - this.window.getSize().height / 2) - 300);

		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void log(String message, boolean error) {
		// Info infoWindow = new Info();
		if (error) {
			output.setText(message);
			output.setForeground(Color.RED);
		} else {
			output.setText(message);
			output.setForeground(Color.WHITE);

		}
	}

	public static void deviceConnect(String deviceName) {

		for (int i = 0; i < 6; i++) {
			if (connectedDevices[i].equals("None")) {
				connectedDevices[i] = deviceName;
				break;
			}
		}
		Device1.setText(connectedDevices[0]);
		Device2.setText(connectedDevices[1]);
		Device3.setText(connectedDevices[2]);
		Device4.setText(connectedDevices[3]);
		Device5.setText(connectedDevices[4]);
		Device6.setText(connectedDevices[5]);

	}

	public static void deviceDisconnect(String deviceName) {
		for (int i = 0; i < 6; i++) {
			if (connectedDevices[i].equals(deviceName)) {
				connectedDevices[i] = "None";
				break;
			}
		}
		Device1.setText(connectedDevices[0]);
		Device2.setText(connectedDevices[1]);
		Device3.setText(connectedDevices[2]);
		Device4.setText(connectedDevices[3]);
		Device5.setText(connectedDevices[4]);
		Device6.setText(connectedDevices[5]);
	}

	public static void writeToFileStandScouting(String data) {
		// Write the data to a CSV file
		log("Writing data to file", false);
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(System.getProperty("user.dir") + "/scouting_data.csv").getAbsolutePath(),
					true);
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

	public static void writeToFilePitScouting(String data) {
		log("Writing data to file", false);
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(System.getProperty("user.dir") + "/pit_data.csv").getAbsolutePath(), true);
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
