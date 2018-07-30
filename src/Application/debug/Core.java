package Application.debug;

public class Core {

	public static int BasicAutoValue = 0, SwitchAutoValue = 0, ScaleAutoValue = 0, SwitchTeleValue = 0,
			ScaleTeleValue = 0, ExchangeTeleValue = 0, SingleClimbSide = 0, SingleClimbCenter = 0, DoubleClimbSide = 0,
			DoubleClimbCenter = 0, TripleClimbSide = 0, TripleClimbCenter = 0, RampClimb = 0, DidntClimb = 1;
	public static String comments;

	// TODO: Store team number in core
	
	public static void reset() {
		BasicAutoValue = 0;
		SwitchAutoValue = 0;
		ScaleAutoValue = 0;
		SwitchTeleValue = 0;
		ScaleTeleValue = 0;
		ExchangeTeleValue = 0;
		SingleClimbSide = 0;
		SingleClimbCenter = 0;
		DoubleClimbSide = 0;
		DoubleClimbCenter = 0;
		TripleClimbSide = 0;
		TripleClimbCenter = 0;
		RampClimb = 0;
		DidntClimb = 1;
	}

	public static int BooleanToInt(Boolean bool) {
		if (bool) {
			return 1;
		} else {
			return 0;
		}
	}

	public static boolean IntToBoolean(int Int) {
		if (Int == 1) {
			return true;
		} else {
			return false;
		}
	}

}
