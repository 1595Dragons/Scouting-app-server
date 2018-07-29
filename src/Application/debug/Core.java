package Application.debug;

public class Core {

	public static int BasicAutoValue = 0, SwitchAutoValue = 0, ScaleAutoValue = 0, SwitchTeleValue = 0,
			ScaleTeleValue = 0, ExchangeTeleValue = 0; // TODO: Climbing
	public static String comments;

	public static void reset() {
		// TODO: Can use this to reset the scouting windows
	}
	
	public static int BooleanToInt(Boolean bool) {
		if (bool) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public static boolean IntToBoolean(int Int) {
		if (Int==1) {
			return true;
		} else {
			return false;
		}
	}

}
