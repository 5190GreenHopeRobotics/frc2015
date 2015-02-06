package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * the arm subsystem
 */
public class ArmSubsystem extends Subsystem {
	private TalonSRX armLengthTalon = new TalonSRX(
			RobotMap.ARMLENGTH_TALONSRX_PORT);
	private TalonSRX armAngleTalon = new TalonSRX(
			RobotMap.ARMANGLE_TALONSRX_PORT);
	private double motorSpeed = 0.5;
	private Encoder armLengthEncoder = new Encoder(3, 4, false,
			Encoder.EncodingType.k4X);
	// Counterclockwise for getdirection() is true
	private double currentdegrees = 0;
	private DigitalInput armraiseLimitSwitch = new DigitalInput(
			RobotMap.ARM_RAISE_LIMIT_SWITCH_PORT);
	private DigitalInput armlowerLimitSwitch = new DigitalInput(
			RobotMap.ARM_LOWER_LIMIT_SWITCH_PORT);
	private final double shaftcircumference = 0; // Give this a real value when
													// we find the circumference
													// of
													// the shaft

	/**
	 * nothing needs to go here.
	 */
	public void initDefaultCommand() {

	}

	/**
	 * Turns the arm on, and extends it with a positive speed.
	 */

	public ArmSubsystem() {
		armLengthEncoder.setMaxPeriod(.1);
		armLengthEncoder.setMinRate(10);
		armLengthEncoder.setDistancePerPulse(0.075);
		armLengthEncoder.setReverseDirection(true);
		armLengthEncoder.setSamplesToAverage(7);
	}

	/**
	 * extends the arm
	 */
	public void extendArm() {
		armLengthTalon.set(motorSpeed);
	}

	/**
	 * Turns the arm off, by putting the motor to a stop with a speed of 0.
	 */
	public void stopArmLengthChange() {
		armLengthTalon.set(0);
	}

	/**
	 * This sets the speed as negative, retracting the arm.
	 */
	public void retractArm() {
		armLengthTalon.set(-motorSpeed);

	}

	/**
	 * This raises the arm by using motorSpeed (positive value).
	 */
	public void raiseArm() {
		armAngleTalon.set(motorSpeed);

	}

	/**
	 * This stops the arm angle from changing (No rise or lowering) = speed is
	 * 0.
	 */
	public void stopArmAngleChange() {
		armAngleTalon.set(0);
	}

	/**
	 * This sets the motorSpeed to negative, lowering the arm.
	 */
	public void lowerArm() {
		armAngleTalon.set(-motorSpeed);

	}

	public boolean getlowerarmlimitswitch() {
		return armlowerLimitSwitch.get();
	}

	public boolean getraisearmlimitswitch() {
		return armraiseLimitSwitch.get();
	}

	public boolean getencoderdirection() {
		return armLengthEncoder.getDirection();
	}

	public double getencoderangle() {
		currentdegrees = armLengthEncoder.getDistance() / shaftcircumference
				* 360;
		return currentdegrees;
	}

	public void resetencoder() {
		armLengthEncoder.reset();
	}

	public void changeencoderdirection() {
		currentdegrees = 80 - currentdegrees;
		resetencoder();
	}
}
