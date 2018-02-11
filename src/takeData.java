import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;

public class takeData {

	JFrame window;

	JTextArea teamNumber;

	JLabel teamHeader, autoHeader, teleHeader;

	JCheckBox hasAuto, autoSwitch, autoScale, teleSwitch, teleScale, endClimb, endClimbAssist;

	JSlider cubeNumber;

	JButton submit, cancel;

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

		for (int i = 1; i <= objects.length; i++) {
			this.layout.gridheight = 1;
			this.layout.gridwidth = 2;
			this.layout.gridx = 0;
			this.layout.gridy = i;
			objects[i - 1].setSize(objects[i - 1].getSize().width * 2, objects[i - 1].getSize().height * 2);
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

	}

	public void startGUI() {
		this.window.pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.window.setLocation(d.width / 2 - this.window.getSize().width / 2,
				d.height / 2 - this.window.getSize().height / 2);
		readyListeners();
		this.window.setVisible(true);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void reset() {
		// TODO: Reset everyghing (Submit/Cancel was clicked)
	}

	public void readyListeners() {
		// TODO: Action listneners
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
				// TODO: Check if a team number is entered, then write the data to the file
				String data = "";
				if (!takeData.this.teamNumber.getText().isEmpty()) {
					try {
						data = data + Integer.parseInt(takeData.this.teamNumber.getText());
						System.out.print(data);
					} catch (Exception e) {
						takeData.this.teamHeader.setForeground(Color.RED);
						return;
					}
				} else {
					takeData.this.teamHeader.setForeground(Color.RED);
					return;
				}

			}

		});
	}

}
