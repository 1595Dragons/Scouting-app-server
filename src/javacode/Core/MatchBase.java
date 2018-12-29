package javacode.Core;

import java.util.ArrayList;

import javax.json.JsonValue;

public class MatchBase {

	public String name;
	public DataType datatype;
	public ArrayList<JsonValue> value;
	
	public static enum DataType {

		Text, Number, Boolean, BooleanGroup;

	}
	
}
