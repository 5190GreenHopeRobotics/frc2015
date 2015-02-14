package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 * the arm subsystem
 */
public class ArmSubsystem extends Subsystem {
	private Potentiometer armPot = new AnalogPotentiometer(1, 40, 0);
	private TalonSRX armLengthTalon = new TalonSRX(
			RobotMap.ARMLENGTH_TALONSRX_PORT);
	private Jaguar armJaguar1 = new Jaguar(RobotMap.ARM_JAGUAR1_PORT);
	private Jaguar armJaguar2 = new Jaguar(RobotMap.ARM_JAGUAR2_PORT);
	private double motorSpeed = 0.5;
	private Encoder armLengthEncoder = new Encoder(3, 4, false,
			Encoder.EncodingType.k4X);
	private DigitalInput armMax = new DigitalInput(
			RobotMap.ARM_MAX_LIMIT_SWITCH_PORT);
	private DigitalInput armMin = new DigitalInput(
			RobotMap.ARM_MIN_LIMIT_SWITCH_PORT);
	private DigitalInput armReachedLimitStop = new DigitalInput(1);
	ArmPIDOutput armPIDOut = new ArmPIDOutput(armJaguar1, armJaguar2);
	public static final double[] ARM_POWER_RANGE = { -0.2, 0.2 };

	public class SetArmAngle {
		private PIDController armPID;

		private SetArmAngle() {
			armPID = new PIDController(0.3, 0, 0.1, armPot, armPIDOut);
			armPID.setAbsoluteTolerance(1);
			armPID.setOutputRange(ARM_POWER_RANGE[0], ARM_POWER_RANGE[1]);
		}

		public void start(double angle) {
			armPID.setSetpoint(angle);
			armPID.enable();

		}

		public void end() {
			armPID.disable();
		}

		// This asks to see if it has gotten to the distance.
		public boolean reachedAngle() {
			return armPID.onTarget();
		}
	}

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
		armJaguar1.set(motorSpeed);
		armJaguar2.set(motorSpeed);

	}

	/**
	 * This stops the arm angle from changing (No rise or lowering) = speed is
	 * 0.
	 */
	public void stopArm() {
		armJaguar1.set(0);
		armJaguar2.set(0);
	}

	/**
	 * This sets the motorSpeed to negative, lowering the arm.
	 */
	public void lowerArm() {
		armJaguar1.set(-motorSpeed);
		armJaguar2.set(-motorSpeed);

	}

	public boolean getArmMinLimitSwitch() {
		return armMin.get();
	}

	public boolean getArmMaxLimitSwitch() {
		return armMax.get();
	}

	public boolean getencoderdirection() {
		return armLengthEncoder.getDirection();
	}

	public double getangle() {
		return armPot.get();
	}

	public void resetencoder() {
		armLengthEncoder.reset();
	}

	public SetArmAngle setArmAngle() {
		return new SetArmAngle();
	}
}
