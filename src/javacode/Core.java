package javacode;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import javacode.Debugger;

public class Core {

	public static int BasicAutoValue = 0, SwitchAutoValue = 0, ScaleAutoValue = 0, SwitchTeleValue = 0,
			ScaleTeleValue = 0, ExchangeTeleValue = 0, SingleClimbSide = 0, SingleClimbCenter = 0, DoubleClimbSide = 0,
			DoubleClimbCenter = 0, TripleClimbSide = 0, TripleClimbCenter = 0, RampClimb = 0, DidntClimb = 1,
			teamNumber = 0, switchOffSet = 0, scaleOffset = 0;
	
	public static String comments;

	public static void reset() {
		
		Debugger.d(Core.class, String.format("Resetting BasicAutoValue: %s -> 0", String.valueOf(BasicAutoValue)));
		BasicAutoValue = 0;
		
		Debugger.d(Core.class, String.format("Resetting SwitchAutoValue: %s -> 0", String.valueOf(SwitchAutoValue)));
		SwitchAutoValue = 0;
		
		Debugger.d(Core.class, String.format("Resetting ScaleAutoValue: %s -> 0", String.valueOf(ScaleAutoValue)));
		ScaleAutoValue = 0;
		
		Debugger.d(Core.class, String.format("Resetting SwitchTeleValue: %s -> 0", String.valueOf(SwitchTeleValue)));
		SwitchTeleValue = 0;
		
		Debugger.d(Core.class, String.format("Resetting ScaleTeleValue: %s -> 0", String.valueOf(ScaleTeleValue)));
		ScaleTeleValue = 0;
		
		Debugger.d(Core.class, String.format("Resetting ExchangeTeleValue: %s -> 0", String.valueOf(ExchangeTeleValue)));
		ExchangeTeleValue = 0;
		
		Debugger.d(Core.class, String.format("Resetting SingleClimbSide: %s -> 0", String.valueOf(SingleClimbSide)));
		SingleClimbSide = 0;
		
		Debugger.d(Core.class, String.format("Resetting SingleClimbCenter: %s -> 0", String.valueOf(SingleClimbCenter)));
		SingleClimbCenter = 0;
		
		Debugger.d(Core.class, String.format("Resetting DoubleClimbSide: %s -> 0", String.valueOf(DoubleClimbSide)));
		DoubleClimbSide = 0;
		
		Debugger.d(Core.class, String.format("Resetting DoubleClimbCenter: %s -> 0", String.valueOf(DoubleClimbCenter)));
		DoubleClimbCenter = 0;
		
		Debugger.d(Core.class, String.format("Resetting TripleClimbSide: %s -> 0", String.valueOf(TripleClimbSide)));
		TripleClimbSide = 0;
		
		Debugger.d(Core.class, String.format("Resetting TripleClimbCenter: %s -> 0", String.valueOf(TripleClimbCenter)));
		TripleClimbCenter = 0;
		
		Debugger.d(Core.class, String.format("Resetting RampClimb: %s -> 0", String.valueOf(RampClimb)));
		RampClimb = 0;
		
		Debugger.d(Core.class, String.format("Resetting DidntClimb: %s -> 1", String.valueOf(DidntClimb)));
		DidntClimb = 1;
		
		Debugger.d(Core.class, String.format("Resetting teamNumber: %s -> 0", String.valueOf(teamNumber)));
		teamNumber = 0;
		
		Debugger.d(Core.class, String.format("Resetting switchOffSet: %s -> 0", String.valueOf(switchOffSet)));
		switchOffSet = 0;
		
		Debugger.d(Core.class, String.format("Resetting ScaleOffSet: %s -> 0", String.valueOf(scaleOffset)));
		scaleOffset = 0;
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

	public static void CenterWindowLocaiton(JFrame ScoutingWindow) {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Debugger.d(Core.class, "Dimension value: " + d.toString());
		ScoutingWindow.setLocation((d.width / 2 - ScoutingWindow.getSize().width / 2),
				(d.height / 2 - ScoutingWindow.getSize().height / 2));
	}

}
