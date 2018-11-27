package javacode.Core;

public class Debugger {
	public static void d(Class<?> name, String message) {
		if (javacode.ScoutingApp.debug) {
			System.out.println(String.format("%s: %s", name.getName(), message));
		}
	}
}
