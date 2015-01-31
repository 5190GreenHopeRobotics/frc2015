package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */

public class ArmextenderPIDSubsystem extends PIDSubsystem {
	public DigitalInput limitswitchretracted = new DigitalInput(1);
	public DigitalInput limitswitchextended = new DigitalInput(2);
	private Encoder armExtenderEncoder = new Encoder(5, 6, false,
			Encoder.EncodingType.k4X);
	private TalonSRX armExtenderTalon = new TalonSRX(2);

	public ArmextenderPIDSubsystem() {
		super("Armextender", 1.0, 0.0, 0.0);
		setSetpoint(0.0);
		enable();
		armExtenderEncoder.setMaxPeriod(.1);
		armExtenderEncoder.setMinRate(10);
		armExtenderEncoder.setDistancePerPulse(5);
		armExtenderEncoder.setReverseDirection(true);
		armExtenderEncoder.setSamplesToAverage(7);
	}

	public double getEncoderangle() {
		return Math.abs(armExtenderEncoder.get());
	}

	public void stopExtension() {
		setSetpoint(getEncoderangle());
	}

	public void resetEncoder() {
		armExtenderEncoder.reset();
	}

	public boolean getEncoderdirection() {
		return armExtenderEncoder.getDirection();
	}

	protected double returnPIDInput() {
		// Return your input value for the PID loop
		// e.g. a sensor, like a potentiometer:
		// yourPot.getAverageVoltage() / kYourMaxVoltage;
		// Limit switch pressed down is false
		if (getEncoderdirection() == true) {
			return Math.abs(armExtenderEncoder.pidGet());
		} else
			return -Math.abs(armExtenderEncoder.pidGet());
	}

	protected void usePIDOutput(double output) {
		// Use output to drive your system, like a motor
		// e.g. yourMotor.set(output);
		armExtenderTalon.set(output);
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
