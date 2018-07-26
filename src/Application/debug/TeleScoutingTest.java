package Application.debug;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import Application.Debugger;

public class TeleScoutingTest {

	private JFrame ScoutingWindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeleScoutingTest window = new TeleScoutingTest();
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
	public TeleScoutingTest() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			Debugger.d(InfoTest.class, e.getMessage());
			e.printStackTrace();
		}

		ScoutingWindow = new JFrame();
		ScoutingWindow.getContentPane().setBackground(Color.BLACK);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 70, 70, 70, 70, 70, 70, 70, 70, 0 };
		gridBagLayout.rowHeights = new int[] { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		ScoutingWindow.getContentPane().setLayout(gridBagLayout);
		
		JLabel ScoutingTeamText = new JLabel("Scouting team: " + EnterTeamNumberTest.teamNumber);
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
		
		
		
		ScoutingWindow.setTitle("1595 Scouting App");
		ScoutingWindow.setResizable(false);
		ScoutingWindow.setBounds(100, 100, 566, 630);

		ScoutingWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

}
