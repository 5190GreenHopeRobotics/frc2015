package org.usfirst.frc.team5190.robot;

import org.usfirst.frc.team5190.robot.commands.EnableRobotCommand;
import org.usfirst.frc.team5190.robot.commands.TerminateRobotCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class OI {
	// alex was here
	// ports on laptop of Joysticks
	public static final int DRIVESTICK_PORT = 0;
	public static final int SHOOTSTICK_PORT = 1;

	// Button numbers on joystick
	public static final int TRIGGER = 1;
	public static final int THUMB_BUTTON = 2;
	public static final int OPENFORKLIFT_BUTTON = 3;
	public static final int CLOSEFORKLIFT_BUTTON = 5;
	public static final int EXTENDARM_BUTTON = 4;
	public static final int RETRACTARM_BUTTON = 6;
	public static final int KILL_SWITCH = 11;
	public static final int ENABLE_SWITCH = 12;

	// Initialize joysticks
	private Joystick driveStick = new Joystick(DRIVESTICK_PORT);
	private Joystick shootStick = new Joystick(SHOOTSTICK_PORT);

	// Drive Stick button/peripheral initialization
	private Button killSwitch = new JoystickButton(driveStick, KILL_SWITCH);
	private Button enableSwitch = new JoystickButton(driveStick, ENABLE_SWITCH);

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
		// buttons to link up to commands. (Shootstick)
		// raiseArmButton.whenPressed(new RaiseArmCommand()); // raise/open arm
		// lowerArmButton.whenPressed(new LowerArmCommand()); // lower/close arm

		// openForkliftButton.whileHeld(new OpenForkliftCommand()); // open
		// Forklift/grabber
		// closeForkliftButton.whileHeld(new CloseForkliftCommand()); // close
		// Forklift/grabber

		// extendArmButton.whileHeld(new ExtendArmCommand()); // extends the arm
		// retractArmButton.whileHeld(new RetractArmCommand()); // retracts the
		// arm
		// raiseArmButton.whenPressed(new RaiseArmCommand()); // raise/open arm
		// lowerArmButton.whenPressed(new LowerArmCommand()); // lower/close arm
		// openForkliftButton.whileHeld(new OpenForkliftCommand());
		// closeForkliftButton.whileHeld(new CloseForkliftCommand());
		// extendArmButton.whileHeld(new ExtendArmCommand());
		// retractArmButton.whileHeld(new RetractArmCommand());
		// buttons to link up to commands (Drivestick)
		killSwitch.whenReleased(new TerminateRobotCommand()); // kill robot
																// after release
		enableSwitch.whenReleased(new EnableRobotCommand()); // undo kill after
																// release
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

	public double getSpeed() {
		double originalValue = driveStick.getThrottle();
		originalValue -= 1;
		return -1 * (originalValue / 2);
	}
	//
	// // CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	// // TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
