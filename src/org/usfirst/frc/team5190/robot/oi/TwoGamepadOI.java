package org.usfirst.frc.team5190.robot.oi;

import org.usfirst.frc.team5190.robot.commands.ArmLevelDownCommand;
import org.usfirst.frc.team5190.robot.commands.ArmLevelUpCommand;
import org.usfirst.frc.team5190.robot.commands.ZeroPawlCommand;
import org.usfirst.frc.team5190.robot.joystick.LogitechGamepad;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * 
 * @author Tony Zhang
 * @description Operate robot with two gamepads
 */
public class TwoGamepadOI implements OI {
	// initailize gamepads
	private Joystick gamepadDrive;
	private Joystick gamepadShoot;
	// initialize buttons
	private Button zeroPawlButton;

	/**
	 * Call on normal constructor in program
	 */
	public TwoGamepadOI() {
		// set gamepads equal to ports
		this(0, 1);
	}

	/**
	 * 
	 * @param gamepad1port
	 * @param gamepad2port
	 */
	public TwoGamepadOI(int gamepadDrivePort, int gamepadShootPort) {
		// set gamepads equal to ports
		gamepadDrive = new Joystick(gamepadDrivePort);
		gamepadShoot = new Joystick(gamepadShootPort);
		// initialize buttons
		zeroPawlButton = new JoystickButton(gamepadShoot,
				LogitechGamepad.RIGHT_BUMPER);
		zeroPawlButton.whenPressed(new ZeroPawlCommand());
		// do actions with buttons
		JoystickButton levelUpCommand = new JoystickButton(gamepadShoot,
				LogitechGamepad.B_BUTTON);
		levelUpCommand.whenPressed(new ArmLevelUpCommand(true));
		JoystickButton levelDownCommand = new JoystickButton(gamepadShoot,
				LogitechGamepad.A_BUTTON);
		levelDownCommand.whenPressed(new ArmLevelDownCommand(true));

		// Arm Test Stuff
		// JoystickButton Level0Button = new JoystickButton(gamepad2,
		// LogitechGamepad.X_BUTTON);
		// JoystickButton Level1Button = new JoystickButton(gamepad2,
		// LogitechGamepad.A_BUTTON);
		// JoystickButton Level2Button = new JoystickButton(gamepad2,
		// LogitechGamepad.B_BUTTON);
		// JoystickButton Level3Button = new JoystickButton(gamepad2,
		// LogitechGamepad.Y_BUTTON);
		// JoystickButton Level4Button = new JoystickButton(gamepad2,
		// LogitechGamepad.A_BUTTON)
	}

	/**
	 * @return Gamepad Drive right joystick, y axis
	 */
	@Override
	public double getForwardReverseAxis() {
		return OIUtils.zeroSmallValues(0.05,
				-gamepadDrive.getRawAxis(LogitechGamepad.LEFT_JOYSTICK_Y_AXIS));

	}

	/**
	 * @return gamepad Drive right joystick, x axis
	 */
	@Override
	public double getLeftRightAxis() {
		return OIUtils
				.zeroSmallValues(0.05, -gamepadDrive
						.getRawAxis(LogitechGamepad.RIGHT_JOYSTICK_X_AXIS));
	}

	/**
	 * @return gamepad Shoot Left Joystick, y axis
	 */
	@Override
	public double getArmAxis() {
		return OIUtils.zeroSmallValues(0.05,
				-gamepadShoot.getRawAxis(LogitechGamepad.LEFT_JOYSTICK_Y_AXIS));
	}

	/**
	 * @return retract or extend value for cherry picker.
	 */
	@Override
	public double getCherryPickerAxis() {
		double retractValue = gamepadShoot
				.getRawAxis(LogitechGamepad.LEFT_TRIGGER_AXIS);
		double extendValue = gamepadShoot
				.getRawAxis(LogitechGamepad.RIGHT_TRIGGER_AXIS);
		if (retractValue > 0.0) {
			return -retractValue;
		}
		return extendValue;
	}

	/**
	 * @return shooting gamepad right joystick, x axis
	 */
	@Override
	public double getPawlAxis() {
		return OIUtils
				.zeroSmallValues(0.05, -gamepadShoot
						.getRawAxis(LogitechGamepad.RIGHT_JOYSTICK_X_AXIS));
	}

	/**
	 * 
	 * @return gamepad shoot Right bumper,
	 */
	public boolean zeroPawlButton() {
		return gamepadShoot.getRawButton(LogitechGamepad.RIGHT_BUMPER);
	}

}
