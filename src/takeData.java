import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JToolTip;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class takeData {

	JFrame window;

	JLabel teamHeader, autoHeader, teleHeader;

	JCheckBox hasAuto, autoSwitch, autoScale, teleSwitch, teleScale, endClimb, endClimbAssist;

	JSlider cubeNumber;

	JButton submit, cancel;

	GridBagConstraints layout;

	int teamNumber;
	int cube = 0;

	public void init() {
		window = new JFrame("Data Collector");
		layout = new GridBagConstraints();
		teamHeader = new JLabel("Team to scout: ");
		autoHeader = new JLabel("\nAutonomous");
		teleHeader = new JLabel("\nTeleOp");
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

		this.cubeNumber.setMajorTickSpacing(10);
		this.cubeNumber.setMinorTickSpacing(1);
		this.cubeNumber.setPaintTicks(true);
		this.cubeNumber.setPaintLabels(true);
		//this.cubeNumber.setToolTipText(String.valueOf(this.cube));
		

		this.teamHeader.setText(teamHeader.getText() + number);
		this.layout.gridheight = 1;
		this.layout.gridwidth = 2;
		this.layout.gridx = 0;
		this.layout.gridy = 0;
		this.teamHeader.setFont(new Font(null, Font.BOLD, 40));
		this.window.add(this.teamHeader, this.layout);

		JComponent[] objects = { this.autoHeader, this.hasAuto, this.autoSwitch, this.autoScale, this.teleHeader,
				this.teleSwitch, this.teleScale, this.cubeNumber, this.endClimb, this.endClimbAssist };

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
		this.layout.gridy = 14;
		this.cancel.setFont(new Font(null, Font.BOLD, 25));
		this.window.add(cancel, this.layout);

		this.layout.gridheight = 1;
		this.layout.gridwidth = 1;
		this.layout.gridx = 1;
		this.layout.gridy = 14;
		this.submit.setFont(new Font(null, Font.BOLD, 25));
		this.window.add(submit, this.layout, JButton.EAST);

		startGUI();

	}

	public void startGUI() {
		// this.window.pack();
		this.window.setSize(778, 1058);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.window.setLocation(d.width / 2 - this.window.getSize().width / 2,
				d.height / 2 - this.window.getSize().height / 2);
		readyListeners();
		this.window.setVisible(true);
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
				String data = "";
				data = String.format("%s, %s, %s, %s, %s, %s, %s, %s", takeData.this.teamNumber,
						takeData.this.hasAuto.isSelected(), takeData.this.autoSwitch.isSelected(),
						takeData.this.teleSwitch.isSelected(), takeData.this.teleScale.isSelected(),
						takeData.this.cubeNumber.getValue(), takeData.this.endClimb.isSelected(),
						takeData.this.endClimbAssist.isSelected()).toUpperCase();
				System.out.println(data);
				reset();
			}

		});
		
		this.cubeNumber.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				takeData.this.cube = takeData.this.cubeNumber.getValue();
				
			}
		});
		
		
	}
	
	public void writeToFile(String data) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(file.file.getAbsolutePath(), true);
		} catch (IOException e1) {
			System.out.println("Error, cannot edit file: " + e1.getMessage());
			e1.printStackTrace();
		}
		try {
			fw.append(System.getProperty("line.separator")+data);
			fw.flush();
		} catch (IOException e1) {
			System.out.println("Error, cannot write to file: " + e1.getMessage());
			e1.printStackTrace();
		}

		System.out.print("finishing data for team: " + data.split(",")[0] + "\n");
		try {
			fw.close();
		} catch (IOException e1) {
			System.out.println("Error, cannot close streams: " + e1.getMessage());
			e1.printStackTrace();
		}
		System.out.println("Data written successfully!");
	}

}
