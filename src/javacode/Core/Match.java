package javacode.Core;

import java.util.ArrayList;

import javax.json.JsonValue;

public class Match {

	public Autonomous[] autonomousData;
	public TeleOp[] teleopData;
	public Endgame[] endgameData;

	
	public class Autonomous {
	
		public String name;
		public DataType datatype;
		public ArrayList<JsonValue> value;
		
	}
	
	
	public class TeleOp {

		public String name;
		public DataType datatype;
		public ArrayList<JsonValue> value;

	}

	
	public class Endgame {

		public String name;
		public DataType datatype;
		public ArrayList<JsonValue> value;

	}

	
	public enum DataType {

		Text, Number, Boolean, BooleanGroup;

	}

}
