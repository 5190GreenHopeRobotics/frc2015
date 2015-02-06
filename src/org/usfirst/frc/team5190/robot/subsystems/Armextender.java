package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Armextender extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private TalonSRX armextendertalon = new TalonSRX(
			RobotMap.ARM_EXTEND_TALONSRX_PORT);
	private Encoder armextenderEncoder = new Encoder(5, 6, false,
			Encoder.EncodingType.k4X);
	private DigitalInput armextendlimitswitch = new DigitalInput(
			RobotMap.ARM_EXTEND_LIMIT_SWITCH_PORT);
	private DigitalInput armretractlimitswitch = new DigitalInput(
			RobotMap.ARM_RETRACT_LIMIT_SWITCH_PORT);
	private final double motorspeed = 0.5;
	public double maxextension = 10; // Fix this when you get a real value

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public Armextender() {
		armextenderEncoder.setMaxPeriod(.1);
		armextenderEncoder.setMinRate(10);
		armextenderEncoder.setDistancePerPulse(0.075);
		armextenderEncoder.setReverseDirection(true);
		armextenderEncoder.setSamplesToAverage(7);
	}

	public void extendarm() {
		armextendertalon.set(motorspeed);
	}

	public void retractarm() {
		armextendertalon.set(motorspeed);
	}

	public void stopextender() {
		armextendertalon.set(0);
	}

	public boolean getextendlimitswitch() {
		return armextendlimitswitch.get();
	}

	public boolean getretractlimitswitch() {
		return armretractlimitswitch.get();
	}

	public boolean getencoderdirection() {
		return armextenderEncoder.getDirection();
	}

	public double getencoderdistance() {
		return armextenderEncoder.getDistance();
	}

	public void resetencoder() {
		armextenderEncoder.reset();
	}

}
