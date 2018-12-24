package javacode.Core;

public class Match {

	Match.Autonomous[] autonomousData;
	Match.TeleOp[] teleopData;
	Match.Endgame[] endgameData;
	
	
	public class Autonomous {
		
		String name;
		DataType datatype;
		Object count;
		
	}
	
	
	public class TeleOp {
		
		String name;
		DataType datatype;
		Object count;
		
	}
	
	
	public class Endgame {
		
		String name;
		DataType datatype;
		Object count;
		
	}
	
	
	public enum DataType {
		
		Text,
		Number,
		Boolean,
		BooleanGroup;
		
	}
	
}
