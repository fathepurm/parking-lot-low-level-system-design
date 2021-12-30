package com.sd.handlers;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.sd.model.Slots;
import com.sd.model.Ticket;
import com.sd.model.Vehicle;
import com.sd.parking.Utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Search implements IHandler {

	@Override
	public void handle(Date date, Vehicle vehicle) throws ClassNotFoundException, IOException {
	}

	@Override
	public void updateSlot(List<Slots> slots) throws IOException {
	}

	@Override
	public void updateTicket(List<Ticket> tickets) throws IOException, ClassNotFoundException {
	}

	public void listSlots(String status) throws ClassNotFoundException, IOException {
		List<Slots> slots = Utility.readFromFile("slots.db", Slots.class);
		Iterator<Slots> ite = slots.iterator();
		log.info("List of slots with {} status:", status);
		while (ite.hasNext()) {
			Slots s = ite.next();
			if (status.equalsIgnoreCase("free")) {
				if (s.isSlotAvailable()) {
					log.info("[Slot: {}, Slot type: {}]", s.getSlotNumber(), s.getSlotType());
				}
			} else if (status.equalsIgnoreCase("busy")) {
				if (!s.isSlotAvailable()) {
					log.info("[Slot: {}, Slot type: {}. Vehicle No.: {}]", s.getSlotNumber(), s.getSlotType(),
							s.getVehicle().getVehicleNumber());
				}
			}
		}
	}

	public void vehicleSlot(String vehicleNumber) throws ClassNotFoundException, IOException {
		log.debug("Searching slot for vehicle {}", vehicleNumber);
		List<Slots> slots = Utility.readFromFile("slots.db", Slots.class);
		Iterator<Slots> ite = slots.iterator();
		boolean foundVehicle = false;

		while (ite.hasNext()) {
			Slots s = ite.next();
			if (vehicleNumber.equalsIgnoreCase(s.getVehicle().getVehicleNumber())) {
				log.info("[Vehicle No.: {} found in slot: {}]", vehicleNumber, s.getSlotNumber());
				foundVehicle = true;
			}
		}

		if (!foundVehicle) {
			log.error("Vehicle {} not parked in any slot".concat(vehicleNumber));
		}
	}

	public void ticketByVehicleNumber(String vehicleNumber) throws ClassNotFoundException, IOException {
		log.debug("Searching tickets for vehicle {}", vehicleNumber);
		List<Ticket> slots = Utility.readFromFile("tickets.db", Ticket.class);
		Iterator<Ticket> ite = slots.iterator();

		log.info("Tickets list for vehicle No. {} are: ", vehicleNumber);

		while (ite.hasNext()) {
			Ticket t = ite.next();
			if (vehicleNumber.equalsIgnoreCase(t.getVehicle().getVehicleNumber())) {
				log.info("[Tickets ID: {}, Ticket Time: {}, Ticket Status: {} ]", t.getTicketId(), t.getEntryTimeOfVehicle(),
						t.isValidTicket() ? "Valid" : "Invalid");
			}
		}
	}

	public void ticketByVehicleType(String vehicleType) throws ClassNotFoundException, IOException {
		log.debug("Searching tickets for vehicle type {}", vehicleType);
		List<Ticket> slots = Utility.readFromFile("tickets.db", Ticket.class);
		Iterator<Ticket> ite = slots.iterator();

		log.info("Tickets list for vehicle type {} are: ", vehicleType);

		vehicleType = vehicleType.equalsIgnoreCase("4") ? "FOUR_WHEELER" : "TWO_WHEELER";
		while (ite.hasNext()) {
			Ticket t = ite.next();
			if (vehicleType.equalsIgnoreCase(t.getVehicle().getVehicleType().toString())) {
				log.info("[Tickets ID: {}, Ticket Time: {}, Vehicle No.: {}, Ticket Status: {} ]", t.getTicketId(),
						t.getEntryTimeOfVehicle(), t.getVehicle().getVehicleNumber(), t.isValidTicket() ? "Valid" : "Invalid");
			}
		}
	}
}
