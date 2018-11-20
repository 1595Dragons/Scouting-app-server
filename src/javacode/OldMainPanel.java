package javacode;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javacode.Debugger;

public class OldMainPanel {

	private JFrame ScoutingAppWindow;

	public static JLabel MACAddress, FileLocation, ConsoleText, Device1, Device2, Device3, Device4, Device5, Device6;

	public static JButton GoToFileButton, StartScoutingButton;

	private EnterTeamNumberPrompt enterTeamPrompt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OldMainPanel window = new OldMainPanel();
					window.ScoutingAppWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OldMainPanel() {
		initialize();
		Debugger.d(OldMainPanel.class, "Creating the prompt window");
		enterTeamPrompt = new EnterTeamNumberPrompt();
	}

	/**
	 * Show the application.
	 */
	public void showWindow() {
		this.ScoutingAppWindow.setVisible(true);
		this.ScoutingAppWindow.getRootPane().setDefaultButton(StartScoutingButton);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (UnsupportedLookAndFeelException notWindows) {
			JFrame.setDefaultLookAndFeelDecorated(true);
		} catch (Exception e) {
				Debugger.d(OldMainPanel.class, e.getMessage());
				e.printStackTrace();
		}

		ScoutingAppWindow = new JFrame();
		ScoutingAppWindow.getContentPane().setBackground(Color.BLACK);
		ScoutingAppWindow.setFont(new Font("Arial Black", Font.PLAIN, 16));
		ScoutingAppWindow.setResizable(false);
		ScoutingAppWindow.setTitle("1595 Scouting App");
		ScoutingAppWindow.setBounds(100, 100, 1260, 630);
		ScoutingAppWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 75, 70, 70, 70, 70, 70, 69, 70,
				0 };
		gridBagLayout.rowHeights = new int[] { 46, 45, 45, 45, 45, 0, 45, 44, 45, 45, 45, 45, 43, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		ScoutingAppWindow.getContentPane().setLayout(gridBagLayout);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridwidth = 8;
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 0;
		ScoutingAppWindow.getContentPane().add(verticalStrut, gbc_verticalStrut);

		MACAddress = new JLabel("MAC Address:  --:--:--:--:--:--");
		MACAddress.setForeground(Color.WHITE);
		MACAddress.setBackground(Color.BLACK);
		MACAddress.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_MACAddress = new GridBagConstraints();
		gbc_MACAddress.gridwidth = 18;
		gbc_MACAddress.insets = new Insets(0, 0, 5, 0);
		gbc_MACAddress.gridx = 0;
		gbc_MACAddress.gridy = 1;
		ScoutingAppWindow.getContentPane().add(MACAddress, gbc_MACAddress);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridwidth = 8;
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 2;
		ScoutingAppWindow.getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);

		FileLocation = new JLabel("Data Location: -");
		FileLocation.setForeground(Color.WHITE);
		FileLocation.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_FileLocation = new GridBagConstraints();
		gbc_FileLocation.gridwidth = 16;
		gbc_FileLocation.insets = new Insets(0, 0, 5, 5);
		gbc_FileLocation.gridx = 0;
		gbc_FileLocation.gridy = 3;
		ScoutingAppWindow.getContentPane().add(FileLocation, gbc_FileLocation);

		GoToFileButton = new JButton("Go to");
		GoToFileButton.setToolTipText("View the file location in explorer");
		GoToFileButton.setEnabled(false);
		GoToFileButton.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_GoToFileButton = new GridBagConstraints();
		gbc_GoToFileButton.insets = new Insets(0, 0, 5, 0);
		gbc_GoToFileButton.gridwidth = 2;
		gbc_GoToFileButton.gridx = 16;
		gbc_GoToFileButton.gridy = 3;
		ScoutingAppWindow.getContentPane().add(GoToFileButton, gbc_GoToFileButton);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_2.gridwidth = 10;
		gbc_verticalStrut_2.gridx = 0;
		gbc_verticalStrut_2.gridy = 4;
		ScoutingAppWindow.getContentPane().add(verticalStrut_2, gbc_verticalStrut_2);

		JLabel ConsoleHeader = new JLabel("Console");
		ConsoleHeader.setForeground(Color.WHITE);
		ConsoleHeader.setFont(new Font("Arial Black", Font.PLAIN, 30));
		GridBagConstraints gbc_ConsoleHeader = new GridBagConstraints();
		gbc_ConsoleHeader.insets = new Insets(0, 0, 5, 0);
		gbc_ConsoleHeader.gridwidth = 18;
		gbc_ConsoleHeader.gridx = 0;
		gbc_ConsoleHeader.gridy = 5;
		ScoutingAppWindow.getContentPane().add(ConsoleHeader, gbc_ConsoleHeader);

