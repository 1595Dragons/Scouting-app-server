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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javacode.OldCore.Debugger;

public class Finalize {

	private JFrame ScoutingWindow;
	private JTextField CommentsField;
	private Component verticalStrut;
	private JLabel ReviewHeader, ReviewTeamNumber, ReviewHasAuto, ReviewSwitchAuto, ReviewScaleAuto, ReviewSwitch,
			ReviewScale, ReviewExchange, ReviewClimb;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Finalize window = new Finalize();
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
	public Finalize() {
		initialize();
	}

	/**
	 * Show the application.
	 */
	public void showFinalization() {
		ScoutingWindow.setVisible(true);
		ReviewClimb.setText(String.format("<html>Can this team climb:<br><center>%s</center></html>",
				String.valueOf(!OldCore.IntToBoolean(OldCore.DidntClimb))));
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
		ScoutingWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ScoutingWindow.getContentPane().setBackground(Color.BLACK);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 70, 70, 70, 70, 70, 70, 70, 70, 0 };
		gridBagLayout.rowHeights = new int[] { 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		ScoutingWindow.getContentPane().setLayout(gridBagLayout);

		JLabel CommentsHeader = new JLabel("Comments");
		CommentsHeader.setForeground(Color.WHITE);
		CommentsHeader.setFont(new Font("Arial Black", Font.PLAIN, 30));
		GridBagConstraints gbc_CommentsHeader = new GridBagConstraints();
		gbc_CommentsHeader.gridwidth = 8;
		gbc_CommentsHeader.insets = new Insets(0, 0, 5, 0);
		gbc_CommentsHeader.gridx = 0;
		gbc_CommentsHeader.gridy = 0;
		ScoutingWindow.getContentPane().add(CommentsHeader, gbc_CommentsHeader);

		CommentsField = new JTextField();
		CommentsField.setForeground(Color.WHITE);
		CommentsField.setFont(new Font("Arial", Font.PLAIN, 25));
		CommentsField.setBackground(Color.DARK_GRAY);
		GridBagConstraints gbc_CommentsField = new GridBagConstraints();
		gbc_CommentsField.gridheight = 3;
		gbc_CommentsField.gridwidth = 8;
		gbc_CommentsField.insets = new Insets(0, 0, 5, 0);
		gbc_CommentsField.fill = GridBagConstraints.BOTH;
		gbc_CommentsField.gridx = 0;
		gbc_CommentsField.gridy = 1;
		ScoutingWindow.getContentPane().add(CommentsField, gbc_CommentsField);
		CommentsField.setColumns(10);

		verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridwidth = 8;
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 4;
		ScoutingWindow.getContentPane().add(verticalStrut, gbc_verticalStrut);

		ReviewHeader = new JLabel("Review");
		ReviewHeader.setForeground(Color.WHITE);
		ReviewHeader.setFont(new Font("Arial Black", Font.PLAIN, 35));
		GridBagConstraints gbc_ReviewHeader = new GridBagConstraints();
		gbc_ReviewHeader.gridwidth = 8;
		gbc_ReviewHeader.insets = new Insets(0, 0, 5, 0);
		gbc_ReviewHeader.gridx = 0;
		gbc_ReviewHeader.gridy = 5;
		ScoutingWindow.getContentPane().add(ReviewHeader, gbc_ReviewHeader);

		ReviewTeamNumber = new JLabel("Team number: " + OldCore.teamNumber);
		ReviewTeamNumber.setFont(new Font("Arial", Font.PLAIN, 25));
		ReviewTeamNumber.setForeground(Color.WHITE);
		GridBagConstraints gbc_ReviewTeamNumber = new GridBagConstraints();
		gbc_ReviewTeamNumber.gridwidth = 4;
		gbc_ReviewTeamNumber.insets = new Insets(0, 0, 5, 5);
		gbc_ReviewTeamNumber.gridx = 0;
		gbc_ReviewTeamNumber.gridy = 6;
		ScoutingWindow.getContentPane().add(ReviewTeamNumber, gbc_ReviewTeamNumber);

		ReviewHasAuto = new JLabel("Has basic auto: " + String.valueOf(OldCore.IntToBoolean(OldCore.BasicAutoValue)));
		ReviewHasAuto.setFont(new Font("Arial", Font.PLAIN, 25));
		ReviewHasAuto.setForeground(Color.WHITE);
		GridBagConstraints gbc_ReviewHasAuto = new GridBagConstraints();
		gbc_ReviewHasAuto.gridwidth = 4;
		gbc_ReviewHasAuto.insets = new Insets(0, 0, 5, 5);
		gbc_ReviewHasAuto.gridx = 4;
		gbc_ReviewHasAuto.gridy = 6;
		ScoutingWindow.getContentPane().add(ReviewHasAuto, gbc_ReviewHasAuto);

		ReviewSwitchAuto = new JLabel("Has switch auto: " + String.valueOf(OldCore.IntToBoolean(OldCore.SwitchAutoValue)));
		ReviewSwitchAuto.setFont(new Font("Arial", Font.PLAIN, 25));
		ReviewSwitchAuto.setForeground(Color.WHITE);
		GridBagConstraints gbc_ReviewSwitchAuto = new GridBagConstraints();
		gbc_ReviewSwitchAuto.gridwidth = 4;
		gbc_ReviewSwitchAuto.insets = new Insets(0, 0, 5, 5);
		gbc_ReviewSwitchAuto.gridx = 0;
		gbc_ReviewSwitchAuto.gridy = 7;
		ScoutingWindow.getContentPane().add(ReviewSwitchAuto, gbc_ReviewSwitchAuto);

		ReviewScaleAuto = new JLabel("Has scale auto: " + String.valueOf(OldCore.IntToBoolean(OldCore.ScaleAutoValue)));
		ReviewScaleAuto.setFont(new Font("Arial", Font.PLAIN, 25));
		ReviewScaleAuto.setForeground(Color.WHITE);
		GridBagConstraints gbc_ReviewScaleAuto = new GridBagConstraints();
		gbc_ReviewScaleAuto.gridwidth = 4;
		gbc_ReviewScaleAuto.insets = new Insets(0, 0, 5, 5);
		gbc_ReviewScaleAuto.gridx = 4;
		gbc_ReviewScaleAuto.gridy = 7;
		ScoutingWindow.getContentPane().add(ReviewScaleAuto, gbc_ReviewScaleAuto);

		ReviewSwitch = new JLabel(String.format("<html>Total cubes placed<br><center>on switch: %s</center></html>",
				String.valueOf(OldCore.SwitchTeleValue)));
		ReviewSwitch.setFont(new Font("Arial", Font.PLAIN, 25));
		ReviewSwitch.setForeground(Color.WHITE);
		GridBagConstraints gbc_ReviewSwitch = new GridBagConstraints();
		gbc_ReviewSwitch.gridheight = 2;
		gbc_ReviewSwitch.gridwidth = 4;
		gbc_ReviewSwitch.insets = new Insets(0, 0, 5, 5);
		gbc_ReviewSwitch.gridx = 0;
		gbc_ReviewSwitch.gridy = 8;
		ScoutingWindow.getContentPane().add(ReviewSwitch, gbc_ReviewSwitch);

		ReviewScale = new JLabel(String.format("<html>Total cubes placed<br><center>on scale: %s</center></html>",
				String.valueOf(OldCore.ScaleTeleValue)));
		ReviewScale.setForeground(Color.WHITE);
		ReviewScale.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_ReviewScale = new GridBagConstraints();
		gbc_ReviewScale.gridheight = 2;
		gbc_ReviewScale.gridwidth = 4;
		gbc_ReviewScale.insets = new Insets(0, 0, 5, 5);
		gbc_ReviewScale.gridx = 4;
		gbc_ReviewScale.gridy = 8;
		ScoutingWindow.getContentPane().add(ReviewScale, gbc_ReviewScale);

		ReviewExchange = new JLabel(String.format("<html>Total cubes placed<br>\tin the exchange: %s</html>",
				String.valueOf(OldCore.ExchangeTeleValue)));
		ReviewExchange.setFont(new Font("Arial", Font.PLAIN, 25));
		ReviewExchange.setForeground(Color.WHITE);
		GridBagConstraints gbc_ReviewExchange = new GridBagConstraints();
		gbc_ReviewExchange.gridheight = 2;
		gbc_ReviewExchange.gridwidth = 4;
		gbc_ReviewExchange.insets = new Insets(0, 0, 5, 5);
		gbc_ReviewExchange.gridx = 0;
		gbc_ReviewExchange.gridy = 10;
		ScoutingWindow.getContentPane().add(ReviewExchange, gbc_ReviewExchange);

		ReviewClimb = new JLabel(String.format("<html>Can this team climb:<br><center>%s</center></html>",
				String.valueOf(!OldCore.IntToBoolean(OldCore.DidntClimb))));
		ReviewClimb.setFont(new Font("Arial", Font.PLAIN, 25));
		ReviewClimb.setForeground(Color.WHITE);
		GridBagConstraints gbc_ReviewClimb = new GridBagConstraints();
		gbc_ReviewClimb.gridheight = 2;
		gbc_ReviewClimb.gridwidth = 4;
		gbc_ReviewClimb.insets = new Insets(0, 0, 5, 5);
		gbc_ReviewClimb.gridx = 4;
		gbc_ReviewClimb.gridy = 10;
		ScoutingWindow.getContentPane().add(ReviewClimb, gbc_ReviewClimb);

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
				EndGame EGT = new EndGame();
				ScoutingWindow.setVisible(false);
				EGT.showEndGame();
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
				CommentsField.setText("");
				OldCore.reset();
				ScoutingWindow.setVisible(false);
			}
		});

		JButton Finish = new JButton("Finish");
		Finish.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_Finish = new GridBagConstraints();
		gbc_Finish.gridwidth = 2;
		gbc_Finish.gridx = 6;
		gbc_Finish.gridy = 15;
		ScoutingWindow.getContentPane().add(Finish, gbc_Finish);

		Finish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String data = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", OldCore.teamNumber,
						OldCore.BasicAutoValue, OldCore.SwitchAutoValue, OldCore.ScaleAutoValue, OldCore.SwitchTeleValue,
						OldCore.ScaleTeleValue, OldCore.ExchangeTeleValue, OldCore.SingleClimbSide, OldCore.SingleClimbCenter,
						OldCore.DoubleClimbSide, OldCore.DoubleClimbCenter, OldCore.TripleClimbSide, OldCore.TripleClimbCenter,
						OldCore.RampClimb, CommentsField.getText().replace(",", "."));
				Debugger.d(Finalize.class, "Data: " + data);
				ScoutingFile.writeToFile(data);
				OldCore.reset();
				CommentsField.setText("");
				ScoutingWindow.setVisible(false);
			}
		});

		ScoutingWindow.setTitle("1595 Scouting App");
		ScoutingWindow.setResizable(false);
		ScoutingWindow.setBounds(100, 100, 570, 800);

		ScoutingWindow.getRootPane().setDefaultButton(Finish);

		OldCore.CenterWindowLocaiton(ScoutingWindow);
	}

}
