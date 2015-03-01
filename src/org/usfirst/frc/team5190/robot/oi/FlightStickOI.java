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
	 * @return speed from throttle on joystick
	 */
	public double getFlightStickSpeed() {
		return (flightStickDrive.getThrottle() + 1.0) / 2.0;
	}

	/**
	 * @return allow the user to move the arm up and down by passing in a
	 *         condition to caller (1Raise, -1Lower, 0stop)
	 */
	@Override
	public double getArmAxis() {
		double condition = 0;
		if (flightStickShoot.getRawButton(LogitechExtreme3D.TRIGGER)
				&& flightStickShoot
						.getRawButton(LogitechExtreme3D.THUMB_BUTTON)) {
			condition = 0; // DO NOTHING
		} else if (flightStickShoot
				.getRawButton(LogitechExtreme3D.THUMB_BUTTON)) {
			condition = -1.0; // LOWER
		} else if (flightStickShoot.getRawButton(LogitechExtreme3D.TRIGGER)) {
			condition = 1.0; // RAISE
		} else {
			condition = 0; // DO NOTHING
		}
		return condition;
	}

	/**
	 * @return get cherry picker speed
	 */
	@Override
	public double getCherryPickerAxis() {
		double cherryPickerSpeed = 0;
		if (flightStickShoot
				.getRawButton(LogitechExtreme3D.UPPER_BUTTON_BOTTOM_LEFT)
				&& flightStickShoot
						.getRawButton(LogitechExtreme3D.UPPER_BUTTON_BOTTOM_RIGHT)) {
			cherryPickerSpeed = 0; // DO NOTHING
		} else if (flightStickShoot
				.getRawButton(LogitechExtreme3D.UPPER_BUTTON_BOTTOM_LEFT)) {
			cherryPickerSpeed = 1; // EXTEND
		} else if (flightStickShoot
				.getRawButton(LogitechExtreme3D.UPPER_BUTTON_BOTTOM_RIGHT)) {
			cherryPickerSpeed = -1; // RETRACT
		} else {
			cherryPickerSpeed = 0; // DO NOTHING
		}
		return cherryPickerSpeed;
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