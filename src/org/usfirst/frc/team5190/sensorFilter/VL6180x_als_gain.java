package org.usfirst.frc.team5190.sensorFilter;

public enum VL6180x_als_gain { // Data sheet shows gain values as binary list

	GAIN_20(0), // Actual ALS Gain of 20
	GAIN_10(1), // Actual ALS Gain of 10.32
	GAIN_5(2), // Actual ALS Gain of 5.21
	GAIN_2_5(3), // Actual ALS Gain of 2.60
	GAIN_1_67(4), // Actual ALS Gain of 1.72
	GAIN_1_25(5), // Actual ALS Gain of 1.28
	GAIN_1(6), // Actual ALS Gain of 1.01
	GAIN_40(7); // Actual ALS Gain of 40

	private int value;

	private VL6180x_als_gain(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
