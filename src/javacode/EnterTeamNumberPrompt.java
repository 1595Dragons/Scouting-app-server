package javacode;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javacode.OldCore.Debugger;

public class EnterTeamNumberPrompt extends Thread {

	private JFrame frame;

	private JTextField teamNumberInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnterTeamNumberPrompt window = new EnterTeamNumberPrompt();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EnterTeamNumberPrompt() {
		initialize();
	}

	/**
	 * Show the application.
	 */
	public void showPromptWindow() {
		this.frame.setVisible(true);
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

		frame = new JFrame();

		frame.setTitle("Enter team to scout");
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setBounds(100, 100, 420, 325);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 140, 140, 140, 0 };
		gridBagLayout.rowHeights = new int[] { 70, 45, 69, 45, 70, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel teamToScoutHeader = new JLabel("Team to scout:");
		teamToScoutHeader.setForeground(Color.WHITE);
		teamToScoutHeader.setBackground(Color.WHITE);
		teamToScoutHeader.setFont(new Font("Arial Black", Font.PLAIN, 45));
		GridBagConstraints gbc_teamToScoutHeader = new GridBagConstraints();
		gbc_teamToScoutHeader.gridwidth = 3;
		gbc_teamToScoutHeader.insets = new Insets(0, 0, 5, 0);
		gbc_teamToScoutHeader.gridx = 0;
		gbc_teamToScoutHeader.gridy = 0;
		frame.getContentPane().add(teamToScoutHeader, gbc_teamToScoutHeader);

		teamNumberInput = new JTextField();
		teamNumberInput.setToolTipText("Enter team number to scout");
		teamNumberInput.setForeground(Color.WHITE);
		teamNumberInput.setBackground(Color.BLACK);
		teamNumberInput.setHorizontalAlignment(SwingConstants.CENTER);
		teamNumberInput.setFont(new Font("Arial", Font.PLAIN, 45));

		GridBagConstraints gbc_teamNumberInput = new GridBagConstraints();
		gbc_teamNumberInput.fill = GridBagConstraints.HORIZONTAL;
		gbc_teamNumberInput.gridwidth = 3;
		gbc_teamNumberInput.insets = new Insets(0, 0, 5, 0);
		gbc_teamNumberInput.gridx = 0;
		gbc_teamNumberInput.gridy = 2;
		frame.getContentPane().add(teamNumberInput, gbc_teamNumberInput);
		teamNumberInput.setColumns(10);

		JButton Cancel = new JButton("Cancel");
		Cancel.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_Cancel = new GridBagConstraints();
		gbc_Cancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_Cancel.insets = new Insets(0, 0, 0, 5);
		gbc_Cancel.gridx = 0;
		gbc_Cancel.gridy = 4;
		frame.getContentPane().add(Cancel, gbc_Cancel);

		JButton Start = new JButton("Start");
		Start.setFont(new Font("Arial Black", Font.PLAIN, 30));
		GridBagConstraints gbc_Start = new GridBagConstraints();
		gbc_Start.fill = GridBagConstraints.HORIZONTAL;
		gbc_Start.gridx = 2;
		gbc_Start.gridy = 4;
		frame.getContentPane().add(Start, gbc_Start);
		frame.getRootPane().setDefaultButton(Start);

		Cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				teamNumberInput.setText("");
			}
		});
		
		Start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				try {
					OldCore.teamNumber = Integer.parseInt(teamNumberInput.getText());
					teamNumberInput.setText("");
					AutoScouting autoScouting = new AutoScouting();
					autoScouting.showAutonomous();
				} catch (NumberFormatException InvalidNumber) {
					// Not a valid number (do nothing)
					frame.setVisible(true);
					Toolkit.getDefaultToolkit().beep();
					return;
				}
			}
		});

		OldCore.CenterWindowLocaiton(frame);
	}

}
