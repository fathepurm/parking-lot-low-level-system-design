package com.sd.model;

import com.sd.parking.Utility;

import lombok.Data;

@Data
public class Slots {
	static int index = 1;
	Integer slotNumber;
	Vehicle vehicle;
	VehicleType slotType;
	boolean slotAvailable;

	private Slots() {
		this.slotNumber = getSlot();
		this.slotAvailable = true;
		this.slotType = Utility.randomEnum(VehicleType.class);
		this.vehicle = new Vehicle(null, null);
	}

	private int getSlot() {
		return index++;
	}
}
