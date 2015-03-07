package org.usfirst.frc.team5190.robot.oi;

import org.usfirst.frc.team5190.robot.joystick.LogitechExtreme3D;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class TwoFlightStickOI implements OI {
	// init joystick
	private Joystick flightStickDrive;
	private Joystick flightStickShoot;
	private double cherryPickerButtonSpeed = 1.0;

	public TwoFlightStickOI() {
		JoystickButton zeroPawl = new JoystickButton(flightStickShoot,
				LogitechExtreme3D.BOTTOM_BUTTON_BOTTOM_LEFT);
	}

	public TwoFlightStickOI(int portDrive, int portShoot) {
		flightStickDrive = new Joystick(portDrive);
		flightStickShoot = new Joystick(portShoot);
	}

	/**
	 * 
	 * @return gives caller the driveStick Joystick for their use.
	 */
	public Joystick getDriveStick() {
		return flightStickDrive;
	}

	/**
	 * 
	 * @return gives caller shootStick for their use.
	 */
	public Joystick getShootStick() {
		return flightStickShoot;
	}

	/**
	 * @return gives the user the Y axis value of the joystick
	 */
	@Override
	public double getForwardReverseAxis() {
		return flightStickDrive.getRawAxis(LogitechExtreme3D.Y_AXIS);
	}

	/**
	 * @return gives the user the X axis value of the joystick
	 */
	@Override
	public double getLeftRightAxis() {
		return flightStickDrive.getRawAxis(LogitechExtreme3D.X_AXIS);
	}

	/**
	 * @return speed from throttle on joystick (slider)
	 */
	public double getFlightStickSpeed() {
		return (flightStickDrive.getThrottle() + 1.0) / 2.0;
	}

	/**
	 * @return Axis for arm (y axis on joystick)
	 */
	@Override
	public double getArmAxis() {
		return flightStickShoot.getRawAxis(LogitechExtreme3D.Y_AXIS);
	}

	/**
	 * @return get cherry picker speed (y axis on joystick) Recommended that in
	 *         your command, have a button pressed to activate the cherry
	 *         picker.
	 */
	@Override
	public double getCherryPickerAxis() {
		return flightStickShoot.getRawAxis(LogitechExtreme3D.Y_AXIS);
	}

	/**
	 * @return pawl speed via twist from FlightstickShoot
	 */
	@Override
	public double getPawlAxis() {
		return flightStickShoot.getRawAxis(LogitechExtreme3D.Z_ROTATE);
	}

}
// hail hydra