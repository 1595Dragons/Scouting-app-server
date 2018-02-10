import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTextArea;

public class takeData {
	
	JFrame window;
	
	JTextArea teamNumber;
	
	JCheckBox hasAuto, autoSwitch, autoScale, teleSwitch, teleScale, endClimb, endClimbAssist;
	
	JSlider cubeNumber;
	
	JButton submit, cancel;
	
	public void init() {
		window = new JFrame("Data Collection");
		teamNumber = new JTextArea("Enter team number");
		cubeNumber = new JSlider(0, 50, 0);
		hasAuto = new JCheckBox("This team has an auto");
		autoSwitch = new JCheckBox("Can place cube on switch during auto");
		autoScale = new JCheckBox("Can place cube on scale during auto");
		teleSwitch = new JCheckBox("Can place cube on switch during teleop");
		teleScale = new JCheckBox("Can place cube on scale during teleop");
		endClimb = new JCheckBox("This team can climb");
		endClimbAssist = new JCheckBox("THis team can help others climb");
	}
	
	public void startGUI() {
		this.window.setVisible(true);
	}
	
	
}
