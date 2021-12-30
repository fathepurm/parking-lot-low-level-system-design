package com.sd.parking;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Start of the application
 *
 */
public class App {
	public static Initializer app = new Initializer();;

	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		app.start();
		app.readInstructions();
	}
}
