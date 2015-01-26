package org.usfirst.frc.team5190.robot;

import org.usfirst.frc.team5190.robot.commands.EnableRobotCommand;
import org.usfirst.frc.team5190.robot.commands.LightsOnCommand;
import org.usfirst.frc.team5190.robot.commands.TerminateRobotCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
	public static final int TRIGGER = 1;
	public static final int THUMB_BUTTON = 2;
	public static final int OPENFORKLIFT_BUTTON = 3;
	public static final int LIGHT_ON_BUTTON = 9;
	public static final int CLOSEFORKLIFT_BUTTON = 5;
	public static final int EXTENDARM_BUTTON = 4;
	public static final int RETRACTARM_BUTTON = 6;
	public static final int KILL_SWITCH = 11;
	public static final int ENABLE_SWITCH = 12;
	public static final int ADD_SERVO = 7;
	public static final int SUB_SERVO = 8;

	// Initialize joysticks
	private Joystick driveStick = new Joystick(DRIVESTICK_PORT);
	private Joystick shootStick = new Joystick(SHOOTSTICK_PORT);

	// Drive Stick button/peripheral initialization
	private Button killSwitch = new JoystickButton(driveStick, KILL_SWITCH);
	private Button enableSwitch = new JoystickButton(driveStick, ENABLE_SWITCH);

	// light button
	private Button lightSwitch = new JoystickButton(driveStick, LIGHT_ON_BUTTON);

	/**
	 * init the commands for the buttons
	 */
	public OI() {

		// stops the drive train when pressed
		killSwitch.whenPressed(new TerminateRobotCommand());

		// undo kill
		enableSwitch.whenPressed(new EnableRobotCommand());

		// turn on the led
		lightSwitch.whenPressed(new LightsOnCommand());

	}

	/**
	 * @return returns driveStick instance
	 */
	public Joystick getDriveStick() {
		return driveStick;
	}

	/**
	 * @return returns shootStick instance
	 */
	public Joystick getShootStick() {
		return shootStick;
	}

	/**
	 * get the reading from the slider on the joystick, and convert the reading
	 * to a speed readable by RobotDrive
	 * 
	 * @return the speed
	 */

	public double getSpeed() {
		double originalValue = driveStick.getThrottle();
		originalValue -= 1;
		return -1 * (originalValue / 2);
	}
}
