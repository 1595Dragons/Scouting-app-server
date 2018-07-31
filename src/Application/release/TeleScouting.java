package Application.release;

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
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Application.Debugger;

public class TeleScouting {

	private JFrame ScoutingWindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeleScouting window = new TeleScouting();
					window.ScoutingWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TeleScouting() {
		initialize();
	}

	/**
	 * Show the application.
	 */
	public void showTeleOp() {
		ScoutingWindow.setVisible(true);
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
			Debugger.d(MainPanel.class, e.getMessage());
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
		progressBar.setValue(2);
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

		JLabel TeleOperatedHeader = new JLabel("TeleOperated");
		TeleOperatedHeader.setForeground(Color.WHITE);
		TeleOperatedHeader.setFont(new Font("Arial Black", Font.PLAIN, 30));
		GridBagConstraints gbc_TeleOperatedHeader = new GridBagConstraints();
		gbc_TeleOperatedHeader.insets = new Insets(0, 0, 5, 0);
		gbc_TeleOperatedHeader.gridwidth = 8;
		gbc_TeleOperatedHeader.gridx = 0;
		gbc_TeleOperatedHeader.gridy = 5;
		ScoutingWindow.getContentPane().add(TeleOperatedHeader, gbc_TeleOperatedHeader);

		JLabel SwitchCubeHeader = new JLabel("Number of cubes placed on the switch:");
		SwitchCubeHeader.setFont(new Font("Arial", Font.PLAIN, 25));
		SwitchCubeHeader.setForeground(Color.WHITE);
		GridBagConstraints gbc_SwitchCubeHeader = new GridBagConstraints();
		gbc_SwitchCubeHeader.gridwidth = 8;
		gbc_SwitchCubeHeader.insets = new Insets(0, 0, 5, 0);
		gbc_SwitchCubeHeader.gridx = 0;
		gbc_SwitchCubeHeader.gridy = 6;
		ScoutingWindow.getContentPane().add(SwitchCubeHeader, gbc_SwitchCubeHeader);

		JSpinner SwitchCube = new JSpinner();
		SwitchCube.setFont(new Font("Arial", Font.PLAIN, 30));
		if (Core.IntToBoolean(Core.switchOffSet)) {
			SwitchCube.setModel(new SpinnerNumberModel(1, 1, 25, 1));
			if (Core.SwitchTeleValue != 0) {
				SwitchCube.setValue(Core.SwitchTeleValue);
			} else {
				SwitchCube.setValue(1);
			}
		} else {
			SwitchCube.setModel(new SpinnerNumberModel(0, 0, 25, 1));
			SwitchCube.setValue(Core.SwitchTeleValue);
		}

		GridBagConstraints gbc_SwitchCube = new GridBagConstraints();
		gbc_SwitchCube.gridwidth = 2;
		gbc_SwitchCube.insets = new Insets(0, 0, 5, 5);
		gbc_SwitchCube.gridx = 3;
		gbc_SwitchCube.gridy = 7;
		ScoutingWindow.getContentPane().add(SwitchCube, gbc_SwitchCube);

		JLabel ScaleCubeHeader = new JLabel("Number of cubes placed on the scale:");
		ScaleCubeHeader.setForeground(Color.WHITE);
		ScaleCubeHeader.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_ScaleCubeHeader = new GridBagConstraints();
		gbc_ScaleCubeHeader.gridwidth = 8;
		gbc_ScaleCubeHeader.insets = new Insets(0, 0, 5, 0);
		gbc_ScaleCubeHeader.gridx = 0;
		gbc_ScaleCubeHeader.gridy = 8;
		ScoutingWindow.getContentPane().add(ScaleCubeHeader, gbc_ScaleCubeHeader);

		JSpinner ScaleCube = new JSpinner();
		if (Core.IntToBoolean(Core.scaleOffset)) {
			ScaleCube.setModel(new SpinnerNumberModel(1, 1, 25, 1));
			if (Core.ScaleTeleValue != 0) {
				ScaleCube.setValue(Core.ScaleTeleValue);
			} else {
				ScaleCube.setValue(1);
			}
		} else {
			ScaleCube.setModel(new SpinnerNumberModel(0, 0, 25, 1));
			ScaleCube.setValue(Core.ScaleTeleValue);
		}
		ScaleCube.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_ScaleCube = new GridBagConstraints();
		gbc_ScaleCube.gridwidth = 2;
		gbc_ScaleCube.insets = new Insets(0, 0, 5, 5);
		gbc_ScaleCube.gridx = 3;
		gbc_ScaleCube.gridy = 9;
		ScoutingWindow.getContentPane().add(ScaleCube, gbc_ScaleCube);

		JLabel ExchangeCubeHeader = new JLabel("Number of cubes placed in the exchange:");
		ExchangeCubeHeader.setForeground(Color.WHITE);
		ExchangeCubeHeader.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_ExchangeCubeHeader = new GridBagConstraints();
		gbc_ExchangeCubeHeader.gridwidth = 8;
		gbc_ExchangeCubeHeader.insets = new Insets(0, 0, 5, 0);
		gbc_ExchangeCubeHeader.gridx = 0;
		gbc_ExchangeCubeHeader.gridy = 10;
		ScoutingWindow.getContentPane().add(ExchangeCubeHeader, gbc_ExchangeCubeHeader);

		JSpinner ExchangeCube = new JSpinner();
		ExchangeCube.setFont(new Font("Arial", Font.PLAIN, 30));
		ExchangeCube.setModel(new SpinnerNumberModel(0, 0, 25, 1));
		ExchangeCube.setValue(Core.ExchangeTeleValue);
		GridBagConstraints gbc_ExchangeCube = new GridBagConstraints();
		gbc_ExchangeCube.gridwidth = 2;
		gbc_ExchangeCube.insets = new Insets(0, 0, 5, 5);
		gbc_ExchangeCube.gridx = 3;
		gbc_ExchangeCube.gridy = 11;
		ScoutingWindow.getContentPane().add(ExchangeCube, gbc_ExchangeCube);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.gridheight = 3;
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_2.gridwidth = 8;
		gbc_verticalStrut_2.gridx = 0;
		gbc_verticalStrut_2.gridy = 12;
		ScoutingWindow.getContentPane().add(verticalStrut_2, gbc_verticalStrut_2);

		JButton Back = new JButton("Back");
		Back.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_Back = new GridBagConstraints();
		gbc_Back.gridwidth = 2;
		gbc_Back.insets = new Insets(0, 0, 0, 5);
		gbc_Back.gridx = 0;
		gbc_Back.gridy = 15;
		ScoutingWindow.getContentPane().add(Back, gbc_Back);

		Back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AutoScouting AST = new AutoScouting();
				ScoutingWindow.setVisible(false);
				AST.showAutonomous();
			}
		});

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
				Core.SwitchTeleValue = (int) SwitchCube.getValue();
				Core.ScaleTeleValue = (int) ScaleCube.getValue();
				Core.ExchangeTeleValue = (int) ExchangeCube.getValue();
				ScoutingWindow.setVisible(false);
				EndGame EGT = new EndGame();
				EGT.showEndGame();
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