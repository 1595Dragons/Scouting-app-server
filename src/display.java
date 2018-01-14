
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

@Deprecated
public class display {

	public JFrame frame = new JFrame("1595 Scouting App");
	GridBagConstraints constraints = new GridBagConstraints();
	JLabel MACAddress = new JLabel("MAC Address");
	JLabel feed = new JLabel("---");

	@SuppressWarnings("static-access")
	public void makePanel() {
		this.frame.setLayout(new GridLayout(0, 1));
		this.frame.setDefaultCloseOperation(this.frame.EXIT_ON_CLOSE);

		this.constraints.gridx = 0;
		this.constraints.gridy = 0;
		this.constraints.gridheight = 1;
		this.constraints.gridwidth = 1;
		this.frame.add(MACAddress, constraints);

		this.constraints.gridheight = 1;
		this.constraints.gridwidth = 1;
		this.constraints.gridx = 0;
		this.constraints.gridy = 1;
		this.frame.add(feed, constraints);

		this.frame.setSize(350,75);
		this.frame.setLocation(600, 400);
		this.frame.setVisible(true);
	}

}
