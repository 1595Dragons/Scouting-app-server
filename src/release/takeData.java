package release;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class takeData extends Thread {

	JFrame window = new JFrame("1595 Scouting App"), promptWindow = new JFrame("1595 Scouting App");

	JTextField teamNumberText;

	JLabel teamToScoutText;

	int teamNumber = 0;

	public void run() {
		init_promptNumber();
		init_collectData();
		showPrompt();

	}

	public void init_promptNumber() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.promptWindow.setTitle("Enter team to scout");
		this.promptWindow.setResizable(false);
		this.promptWindow.getContentPane().setBackground(Color.BLACK);
		this.promptWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.promptWindow.setBounds(100, 100, 411, 289);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 130, 130, 130, 0 };
		gridBagLayout.rowHeights = new int[] { 56, 30, 0, 30, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		this.promptWindow.getContentPane().setLayout(gridBagLayout);

		JLabel teamToScoutHeader = new JLabel("Team to scout:");
		teamToScoutHeader.setForeground(Color.WHITE);
		teamToScoutHeader.setBackground(Color.WHITE);
		teamToScoutHeader.setFont(new Font("Verdana", Font.PLAIN, 50));
		GridBagConstraints gbc_teamToScoutHeader = new GridBagConstraints();
		gbc_teamToScoutHeader.gridwidth = 3;
		gbc_teamToScoutHeader.insets = new Insets(0, 0, 5, 0);
		gbc_teamToScoutHeader.gridx = 0;
		gbc_teamToScoutHeader.gridy = 0;
		this.promptWindow.getContentPane().add(teamToScoutHeader, gbc_teamToScoutHeader);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridwidth = 3;
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		this.promptWindow.getContentPane().add(verticalStrut, gbc_verticalStrut);

		teamNumberText = new JTextField();
		teamNumberText.setToolTipText("Enter team number to scout");
		teamNumberText.setForeground(Color.WHITE);
		teamNumberText.setBackground(Color.BLACK);
		teamNumberText.setHorizontalAlignment(SwingConstants.CENTER);
		teamNumberText.setFont(new Font("Tahoma", Font.PLAIN, 45));

		GridBagConstraints gbc_teamNumber = new GridBagConstraints();
		gbc_teamNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_teamNumber.gridwidth = 3;
		gbc_teamNumber.insets = new Insets(0, 0, 5, 0);
		gbc_teamNumber.gridx = 0;
		gbc_teamNumber.gridy = 2;
		this.promptWindow.getContentPane().add(this.teamNumberText, gbc_teamNumber);
		this.teamNumberText.setColumns(10);

		JButton Cancel = new JButton("Cancel");
		Cancel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		GridBagConstraints gbc_Cancel = new GridBagConstraints();
		gbc_Cancel.anchor = GridBagConstraints.WEST;
		gbc_Cancel.insets = new Insets(0, 0, 0, 5);
		gbc_Cancel.gridx = 0;
		gbc_Cancel.gridy = 4;
		this.promptWindow.getContentPane().add(Cancel, gbc_Cancel);

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut_2.gridx = 1;
		gbc_horizontalStrut_2.gridy = 4;
		this.promptWindow.getContentPane().add(horizontalStrut_2, gbc_horizontalStrut_2);

		JButton Start = new JButton("Start");
		Start.setFont(new Font("Tahoma", Font.BOLD, 35));
		GridBagConstraints gbc_Start = new GridBagConstraints();
		gbc_Start.anchor = GridBagConstraints.EAST;
		gbc_Start.gridx = 2;
		gbc_Start.gridy = 4;
		this.promptWindow.getContentPane().add(Start, gbc_Start);
		this.promptWindow.getRootPane().setDefaultButton(Start);

		Cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				takeData.this.promptWindow.setState(Frame.ICONIFIED);
				Info.log("Dialog box has been minimized to taskbar", false);
			}

		});

		Start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					takeData.this.teamNumber = Integer.parseInt(takeData.this.teamNumberText.getText());
					takeData.this.promptWindow.setVisible(false);
					takeData.this.teamToScoutText.setText(String.valueOf(takeData.this.teamNumber));
					takeData.this.showWindow();
					takeData.this.teamNumberText.setText("");
				} catch (Exception e) {
					return;
				}

			}

		});
		
		this.promptWindow.pack();
		
	}

	public void init_collectData() {
		this.window.setResizable(false);
		this.window.getContentPane().setBackground(Color.BLACK);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.window.setBounds(100, 100, 1259, 1321);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 300, 300, 15, 300, 269, 0 };
		gridBagLayout.rowHeights = new int[] { 56, 34, 0, 25, 0, 0, 0, 35, 0, 30, 0, 0, 15, 0, 0, 15, 0, 0, 30, 0, 30,
				0, 0, 0, 0, 30, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		this.window.getContentPane().setLayout(gridBagLayout);

		ButtonGroup climberType = new ButtonGroup();

		JLabel ScoutingTeamHeader = new JLabel("Scouting team: ");
		ScoutingTeamHeader.setForeground(Color.WHITE);
		ScoutingTeamHeader.setBackground(Color.WHITE);
		ScoutingTeamHeader.setFont(new Font("Verdana", Font.PLAIN, 45));
		GridBagConstraints gbc_ScoutingTeamHeader = new GridBagConstraints();
		gbc_ScoutingTeamHeader.gridwidth = 2;
		gbc_ScoutingTeamHeader.anchor = GridBagConstraints.EAST;
		gbc_ScoutingTeamHeader.insets = new Insets(0, 0, 5, 5);
		gbc_ScoutingTeamHeader.gridx = 0;
		gbc_ScoutingTeamHeader.gridy = 0;
		this.window.getContentPane().add(ScoutingTeamHeader, gbc_ScoutingTeamHeader);

		teamToScoutText = new JLabel("----");
		teamToScoutText.setForeground(Color.WHITE);
		teamToScoutText.setHorizontalAlignment(SwingConstants.CENTER);
		teamToScoutText.setFont(new Font("Verdana", Font.BOLD, 40));
		GridBagConstraints gbc_teamToScoutText = new GridBagConstraints();
		gbc_teamToScoutText.gridwidth = 3;
		gbc_teamToScoutText.anchor = GridBagConstraints.WEST;
		gbc_teamToScoutText.insets = new Insets(0, 0, 5, 0);
		gbc_teamToScoutText.gridx = 2;
		gbc_teamToScoutText.gridy = 0;
		this.window.getContentPane().add(teamToScoutText, gbc_teamToScoutText);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridwidth = 5;
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		this.window.getContentPane().add(verticalStrut, gbc_verticalStrut);

		JLabel AutoHeader = new JLabel("Autonomous");
		AutoHeader.setForeground(Color.WHITE);
		AutoHeader.setFont(new Font("Verdana", Font.PLAIN, 35));
		GridBagConstraints gbc_AutoHeader = new GridBagConstraints();
		gbc_AutoHeader.gridwidth = 5;
		gbc_AutoHeader.insets = new Insets(0, 0, 5, 0);
		gbc_AutoHeader.gridx = 0;
		gbc_AutoHeader.gridy = 2;
		this.window.getContentPane().add(AutoHeader, gbc_AutoHeader);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.gridwidth = 5;
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 3;
		this.window.getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);

		JCheckBox hasAuto = new JCheckBox("This team has an auto");
		hasAuto.setBackground(Color.BLACK);
		hasAuto.setForeground(Color.WHITE);
		hasAuto.setFont(new Font("Verdana", Font.PLAIN, 25));
		GridBagConstraints gbc_hasAuto = new GridBagConstraints();
		gbc_hasAuto.gridwidth = 5;
		gbc_hasAuto.insets = new Insets(0, 0, 5, 0);
		gbc_hasAuto.gridx = 0;
		gbc_hasAuto.gridy = 4;
		this.window.getContentPane().add(hasAuto, gbc_hasAuto);

		JCheckBox switchAuto = new JCheckBox("Team can place cube on switch");
		switchAuto.setBackground(Color.BLACK);
		switchAuto.setForeground(Color.WHITE);
		switchAuto.setFont(new Font("Verdana", Font.PLAIN, 25));
		GridBagConstraints gbc_switchAuto = new GridBagConstraints();
		gbc_switchAuto.gridwidth = 5;
		gbc_switchAuto.insets = new Insets(0, 0, 5, 0);
		gbc_switchAuto.gridx = 0;
		gbc_switchAuto.gridy = 5;
		this.window.getContentPane().add(switchAuto, gbc_switchAuto);

		JCheckBox scaleAuto = new JCheckBox("Team can place cube on scale");
		scaleAuto.setBackground(Color.BLACK);
		scaleAuto.setForeground(Color.WHITE);
		scaleAuto.setFont(new Font("Verdana", Font.PLAIN, 25));
		GridBagConstraints gbc_scaleAuto = new GridBagConstraints();
		gbc_scaleAuto.gridwidth = 5;
		gbc_scaleAuto.insets = new Insets(0, 0, 5, 0);
		gbc_scaleAuto.gridx = 0;
		gbc_scaleAuto.gridy = 6;
		this.window.getContentPane().add(scaleAuto, gbc_scaleAuto);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.gridwidth = 5;
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_2.gridx = 0;
		gbc_verticalStrut_2.gridy = 7;
		this.window.getContentPane().add(verticalStrut_2, gbc_verticalStrut_2);

		JLabel teleOpHeader = new JLabel("TeleOp");
		teleOpHeader.setForeground(Color.WHITE);
		teleOpHeader.setFont(new Font("Tahoma", Font.PLAIN, 35));
		GridBagConstraints gbc_teleOpHeader = new GridBagConstraints();
		gbc_teleOpHeader.gridwidth = 5;
		gbc_teleOpHeader.insets = new Insets(0, 0, 5, 0);
		gbc_teleOpHeader.gridx = 0;
		gbc_teleOpHeader.gridy = 8;
		this.window.getContentPane().add(teleOpHeader, gbc_teleOpHeader);

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.gridwidth = 5;
		gbc_verticalStrut_3.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_3.gridx = 0;
		gbc_verticalStrut_3.gridy = 9;
		this.window.getContentPane().add(verticalStrut_3, gbc_verticalStrut_3);

		JLabel teleSwitchTele = new JLabel("Number of cubes placed on switch");
		teleSwitchTele.setForeground(Color.WHITE);
		teleSwitchTele.setFont(new Font("Verdana", Font.PLAIN, 25));
		GridBagConstraints gbc_teleSwitchTele = new GridBagConstraints();
		gbc_teleSwitchTele.gridwidth = 5;
		gbc_teleSwitchTele.insets = new Insets(0, 0, 5, 0);
		gbc_teleSwitchTele.gridx = 0;
		gbc_teleSwitchTele.gridy = 10;
		this.window.getContentPane().add(teleSwitchTele, gbc_teleSwitchTele);

		SpinnerNumberModel mod1 = new SpinnerNumberModel(0, 0, 25, 1);

		JSpinner teleSwitchValue = new JSpinner(mod1);
		teleSwitchValue.setFont(new Font("Verdana", Font.PLAIN, 25));
		Dimension d = teleSwitchValue.getPreferredSize();
		d.width = 60;
		teleSwitchValue.setPreferredSize(d);
		GridBagConstraints gbc_teleSwitchValue = new GridBagConstraints();
		gbc_teleSwitchValue.gridwidth = 5;
		gbc_teleSwitchValue.insets = new Insets(0, 0, 5, 0);
		gbc_teleSwitchValue.gridx = 0;
		gbc_teleSwitchValue.gridy = 11;
		this.window.getContentPane().add(teleSwitchValue, gbc_teleSwitchValue);

		Component verticalStrut_4 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_4 = new GridBagConstraints();
		gbc_verticalStrut_4.gridwidth = 5;
		gbc_verticalStrut_4.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_4.gridx = 0;
		gbc_verticalStrut_4.gridy = 12;
		this.window.getContentPane().add(verticalStrut_4, gbc_verticalStrut_4);

		JLabel teleScaleTele = new JLabel("Number of cubes placed on scale");
		teleScaleTele.setForeground(Color.WHITE);
		teleScaleTele.setFont(new Font("Verdana", Font.PLAIN, 25));
		GridBagConstraints gbc_teleScaleTele = new GridBagConstraints();
		gbc_teleScaleTele.gridwidth = 5;
		gbc_teleScaleTele.insets = new Insets(0, 0, 5, 0);
		gbc_teleScaleTele.gridx = 0;
		gbc_teleScaleTele.gridy = 13;
		this.window.getContentPane().add(teleScaleTele, gbc_teleScaleTele);

		SpinnerNumberModel mod2 = new SpinnerNumberModel(0, 0, 25, 1);
		JSpinner teleScaleValue = new JSpinner(mod2);
		teleScaleValue.setFont(new Font("Verdana", Font.PLAIN, 25));
		GridBagConstraints gbc_teleScaleValue = new GridBagConstraints();
		teleScaleValue.setPreferredSize(d);
		gbc_teleScaleValue.gridwidth = 5;
		gbc_teleScaleValue.insets = new Insets(0, 0, 5, 0);
		gbc_teleScaleValue.gridx = 0;
		gbc_teleScaleValue.gridy = 14;
		this.window.getContentPane().add(teleScaleValue, gbc_teleScaleValue);

		Component verticalStrut_5 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_5 = new GridBagConstraints();
		gbc_verticalStrut_5.gridwidth = 5;
		gbc_verticalStrut_5.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_5.gridx = 0;
		gbc_verticalStrut_5.gridy = 15;
		this.window.getContentPane().add(verticalStrut_5, gbc_verticalStrut_5);

		JLabel teleExchangeHeader = new JLabel("Number of cubes placed in exchange");
		teleExchangeHeader.setForeground(Color.WHITE);
		teleExchangeHeader.setFont(new Font("Verdana", Font.PLAIN, 25));
		GridBagConstraints gbc_teleExchangeHeader = new GridBagConstraints();
		gbc_teleExchangeHeader.gridwidth = 5;
		gbc_teleExchangeHeader.insets = new Insets(0, 0, 5, 0);
		gbc_teleExchangeHeader.gridx = 0;
		gbc_teleExchangeHeader.gridy = 16;
		this.window.getContentPane().add(teleExchangeHeader, gbc_teleExchangeHeader);

		SpinnerNumberModel mod3 = new SpinnerNumberModel(0, 0, 25, 1);
		JSpinner teleExchangeValue = new JSpinner(mod3);
		teleExchangeValue.setFont(new Font("Verdana", Font.PLAIN, 25));
		GridBagConstraints gbc_teleExchangeValue = new GridBagConstraints();
		gbc_teleExchangeValue.gridwidth = 5;
		teleExchangeValue.setPreferredSize(d);
		gbc_teleExchangeValue.insets = new Insets(0, 0, 5, 0);
		gbc_teleExchangeValue.gridx = 0;
		gbc_teleExchangeValue.gridy = 17;
		this.window.getContentPane().add(teleExchangeValue, gbc_teleExchangeValue);

		Component verticalStrut_6 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_6 = new GridBagConstraints();
		gbc_verticalStrut_6.gridwidth = 5;
		gbc_verticalStrut_6.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_6.gridx = 0;
		gbc_verticalStrut_6.gridy = 18;
		this.window.getContentPane().add(verticalStrut_6, gbc_verticalStrut_6);

		JLabel ClimbingHeader = new JLabel("Climbing");
		ClimbingHeader.setForeground(Color.WHITE);
		ClimbingHeader.setFont(new Font("Verdana", Font.PLAIN, 35));
		GridBagConstraints gbc_ClimbingHeader = new GridBagConstraints();
		gbc_ClimbingHeader.insets = new Insets(0, 0, 5, 0);
		gbc_ClimbingHeader.gridwidth = 5;
		gbc_ClimbingHeader.gridx = 0;
		gbc_ClimbingHeader.gridy = 19;
		this.window.getContentPane().add(ClimbingHeader, gbc_ClimbingHeader);

		Component verticalStrut_7 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_7 = new GridBagConstraints();
		gbc_verticalStrut_7.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_7.gridwidth = 5;
		gbc_verticalStrut_7.gridx = 0;
		gbc_verticalStrut_7.gridy = 20;
		this.window.getContentPane().add(verticalStrut_7, gbc_verticalStrut_7);

		JRadioButton noClimb = new JRadioButton("This bot failed to climb");
		noClimb.setBackground(Color.BLACK);
		noClimb.setForeground(Color.WHITE);
		noClimb.setFont(new Font("Verdana", Font.PLAIN, 20));
		GridBagConstraints gbc_noClimb = new GridBagConstraints();
		gbc_noClimb.anchor = GridBagConstraints.WEST;
		gbc_noClimb.insets = new Insets(0, 0, 5, 5);
		gbc_noClimb.gridwidth = 2;
		gbc_noClimb.gridx = 0;
		gbc_noClimb.gridy = 21;
		this.window.getContentPane().add(noClimb, gbc_noClimb);
		climberType.add(noClimb);

		Component verticalStrut_9 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_9 = new GridBagConstraints();
		gbc_verticalStrut_9.gridheight = 4;
		gbc_verticalStrut_9.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_9.gridx = 2;
		gbc_verticalStrut_9.gridy = 21;
		this.window.getContentPane().add(verticalStrut_9, gbc_verticalStrut_9);

		JRadioButton climbRamp = new JRadioButton(
				"<html>This bot is a ramp bot (Lifts 2 others bot but not<br >themselves)</html>");
		climbRamp.setBackground(Color.BLACK);
		climbRamp.setForeground(Color.WHITE);
		climbRamp.setFont(new Font("Verdana", Font.PLAIN, 20));
		GridBagConstraints gbc_climbRamp = new GridBagConstraints();
		gbc_climbRamp.anchor = GridBagConstraints.WEST;
		gbc_climbRamp.gridwidth = 2;
		gbc_climbRamp.insets = new Insets(0, 0, 5, 0);
		gbc_climbRamp.gridx = 3;
		gbc_climbRamp.gridy = 21;
		this.window.getContentPane().add(climbRamp, gbc_climbRamp);
		climberType.add(climbRamp);

		JRadioButton oneSideClimb = new JRadioButton(
				"This bot did a single climb on the side of the bar");
		oneSideClimb.setForeground(Color.WHITE);
		oneSideClimb.setBackground(Color.BLACK);
		oneSideClimb.setFont(new Font("Verdana", Font.PLAIN, 20));
		GridBagConstraints gbc_oneSideClimb = new GridBagConstraints();
		gbc_oneSideClimb.anchor = GridBagConstraints.WEST;
		gbc_oneSideClimb.insets = new Insets(0, 0, 5, 5);
		gbc_oneSideClimb.gridwidth = 2;
		gbc_oneSideClimb.gridx = 0;
		gbc_oneSideClimb.gridy = 22;
		this.window.getContentPane().add(oneSideClimb, gbc_oneSideClimb);
		climberType.add(oneSideClimb);

		JRadioButton oneCenterClimb = new JRadioButton(
				"This bot did a single climb on the center of the bar");
		oneCenterClimb.setBackground(Color.BLACK);
		oneCenterClimb.setForeground(Color.WHITE);
		oneCenterClimb.setFont(new Font("Verdana", Font.PLAIN, 20));
		GridBagConstraints gbc_oneCenterClimb = new GridBagConstraints();
		gbc_oneCenterClimb.anchor = GridBagConstraints.WEST;
		gbc_oneCenterClimb.insets = new Insets(0, 0, 5, 0);
		gbc_oneCenterClimb.gridwidth = 2;
		gbc_oneCenterClimb.gridx = 3;
		gbc_oneCenterClimb.gridy = 22;
		this.window.getContentPane().add(oneCenterClimb, gbc_oneCenterClimb);
		climberType.add(oneCenterClimb);

		JRadioButton twoSideClimb = new JRadioButton(
				"<html>This bot climbed on the side and helped another<br >bot climb</html>");
		twoSideClimb.setForeground(Color.WHITE);
		twoSideClimb.setBackground(Color.BLACK);
		twoSideClimb.setFont(new Font("Verdana", Font.PLAIN, 20));
		GridBagConstraints gbc_twoSideClimb = new GridBagConstraints();
		gbc_twoSideClimb.anchor = GridBagConstraints.WEST;
		gbc_twoSideClimb.insets = new Insets(0, 0, 5, 5);
		gbc_twoSideClimb.gridwidth = 2;
		gbc_twoSideClimb.gridx = 0;
		gbc_twoSideClimb.gridy = 23;
		this.window.getContentPane().add(twoSideClimb, gbc_twoSideClimb);
		climberType.add(twoSideClimb);

		JRadioButton twoCenterClimb = new JRadioButton(
				"<html>This bot climbed on the center and helped another<br >bot climb</html>");
		twoCenterClimb.setForeground(Color.WHITE);
		twoCenterClimb.setBackground(Color.BLACK);
		twoCenterClimb.setFont(new Font("Verdana", Font.PLAIN, 20));
		GridBagConstraints gbc_twoCenterClimb = new GridBagConstraints();
		gbc_twoCenterClimb.anchor = GridBagConstraints.WEST;
		gbc_twoCenterClimb.gridwidth = 2;
		gbc_twoCenterClimb.insets = new Insets(0, 0, 5, 0);
		gbc_twoCenterClimb.gridx = 3;
		gbc_twoCenterClimb.gridy = 23;
		this.window.getContentPane().add(twoCenterClimb, gbc_twoCenterClimb);
		climberType.add(twoCenterClimb);

		JRadioButton threeSideClimb = new JRadioButton(
				"<html>This bot climbed on the side and helped two other<br >bots climb</html>");
		threeSideClimb.setBackground(Color.BLACK);
		threeSideClimb.setForeground(Color.WHITE);
		threeSideClimb.setFont(new Font("Verdana", Font.PLAIN, 20));
		GridBagConstraints gbc_threeSideClimb = new GridBagConstraints();
		gbc_threeSideClimb.anchor = GridBagConstraints.WEST;
		gbc_threeSideClimb.gridwidth = 2;
		gbc_threeSideClimb.insets = new Insets(0, 0, 5, 5);
		gbc_threeSideClimb.gridx = 0;
		gbc_threeSideClimb.gridy = 24;
		this.window.getContentPane().add(threeSideClimb, gbc_threeSideClimb);
		climberType.add(threeSideClimb);

		JRadioButton threeCenterClimb = new JRadioButton(
				"<html>This bot climbed on the center and helped two other<br >bots climb</html>");
		threeCenterClimb.setBackground(Color.BLACK);
		threeCenterClimb.setForeground(Color.WHITE);
		threeCenterClimb.setFont(new Font("Verdana", Font.PLAIN, 20));
		GridBagConstraints gbc_threeCenterClimb = new GridBagConstraints();
		gbc_threeCenterClimb.anchor = GridBagConstraints.WEST;
		gbc_threeCenterClimb.gridwidth = 2;
		gbc_threeCenterClimb.insets = new Insets(0, 0, 5, 0);
		gbc_threeCenterClimb.gridx = 3;
		gbc_threeCenterClimb.gridy = 24;
		this.window.getContentPane().add(threeCenterClimb, gbc_threeCenterClimb);
		climberType.add(threeCenterClimb);

		noClimb.setSelected(true);
		
		Component verticalStrut_8 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_8 = new GridBagConstraints();
		gbc_verticalStrut_8.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_8.gridwidth = 5;
		gbc_verticalStrut_8.gridx = 0;
		gbc_verticalStrut_8.gridy = 25;
		this.window.getContentPane().add(verticalStrut_8, gbc_verticalStrut_8);

		JButton Cancel = new JButton("Cancel");
		Cancel.setFont(new Font("Verdana", Font.PLAIN, 25));
		GridBagConstraints gbc_Cancel = new GridBagConstraints();
		gbc_Cancel.anchor = GridBagConstraints.WEST;
		gbc_Cancel.insets = new Insets(0, 0, 0, 5);
		gbc_Cancel.gridx = 0;
		gbc_Cancel.gridy = 26;
		this.window.getContentPane().add(Cancel, gbc_Cancel);

		JButton Submit = new JButton("Submit");
		Submit.setFont(new Font("Verdana", Font.BOLD, 25));
		GridBagConstraints gbc_Submit = new GridBagConstraints();
		gbc_Submit.anchor = GridBagConstraints.EAST;
		gbc_Submit.gridx = 4;
		gbc_Submit.gridy = 26;
		this.window.getContentPane().add(Submit, gbc_Submit);

		switchAuto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (switchAuto.isSelected()) {
					teleSwitchTele.setText(teleSwitchTele.getText() + " (+1)");
				} else if (!switchAuto.isSelected()) {
					teleSwitchTele.setText(teleSwitchTele.getText().replace(" (+1)", ""));
				}

			}

		});

		scaleAuto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (scaleAuto.isSelected()) {
					teleScaleTele.setText(teleScaleTele.getText() + " (+1)");
				} else if (!scaleAuto.isSelected()) {
					teleScaleTele.setText(teleScaleTele.getText().replace(" (+1)", ""));
				}
			}

		});

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
				noClimb.setSelected(true);
				if (switchAuto.isSelected()) {
					teleSwitchTele.setText(teleSwitchTele.getText() + " (+1)");
				} else if (!switchAuto.isSelected()) {
					teleSwitchTele.setText(teleSwitchTele.getText().replace(" (+1)", ""));
				}
				if (scaleAuto.isSelected()) {
					teleScaleTele.setText(teleScaleTele.getText() + " (+1)");
				} else if (!scaleAuto.isSelected()) {
					teleScaleTele.setText(teleScaleTele.getText().replace(" (+1)", ""));
				}
				takeData.this.showPrompt();
			}

		});

		Submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				takeData.this.window.setVisible(false);
				String data = "";
				data = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", teamToScoutText.getText().toString(),
						(byte) (hasAuto.isSelected() ? 1 : 0), (byte) (switchAuto.isSelected() ? 1 : 0),
						(byte) (scaleAuto.isSelected() ? 1 : 0),
						Byte.parseByte(teleSwitchValue.getValue().toString())
								+ (byte) (switchAuto.isSelected() ? 1 : 0),
						Byte.parseByte(teleScaleValue.getValue().toString()) + (byte) (scaleAuto.isSelected() ? 1 : 0),
						Byte.parseByte(teleExchangeValue.getValue().toString()),
						(byte) (oneSideClimb.isSelected() ? 1 : 0), (byte) (oneCenterClimb.isSelected() ? 1 : 0),
						(byte) (twoSideClimb.isSelected() ? 1 : 0), (byte) (twoCenterClimb.isSelected() ? 1 : 0),
						(byte) (threeSideClimb.isSelected() ? 1 : 0), (byte) (threeCenterClimb.isSelected() ? 1 : 0),
						(byte) (climbRamp.isSelected() ? 1 : 0));

				Info.writeToFileStandScouting(data);

				hasAuto.setSelected(false);
				switchAuto.setSelected(false);
				scaleAuto.setSelected(false);
				teleSwitchValue.setValue(0);
				teleScaleValue.setValue(0);
				teleExchangeValue.setValue(0);
				climberType.clearSelection();
				noClimb.setSelected(true);
				if (switchAuto.isSelected()) {
					teleSwitchTele.setText(teleSwitchTele.getText() + " (+1)");
				} else if (!switchAuto.isSelected()) {
					teleSwitchTele.setText(teleSwitchTele.getText().replace(" (+1)", ""));
				}
				if (scaleAuto.isSelected()) {
					teleScaleTele.setText(teleScaleTele.getText() + " (+1)");
				} else if (!scaleAuto.isSelected()) {
					teleScaleTele.setText(teleScaleTele.getText().replace(" (+1)", ""));
				}
				takeData.this.showPrompt();

			}

		});
		
		this.window.pack();

	}

	public void showPrompt() {
		this.teamNumber = 0;
		this.teamNumberText.requestFocusInWindow();
		this.promptWindow.setVisible(true);
	}

	public void showWindow() {
		this.window.setVisible(true);
	}

}
