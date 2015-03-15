package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.joystick.ArmJoystickCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Preferences;

/**
 * the arm subsystem
 */
public class ArmSubsystem extends LifecycleSubsystem implements Displayable {
	private static ArmSubsystem instance;

	private static final int ARM_RANGE = 363;
	private static final int ARM_BOTTOM_OFFSET = 313;

	private static final double ARM_SET_ANGLE_P = 0.5;
	private static final double ARM_SET_ANGLE_I = 0.003;
	private static final double ARM_SET_ANGLE_D = 0;
	private static final int ARM_SET_ANGLE_IZONE = 100;
	private static final double ARM_SET_ANGLE_RAMP_RATE = 0;
	private static final int ARM_SET_ANGLE_PROFILE = 1;

	private static final double ARM_HOLD_P = 1.8;
	private static final double ARM_HOLD_I = 0.003;
	private static final double ARM_HOLD_D = 0;
	private static final int ARM_HOLD_IZONE = 100;
	private static final double ARM_HOLD_RAMP_RATE = 0;
	private static final int ARM_HOLD_PROFILE = 0;

	// Levels for arm corresponding with totes, current values are
	// placeholders,
	// need to acquire more math to find real values
	private CANTalon armCANTalonLeft;
	private CANTalon armCANTalonRight;
	private ControlMode controlMode;
	private int armBottomOffset;
	private int armRange;
	private static final int level0 = 7;
	private static final int level1 = 80;
	private static final int level2 = 152;
	private static final int level3 = 225;
	private static final int level4 = 287;

	private Preferences prefs = Preferences.getInstance();

	public class SetArmAngle {

		private double angle;

		private SetArmAngle(double angle) {
			this.angle = armBottomOffset + angle;

			System.out.println("The Angle Given To Be Set" + this.angle);
		}

		public void start() {
			if (controlMode != ControlMode.Position) {
				armCANTalonLeft.changeControlMode(ControlMode.Position);
				controlMode = ControlMode.Position;
			}
			configureSetAnglePID();
		}

		public void execute() {
			armCANTalonLeft.set(angle);
		}

		/**
		 * stop the pid
		 */
		public void end() {
		}
	}

