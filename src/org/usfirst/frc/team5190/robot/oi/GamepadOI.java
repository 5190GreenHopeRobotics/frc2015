package org.usfirst.frc.team5190.robot.oi;

import org.usfirst.frc.team5190.robot.joystick.LogitechGamepad;

import edu.wpi.first.wpilibj.Joystick;

public class GamepadOI implements OI {

	private Joystick gamepad;

	public GamepadOI() {
		this(0);
	}

	public GamepadOI(int port) {
		gamepad = new Joystick(port);
	}

	public Joystick getGamepad() {
		return gamepad;
	}

	@Override
	public double getForwardReverseAxis() {
		return -gamepad.getRawAxis(LogitechGamepad.LEFT_JOYSTICK_Y_AXIS);
	}

	@Override
	public double getLeftRightAxis() {
		return -gamepad.getRawAxis(LogitechGamepad.LEFT_JOYSTICK_X_AXIS);
	}

	@Override
	public double getArmAxis() {
		return -gamepad.getRawAxis(LogitechGamepad.RIGHT_JOYSTICK_Y_AXIS);
	}

	@Override
	public double getCherryPickerAxis() {
		double retractValue = gamepad
				.getRawAxis(LogitechGamepad.LEFT_TRIGGER_AXIS);
		double extendValue = gamepad
				.getRawAxis(LogitechGamepad.RIGHT_TRIGGER_AXIS);
		if (retractValue > 0.0) {
			return -retractValue;
		}
		return extendValue;
	}

	@Override
	public double getPawlAxis() {
		return -gamepad.getRawAxis(LogitechGamepad.RIGHT_JOYSTICK_X_AXIS);
	}
}
