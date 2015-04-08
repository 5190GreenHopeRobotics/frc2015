package org.usfirst.frc.team5190.robot.oi;

import org.usfirst.frc.team5190.robot.joystick.LogitechGamepad;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * 
 * @author Tony Zhang
 * @description Operate robot with two gamepads
 */
public class TwoGamepadOI extends AbstractOI {
	private Joystick gamepadDrive;
	private Joystick gamepadShoot;

	public TwoGamepadOI() {
		// set gamepads equal to ports
		this(0, 1);
	}

	public TwoGamepadOI(int gamepadDrivePort, int gamepadShootPort) {
		// set gamepads equal to ports
		gamepadDrive = new Joystick(gamepadDrivePort);
		gamepadShoot = new Joystick(gamepadShootPort);

		initializeButtons();
	}

	/**
	 * @return Gamepad Drive right joystick, y axis
	 */
	@Override
	public double getForwardReverseAxis() {
		return -gamepadDrive.getRawAxis(LogitechGamepad.LEFT_JOYSTICK_Y_AXIS);

	}

	/**
	 * @return gamepad Drive right joystick, x axis
	 */
	@Override
	public double getLeftRightAxis() {
		return -gamepadDrive.getRawAxis(LogitechGamepad.RIGHT_JOYSTICK_X_AXIS);
	}

	/**
	 * @return speed from throttle on joystick (slider)
	 */
	public double getFlightStickSpeed() {
		return 1.0;
//		return (flightStickDrive.getThrottle() + 1.0) / 2.0;
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

	@Override
	protected Button getLevelUpButton() {
		return new JoystickButton(gamepadShoot, LogitechGamepad.B_BUTTON);
	}

	@Override
	protected Button getLevelDownButton() {
		return new JoystickButton(gamepadShoot, LogitechGamepad.A_BUTTON);
	}

	@Override
	protected Button getZeroPawlButton() {
		return new JoystickButton(gamepadShoot, LogitechGamepad.RIGHT_BUMPER);
	}

	@Override
	protected Button getKillButton() {
		return new JoystickButton(gamepadDrive, LogitechGamepad.START);
	}

	@Override
	protected Button getMoarPowahButton() {
		return new JoystickButton(gamepadDrive, LogitechGamepad.LEFT_BUMPER);
	}

}
