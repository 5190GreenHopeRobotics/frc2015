package org.usfirst.frc.team5190.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CherryPickerSubsystem extends Subsystem {
	// DIO ports temporary in this class.
	private Talon cherryPickerController = new Talon(1);
	private DigitalInput limitSwitchRetractMinLimit = new DigitalInput(2);
	private DigitalInput limitSwitchExtendMaxLimit = new DigitalInput(3);
	private DigitalInput limitSwitchReachedObject = new DigitalInput(4);

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void operateWithJoystick(Joystick shootStick) {
		// operator can control back & forth along with speed.
		double speed = -1.0 * (shootStick.getY());
		if (!hasCherryPickerReachedAnObject()
				&& !hasCherryPickerReachedExtendLimit()
				&& !hasCherryPickerReachedRetractLimit()) {
			cherryPickerController.set(speed);
		} else {
			cherryPickerController.set(0.0);

			int pressedLimitSwitch = findToggledLimitSwitch();
			switch (pressedLimitSwitch) {
			case 1:
				cherryPickerController.set(speed);
				break;
			case 2:
				cherryPickerController.set((speed - 1) / 2);
				break;
			case 3:
				cherryPickerController.set((speed * 2) - 1);
				break;
			default:
				System.out.println("Something has gone seriously wrong");
				cherryPickerController.set(0);
				break;
			}
		}

		// if extend limit is reached then only allow retract

		// if retract limit is reached then only allow extend

		// if object limit is reached then only allow extend or retract if an
		// override button is pressed

		// if non of the limit switch is reached then allow both extend and
		// retract

	}

	public int findToggledLimitSwitch() {
		int pressedSwitch = 0;
		if (hasCherryPickerReachedAnObject()) {
			pressedSwitch = 1;
		} else if (hasCherryPickerReachedExtendLimit()) {
			pressedSwitch = 2;
		} else if (hasCherryPickerReachedRetractLimit()) {
			pressedSwitch = 3;
		}
		return pressedSwitch;
	}

	public void operateWithGamepad(Joystick gamepad) {
		// double speed = -1.0 * (gamepad.getY());
	}

	/**
	 * @return If limit switch on Cherry Picker attachment has been triggered
	 *         Extend Limit (boolean)
	 */
	public boolean hasCherryPickerReachedExtendLimit() {
		boolean extendLimitReached = !limitSwitchExtendMaxLimit.get();
		return extendLimitReached;
	}

	/**
	 * @return If limit switch on Cherry Picker attachment has been triggered
	 *         Retract Limit (Boolean)
	 */
	public boolean hasCherryPickerReachedRetractLimit() {
		boolean retractLimitReached = !limitSwitchRetractMinLimit.get();
		return retractLimitReached;
	}

	/**
	 * @return If limit switch on Cherry Picker attachment has been triggered by
	 *         reaching an object at the end of the picker (Boolean)
	 */
	public boolean hasCherryPickerReachedAnObject() {
		boolean reachedObject = !limitSwitchRetractMinLimit.get();
		return reachedObject;
	}
}
