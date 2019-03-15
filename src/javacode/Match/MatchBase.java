package javacode.Match;

public class MatchBase {

	public String name;
	public DataType datatype;
	public java.util.ArrayList<javax.json.JsonValue> value;

	public static enum DataType {
		Text, Number, Boolean, BooleanGroup;
	}
}
