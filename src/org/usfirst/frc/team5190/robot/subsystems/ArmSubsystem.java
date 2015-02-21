package org.usfirst.frc.team5190.robot.subsystems;

import java.util.Collection;
import java.util.LinkedList;

import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.smartDashBoard.Displayable;
import org.usfirst.frc.team5190.smartDashBoard.Pair;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 * the arm subsystem
 */
public class ArmSubsystem extends Subsystem implements Displayable {
	public static final double[] ARM_POWER_RANGE = { -0.2, 0.2 };

	private Potentiometer armPot = new AnalogPotentiometer(1, 40, 0);
	private Jaguar armJaguar1 = new Jaguar(RobotMap.ARM_JAGUAR1_PORT);
	private Jaguar armJaguar2 = new Jaguar(RobotMap.ARM_JAGUAR2_PORT);
	private double motorSpeed = 0.1;
	private DigitalInput armMax = new DigitalInput(
			RobotMap.ARM_MAX_LIMIT_SWITCH_PORT);
	private DigitalInput armMin = new DigitalInput(
			RobotMap.ARM_MIN_LIMIT_SWITCH_PORT);

	ArmDrive armDrive = new ArmDrive(armJaguar1, armJaguar2);

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
		armJaguar1.set(0);
		armJaguar2.set(0);
	}

	public void joystickControl(Joystick stick) {
		armDrive.set(stick.getY());
		// if (stick.getY() < 0 && !armMin.get()) {
		// stopArm();
		// } else if (stick.getY() > 0 && !armMax.get()) {
		// stopArm();
		// } else {
		// armDrive.set(stick.getY());
		// }
	}

	/**
	 * This sets the motorSpeed to negative, lowering the arm.
	 */
	public void lowerArm() {
		armDrive.set(-motorSpeed);
	}

	public boolean getArmMinLimitSwitch() {
		return armMin.get();
	}

	public boolean getArmMaxLimitSwitch() {
		return armMax.get();
	}

	public double getAngle() {
		return armPot.get();
	}

	public SetArmAngle setArmAngle() {
		return new SetArmAngle();
	}

	@Override
	public Collection<Pair<String, Boolean>> getBooleanValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Pair<String, Double>> getDecimalValues() {
		LinkedList<Pair<String, Double>> result = new LinkedList<Pair<String, Double>>();
		result.add(new Pair<String, Double>("Arm Angle is:", getAngle()));
		return result;
	}
}
