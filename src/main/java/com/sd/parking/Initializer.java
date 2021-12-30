package com.sd.parking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.jeasy.random.EasyRandom;

import com.sd.handlers.IHandler;
import com.sd.model.Slots;
import com.sd.model.Ticket;
import com.sd.model.Vehicle;
import com.sd.model.VehicleType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Initializer {
	private String instructionFile;

	private Properties prop = new Properties();

	public void start() throws IOException, ClassNotFoundException {
		prop.load(new FileInputStream("application.properties"));
		Integer numberOfSlots = Integer.valueOf(prop.getProperty("noOfSlots"));
		this.instructionFile = prop.getProperty("instructionFile");
		EasyRandom generator = new EasyRandom();

		Utility.writeToFile("tickets.db", new ArrayList<Ticket>());
		List<Slots> slots = generator.objects(Slots.class, numberOfSlots).collect(Collectors.toList());
		Utility.writeToFile("slots.db", slots);

		log.debug("Number of slots: {}", numberOfSlots);
		log.debug("Instruction file: {}", this.instructionFile);
	}

	public void readInstructions() throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		File file = new File(this.instructionFile);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while ((line = br.readLine()) != null) {
			IHandler handler = Utility.instantiate("com.sd.handlers." + line.split(":")[0], IHandler.class);

			switch (handler.getClass().getSimpleName()) {
			case "Entry":
			case "Exit":
				switch (Integer.valueOf(line.split(":")[3])) {
				case 2:
					handler.handle(new Date(), new Vehicle(line.split(":")[2], VehicleType.TWO_WHEELER));
					break;
				case 4:
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(line.split(":")[1]));
					handler.handle(cal.getTime(), new Vehicle(line.split(":")[2], VehicleType.FOUR_WHEELER));
					break;
				default:
					log.error("Not a valid vehicle type");
				}
				break;

			case "Search":
				Method method = handler.getClass().getMethod(line.split(":")[1], String.class);
				method.invoke(handler, line.split(":")[2]);
				break;

			default:
				log.error("Not valid instruction");
			}

		}
		fr.close();
		br.close();
	}
}
