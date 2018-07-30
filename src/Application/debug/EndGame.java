package Application.debug;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Application.Debugger;

public class EndGame {

	private JFrame ScoutingWindow;
	private ButtonGroup climb = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EndGame window = new EndGame();
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
	public EndGame() {
		initialize();
	}

	/**
	 * Show the application.
	 */
	public void showEndGame() {
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

		JLabel ScoutingTeamText = new JLabel("Scouting team: " + EnterTeamNumberPrompt.teamNumber);
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
		progressBar.setValue(3);
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

		JLabel EndGameHeader = new JLabel("End Game");
		EndGameHeader.setForeground(Color.WHITE);
		EndGameHeader.setFont(new Font("Arial Black", Font.PLAIN, 30));
		GridBagConstraints gbc_EndGameHeader = new GridBagConstraints();
		gbc_EndGameHeader.insets = new Insets(0, 0, 5, 0);
		gbc_EndGameHeader.gridwidth = 8;
		gbc_EndGameHeader.gridx = 0;
		gbc_EndGameHeader.gridy = 5;
		ScoutingWindow.getContentPane().add(EndGameHeader, gbc_EndGameHeader);

		JRadioButton RampClimb = new JRadioButton("<html>This bot used a ramp to<br>help others climb</html>");
		RampClimb.setFont(new Font("Arial", Font.PLAIN, 20));
		RampClimb.setForeground(Color.WHITE);
		climb.add(RampClimb);
		RampClimb.setSelected(Core.IntToBoolean(Core.RampClimb));
		GridBagConstraints gbc_RampClimb = new GridBagConstraints();
		gbc_RampClimb.anchor = GridBagConstraints.WEST;
		gbc_RampClimb.gridheight = 2;
		gbc_RampClimb.gridwidth = 4;
		gbc_RampClimb.insets = new Insets(0, 0, 5, 0);
		gbc_RampClimb.gridx = 4;
		gbc_RampClimb.gridy = 6;
		ScoutingWindow.getContentPane().add(RampClimb, gbc_RampClimb);

		JRadioButton DidntClimb = new JRadioButton("This robot failed to climb");
		DidntClimb.setForeground(Color.WHITE);
		DidntClimb.setFont(new Font("Arial", Font.PLAIN, 20));
		DidntClimb.setSelected(Core.IntToBoolean(Core.DidntClimb));
		climb.add(DidntClimb);
		GridBagConstraints gbc_DidntClimb = new GridBagConstraints();
		gbc_DidntClimb.gridheight = 2;
		gbc_DidntClimb.anchor = GridBagConstraints.WEST;
		gbc_DidntClimb.gridwidth = 4;
		gbc_DidntClimb.insets = new Insets(0, 0, 5, 5);
		gbc_DidntClimb.gridx = 0;
		gbc_DidntClimb.gridy = 6;
		ScoutingWindow.getContentPane().add(DidntClimb, gbc_DidntClimb);

		JRadioButton SingleSideClimb = new JRadioButton(
				"<html>This bot did a single climb\n<br>on the side of the bar</html>");
		SingleSideClimb.setFont(new Font("Arial", Font.PLAIN, 20));
		SingleSideClimb.setForeground(Color.WHITE);
		climb.add(SingleSideClimb);
		SingleSideClimb.setSelected(Core.IntToBoolean(Core.SingleClimbSide));
		GridBagConstraints gbc_SingleSideClimb = new GridBagConstraints();
		gbc_SingleSideClimb.anchor = GridBagConstraints.WEST;
		gbc_SingleSideClimb.gridheight = 2;
		gbc_SingleSideClimb.gridwidth = 4;
		gbc_SingleSideClimb.insets = new Insets(0, 0, 5, 5);
		gbc_SingleSideClimb.gridx = 0;
		gbc_SingleSideClimb.gridy = 8;
		ScoutingWindow.getContentPane().add(SingleSideClimb, gbc_SingleSideClimb);

		JRadioButton SingleCenterClimb = new JRadioButton(
				"<html>This bot did a single climb<br>on the center of the bar</html>");
		SingleCenterClimb.setFont(new Font("Arial", Font.PLAIN, 20));
		SingleCenterClimb.setForeground(Color.WHITE);
		climb.add(SingleCenterClimb);
		SingleCenterClimb.setSelected(Core.IntToBoolean(Core.SingleClimbCenter));
		GridBagConstraints gbc_SingleCenterClimb = new GridBagConstraints();
		gbc_SingleCenterClimb.anchor = GridBagConstraints.WEST;
		gbc_SingleCenterClimb.gridheight = 2;
		gbc_SingleCenterClimb.gridwidth = 4;
		gbc_SingleCenterClimb.insets = new Insets(0, 0, 5, 0);
		gbc_SingleCenterClimb.gridx = 4;
		gbc_SingleCenterClimb.gridy = 8;
		ScoutingWindow.getContentPane().add(SingleCenterClimb, gbc_SingleCenterClimb);

		JRadioButton DoubleSideClimb = new JRadioButton(
				"<html>This bot climbed on the<br>side and helped another<br>bot climb</html>");
		DoubleSideClimb.setFont(new Font("Arial", Font.PLAIN, 20));
		DoubleSideClimb.setForeground(Color.WHITE);
		climb.add(DoubleSideClimb);
		DoubleSideClimb.setSelected(Core.IntToBoolean(Core.DoubleClimbSide));
		GridBagConstraints gbc_DoubleSideClimb = new GridBagConstraints();
		gbc_DoubleSideClimb.anchor = GridBagConstraints.NORTHWEST;
		gbc_DoubleSideClimb.gridheight = 2;
		gbc_DoubleSideClimb.gridwidth = 4;
		gbc_DoubleSideClimb.insets = new Insets(0, 0, 5, 5);
		gbc_DoubleSideClimb.gridx = 0;
		gbc_DoubleSideClimb.gridy = 10;
		ScoutingWindow.getContentPane().add(DoubleSideClimb, gbc_DoubleSideClimb);

		JRadioButton DoubleCenterClimb = new JRadioButton(
				"<html>This bot climbed on the<br>center and helped another<br>bot climb</html>");
		DoubleCenterClimb.setFont(new Font("Arial", Font.PLAIN, 20));
		DoubleCenterClimb.setForeground(Color.WHITE);
		climb.add(DoubleCenterClimb);
		DoubleCenterClimb.setSelected(Core.IntToBoolean(Core.DoubleClimbCenter));
		GridBagConstraints gbc_DoubleCenterClimb = new GridBagConstraints();
		gbc_DoubleCenterClimb.anchor = GridBagConstraints.NORTHWEST;
		gbc_DoubleCenterClimb.gridheight = 2;
		gbc_DoubleCenterClimb.gridwidth = 4;
		gbc_DoubleCenterClimb.insets = new Insets(0, 0, 5, 0);
		gbc_DoubleCenterClimb.gridx = 4;
		gbc_DoubleCenterClimb.gridy = 10;
		ScoutingWindow.getContentPane().add(DoubleCenterClimb, gbc_DoubleCenterClimb);

		JRadioButton TripleSideClimb = new JRadioButton(
				"<html>This bot climbed on the<br>side and helped two other<br>bots climb</html>");
		TripleSideClimb.setFont(new Font("Arial", Font.PLAIN, 20));
		TripleSideClimb.setForeground(Color.WHITE);
		climb.add(TripleSideClimb);
		TripleSideClimb.setSelected(Core.IntToBoolean(Core.TripleClimbSide));
		GridBagConstraints gbc_TripleSideClimb = new GridBagConstraints();
		gbc_TripleSideClimb.gridheight = 2;
		gbc_TripleSideClimb.anchor = GridBagConstraints.NORTHWEST;
		gbc_TripleSideClimb.gridwidth = 4;
		gbc_TripleSideClimb.insets = new Insets(0, 0, 5, 5);
		gbc_TripleSideClimb.gridx = 0;
		gbc_TripleSideClimb.gridy = 12;
		ScoutingWindow.getContentPane().add(TripleSideClimb, gbc_TripleSideClimb);

		JRadioButton TripleCenterClimb = new JRadioButton(
				"<html>This bot climbed on the<br>center and helped two<br>other bots climb</html>");
		TripleCenterClimb.setFont(new Font("Arial", Font.PLAIN, 20));
		TripleCenterClimb.setForeground(Color.WHITE);
		climb.add(TripleCenterClimb);
		TripleCenterClimb.setSelected(Core.IntToBoolean(Core.TripleClimbCenter));
		GridBagConstraints gbc_TripleCenterClimb = new GridBagConstraints();
		gbc_TripleCenterClimb.anchor = GridBagConstraints.NORTHWEST;
		gbc_TripleCenterClimb.gridheight = 2;
		gbc_TripleCenterClimb.gridwidth = 4;
		gbc_TripleCenterClimb.insets = new Insets(0, 0, 5, 0);
		gbc_TripleCenterClimb.gridx = 4;
		gbc_TripleCenterClimb.gridy = 12;
		ScoutingWindow.getContentPane().add(TripleCenterClimb, gbc_TripleCenterClimb);

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
				TeleScouting TST = new TeleScouting();
				ScoutingWindow.setVisible(false);
				TST.showTeleOp();
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

		JButton Review = new JButton("Review");
		Review.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_Review = new GridBagConstraints();
		gbc_Review.gridwidth = 2;
		gbc_Review.gridx = 6;
		gbc_Review.gridy = 15;
		ScoutingWindow.getContentPane().add(Review, gbc_Review);

		Review.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Finalize FT = new Finalize();
				Core.SingleClimbSide = Core.BooleanToInt(SingleSideClimb.isSelected());
				Core.SingleClimbCenter = Core.BooleanToInt(SingleCenterClimb.isSelected());
				Core.DoubleClimbSide = Core.BooleanToInt(DoubleSideClimb.isSelected());
				Core.DoubleClimbCenter = Core.BooleanToInt(DoubleCenterClimb.isSelected());
				Core.TripleClimbSide = Core.BooleanToInt(TripleSideClimb.isSelected());
				Core.TripleClimbCenter = Core.BooleanToInt(TripleCenterClimb.isSelected());
				Core.RampClimb = Core.BooleanToInt(RampClimb.isSelected());
				Core.DidntClimb = Core.BooleanToInt(DidntClimb.isSelected());

				ScoutingWindow.setVisible(false);
				FT.showFinalization();
			}
		});

		ScoutingWindow.setTitle("1595 Scouting App");
		ScoutingWindow.setResizable(false);
		ScoutingWindow.setBounds(100, 100, 566, 772);

		ScoutingWindow.getRootPane().setDefaultButton(Review);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		ScoutingWindow.setLocation((d.width / 2 - ScoutingWindow.getSize().width / 2),
				(d.height / 2 - ScoutingWindow.getSize().height / 2));

	}
}
