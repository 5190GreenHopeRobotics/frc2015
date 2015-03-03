package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.joystick.ArmJoystickCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * the arm subsystem
 */
public class ArmSubsystem extends Subsystem implements Displayable {
	private static ArmSubsystem instance;

	// Levels for arm corresponding with totes, current values are
	// placeholders,
	// need to acquire more math to find real values
	private CANTalon armCANTalonLeft;
	private CANTalon armCANTalonRight;
	private ControlMode controlMode;
	public static final double level0 = 0;
	public static final double level1 = 12.5;
	public static final double level2 = 32.1;
	public static final double level3 = 51.7;
	public static final double level4 = 71.3;

	public static final double[] ARM_POWER_RANGE = { -0.2, 0.2 };

	// private Potentiometer armPot = new AnalogPotentiometer(
	// RobotMap.ARM_CANTALONLEFT_PORT, 40, 0);
	private double motorSpeed = 0.1;

	private ArmSubsystem() {
		controlMode = ControlMode.PercentVbus;
		armCANTalonLeft = new CANTalon(RobotMap.ARM_TALONSRX_LEFT_CAN_ID);
		armCANTalonLeft.enableBrakeMode(true);
		armCANTalonLeft.setFeedbackDevice(FeedbackDevice.AnalogPot);
		armCANTalonLeft.setForwardSoftLimit(400);
		armCANTalonLeft.setReverseSoftLimit(45);
		armCANTalonLeft.enableForwardSoftLimit(true);
		armCANTalonLeft.enableReverseSoftLimit(true);
		armCANTalonLeft.setPID(2, 0.004, 0, 0, 0, 0, 0);
		armCANTalonLeft.changeControlMode(controlMode);
		armCANTalonLeft.set(0);

		armCANTalonRight = new CANTalon(RobotMap.ARM_TALONSRX_RIGHT_CAN_ID);
		armCANTalonRight.changeControlMode(ControlMode.Follower);
		armCANTalonRight.set(RobotMap.ARM_TALONSRX_LEFT_CAN_ID);
		armCANTalonRight.reverseOutput(true);
		armCANTalonRight.enableBrakeMode(true);
	}

	public static ArmSubsystem getInstance() {
		if (instance == null) {
			try {
				instance = new ArmSubsystem();
			} catch (Throwable t) {
				t.printStackTrace();
				throw t;
			}
		}
		return instance;
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new ArmJoystickCommand());
	}

	/**
	 * This raises the arm by using motorSpeed (positive value).
	 */
	public void raiseArm() {
		armCANTalonLeft.set(motorSpeed);

	}

	/**
	 * This stops the arm angle from changing (No rise or lowering) = speed is
	 * 0.
	 */
	public void stopArm() {
		armCANTalonLeft.set(0);
		;
	}

	public void moveArm(double power) {
		if (power > 0.05 || power < -0.05) {
			if (controlMode != ControlMode.PercentVbus) {
				armCANTalonLeft.changeControlMode(ControlMode.PercentVbus);
				controlMode = ControlMode.PercentVbus;
			}
			armCANTalonLeft.set(power);
		} else {
			if (controlMode != ControlMode.Position) {
				armCANTalonLeft.changeControlMode(ControlMode.Position);
				armCANTalonLeft.set(getAngle());
				controlMode = ControlMode.Position;
			}
		}
	}

	/**
	 * This sets the motorSpeed to negative, lowering the arm.
	 */
	public void lowerArm() {
		armCANTalonLeft.set(motorSpeed);
	}

	public double getAngle() {
		return armCANTalonLeft.getPosition();
	}

	public void setArmAngle(double positionAngle) {
		if (controlMode != ControlMode.Position) {
			armCANTalonLeft.changeControlMode(ControlMode.Position);
			controlMode = ControlMode.Position;
		}
		armCANTalonLeft.set(positionAngle);
	}

	@Override
	public void displayValues(Display display) {
		display.putNumber("Arm Angle", getAngle());
	}

	// Set a level for quick tote stacking
	// arm is 31 inches long
	// arm is 30.5 inches off the ground
	// Tote is 12.1 inches tall(11.5 to hold)
	// Level 1 = 23.6 inches high, 6.9 inches below arm
	// Level 2 = 35.7 inches high
	public void setLevel(int level) {

		// if (level < 0) {
		// level = 0;
		// }
		// if (level > 4) {
		// level = 4;
		// }
		//
		// switch (level) {
		// case 0:
		// setArmAngle().start(level0);
		// break;
		// case 1:
		// setArmAngle().start(level1);
		// break;
		// case 2:
		// setArmAngle().start(level2);
		// break;
		// case 3:
		// setArmAngle().start(level3);
		// break;
		// case 4:
		// setArmAngle().start(level4);
		// }
	}

	public void levelup() {
		// double nextlevel = 0;
		//
		// if (getAngle() < level1) {
		// nextlevel = level1;
		// } else if (getAngle() < level2) {
		// nextlevel = level2;
		// } else if (getAngle() < level3) {
		// nextlevel = level3;
		// } else {
		// nextlevel = level4;
		// }
		//
		// setArmAngle().start(nextlevel);

	}

	public void leveldown() {
		// double previouslevel = 0;
		//
		// if (getAngle() > level3) {
		// previouslevel = level3;
		// } else if (getAngle() > level2) {
		// previouslevel = level2;
		// } else if (getAngle() > level1) {
		// previouslevel = level1;
		// } else {
		// previouslevel = 0;
		// }
		//
		// setArmAngle().start(previouslevel);

	}
}
