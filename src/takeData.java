import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class takeData {

	JFrame window;

	JTextArea teamNumber;

	JLabel teamHeader, autoHeader, teleHeader;

	JCheckBox hasAuto, autoSwitch, autoScale, teleSwitch, teleScale, endClimb, endClimbAssist;

	JSlider cubeNumber;

	JButton submit, cancel, quit;

	GridBagConstraints layout;

	public void init() {
		window = new JFrame("Data Collector");
		layout = new GridBagConstraints();
		teamHeader = new JLabel("Team to scout: ");
		autoHeader = new JLabel("\nAutonomous");
		teleHeader = new JLabel("\nTeleOp");
		teamNumber = new JTextArea("Enter team number");
		cubeNumber = new JSlider(0, 50, 0);
		hasAuto = new JCheckBox("This team has an auto");
		autoSwitch = new JCheckBox("Can place cube on switch during auto");
		autoScale = new JCheckBox("Can place cube on scale during auto");
		teleSwitch = new JCheckBox("Can place cube on switch during teleop");
		teleScale = new JCheckBox("Can place cube on scale during teleop");
		endClimb = new JCheckBox("This team can climb");
		endClimbAssist = new JCheckBox("This team can help others climb");
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		quit = new JButton("Quit");
		addComponents();
	}

	public void addComponents() {
		this.window.setLayout(new GridBagLayout());

		this.cubeNumber.setMajorTickSpacing(10);
		this.cubeNumber.setMinorTickSpacing(1);
		this.cubeNumber.setPaintTicks(true);
		this.cubeNumber.setPaintLabels(true);

		this.layout.gridheight = 1;
		this.layout.gridwidth = 1;
		this.layout.gridx = 0;
		this.layout.gridy = 0;
		this.window.add(this.teamHeader, this.layout);

		this.layout.gridheight = 1;
		this.layout.gridwidth = 1;
		this.layout.gridx = 1;
		this.layout.gridy = 0;
		this.window.add(this.teamNumber, this.layout);

		JComponent[] objects = { this.autoHeader, this.hasAuto, this.autoSwitch, this.autoScale, this.teleHeader,
				this.teleSwitch, this.teleScale, this.cubeNumber, this.endClimb, this.endClimbAssist };

		for (int i = 1; i < objects.length; i++) {
			this.layout.gridheight = 1;
			this.layout.gridwidth = 2;
			this.layout.gridx = 0;
			this.layout.gridy = i;
			this.window.add(objects[i - 1], this.layout);
		}

		this.layout.gridheight = 1;
		this.layout.gridwidth = 1;
		this.layout.gridx = 0;
		this.layout.gridy = 14;
		this.window.add(cancel, this.layout);

		this.layout.gridheight = 1;
		this.layout.gridwidth = 1;
		this.layout.gridx = 1;
		this.layout.gridy = 14;
		this.window.add(submit, this.layout);

		this.layout.gridheight = 1;
		this.layout.gridwidth = 2;
		this.layout.gridx = 0;
		this.layout.gridy = 15;
		this.window.add(quit, this.layout);

		resize();
		this.window.pack();
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void resize() {
		// TODO: Resize everything
	}

	public void startGUI() {
		this.window.setLocation(960, 540);
		this.window.setVisible(true);
	}
	public void reset() {
		// TODO: Reset everyghing (Submit/Cancel was clicked)
	}

}
