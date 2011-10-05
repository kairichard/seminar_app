// $Id: java5.java,v 1.1 2005/11/23 22:18:45 dds Exp $
import java.util.*;

/**
 * @opt attributes
 * @opt operations
 * @opt visibility
 * @opt noqualify
 * @opt types
 * @opt enumerations
 * @opt enumconstants
 * @hidden
 */
class UMLOptions {}

/** Test Java 5 features */
class Java5 {
	/** Enum */
	enum States {start, dash, colon, space, open, w, close};
	States state = States.start;
	/** Generics */
	private Set<String> specifiedPackages;
	/** Varargs */
	public static void printAll(String... args) {
		for (String n : args)
			System.out.println("Hello " + n + ". ");
	}
}