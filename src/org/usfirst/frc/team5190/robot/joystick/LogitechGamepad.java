package org.usfirst.frc.team5190.robot.joystick;

import edu.wpi.first.wpilibj.Joystick;

public class LogitechGamepad {
	// gamepad.getZ() = left trigger backwards
	// gamepad.getX() = left joystick x
	// gamepad.getY() = left joystick y
	// gamepad.getThrottle = right trigger forwards
	// gamepad.getTwist() = left trigger backwards
	// gamepad.getrawaxis(1) = left joystick y
	// gamepad.getrawaxis(2) = left trigger backwards
	// gamepad.getrawaxis(3) = right trigger backwards
	// gamepad.getrawaxis(4) = right joystick x backwards
	// gamepad.getrawaxis(5) = right joystick y forwards
	// gamepad.getrawaxis(6) = Nothing

	// A is 1
	// B is 2
	// X is 3
	// Y is 4
	// Left Bumper is 5
	// Right Bumper is 6
	// Back is 7
	// Start is 8
	// Left stick is 9
	// Right stick is 10

	private Joystick gamepad;

	public LogitechGamepad(int joystickport) {
		gamepad = new Joystick(joystickport);
	}

	public double getlefttrigger() {
		return -gamepad.getZ();
	}

	public double getrighttrigger() {
		return -gamepad.getRawAxis(3);
	}

	public double getLeftJoystickY() {
		return gamepad.getRawAxis(1);
	}

	public double getLeftJoystickX() {
		return gamepad.getX();
	}

	public double getRightJoystickY() {
		return gamepad.getRawAxis(5);
	}

	public double getRightJoystick() {
		return -gamepad.getRawAxis(4);
		return gamepad.
	}

	public Joystick getJoystick() {
		return gamepad;
	}
}
