package org.usfirst.frc.team5190.robot.oi;

import org.usfirst.frc.team5190.robot.joystick.LogitechExtreme3D;
import org.usfirst.frc.team5190.robot.joystick.LogitechGamepad;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * 
 * @author Alex Chung
 * @description Run Robot with a Joystick for driving and a Logitech Gamepad for
 *              shooting
 */
public class FlightStickWithGamePadOI extends AbstractOI {
	// init joystick and gamepad
	private Joystick flightStickDrive = null;
	private Joystick gamePadShoot = null;

	/**
	 * Use to operate buttons on Gamepad and Joystick
	 */
	public FlightStickWithGamePadOI() {
		this(0, 1);
	}

	/**
	 * 
	 * @param joystickPort
	 * @param gamepadPort
	 */
	public FlightStickWithGamePadOI(int joystickPort, int gamepadPort) {
		this.flightStickDrive = new Joystick(joystickPort);
		this.gamePadShoot = new Joystick(gamepadPort);

		initializeButtons();
	}

	/**
	 * @return backward and forward speed data form logitech Joystick for
	 *         driving
	 * 
	 */
	@Override
	public double getForwardReverseAxis() {
		return (-flightStickDrive.getY()) * (-flightStickDrive.getY())
				* (-flightStickDrive.getY()) * (-flightStickDrive.getY())
				* (-flightStickDrive.getY());
	}

	/**
	 * @return left and right speed data from logitech joystick for driving
	 */
	@Override
	public double getLeftRightAxis() {
		return -flightStickDrive.getRawAxis(4)
				* -flightStickDrive.getRawAxis(4)
				* -flightStickDrive.getRawAxis(4)
				* -flightStickDrive.getRawAxis(4)
				* -flightStickDrive.getRawAxis(4);
	}

	/**
	 * 
	 * @return Z axis (Joystick Twist) from Logitech joystick.
	 */
	public double getJoystickZAxis() {
		return flightStickDrive.getRawAxis(LogitechExtreme3D.Z_ROTATE);
	}

	/**
	 * @return Left stick, y axis from Logitech gamepad for shooting
	 */
	@Override
	public double getArmAxis() {
		return gamePadShoot.getRawAxis(LogitechGamepad.LEFT_JOYSTICK_Y_AXIS);
	}

	/**
	 * @return Right stick, Y axis from logitech gamepad for shooting
	 */
	@Override
	public double getCherryPickerAxis() {
		return gamePadShoot.getRawAxis(LogitechGamepad.RIGHT_JOYSTICK_Y_AXIS);
	}

	/**
	 * @return right stick, y axis from logitech gamepad for shooting.
	 */
	@Override
	public double getPawlAxis() {
		return gamePadShoot.getRawAxis(LogitechGamepad.RIGHT_JOYSTICK_X_AXIS);
	}

	@Override
	protected Button getLevelUpButton() {
		return new JoystickButton(gamePadShoot, LogitechGamepad.B_BUTTON);
	}

	@Override
	protected Button getLevelDownButton() {
		return new JoystickButton(gamePadShoot, LogitechGamepad.A_BUTTON);
	}

	@Override
	protected Button getZeroPawlButton() {
		return new JoystickButton(gamePadShoot, LogitechGamepad.RIGHT_BUMPER);
	}

	@Override
	protected Button getKillButton() {
		return new JoystickButton(gamePadShoot, LogitechGamepad.START);
	}

	@Override
	protected Button getMoarPowahButton() {
		return new JoystickButton(gamePadShoot, LogitechGamepad.LEFT_BUMPER);
	}

}
