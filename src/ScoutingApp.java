import java.io.IOException;

public class ScoutingApp {

	public static void main(String args[]) throws IOException {
		
		display d  = new display();
		server blueToothServer = new server();
		d.makePanel();
		if (selectFile.fileMissing()) {
			d.feed.setText("File missing, creating new file");
			selectFile.makeFile();
			d.feed.setText("File created! Awaiting data...");
		} else {
			d.feed.setText("File found! Awaiting data...");
		}
		
		blueToothServer.startServer();
		
	}
	
}
