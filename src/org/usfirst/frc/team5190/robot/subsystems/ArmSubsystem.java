package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.joystick.ArmJoystickCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;

/**
 * the arm subsystem
 */
public class ArmSubsystem extends LifecycleSubsystem implements Displayable {
	private static ArmSubsystem instance;

	// Levels for arm corresponding with totes, current values are
	// placeholders,
	// need to acquire more math to find real values
	private CANTalon armCANTalonLeft;
	private CANTalon armCANTalonRight;
	private ControlMode controlMode;
	protected int lowLimit;
	protected int highLimit;
	public static final double level0 = 320;
	public static final double level1 = 392.5983675;
	public static final double level2 = 465.196735;
	public static final double level3 = 537.7951025;
	public static final double level4 = 610.39347;

	private double motorSpeed = 0.1;

	private ArmSubsystem() {
		super("ArmSubsystem");
		armCANTalonLeft = new CANTalon(RobotMap.ARM_TALONSRX_LEFT_CAN_ID);
		controlMode = ControlMode.PercentVbus;
		armCANTalonLeft.changeControlMode(controlMode);
		armCANTalonLeft.set(0);
		armCANTalonLeft.setFeedbackDevice(FeedbackDevice.AnalogPot);
		armCANTalonLeft.setPID(2, 0.004, 0, 0, 0, 0, 0);
		armCANTalonLeft.enableBrakeMode(true);
		armCANTalonLeft.setForwardSoftLimit(676);
		armCANTalonLeft.setReverseSoftLimit(313);
		armCANTalonLeft.enableForwardSoftLimit(true);
		armCANTalonLeft.enableReverseSoftLimit(true);

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

	public void setLimit(int low, int high) {
		lowLimit = low;
		highLimit = high;
		armCANTalonLeft.setForwardSoftLimit(high);
		armCANTalonLeft.setReverseSoftLimit(low);
	}

	public int getHighLimit() {
		return highLimit;
	}

	public int getLowLimit() {
		return lowLimit;
	}

	/**
	 * move the arm down, won't do anything if hit the reverse limit switch
	 * 
	 * @return if it reached bottom
	 */
	public boolean goToLowest() {
		if (!armCANTalonLeft.isRevLimitSwitchClosed()) {
			moveArm(-0.1);

		}
		return armCANTalonLeft.isRevLimitSwitchClosed();
	}

	/**
	 * move the arm up, wom't do anything if hit the top limit switch
	 * 
	 * @return if reached top
	 */
	public boolean goToHighest() {
		if (!armCANTalonLeft.isFwdLimitSwitchClosed()) {
			moveArm(0.1);
		}
		return armCANTalonLeft.isFwdLimitSwitchClosed();
	}

	@Override
	// Display values
	public void displayValues(Display display) {
		display.putNumber("Arm Angle", getAngle());
		display.putNumber("Arm Level(No platform)", CurrentLevel());
		display.putNumber("Arm Speed", armCANTalonLeft.getEncVelocity());
		display.putBoolean("Arm Top Limit Switch",
				armCANTalonLeft.isFwdLimitSwitchClosed());
		display.putBoolean("Arm Bottom Limit Switch",
				armCANTalonLeft.isRevLimitSwitchClosed());
		display.putBoolean("Arm Enabled", armCANTalonLeft.isAlive());
	}

	@Override
	protected void init() {
	}

	@Override
	protected void disable() {
		armCANTalonLeft.changeControlMode(ControlMode.PercentVbus);
		controlMode = ControlMode.PercentVbus;
		armCANTalonLeft.set(0);
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
		switch (level) {
		case 0:
			setArmAngle(level0);
			break;
		case 1:
			setArmAngle(level1);
			break;
		case 2:
			setArmAngle(level2);
			break;
		case 3:
			setArmAngle(level3);
			break;
		case 4:
			setArmAngle(level4);
		}
	}

	public double levelup() {
		double nextLevel;

		if (getAngle() < level1) {
			nextLevel = level1;
		} else if (getAngle() < level2) {
			nextLevel = level2;
		} else if (getAngle() < level3) {
			nextLevel = level3;
		} else {
			nextLevel = level4;
		}

		setArmAngle(nextLevel);
		return nextLevel;
	}

	public int CurrentLevel() {
		int currentLevel;

		if (getAngle() < level1) {
			currentLevel = 0;
		} else if (getAngle() < level2) {
			currentLevel = 1;
		} else if (getAngle() < level3) {
			currentLevel = 2;
		} else if (getAngle() < level4) {
			currentLevel = 3;
		} else {
			currentLevel = 4;

		}

		return currentLevel;
	}

	public double leveldown() {
		double previouslevel;

		if (getAngle() > level3) {
			previouslevel = level3;
		} else if (getAngle() > level2) {
			previouslevel = level2;
		} else if (getAngle() > level1) {
			previouslevel = level1;
		} else {
			previouslevel = level0;
		}

		setArmAngle(previouslevel);
		return previouslevel;
	}

}