		ConsoleText = new JLabel("-");
		ConsoleText.setForeground(Color.LIGHT_GRAY);
		ConsoleText.setFont(new Font("Monospaced", Font.PLAIN, 25));
		GridBagConstraints gbc_ConsoleText = new GridBagConstraints();
		gbc_ConsoleText.anchor = GridBagConstraints.NORTH;
		gbc_ConsoleText.gridheight = 2;
		gbc_ConsoleText.insets = new Insets(0, 0, 5, 0);
		gbc_ConsoleText.gridwidth = 18;
		gbc_ConsoleText.gridx = 0;
		gbc_ConsoleText.gridy = 6;
		ScoutingAppWindow.getContentPane().add(ConsoleText, gbc_ConsoleText);

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_3.gridx = 0;
		gbc_verticalStrut_3.gridy = 8;
		ScoutingAppWindow.getContentPane().add(verticalStrut_3, gbc_verticalStrut_3);

		JLabel ConnectedDevicesHeader = new JLabel("Connected Devices");
		ConnectedDevicesHeader.setForeground(Color.WHITE);
		ConnectedDevicesHeader.setFont(new Font("Arial Black", Font.PLAIN, 30));
		GridBagConstraints gbc_ConnectedDevicesHeader = new GridBagConstraints();
		gbc_ConnectedDevicesHeader.insets = new Insets(0, 0, 5, 0);
		gbc_ConnectedDevicesHeader.gridwidth = 18;
		gbc_ConnectedDevicesHeader.gridx = 0;
		gbc_ConnectedDevicesHeader.gridy = 9;
		ScoutingAppWindow.getContentPane().add(ConnectedDevicesHeader, gbc_ConnectedDevicesHeader);

		Device1 = new JLabel("None");
		Device1.setForeground(Color.WHITE);
		Device1.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_Device1 = new GridBagConstraints();
		gbc_Device1.gridwidth = 3;
		gbc_Device1.insets = new Insets(0, 0, 5, 5);
		gbc_Device1.gridx = 0;
		gbc_Device1.gridy = 10;
		ScoutingAppWindow.getContentPane().add(Device1, gbc_Device1);

		Device2 = new JLabel("None");
		Device2.setForeground(Color.WHITE);
		Device2.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_Device2 = new GridBagConstraints();
		gbc_Device2.gridwidth = 3;
		gbc_Device2.insets = new Insets(0, 0, 5, 5);
		gbc_Device2.gridx = 3;
		gbc_Device2.gridy = 10;
		ScoutingAppWindow.getContentPane().add(Device2, gbc_Device2);

		Device3 = new JLabel("None");
		Device3.setForeground(Color.WHITE);
		Device3.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_Device3 = new GridBagConstraints();
		gbc_Device3.gridwidth = 3;
		gbc_Device3.insets = new Insets(0, 0, 5, 5);
		gbc_Device3.gridx = 6;
		gbc_Device3.gridy = 10;
		ScoutingAppWindow.getContentPane().add(Device3, gbc_Device3);

		Device4 = new JLabel("None");
		Device4.setForeground(Color.WHITE);
		Device4.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_Device4 = new GridBagConstraints();
		gbc_Device4.gridwidth = 3;
		gbc_Device4.insets = new Insets(0, 0, 5, 5);
		gbc_Device4.gridx = 9;
		gbc_Device4.gridy = 10;
		ScoutingAppWindow.getContentPane().add(Device4, gbc_Device4);

		Device5 = new JLabel("None");
		Device5.setForeground(Color.WHITE);
		Device5.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_Device5 = new GridBagConstraints();
		gbc_Device5.gridwidth = 3;
		gbc_Device5.insets = new Insets(0, 0, 5, 5);
		gbc_Device5.gridx = 12;
		gbc_Device5.gridy = 10;
		ScoutingAppWindow.getContentPane().add(Device5, gbc_Device5);

		Device6 = new JLabel("None");
		Device6.setForeground(Color.WHITE);
		Device6.setFont(new Font("Arial", Font.PLAIN, 20));
		GridBagConstraints gbc_Device6 = new GridBagConstraints();
		gbc_Device6.gridwidth = 3;
		gbc_Device6.insets = new Insets(0, 0, 5, 0);
		gbc_Device6.gridx = 15;
		gbc_Device6.gridy = 10;
		ScoutingAppWindow.getContentPane().add(Device6, gbc_Device6);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_4 = new GridBagConstraints();
		gbc_verticalStrut_4.gridwidth = 12;
		gbc_verticalStrut_4.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_4.gridx = 0;
		gbc_verticalStrut_4.gridy = 11;
		ScoutingAppWindow.getContentPane().add(verticalStrut_4, gbc_verticalStrut_4);

		StartScoutingButton = new JButton("Start Scouting");
		
		StartScoutingButton.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_StartScoutingButton = new GridBagConstraints();
		gbc_StartScoutingButton.insets = new Insets(0, 0, 0, 5);
		gbc_StartScoutingButton.gridwidth = 18;
		gbc_StartScoutingButton.gridx = 0;
		gbc_StartScoutingButton.gridy = 12;
		ScoutingAppWindow.getContentPane().add(StartScoutingButton, gbc_StartScoutingButton);

		StartScoutingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Core.reset();
				enterTeamPrompt.showPromptWindow();
			}
		});

	}

}