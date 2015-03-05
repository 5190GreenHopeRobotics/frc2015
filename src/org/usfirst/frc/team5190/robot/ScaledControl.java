package org.usfirst.frc.team5190.robot;

public class ScaledControl {

	/**
	 * Squares the power while preserving sign. Gives finer control while at low
	 * power while still giving the full power range.
	 * 
	 * @param power
	 *            The power to square. Should be in the range from -1 to 1.
	 */
	public static double squared(double power) {
		if (power >= 0.0) {
			power = (power * power);
		} else {
			power = -(power * power);
		}
		return power;
	}

}
