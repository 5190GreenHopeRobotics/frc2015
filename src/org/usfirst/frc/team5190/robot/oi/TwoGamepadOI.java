package org.usfirst.frc.team5190.robot.oi;

import org.usfirst.frc.team5190.robot.commands.ArmSetAngleCommand;
import org.usfirst.frc.team5190.robot.joystick.LogitechGamepad;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class TwoGamepadOI implements OI {
	private Joystick gamepad1;
	private Joystick gamepad2;

	public TwoGamepadOI() {
		this(0, 1);
	}

	public TwoGamepadOI(int gamepad1port, int gamepad2port) {
		gamepad1 = new Joystick(gamepad1port);
		gamepad2 = new Joystick(gamepad2port);

		JoystickButton setAngleButton = new JoystickButton(gamepad2,
				LogitechGamepad.A_BUTTON);
		setAngleButton.whenPressed(new ArmSetAngleCommand(264, false));
		gamepad1.get
	}

	@Override
	public double getForwardReverseAxis() {
		return gamepad1.getRawAxis(LogitechGamepad.LEFT_JOYSTICK_Y_AXIS);

	}

	@Override
	public double getLeftRightAxis() {
		return gamepad1.getRawAxis(LogitechGamepad.RIGHT_JOYSTICK_X_AXIS);
	}

	@Override
	public double getArmAxis() {
		return -gamepad2.getRawAxis(LogitechGamepad.LEFT_JOYSTICK_Y_AXIS);
	}

	@Override
	public double getCherryPickerAxis() {
		double retractValue = gamepad2
				.getRawAxis(LogitechGamepad.RIGHT_TRIGGER_AXIS);
		double extendValue = gamepad2
				.getRawAxis(LogitechGamepad.LEFT_TRIGGER_AXIS);
		if (retractValue > 0.0) {
			return -retractValue;
		}
		return extendValue;
	}

	@Override
	public double getPawlAxis() {
		// TODO Auto-generated method stub
		return -gamepad2.getRawAxis(LogitechGamepad.RIGHT_JOYSTICK_X_AXIS);
	}

}
