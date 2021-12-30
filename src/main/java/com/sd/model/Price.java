package com.sd.model;

public class Price {
	public int getPricePerHour(VehicleType vehicleType) {
		switch (vehicleType) {
		case TWO_WHEELER:
			return 100;
		case FOUR_WHEELER:
			return 200;
		default:
			return -1;
		}
	}
}
