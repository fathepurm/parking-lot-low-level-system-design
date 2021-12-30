package com.sd.handlers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.sd.model.Slots;
import com.sd.model.Ticket;
import com.sd.model.Vehicle;

public interface IHandler {
	public void handle(Date date, Vehicle vehicle) throws ClassNotFoundException, IOException;

	void updateSlot(List<Slots> slots) throws IOException;

	void updateTicket(List<Ticket> tickets) throws IOException, ClassNotFoundException;
}
