package org.usfirst.frc.team5190.robot;

import org.usfirst.frc.team5190.robot.joystick.LogitechGamepad;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	// test
	// ports on laptop of Joysticks
	public static final int DRIVESTICK_PORT = 0;
	public static final int SHOOTSTICK_PORT = 1;

	// Button numbers on joystick
	public static final int TRIGGER = 11;
	public static final int THUMB_BUTTON = 2;
	public static final int OPENFORKLIFT_BUTTON = 3;
	public static final int CLOSEFORKLIFT_BUTTON = 5;
	// public static final int EXTENDARM_BUTTON = 4;
	// public static final int RETRACTARM_BUTTON = 6;
	public static final int KILL_SWITCH = 1;
	public static final int ENABLE_SWITCH = 5;

	public static final int ADD_SERVO = 7;
	public static final int SUB_SERVO = 8;
	public static final int RESET_ENCODER = 10;

	// Initialize joysticks
	// private Joystick driveStick = new Joystick(DRIVESTICK_PORT);
	private LogitechGamepad mygamepad = new LogitechGamepad(DRIVESTICK_PORT);
	private Joystick shootStick = new Joystick(SHOOTSTICK_PORT);
	private Joystick gamepad = new Joystick(10); // temporary port

	// Drive Stick button/peripheral initialization
	// private Button killSwitch = new JoystickButton(driveStick, KILL_SWITCH);

	// Arm prototype

	// Shoot stick button links to commands
	// private Button raiseArmButton = new JoystickButton(shootStick, TRIGGER);
	// private Button lowerArmButton = new JoystickButton(shootStick,
	// THUMB_BUTTON);
	// private Button openForkliftButton = new JoystickButton(shootStick,
	// OPENFORKLIFT_BUTTON);
	// private Button closeForkliftButton = new JoystickButton(shootStick,
	// CLOSEFORKLIFT_BUTTON);
	// private Button extendArmButton = new JoystickButton(shootStick,
	// EXTENDARM_BUTTON);
	// private Button retractArmButton = new JoystickButton(shootStick,
	// RETRACTARM_BUTTON);

	// Operator interface constructor
	public OI() {

	}

	/**
	 * @return returns driveStick instance
	 */
	public Joystick getDriveStick() {
		return mygamepad.getJoystick();
	}

	/**
	 * @return returns shootStick instance
	 */
	public Joystick getShootStick() {
		return shootStick;
	}

	public Joystick getGamepad() {
		return gamepad;
	}

	/**
	 * @deprecated
	 * @return Backward compatible with legacy Joystick
	 */
	public double getSpeed() {
		double originalValue = mygamepad.getJoystick().getThrottle();
		originalValue -= 1;
		return -1 * (originalValue / 2);
	}
}
