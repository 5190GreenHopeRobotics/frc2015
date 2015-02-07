package org.usfirst.frc.team5190.robot;

import org.usfirst.frc.team5190.robot.commands.EnableRobotCommand;

import org.usfirst.frc.team5190.robot.commands.PrototypeArmLower;
import org.usfirst.frc.team5190.robot.commands.PrototypeArmRaise;
import org.usfirst.frc.team5190.robot.commands.TerminateRobotCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	int test = 0;
	// test
	// ports on laptop of Joysticks
	public static final int DRIVESTICK_PORT = 0;
	public static final int SHOOTSTICK_PORT = 1;

	// Button numbers on joystick
	public static final int TRIGGER = 1;
	public static final int THUMB_BUTTON = 2;
	public static final int OPENFORKLIFT_BUTTON = 6;
	public static final int CLOSEFORKLIFT_BUTTON = 5;
	// public static final int EXTENDARM_BUTTON = 4;
	// public static final int RETRACTARM_BUTTON = 6;
	public static final int KILL_SWITCH = 11;
	public static final int ENABLE_SWITCH = 12;

	public static final int PNEUMATICS_TEST = 7;

	public static final int ADD_SERVO = 7;
	public static final int SUB_SERVO = 8;
	public static final int RESET_ENCODER = 10;

	// Initialize joysticks
	private Joystick driveStick = new Joystick(DRIVESTICK_PORT);
	private Joystick shootStick = new Joystick(SHOOTSTICK_PORT);

	// Drive Stick button/peripheral initialization
	private Button killSwitch = new JoystickButton(driveStick, KILL_SWITCH);
	private Button enableSwitch = new JoystickButton(driveStick, ENABLE_SWITCH);

	private Button resetEncoder = new JoystickButton(driveStick, RESET_ENCODER);
	// Arm prototype
	private Button raisearm = new JoystickButton(driveStick,
			OPENFORKLIFT_BUTTON);
	private Button lowerarm = new JoystickButton(driveStick,
			CLOSEFORKLIFT_BUTTON);



	// Just a test for Pneumatics
	private Button PneumaticButton = new JoystickButton(driveStick,
			PNEUMATICS_TEST);

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

		// resets the encoders
		// resetEncoder.whenPressed(new ResetEncoderCommand());

		// stops the drive train when pressed
		killSwitch.whenPressed(new TerminateRobotCommand());

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
		//PneumaticButton.whileHeld(new PneumaticsProofOfConcept());
		killSwitch.whenPressed(new TerminateRobotCommand()); // kill robot


		// =======
		// // killSwitch.whenReleased(new TerminateRobotCommand()); // kill
		// robot
		// // after release
		// enableSwitch.whenReleased(new LightsOn()); // undo kill after
		// // relea
		// killSwitch.whenPressed(new getEncoderProofOfConcept()); // kill robot
		// >>>>>>> branch 'master' of
		// https://github.com/5190GreenHopeRobotics/frc2015.git
		// // after
		// // release
		enableSwitch.whenPressed(new EnableRobotCommand()); // undo kill
		// after
		// release

		// Raise Prototype Arm
		raisearm.whileHeld(new PrototypeArmRaise());

		// Lower Prototype Arm
		lowerarm.whileHeld(new PrototypeArmLower());

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
