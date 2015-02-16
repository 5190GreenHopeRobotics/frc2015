package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 * the arm subsystem
 */
public class ArmSubsystem extends Subsystem {
	public static final double[] ARM_POWER_RANGE = { -0.2, 0.2 };

	private Potentiometer armPot = new AnalogPotentiometer(
			RobotMap.ARM_POTENTIOMETER_PORT, 40, 0);
	private double motorSpeed = 0.1;

	private ArmDrive armDrive;

	public class SetArmAngle {
		private PIDController armPID;

		private SetArmAngle() {
			armPID = new PIDController(0.3, 0, 0.1, armPot, armDrive);
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

	public ArmSubsystem() {
		Jaguar armJaguar1 = new Jaguar(RobotMap.ARM_JAGUAR1_PORT);
		Jaguar armJaguar2 = new Jaguar(RobotMap.ARM_JAGUAR2_PORT);
		DigitalInput armMax = new DigitalInput(
				RobotMap.ARM_MAX_LIMIT_SWITCH_PORT);
		DigitalInput armMin = new DigitalInput(
				RobotMap.ARM_MIN_LIMIT_SWITCH_PORT);
		armDrive = new ArmDrive(armJaguar1, armJaguar2, armMax, armMin);
	}

	// the shaft
	/**
	 * nothing needs to go here.
	 */
	public void initDefaultCommand() {

	}

	/**
	 * This raises the arm by using motorSpeed (positive value).
	 */
	public void raiseArm() {
		armDrive.set(motorSpeed);

	}

	/**
	 * This stops the arm angle from changing (No rise or lowering) = speed is
	 * 0.
	 */
	public void stopArm() {
		armDrive.stopArm();
	}

	public void moveArm(double power) {
		armDrive.set(power);
	}

	/**
	 * This sets the motorSpeed to negative, lowering the arm.
	 */
	public void lowerArm() {
		armDrive.set(-motorSpeed);
	}

	public double getAngle() {
		return armPot.get();
	}

	public SetArmAngle setArmAngle() {
		return new SetArmAngle();
	}
}
