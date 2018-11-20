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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AutoScouting {

	private JFrame ScoutingWindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AutoScouting window = new AutoScouting();
					window.ScoutingWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Show the application.
	 */
	public void showAutonomous() {
		ScoutingWindow.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public AutoScouting() {
		initialize();
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

		ScoutingWindow = new JFrame();
		ScoutingWindow.getContentPane().setBackground(Color.BLACK);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 70, 70, 70, 70, 70, 70, 70, 70, 0 };
		gridBagLayout.rowHeights = new int[] { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		ScoutingWindow.getContentPane().setLayout(gridBagLayout);

		JLabel ScoutingTeamText = new JLabel("Scouting team: " + Core.teamNumber);
		ScoutingTeamText.setForeground(Color.WHITE);
		ScoutingTeamText.setFont(new Font("Arial Black", Font.PLAIN, 40));
		GridBagConstraints gbc_ScoutingTeamText = new GridBagConstraints();
		gbc_ScoutingTeamText.gridwidth = 8;
		gbc_ScoutingTeamText.insets = new Insets(0, 0, 5, 0);
		gbc_ScoutingTeamText.gridx = 0;
		gbc_ScoutingTeamText.gridy = 0;
		ScoutingWindow.getContentPane().add(ScoutingTeamText, gbc_ScoutingTeamText);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.gridwidth = 6;
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 1;
		ScoutingWindow.getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);

		JLabel ProgressHeader = new JLabel("Progress");
		ProgressHeader.setForeground(new Color(255, 255, 255));
		ProgressHeader.setFont(new Font("Arial Black", Font.PLAIN, 35));
		GridBagConstraints gbc_ProgressHeader = new GridBagConstraints();
		gbc_ProgressHeader.gridwidth = 8;
		gbc_ProgressHeader.insets = new Insets(0, 0, 5, 0);
		gbc_ProgressHeader.gridx = 0;
		gbc_ProgressHeader.gridy = 2;
		ScoutingWindow.getContentPane().add(ProgressHeader, gbc_ProgressHeader);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBackground(Color.WHITE);
		progressBar.setMaximum(3);
		progressBar.setValue(1);
		progressBar.setFont(new Font("Arial Black", Font.PLAIN, 35));
		progressBar.setForeground(Color.RED);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.insets = new Insets(0, 0, 5, 5);
		gbc_progressBar.gridwidth = 6;
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = 3;
		ScoutingWindow.getContentPane().add(progressBar, gbc_progressBar);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridwidth = 4;
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 4;
		ScoutingWindow.getContentPane().add(verticalStrut, gbc_verticalStrut);

		JLabel AutonomousHeader = new JLabel("Autonomous");
		AutonomousHeader.setForeground(Color.WHITE);
		AutonomousHeader.setFont(new Font("Arial Black", Font.PLAIN, 30));
		GridBagConstraints gbc_AutonomousHeader = new GridBagConstraints();
		gbc_AutonomousHeader.insets = new Insets(0, 0, 5, 0);
		gbc_AutonomousHeader.gridwidth = 8;
		gbc_AutonomousHeader.gridx = 0;
		gbc_AutonomousHeader.gridy = 5;
		ScoutingWindow.getContentPane().add(AutonomousHeader, gbc_AutonomousHeader);

		JCheckBox BasicAuto = new JCheckBox("This team can cross the baseline");
		BasicAuto.setSelected(Core.IntToBoolean(Core.BasicAutoValue));
		BasicAuto.setBackground(Color.BLACK);
		BasicAuto.setForeground(Color.WHITE);
		BasicAuto.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_BasicAuto = new GridBagConstraints();
		gbc_BasicAuto.anchor = GridBagConstraints.WEST;
		gbc_BasicAuto.insets = new Insets(0, 0, 5, 0);
		gbc_BasicAuto.gridwidth = 7;
		gbc_BasicAuto.gridx = 1;
		gbc_BasicAuto.gridy = 6;
		ScoutingWindow.getContentPane().add(BasicAuto, gbc_BasicAuto);

		JCheckBox SwitchAuto = new JCheckBox("This team can place on the switch");
		SwitchAuto.setSelected(Core.IntToBoolean(Core.SwitchAutoValue));
		SwitchAuto.setForeground(Color.WHITE);
		SwitchAuto.setBackground(Color.BLACK);
		SwitchAuto.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_SwitchAuto = new GridBagConstraints();
		gbc_SwitchAuto.anchor = GridBagConstraints.WEST;
		gbc_SwitchAuto.insets = new Insets(0, 0, 5, 0);
		gbc_SwitchAuto.gridwidth = 7;
		gbc_SwitchAuto.gridx = 1;
		gbc_SwitchAuto.gridy = 7;
		ScoutingWindow.getContentPane().add(SwitchAuto, gbc_SwitchAuto);

		JCheckBox ScaleAuto = new JCheckBox("This team can place on the scale");
		ScaleAuto.setSelected(Core.IntToBoolean(Core.ScaleAutoValue));
		ScaleAuto.setForeground(Color.WHITE);
		ScaleAuto.setBackground(Color.BLACK);
		ScaleAuto.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_ScaleAuto = new GridBagConstraints();
		gbc_ScaleAuto.anchor = GridBagConstraints.WEST;
		gbc_ScaleAuto.insets = new Insets(0, 0, 5, 0);
		gbc_ScaleAuto.gridwidth = 7;
		gbc_ScaleAuto.gridx = 1;
		gbc_ScaleAuto.gridy = 8;
		ScoutingWindow.getContentPane().add(ScaleAuto, gbc_ScaleAuto);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.gridheight = 6;
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_2.gridwidth = 8;
		gbc_verticalStrut_2.gridx = 0;
		gbc_verticalStrut_2.gridy = 9;
		ScoutingWindow.getContentPane().add(verticalStrut_2, gbc_verticalStrut_2);

		JButton fakeBack = new JButton("Back");
		fakeBack.setFont(new Font("Arial", Font.PLAIN, 25));
		fakeBack.setEnabled(false);
		GridBagConstraints gbc_fakeBack = new GridBagConstraints();
		gbc_fakeBack.gridwidth = 2;
		gbc_fakeBack.insets = new Insets(0, 0, 0, 5);
		gbc_fakeBack.gridx = 0;
		gbc_fakeBack.gridy = 15;
		ScoutingWindow.getContentPane().add(fakeBack, gbc_fakeBack);

		JButton Cancel = new JButton("Cancel");
		Cancel.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_Cancel = new GridBagConstraints();
		gbc_Cancel.gridwidth = 2;
		gbc_Cancel.insets = new Insets(0, 0, 0, 5);
		gbc_Cancel.gridx = 3;
		gbc_Cancel.gridy = 15;
		ScoutingWindow.getContentPane().add(Cancel, gbc_Cancel);
		Cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Debugger.d(getClass(), "Canceling scouting");
				Core.reset();
				ScoutingWindow.setVisible(false);
			}
		});

		JButton Next = new JButton("Next");
		Next.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_Next = new GridBagConstraints();
		gbc_Next.gridwidth = 2;
		gbc_Next.gridx = 6;
		gbc_Next.gridy = 15;
		ScoutingWindow.getContentPane().add(Next, gbc_Next);
		Next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Debugger.d(getClass(), "Basic auto value: " + Core.BooleanToInt(BasicAuto.isSelected()));
				Core.BasicAutoValue = Core.BooleanToInt(BasicAuto.isSelected());

				Debugger.d(getClass(), "Switch auto value: " + Core.BooleanToInt(SwitchAuto.isSelected()));
				Core.SwitchAutoValue = Core.BooleanToInt(SwitchAuto.isSelected());
				
				Debugger.d(getClass(), "Switch offset: " + Core.SwitchAutoValue);
				Core.switchOffSet = Core.SwitchAutoValue;
				
				Debugger.d(getClass(), "Scale auto value: " + Core.BooleanToInt(ScaleAuto.isSelected()));
				Core.ScaleAutoValue = Core.BooleanToInt(ScaleAuto.isSelected());
				
				Debugger.d(getClass(), "Scale offset: " + Core.ScaleAutoValue);
				Core.scaleOffset = Core.ScaleAutoValue;
				
				ScoutingWindow.setVisible(false);
				TeleScouting TST = new TeleScouting();
				TST.showTeleOp();
			}
		});

		ScoutingWindow.setTitle("1595 Scouting App");
		ScoutingWindow.setResizable(false);
		ScoutingWindow.setBounds(100, 100, 570, 800);

		ScoutingWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ScoutingWindow.getRootPane().setDefaultButton(Next);

		Core.CenterWindowLocaiton(ScoutingWindow);
	}

}
