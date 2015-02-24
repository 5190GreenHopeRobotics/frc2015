package org.usfirst.frc.team5190.robot.subsystems;

import java.util.Collection;
import java.util.LinkedList;

import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.smartDashBoard.Displayable;
import org.usfirst.frc.team5190.smartDashBoard.Pair;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 * the arm subsystem
 */
public class ArmSubsystem extends Subsystem implements Displayable {
	// Levels for arm corresponding with totes, current values are
	// placeholders,
	// need to acquire more math to find real values
	private CANTalon armCANTalonLeft;
	private CANTalon armCANTalonRight;
	public static final double level0 = 0;
	public static final double level1 = 12.5;
	public static final double level2 = 32.1;
	public static final double level3 = 51.7;
	public static final double level4 = 71.3;

	public static final double[] ARM_POWER_RANGE = { -0.2, 0.2 };
	private DigitalInput armMaxLimitSwitch;
	private DigitalInput armMinLimitSwitch;

	private Potentiometer armPot = new AnalogPotentiometer(
			RobotMap.ARM_POTENTIOMETER_PORT, 40, 0);
	private double motorSpeed = 0.1;

	public class SetArmAngle {
		private PIDController armPID;

		private SetArmAngle() {
			armPID = new PIDController(0.3, 0, 0.1, armPot, armCANTalonLeft);
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
		armCANTalonLeft = new CANTalon(RobotMap.ARM_CANTALONLEFT_PORT);
		armCANTalonRight = new CANTalon(RobotMap.ARM_CANTALONRIGHT_PORT);
		armCANTalonLeft.changeControlMode(ControlMode.PercentVbus);
		armCANTalonLeft.set(0);
		armCANTalonRight.changeControlMode(ControlMode.Follower);
		armCANTalonRight.set(RobotMap.ARM_CANTALONLEFT_PORT);
		armCANTalonRight.reverseOutput(true);
		armCANTalonLeft.setVoltageRampRate(3.0);
		armMaxLimitSwitch = new DigitalInput(RobotMap.ARM_MAX_LIMIT_SWITCH_PORT);
		armMinLimitSwitch = new DigitalInput(RobotMap.ARM_MIN_LIMIT_SWITCH_PORT);

	}

	// the shaft
	/**
	 * nothing needs to go here.
	 */
	public void initDefaultCommand() {

	}

	public void set(double power) {
		if (power < 0 && !armMinLimitSwitch.get()) {
			stopArm();
		} else if (power > 0 && !armMaxLimitSwitch.get()) {
			stopArm();
		} else {
			armCANTalonLeft.set(power);

		}
	}

	/**
	 * This raises the arm by using motorSpeed (positive value).
	 */
	public void raiseArm() {
		set(motorSpeed);

	}

	/**
	 * This stops the arm angle from changing (No rise or lowering) = speed is
	 * 0.
	 */
	public void stopArm() {
		armCANTalonLeft.set(0);
	}

	public void moveArm(double power) {
		set(power);
	}

	/**
	 * This sets the motorSpeed to negative, lowering the arm.
	 */
	public void lowerArm() {
		set(-motorSpeed);
	}

	public double getAngle() {
		return armPot.get();
	}

	public SetArmAngle setArmAngle() {
		return new SetArmAngle();
	}

	@Override
	public Collection<Pair<String, Boolean>> getBooleanValue() {
		return null;
	}

	@Override
	public Collection<Pair<String, Double>> getDecimalValues() {
		LinkedList<Pair<String, Double>> result = new LinkedList<Pair<String, Double>>();
		result.add(new Pair<String, Double>("Arm Angle is:", getAngle()));
		return result;
	}

	// Set a level for quick tote stacking
	// arm is 31 inches long
	// arm is 30.5 inches off the ground
	// Tote is 12.1 inches tall(11.5 to hold)
	// Level 1 = 23.6 inches high, 6.9 inches below arm
	// Level 2 = 35.7 inches high
	public void setLevel(int level) {

		if (level < 0) {
			level = 0;
		}
		if (level > 4) {
			level = 4;
		}

		switch (level) {
		case 0:
			Robot.armSubsystem.setArmAngle().start(level0);
			break;
		case 1:
			Robot.armSubsystem.setArmAngle().start(level1);
			break;
		case 2:
			Robot.armSubsystem.setArmAngle().start(level2);
			break;
		case 3:
			Robot.armSubsystem.setArmAngle().start(level3);
			break;
		case 4:
			Robot.armSubsystem.setArmAngle().start(level4);
		}
	}

	public void levelup() {
		double nextlevel = 0;

		if (getAngle() < level1) {
			nextlevel = level1;
		} else if (getAngle() < level2) {
			nextlevel = level2;
		} else if (getAngle() < level3) {
			nextlevel = level3;
		} else {
			nextlevel = level4;
		}

		Robot.armSubsystem.setArmAngle().start(nextlevel);

	}

	public void leveldown() {
		double previouslevel = 0;

		if (getAngle() > level3) {
			previouslevel = level3;
		} else if (getAngle() > level2) {
			previouslevel = level2;
		} else if (getAngle() > level1) {
			previouslevel = level1;
		} else {
			previouslevel = 0;
		}

		Robot.armSubsystem.setArmAngle().start(previouslevel);

	}
}
