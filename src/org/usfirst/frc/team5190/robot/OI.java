package org.usfirst.frc.team5190.robot;

import org.usfirst.frc.team5190.robot.commands.CloseGrabberCommand;
import org.usfirst.frc.team5190.robot.commands.OpenGrabberCommand;
import org.usfirst.frc.team5190.robot.subsystems.RobotGrabberSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class OI {
	
	
	// ports on laptop of Joysticks
	public static final int DRIVESTICK_PORT = 0;
	public static final int SHOOTSTICK_PORT = 1;
	
	
	
	// Button numbers on joystick
	public static final int TRIGGER = 1;
	public static final int THUMB_BUTTON = 2;
	
	
	// Initialize joysticks
	private Joystick driveStick = new Joystick(DRIVESTICK_PORT);
	private Joystick shootStick = new Joystick(SHOOTSTICK_PORT);
	
	
	// Drive Stick button/peripheral initialization
	private Button changeSpeed = new JoystickButton(driveStick, THUMB_BUTTON);
	private double findThrottle = driveStick.getThrottle();
	
	
	// private double findX = driveStick.getX();
	// private double findY = driveStick.getY();
	// private double findZ = driveStick.getZ();
	
	private double findTwist = driveStick.getTwist();
	
	// Shoot stick button links to commands
	
	private Button raiseArmButton = new JoystickButton(shootStick, TRIGGER);
	private Button lowerArmButton = new JoystickButton(shootStick, THUMB_BUTTON);

	public OI(){
		//buttons to link up to commands.
		raiseArmButton.whenPressed(new OpenGrabberCommand());
		lowerArmButton.whenPressed(new CloseGrabberCommand());
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
	 * @return double value from twist motion on joystick Drivestick
	 */
	public double accessTwist() {
		
		return findTwist;
		
	}

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
