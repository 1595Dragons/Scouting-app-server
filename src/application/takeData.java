package application;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class takeData extends Thread {

	JFrame window = new JFrame("1595 Scouting App"), promptWindow = new JFrame("1595 Scouting App");

	JTextField teamNumberText;

	int teamNumber = 0;

	public void run() {
		// init();
		init_promptNumber();
		init_collectData();
		showPrompt();

	}

	public void init_promptNumber() {
		this.promptWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.promptWindow.setBounds(100, 100, 426, 280);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 130, 130, 130, 0 };
		gridBagLayout.rowHeights = new int[] { 56, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		this.promptWindow.getContentPane().setLayout(gridBagLayout);

		JLabel teamToScoutHeader = new JLabel("Team to scout:");
		teamToScoutHeader.setFont(new Font("Tahoma", Font.PLAIN, 55));
		GridBagConstraints gbc_teamToScoutHeader = new GridBagConstraints();
		gbc_teamToScoutHeader.gridwidth = 3;
		gbc_teamToScoutHeader.insets = new Insets(0, 0, 5, 5);
		gbc_teamToScoutHeader.gridx = 0;
		gbc_teamToScoutHeader.gridy = 0;
		this.promptWindow.getContentPane().add(teamToScoutHeader, gbc_teamToScoutHeader);

		teamNumberText = new JTextField();
		teamNumberText.setHorizontalAlignment(SwingConstants.CENTER);
		teamNumberText.setFont(new Font("Tahoma", Font.PLAIN, 45));
		GridBagConstraints gbc_teamNumber = new GridBagConstraints();
		gbc_teamNumber.gridwidth = 3;
		gbc_teamNumber.insets = new Insets(0, 0, 5, 0);
		gbc_teamNumber.gridx = 0;
		gbc_teamNumber.gridy = 1;
		this.promptWindow.getContentPane().add(this.teamNumberText, gbc_teamNumber);
		this.teamNumberText.setColumns(10);

		JButton Cancel = new JButton("Cancel");
		Cancel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		GridBagConstraints gbc_Cancel = new GridBagConstraints();
		gbc_Cancel.insets = new Insets(0, 0, 0, 5);
		gbc_Cancel.gridx = 0;
		gbc_Cancel.gridy = 3;
		this.promptWindow.getContentPane().add(Cancel, gbc_Cancel);

		Cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				takeData.this.promptWindow.setVisible(false);
			}

		});

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut_2.gridx = 1;
		gbc_horizontalStrut_2.gridy = 3;
		this.promptWindow.getContentPane().add(horizontalStrut_2, gbc_horizontalStrut_2);

		JButton Start = new JButton("Start");
		Start.setFont(new Font("Tahoma", Font.BOLD, 35));
		GridBagConstraints gbc_Start = new GridBagConstraints();
		gbc_Start.gridx = 2;
		gbc_Start.gridy = 3;
		this.promptWindow.getContentPane().add(Start, gbc_Start);

		Start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					takeData.this.teamNumber = Integer.parseInt(takeData.this.teamNumberText.getText());
					takeData.this.promptWindow.setVisible(false);
					takeData.this.showWindow();
					takeData.this.teamNumberText.setText("");
					teamToScoutHeader.setForeground(Color.BLACK);
				} catch (Exception e) {
					teamToScoutHeader.setForeground(Color.RED);
				}

			}

		});
	}

	public void init_collectData() {
		this.window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.window.setBounds(100, 100, 720, 1200);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 460, 230, 0 };
		gridBagLayout.rowHeights = new int[] { 56, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		this.window.getContentPane().setLayout(gridBagLayout);

		JLabel ScoutingTeamHeader = new JLabel("Scouting team: ");
		ScoutingTeamHeader.setFont(new Font("Verdana", Font.PLAIN, 50));
		GridBagConstraints gbc_ScoutingTeamHeader = new GridBagConstraints();
		gbc_ScoutingTeamHeader.anchor = GridBagConstraints.EAST;
		gbc_ScoutingTeamHeader.insets = new Insets(0, 0, 5, 5);
		gbc_ScoutingTeamHeader.gridx = 0;
		gbc_ScoutingTeamHeader.gridy = 0;
		this.window.getContentPane().add(ScoutingTeamHeader, gbc_ScoutingTeamHeader);

		JLabel teamToScoutText = new JLabel("0000");
		teamToScoutText.setHorizontalAlignment(SwingConstants.CENTER);
		teamToScoutText.setFont(new Font("Verdana", Font.BOLD, 45));
		GridBagConstraints gbc_teamToScoutText = new GridBagConstraints();
		gbc_teamToScoutText.anchor = GridBagConstraints.WEST;
		gbc_teamToScoutText.insets = new Insets(0, 0, 5, 0);
		gbc_teamToScoutText.gridx = 1;
		gbc_teamToScoutText.gridy = 0;
		this.window.getContentPane().add(teamToScoutText, gbc_teamToScoutText);
		teamToScoutText.setText(String.valueOf(this.teamNumber));

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridheight = 2;
		gbc_verticalStrut.gridwidth = 2;
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		this.window.getContentPane().add(verticalStrut, gbc_verticalStrut);

		JLabel AutoHeader = new JLabel("Autonomous");
		AutoHeader.setFont(new Font("Verdana", Font.PLAIN, 40));
		GridBagConstraints gbc_AutoHeader = new GridBagConstraints();
		gbc_AutoHeader.gridwidth = 2;
		gbc_AutoHeader.insets = new Insets(0, 0, 5, 0);
		gbc_AutoHeader.gridx = 0;
		gbc_AutoHeader.gridy = 3;
		this.window.getContentPane().add(AutoHeader, gbc_AutoHeader);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.gridwidth = 2;
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 4;
		this.window.getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);

		JCheckBox hasAuto = new JCheckBox("This team has an auto");
		hasAuto.setFont(new Font("Verdana", Font.PLAIN, 30));
		GridBagConstraints gbc_hasAuto = new GridBagConstraints();
		gbc_hasAuto.gridwidth = 2;
		gbc_hasAuto.insets = new Insets(0, 0, 5, 0);
		gbc_hasAuto.gridx = 0;
		gbc_hasAuto.gridy = 5;
		this.window.getContentPane().add(hasAuto, gbc_hasAuto);

		JCheckBox switchAuto = new JCheckBox("Team can place cube on switch");
		switchAuto.setFont(new Font("Verdana", Font.PLAIN, 30));
		GridBagConstraints gbc_switchAuto = new GridBagConstraints();
		gbc_switchAuto.gridwidth = 2;
		gbc_switchAuto.insets = new Insets(0, 0, 5, 0);
		gbc_switchAuto.gridx = 0;
		gbc_switchAuto.gridy = 6;
		this.window.getContentPane().add(switchAuto, gbc_switchAuto);

		JCheckBox scaleAuto = new JCheckBox("Team can place cube on scale");
		scaleAuto.setFont(new Font("Verdana", Font.PLAIN, 30));
		GridBagConstraints gbc_scaleAuto = new GridBagConstraints();
		gbc_scaleAuto.gridwidth = 2;
		gbc_scaleAuto.insets = new Insets(0, 0, 5, 0);
		gbc_scaleAuto.gridx = 0;
		gbc_scaleAuto.gridy = 7;
		this.window.getContentPane().add(scaleAuto, gbc_scaleAuto);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.gridwidth = 2;
		gbc_verticalStrut_2.gridheight = 2;
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_2.gridx = 0;
		gbc_verticalStrut_2.gridy = 8;
		this.window.getContentPane().add(verticalStrut_2, gbc_verticalStrut_2);

		JLabel teleOpHeader = new JLabel("TeleOp");
		teleOpHeader.setFont(new Font("Tahoma", Font.PLAIN, 40));
		GridBagConstraints gbc_teleOpHeader = new GridBagConstraints();
		gbc_teleOpHeader.gridwidth = 2;
		gbc_teleOpHeader.insets = new Insets(0, 0, 5, 0);
		gbc_teleOpHeader.gridx = 0;
		gbc_teleOpHeader.gridy = 10;
		this.window.getContentPane().add(teleOpHeader, gbc_teleOpHeader);

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.gridwidth = 2;
		gbc_verticalStrut_3.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_3.gridx = 0;
		gbc_verticalStrut_3.gridy = 11;
		this.window.getContentPane().add(verticalStrut_3, gbc_verticalStrut_3);

		JLabel teleSwitchTele = new JLabel("Number of cubes placed on switch");
		teleSwitchTele.setFont(new Font("Verdana", Font.PLAIN, 30));
		GridBagConstraints gbc_teleSwitchTele = new GridBagConstraints();
		gbc_teleSwitchTele.gridwidth = 2;
		gbc_teleSwitchTele.insets = new Insets(0, 0, 5, 0);
		gbc_teleSwitchTele.gridx = 0;
		gbc_teleSwitchTele.gridy = 12;
		this.window.getContentPane().add(teleSwitchTele, gbc_teleSwitchTele);

		JSpinner teleSwitchValue = new JSpinner();
		teleSwitchValue.setFont(new Font("Verdana", Font.PLAIN, 30));
		GridBagConstraints gbc_teleSwitchValue = new GridBagConstraints();
		gbc_teleSwitchValue.gridwidth = 2;
		gbc_teleSwitchValue.insets = new Insets(0, 0, 5, 0);
		gbc_teleSwitchValue.gridx = 0;
		gbc_teleSwitchValue.gridy = 13;
		this.window.getContentPane().add(teleSwitchValue, gbc_teleSwitchValue);

		Component verticalStrut_4 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_4 = new GridBagConstraints();
		gbc_verticalStrut_4.gridwidth = 2;
		gbc_verticalStrut_4.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_4.gridx = 0;
		gbc_verticalStrut_4.gridy = 14;
		this.window.getContentPane().add(verticalStrut_4, gbc_verticalStrut_4);

		JLabel teleScaleTele = new JLabel("Number of cubes placed on scale");
		teleScaleTele.setFont(new Font("Verdana", Font.PLAIN, 30));
		GridBagConstraints gbc_teleScaleTele = new GridBagConstraints();
		gbc_teleScaleTele.gridwidth = 2;
		gbc_teleScaleTele.insets = new Insets(0, 0, 5, 0);
		gbc_teleScaleTele.gridx = 0;
		gbc_teleScaleTele.gridy = 15;
		this.window.getContentPane().add(teleScaleTele, gbc_teleScaleTele);

		JSpinner teleScaleValue = new JSpinner();
		teleScaleValue.setFont(new Font("Verdana", Font.PLAIN, 30));
		GridBagConstraints gbc_teleScaleValue = new GridBagConstraints();
		gbc_teleScaleValue.gridwidth = 2;
		gbc_teleScaleValue.insets = new Insets(0, 0, 5, 0);
		gbc_teleScaleValue.gridx = 0;
		gbc_teleScaleValue.gridy = 16;
		this.window.getContentPane().add(teleScaleValue, gbc_teleScaleValue);

		Component verticalStrut_5 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_5 = new GridBagConstraints();
		gbc_verticalStrut_5.gridwidth = 2;
		gbc_verticalStrut_5.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_5.gridx = 0;
		gbc_verticalStrut_5.gridy = 17;
		this.window.getContentPane().add(verticalStrut_5, gbc_verticalStrut_5);

		JLabel teleExchangeHeader = new JLabel("Number of cubes placed in exchange");
		teleExchangeHeader.setFont(new Font("Verdana", Font.PLAIN, 30));
		GridBagConstraints gbc_teleExchangeHeader = new GridBagConstraints();
		gbc_teleExchangeHeader.gridwidth = 2;
		gbc_teleExchangeHeader.insets = new Insets(0, 0, 5, 0);
		gbc_teleExchangeHeader.gridx = 0;
		gbc_teleExchangeHeader.gridy = 18;
		this.window.getContentPane().add(teleExchangeHeader, gbc_teleExchangeHeader);

		JSpinner teleExchangeValue = new JSpinner();
		teleExchangeValue.setFont(new Font("Verdana", Font.PLAIN, 30));
		GridBagConstraints gbc_teleExchangeValue = new GridBagConstraints();
		gbc_teleExchangeValue.gridwidth = 2;
		gbc_teleExchangeValue.insets = new Insets(0, 0, 5, 0);
		gbc_teleExchangeValue.gridx = 0;
		gbc_teleExchangeValue.gridy = 19;
		this.window.getContentPane().add(teleExchangeValue, gbc_teleExchangeValue);

		Component verticalStrut_6 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_6 = new GridBagConstraints();
		gbc_verticalStrut_6.gridheight = 2;
		gbc_verticalStrut_6.gridwidth = 2;
		gbc_verticalStrut_6.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_6.gridx = 0;
		gbc_verticalStrut_6.gridy = 20;
		this.window.getContentPane().add(verticalStrut_6, gbc_verticalStrut_6);

		JLabel ClimbingHeader = new JLabel("Climbing");
		ClimbingHeader.setFont(new Font("Verdana", Font.PLAIN, 40));
		GridBagConstraints gbc_ClimbingHeader = new GridBagConstraints();
		gbc_ClimbingHeader.insets = new Insets(0, 0, 5, 0);
		gbc_ClimbingHeader.gridwidth = 2;
		gbc_ClimbingHeader.gridx = 0;
		gbc_ClimbingHeader.gridy = 22;
		this.window.getContentPane().add(ClimbingHeader, gbc_ClimbingHeader);

		Component verticalStrut_7 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_7 = new GridBagConstraints();
		gbc_verticalStrut_7.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_7.gridwidth = 2;
		gbc_verticalStrut_7.gridx = 0;
		gbc_verticalStrut_7.gridy = 23;
		this.window.getContentPane().add(verticalStrut_7, gbc_verticalStrut_7);

		JRadioButton noClimb = new JRadioButton("This bot failed to climb");
		noClimb.setFont(new Font("Verdana", Font.PLAIN, 30));
		GridBagConstraints gbc_noClimb = new GridBagConstraints();
		gbc_noClimb.insets = new Insets(0, 0, 5, 0);
		gbc_noClimb.gridwidth = 2;
		gbc_noClimb.gridx = 0;
		gbc_noClimb.gridy = 24;
		this.window.getContentPane().add(noClimb, gbc_noClimb);

		JRadioButton oneClimb = new JRadioButton("This bot caused one bot to climb");
		oneClimb.setFont(new Font("Verdana", Font.PLAIN, 30));
		GridBagConstraints gbc_oneClimb = new GridBagConstraints();
		gbc_oneClimb.insets = new Insets(0, 0, 5, 0);
		gbc_oneClimb.gridwidth = 2;
		gbc_oneClimb.gridx = 0;
		gbc_oneClimb.gridy = 25;
		this.window.getContentPane().add(oneClimb, gbc_oneClimb);

		JRadioButton twoClimb = new JRadioButton("This bot caused two bots to climb");
		twoClimb.setFont(new Font("Verdana", Font.PLAIN, 30));
		GridBagConstraints gbc_twoClimb = new GridBagConstraints();
		gbc_twoClimb.insets = new Insets(0, 0, 5, 0);
		gbc_twoClimb.gridwidth = 2;
		gbc_twoClimb.gridx = 0;
		gbc_twoClimb.gridy = 26;
		this.window.getContentPane().add(twoClimb, gbc_twoClimb);

		JRadioButton allClimb = new JRadioButton("All bots climbed");
		allClimb.setFont(new Font("Verdana", Font.PLAIN, 30));
		GridBagConstraints gbc_allClimb = new GridBagConstraints();
		gbc_allClimb.insets = new Insets(0, 0, 5, 0);
		gbc_allClimb.gridwidth = 2;
		gbc_allClimb.gridx = 0;
		gbc_allClimb.gridy = 27;
		this.window.getContentPane().add(allClimb, gbc_allClimb);

		Component verticalStrut_8 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_8 = new GridBagConstraints();
		gbc_verticalStrut_8.gridheight = 2;
		gbc_verticalStrut_8.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_8.gridwidth = 2;
		gbc_verticalStrut_8.gridx = 0;
		gbc_verticalStrut_8.gridy = 28;
		this.window.getContentPane().add(verticalStrut_8, gbc_verticalStrut_8);

		JButton Cancel = new JButton("Cancel\r\n");
		Cancel.setFont(new Font("Verdana", Font.PLAIN, 25));
		GridBagConstraints gbc_Cancel = new GridBagConstraints();
		gbc_Cancel.anchor = GridBagConstraints.WEST;
		gbc_Cancel.insets = new Insets(0, 0, 0, 5);
		gbc_Cancel.gridx = 0;
		gbc_Cancel.gridy = 30;
		this.window.getContentPane().add(Cancel, gbc_Cancel);

		JButton Submit = new JButton("Submit");
		Submit.setFont(new Font("Verdana", Font.BOLD, 25));
		GridBagConstraints gbc_Submit = new GridBagConstraints();
		gbc_Submit.anchor = GridBagConstraints.EAST;
		gbc_Submit.gridx = 1;
		gbc_Submit.gridy = 30;
		this.window.getContentPane().add(Submit, gbc_Submit);

		ButtonGroup climberType = new ButtonGroup();
		climberType.add(noClimb);
		climberType.add(oneClimb);
		climberType.add(twoClimb);
		climberType.add(allClimb);

		Cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				takeData.this.window.setVisible(false);
				hasAuto.setSelected(false);
				switchAuto.setSelected(false);
				scaleAuto.setSelected(false);
				teleSwitchValue.setValue(0);
				teleScaleValue.setValue(0);
				teleExchangeValue.setValue(0);
				climberType.clearSelection();
				takeData.this.showPrompt();
			}

		});

		Submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				takeData.this.window.setVisible(false);
				String data = "";
				int climbValue = 0;
				if (noClimb.isSelected()) {
					climbValue = 0;
				} else if (oneClimb.isSelected()) {
					climbValue = 1;
				} else if (twoClimb.isSelected()) {
					climbValue = 2;
				} else if (allClimb.isSelected()) {
					climbValue = 3;
				}
				data = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",
                        takeData.this.teamNumber,
                        (byte) (hasAuto.isSelected() ? 1 : 0),
                        (byte) (switchAuto.isSelected() ? 1 : 0),
                        (byte) (scaleAuto.isSelected() ? 1 : 0),
                        (byte) teleSwitchValue.getValue() + (byte) (switchAuto.isSelected() ? 1 : 0),
                        (byte) teleScaleValue.getValue() + (byte) (scaleAuto.isSelected() ? 1 : 0),
                        (byte) teleExchangeValue.getValue(), climbValue);
				
				Info.writeToFileStandScouting(data);
				
				hasAuto.setSelected(false);
				switchAuto.setSelected(false);
				scaleAuto.setSelected(false);
				teleSwitchValue.setValue(0);
				teleScaleValue.setValue(0);
				teleExchangeValue.setValue(0);
				climberType.clearSelection();
				takeData.this.showPrompt();

			}

		});

	}

	public void showPrompt() {
		this.teamNumber = 0;
		this.promptWindow.setVisible(true);
	}

	public void showWindow() {
		this.window.setVisible(true);
	}

}
