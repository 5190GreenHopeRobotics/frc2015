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
	private Talon cherryPickerController;
	private DigitalInput minLimitSwitch;
	private DigitalInput maxLimitSwitch;

	public CherryPickerSubsystem() {
		cherryPickerController = new Talon(1);
		minLimitSwitch = new DigitalInput(2);
		maxLimitSwitch = new DigitalInput(3);
	}

	public void initDefaultCommand() {

	}

	public void operateWithGamepad(Joystick gamepad) {
		double speed = -1.0 * (gamepad.getY());
		if (!this.reachedMaxLimit()) {
			if (speed < 0) {
				this.cherryPickerController.set(speed);
			}
		} else if (!this.reachedMinLimit()) {
			if (speed > 0) {
				this.cherryPickerController.set(speed);
			}
		}
	}

	/**
	 * @return If limit switch on Cherry Picker attachment has been triggered
	 *         Extend Limit (boolean)
	 */
	public boolean reachedMaxLimit() {
		return maxLimitSwitch.get();
	}

	/**
	 * @return If limit switch on Cherry Picker attachment has been triggered
	 *         Retract Limit (Boolean)
	 */
	public boolean reachedMinLimit() {
		return minLimitSwitch.get();
	}

}
