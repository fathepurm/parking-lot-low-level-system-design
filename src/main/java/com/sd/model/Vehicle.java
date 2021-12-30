package com.sd.model;

import lombok.Data;

@Data
public class Vehicle {
	String vehicleNumber;
	VehicleType vehicleType;

	public Vehicle(String vehicleNumber, VehicleType vehicleType) {
		this.vehicleNumber = vehicleNumber;
		this.vehicleType = vehicleType;
	}

	public Vehicle() {
	}
}
