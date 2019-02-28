package javacode.Core;

/**
 * Debugger class, basically prints a load of stuff to the console if <code>true</code> is passed as an argument on startup.
 */
public class Debugger {
	/**
	 * Prints debugging stuff to the console.
	 * <br ><br >
	 * Example output:<br ><br ><code>javacode.ScoutingApp: Debugging enabled: true</code>
	 *
	 * @param name    The class (Use <code>this.getClass()</code>).
	 * @param message The output.
	 */
	public static void d(Class<?> name, String message) {
		if (javacode.ScoutingApp.debug) {
			System.out.println(String.format("%s: %s", name.getName(), message));
		}
	}
}
