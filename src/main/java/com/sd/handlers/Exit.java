package com.sd.handlers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.sd.model.Price;
import com.sd.model.Slots;
import com.sd.model.Ticket;
import com.sd.model.Vehicle;
import com.sd.parking.Utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Exit implements IHandler {

	@Override
	public void updateSlot(List<Slots> slots) throws IOException {
		log.debug("Updating slots db!");
		Utility.writeToFile("slots.db", slots);
	}

	@Override
	public void updateTicket(List<Ticket> tickets) throws IOException {
		log.debug("Updating tickets db!");
		Utility.writeToFile("tickets.db", tickets);
	}

	@Override
	public void handle(Date exitTime, Vehicle vehicle) throws ClassNotFoundException, IOException {
		Slots slot = getSlotIfVehicleIsParked(vehicle);
		if (slot == null) {
			log.error("{} vehicle with vehicle number {} is not parked in any slot!", vehicle.getVehicleType(),
					vehicle.getVehicleNumber());
		} else {
			log.info("Exiting vehicle {} from  slot {}", vehicle.getVehicleNumber(), slot.getSlotNumber());
			inValidateTicket(exitTime, vehicle);
		}
	}

	public int getTotalHoursParked(Date exitTime, Ticket ticket) {
		Date parkTime = ticket.getEntryTimeOfVehicle();
		long diffInMillies = Math.abs(exitTime.getTime() - parkTime.getTime());
		long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return (int) diff;
	}

	public int getExitPriceForVehicle(Date exitTime, Ticket ticket) {
		return getTotalHoursParked(exitTime, ticket);
	}

	private void inValidateTicket(Date exitTime, Vehicle vehicle) throws ClassNotFoundException, IOException {
		log.debug("Invalidating ticket");
		Ticket ticket = getTicketIfValid(vehicle);
		if (ticket == null) {
			log.error("Ticket for vehicle {} is not valid!", vehicle.getVehicleNumber());
		} else {
			log.debug("Invalidated ticket for vehicle {}", vehicle.getVehicleNumber());
			int parkedHours = getExitPriceForVehicle(exitTime, ticket);
			;
			log.info("Ticket cost for parking {} vehicle {} for {} hours is {} /- INR", vehicle.getVehicleType(),
					vehicle.getVehicleNumber(), parkedHours,
					(new Price().getPricePerHour(ticket.getVehicle().getVehicleType()) * parkedHours));
		}
	}

	public Slots getSlotIfVehicleIsParked(Vehicle vehicle) throws IOException, ClassNotFoundException {
		List<Slots> slots = Utility.readFromFile("slots.db", Slots.class);
		Slots slotParked = null;

		for (Slots s : slots) {
			if (!s.isSlotAvailable() && vehicle.getVehicleType() == s.getSlotType()
					&& vehicle.getVehicleNumber().equals(s.getVehicle().getVehicleNumber())) {
				s.setSlotAvailable(true);
				s.setVehicle(new Vehicle(null, null));
				slotParked = s;
				updateSlot(slots);
				break;
			}
		}
		return slotParked;
	}

	public Ticket getTicketIfValid(Vehicle vehicle) throws IOException, ClassNotFoundException {
		List<Ticket> tickets = Utility.readFromFile("tickets.db", Ticket.class);
		Ticket foundTicket = null;

		for (Ticket t : tickets) {
			if (vehicle.getVehicleNumber().equals(t.getVehicle().getVehicleNumber())) {
				t.setValidTicket(false);
				foundTicket = t;
				updateTicket(tickets);
				break;
			}
		}
		return foundTicket;
	}
}
