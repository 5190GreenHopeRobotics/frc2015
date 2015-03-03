package org.usfirst.frc.team5190.robot.oi;

import org.usfirst.frc.team5190.robot.joystick.LogitechExtreme3D;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class FlightStickOI implements OI {
	// init joystick
	private Joystick flightStickDrive;
	private Joystick flightStickShoot;

	public FlightStickOI() {
		JoystickButton zeroPawl = new JoystickButton(flightStickShoot,
				LogitechExtreme3D.BOTTOM_BUTTON_BOTTOM_LEFT);
	}

	public FlightStickOI(int portDrive, int portShoot) {
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
		return flightStickDrive.getY();
	}

	/**
	 * @return gives the user the X axis value of the joystick
	 */
	@Override
	public double getLeftRightAxis() {
		return flightStickDrive.getX();
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
		return LogitechExtreme3D.Y_AXIS;
	}

	/**
	 * @return get cherry picker speed (y axis on joystick) Recommended that in
	 *         your command, have a button pressed to activate the cherry
	 *         picker.
	 */
	@Override
	public double getCherryPickerAxis() {
		return LogitechExtreme3D.Y_AXIS;
	}

	/**
	 * @return pawl speed via twist from FlightstickShoot
	 */
	@Override
	public double getPawlAxis() {
		return flightStickShoot.getTwist();
	}

	/**
	 * @return set pawl to original position
	 */
	public boolean zeroPawlButton() {
		boolean zeroPawl = false;
		if (flightStickDrive
				.getRawButton(LogitechExtreme3D.UPPER_BUTTON_BOTTOM_LEFT)) {
			zeroPawl = true;
		}
		return zeroPawl;
	}

}
// hail hydra