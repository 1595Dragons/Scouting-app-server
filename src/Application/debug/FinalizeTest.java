package Application.debug;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class FinalizeTest {

	private JFrame ScoutingWindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FinalizeTest window = new FinalizeTest();
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
	public FinalizeTest() {
		initialize();
	}
	
	/**
	 * Show the application.
	 */
	public void showFinalization() {
		ScoutingWindow.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ScoutingWindow = new JFrame();
		ScoutingWindow.setBounds(100, 100, 450, 300);
		ScoutingWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
