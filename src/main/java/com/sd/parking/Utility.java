package com.sd.parking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utility {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static <T> List<T> readFromFile(String fileName, Class<T> class1) throws IOException, ClassNotFoundException {
		@SuppressWarnings("unchecked")
		Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + class1.getName() + ";");

		T[] objects = null;
		if (fileName.endsWith(".db")) {
			objects = MAPPER.readValue(new File(fileName), arrayClass);
		} else {
			objects = MAPPER.readValue(fileName, arrayClass);
		}
		return new ArrayList<T>(Arrays.asList(objects));
	}

	public static <T> void writeToFile(String fileName, List<T> list) throws IOException {
		MAPPER.writeValue(new File(fileName), list);
	}

	public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
		Random random = new Random();
		int x = random.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}

	@SuppressWarnings("deprecation")
	public static <T> T instantiate(final String className, final Class<T> type) {
		try {
			return type.cast(Class.forName(className).newInstance());
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}
}