	private ArmSubsystem() {
		super("ArmSubsystem");

		armBottomOffset = prefs.getInt("arm.bottom.offset", ARM_BOTTOM_OFFSET);
		armRange = prefs.getInt("arm.range", ARM_RANGE);
		armCANTalonLeft = new CANTalon(RobotMap.ARM_TALONSRX_LEFT_CAN_ID);
		controlMode = ControlMode.PercentVbus;
		armCANTalonLeft.changeControlMode(controlMode);
		armCANTalonLeft.set(0);
		armCANTalonLeft.setFeedbackDevice(FeedbackDevice.AnalogPot);
		configureSetAnglePID();
		configureHoldPID();
		armCANTalonLeft.enableBrakeMode(true);
		armCANTalonLeft.setReverseSoftLimit(armBottomOffset);
		armCANTalonLeft.setForwardSoftLimit(armBottomOffset + armRange);
		armCANTalonLeft.enableForwardSoftLimit(true);
		armCANTalonLeft.enableReverseSoftLimit(true);
		armCANTalonLeft.setSafetyEnabled(false);

		armCANTalonRight = new CANTalon(RobotMap.ARM_TALONSRX_RIGHT_CAN_ID);
		armCANTalonRight.changeControlMode(ControlMode.Follower);
		armCANTalonRight.set(RobotMap.ARM_TALONSRX_LEFT_CAN_ID);
		armCANTalonRight.reverseOutput(true);
		armCANTalonRight.enableBrakeMode(true);
		armCANTalonRight.setSafetyEnabled(false);
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
				configureHoldPID();
				armCANTalonLeft.ClearIaccum();
				armCANTalonLeft.changeControlMode(ControlMode.Position);
				armCANTalonLeft.set(armCANTalonLeft.getPosition());
				controlMode = ControlMode.Position;
			}
		}
	}

	/**
	 * Arm Potentiometer position value shifted by the Arm bottom offset. Will
	 * be in the
	 */
	public double getAngle() {
		return armCANTalonLeft.getPosition() - armBottomOffset;
	}

	public SetArmAngle setArmAngle(double positionAngle) {
		return new SetArmAngle(positionAngle);
	}

	private void configureSetAnglePID() {
		double p = prefs.getDouble("arm.set.angle.p", ARM_SET_ANGLE_P);
		double i = prefs.getDouble("arm.set.angle.i", ARM_SET_ANGLE_I);
		double d = prefs.getDouble("arm.set.angle.d", ARM_SET_ANGLE_D);
		double rampRate = prefs.getDouble("arm.set.angle.ramp.rate",
				ARM_SET_ANGLE_RAMP_RATE);
		int izone = prefs.getInt("arm.set.angle.izone", ARM_SET_ANGLE_IZONE);
		int profile = prefs.getInt("arm.set.angle.profile",
				ARM_SET_ANGLE_PROFILE);
		System.out.println("Set Angle P: " + p + " I: " + i + " D: "
				+ " rampRate: " + rampRate + " izone: " + izone + " profile: "
				+ profile);
		armCANTalonLeft.setPID(p, i, d, 0, izone, rampRate, profile);
	}

	private void configureHoldPID() {
		double p = prefs.getDouble("arm.hold.p", ARM_HOLD_P);
		double i = prefs.getDouble("arm.hold.i", ARM_HOLD_I);
		double d = prefs.getDouble("arm.hold.d", ARM_HOLD_D);
		double rampRate = prefs.getDouble("arm.hold.ramp.rate",
				ARM_HOLD_RAMP_RATE);
		int izone = prefs.getInt("arm.hold.izone", ARM_HOLD_IZONE);
		int profile = prefs.getInt("arm.hold.profile", ARM_HOLD_PROFILE);
		System.out.println("Hold P: " + p + " I: " + i + " D: " + " rampRate: "
				+ rampRate + " izone: " + izone + " profile: " + profile);
		armCANTalonLeft.setPID(p, i, d, 0, izone, rampRate, profile);
	}

	@Override
	// Display values
	public void displayValues(Display display) {
		display.putNumber("Arm Angle", getAngle());
		display.putNumber("Arm Level", CurrentLevel());
		display.putNumber("Arm Power", armCANTalonLeft.getSpeed());
		display.putBoolean("Arm High Hard Limit",
				armCANTalonLeft.isFwdLimitSwitchClosed());
		display.putBoolean("Arm Low Hard Limit",
				armCANTalonLeft.isRevLimitSwitchClosed());
		display.putBoolean("Arm High Soft Limit", getAngle() >= armRange);
		display.putBoolean("Arm Low Soft Limit", getAngle() <= 0);

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
			setArmAngle(nextLevel);
			System.out.println("SetAngle To: " + nextLevel);

		} else if (getAngle() < level2) {

			nextLevel = level2;
			setArmAngle(nextLevel);
			System.out.println("SetAngle To: " + nextLevel);

		} else if (getAngle() < level3) {

			nextLevel = level3;
			setArmAngle(nextLevel);
			System.out.println("SetAngle To: " + nextLevel);

		} else {

			nextLevel = level4;
			setArmAngle(nextLevel);
			System.out.println("SetAngle To: " + nextLevel);

		}

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
			setArmAngle(previouslevel);
			System.out.println("SetAngle To: " + previouslevel);

		} else if (getAngle() > level2) {

			previouslevel = level2;
			setArmAngle(previouslevel);
			System.out.println("SetAngle To: " + previouslevel);

		} else if (getAngle() > level1) {

			previouslevel = level1;
			setArmAngle(previouslevel);
			System.out.println("SetAngle To: " + previouslevel);

		} else {

			previouslevel = level0;
			setArmAngle(previouslevel);
			System.out.println("SetAngle To: " + previouslevel);

		}
		return previouslevel;
	}

}
