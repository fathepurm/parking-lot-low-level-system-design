package com.sd.model;

import java.util.Date;

import lombok.Data;

@Data
public class Ticket {
	private String ticketId;
	private Date entryTimeOfVehicle;
	private Vehicle vehicle;
	private boolean isValidTicket;

	public Ticket(String ticketId, Date entryTimeOfVehicle, Vehicle vehicle, boolean isValidTicket) {
		super();
		this.ticketId = ticketId;
		this.entryTimeOfVehicle = entryTimeOfVehicle;
		this.vehicle = vehicle;
		this.isValidTicket = isValidTicket;
	}

	public Ticket() {
	}
}
