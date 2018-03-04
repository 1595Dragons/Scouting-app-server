package application;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class takeData extends Thread {

	JFrame window;

	JLabel teamHeader, autoHeader, teleHeader, cubeHeader, climbHeader;

	JCheckBox hasAuto, autoSwitch, autoScale, teleSwitch, teleScale;
	
	ButtonGroup climbers;
	
	JRadioButton didntClimb, oneClimb, twoClimb, threeClimb;
	
	JSpinner cubeNumber;

	JButton submit, cancel;

	GridBagConstraints layout;

	int teamNumber;
	int cube = 0;

	public void run() {
		init();
	}
	
	public void init() {
		window = new JFrame("Data Collector");
		layout = new GridBagConstraints();
		teamHeader = new JLabel("Team to scout: ");
		autoHeader = new JLabel("<html><center><u>Autonomous</u></center></html>");
		teleHeader = new JLabel("<html><center><u>TeleOp</u></center></html>");
		SpinnerModel model = new SpinnerNumberModel(0, 0, 25, 1);
		cubeNumber = new JSpinner(model);
		cubeHeader = new JLabel("Number of cubes placed: ");
		hasAuto = new JCheckBox("This team has an auto");
		autoSwitch = new JCheckBox("Can place cube on switch during auto");
		autoScale = new JCheckBox("Can place cube on scale during auto");
		teleSwitch = new JCheckBox("Can place cube on switch during teleop");
		teleScale = new JCheckBox("Can place cube on scale during teleop");
		climbHeader = new JLabel("<html><center><u>Climbing</u></center></html>");
		didntClimb = new JRadioButton("This bot didnt cause any other bots to climb/failed to climb");
		oneClimb = new JRadioButton("This bot caused one bot to climb");
		twoClimb = new JRadioButton("This bot caused two bots to climb");
		threeClimb = new JRadioButton("This team climbed, and lifted two other bots");
		climbers = new ButtonGroup();
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		try {
			addComponents(Integer.parseInt(JOptionPane.showInputDialog("Enter team number", "")));
		} catch (NumberFormatException e) {
			if (e.getLocalizedMessage() == "null") {
				System.exit(0);
			} else {
				JOptionPane.showMessageDialog(null, "That is not a valid team number", "Error",
						JOptionPane.ERROR_MESSAGE);
				init();
			}
		}
	}

	public void addComponents(int number) {
		this.window.setLayout(new GridBagLayout());
		this.layout.weighty = 1;
		this.layout.anchor = GridBagConstraints.NORTH;

		this.teamNumber = number;


		this.teamHeader.setText(teamHeader.getText() + number);
		this.layout.gridheight = 1;
		this.layout.gridwidth = 2;
		this.layout.gridx = 0;
		this.layout.gridy = 0;
		this.teamHeader.setFont(new Font(null, Font.BOLD, 40));
		this.window.add(this.teamHeader, this.layout);
		
		climbers.add(this.didntClimb);
		this.didntClimb.setSelected(true);
		climbers.add(this.oneClimb);
		climbers.add(this.twoClimb);
		climbers.add(this.threeClimb);

		JComponent[] objects = { this.autoHeader, this.hasAuto, this.autoSwitch, this.autoScale, this.teleHeader,
				this.teleSwitch, this.teleScale, this.cubeHeader, this.cubeNumber, this.climbHeader, this.didntClimb, this.oneClimb, this.twoClimb, this.threeClimb };

		for (int i = 1; i <= objects.length; i++) {
			this.layout.gridheight = 1;
			this.layout.gridwidth = 2;
			this.layout.gridx = 0;
			this.layout.gridy = i;
			objects[i - 1].setFont(new Font(null, Font.BOLD, 25));
			this.window.add(objects[i - 1], this.layout);
		}
		
		this.layout.gridheight = 1;
		this.layout.gridwidth = 1;
		this.layout.gridx = 0;
		this.layout.gridy = 15;
		this.cancel.setFont(new Font(null, Font.BOLD, 25));
		this.window.add(cancel, this.layout);

		this.layout.gridheight = 1;
		this.layout.gridwidth = 1;
		this.layout.gridx = 1;
		this.layout.gridy = 15;
		this.submit.setFont(new Font(null, Font.BOLD, 25));
		this.window.add(submit, this.layout, JButton.EAST);

		startGUI();

	}

	public void startGUI() {
		// this.window.pack();
		this.window.setSize(780, 1400);
		this.window.setVisible(true);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.window.setLocation(d.width / 2 - this.window.getSize().width / 2,
				d.height / 2 - this.window.getSize().height / 2);
		readyListeners();
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void reset() {
		takeData.this.window.setVisible(false);
		init();
	}

	public void readyListeners() {
		this.cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object[] options = { "Yes", "No" };
				int confirm = JOptionPane.showOptionDialog(null, "Are you sure you want to cancel?", "Cancel?",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
				if (confirm == 0) {
					reset();
				}
			}

		});

		this.submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int climbValue = 0;
				if (takeData.this.didntClimb.isSelected()) {
					climbValue = 0;
				} else if (takeData.this.oneClimb.isSelected()) {
					climbValue = 1;
				} else if (takeData.this.twoClimb.isSelected()) {
					climbValue = 2;
				} else if (takeData.this.threeClimb.isSelected()) {
					climbValue = 3;
				}
				takeData.this.cube = (int) takeData.this.cubeNumber.getValue();
				String data = "";
				data = String
						.format("%s,%s,%s,%s,%s,%s,%s,%s", takeData.this.teamNumber,
								takeData.this.hasAuto.isSelected(), takeData.this.autoSwitch.isSelected(),
								takeData.this.autoScale.isSelected(), takeData.this.teleSwitch.isSelected(),
								takeData.this.teleScale.isSelected(), takeData.this.cubeNumber.getValue(), climbValue)
						.toUpperCase();
				//System.out.println(data);
				Info.writeToFile(data);

				reset();
			}

		});


	}

}
