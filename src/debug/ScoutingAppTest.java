package debug;

public class ScoutingAppTest {

	public static boolean debug = false;
	
	public static void main(String args[]) {
		debug = Boolean.parseBoolean(args[0]);
	}
	
}
