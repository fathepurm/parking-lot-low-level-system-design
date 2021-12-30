package com.sd.handlers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.sd.model.Slots;
import com.sd.model.Ticket;
import com.sd.model.Vehicle;
import com.sd.parking.Utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Entry implements IHandler {
	private Ticket ticket;
	private boolean duplicateEntry = false;

	@Override
	public void handle(Date date, Vehicle vehicle) throws ClassNotFoundException, IOException {
		Slots slot = checkIfSlotAvailable(vehicle);
		if (slot == null) {
			if (!this.duplicateEntry) {
				log.error("Slot is not available for {} vehicle - {}", vehicle.getVehicleType(), vehicle.getVehicleNumber());
			}
		} else {
			log.info("Assigned slot {}", slot.getSlotNumber());
			this.ticket = new Ticket(UUID.randomUUID().toString(), date, vehicle, true);
			createTicket();
		}
	}

	@Override
	public void updateSlot(List<Slots> slots) throws IOException {
		log.debug("Updating slots db!");
		Utility.writeToFile("slots.db", slots);
	}

	@Override
	public void updateTicket(List<Ticket> tickets) throws IOException, ClassNotFoundException {
		createTicket();
	}

	private boolean duplicateEntry(List<Slots> slots, Vehicle vehicle) {
		for (Slots s : slots) {
			if (vehicle != null && vehicle.getVehicleNumber() != null && s.getVehicle().getVehicleNumber() != null) {
				if (s.getVehicle().getVehicleNumber().equalsIgnoreCase(vehicle.getVehicleNumber())) {
					log.error("Duplicate entry, already same vehicle {} is parked in slot {}", vehicle.getVehicleNumber(),
							s.getSlotNumber());
					return true;
				}
			}
		}

		return false;
	}

	private Slots checkIfSlotAvailable(Vehicle vehicle) throws ClassNotFoundException, IOException {
		List<Slots> slots = Utility.readFromFile("slots.db", Slots.class);
		Slots emptySlot = null;
		this.duplicateEntry = duplicateEntry(slots, vehicle);
		if (this.duplicateEntry) {
			return emptySlot;
		}

		for (Slots s : slots) {
			if (s.isSlotAvailable() && vehicle.getVehicleType() == s.getSlotType()) {
				s.setSlotAvailable(false);
				s.setVehicle(vehicle);
				emptySlot = s;
				updateSlot(slots);
				break;
			}
		}
		return emptySlot;
	}

	private void createTicket() throws ClassNotFoundException, IOException {
		log.info("Created ticket {} for {} vehicle {}", this.ticket.getTicketId(),
				this.ticket.getVehicle().getVehicleType(), this.ticket.getVehicle().getVehicleNumber());
		updateTicketDb();
	}

	private void updateTicketDb() throws ClassNotFoundException, IOException {
		log.debug("Updating ticket db!");
		List<Ticket> tickets = Utility.readFromFile("tickets.db", Ticket.class);
		tickets.add(this.ticket);
		Utility.writeToFile("tickets.db", tickets);

	}
}
