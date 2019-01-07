package javacode.Wireless;

import java.util.ArrayList;

public class bluetoothListener {

	private ArrayList<bluetoothEvent> events = new ArrayList<>();
	
	public void addEventListener(bluetoothEvent event) {
		events.add(event);
	}
	
	public void recievedString(String string) {
		for (bluetoothEvent event : events) {
			event.recievedString(string);
		}
	}
	
	public void recievedLatencyUpdate(int latency) {
		for (bluetoothEvent event : events) {
			event.recievedLatencyUpdate(latency);
		}
	}
	
}
