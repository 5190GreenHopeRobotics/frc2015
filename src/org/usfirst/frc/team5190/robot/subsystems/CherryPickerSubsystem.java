package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
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
		cherryPickerController = new Talon(RobotMap.CHERRY_PICKER_TALON_PORT);
		minLimitSwitch = new DigitalInput(
				RobotMap.CHERRY_PICKER_MIN_LIMIT_SWITCH_PORT);
		maxLimitSwitch = new DigitalInput(
				RobotMap.CHERRY_PICKER_MAX_LIMIT_SWITCH_PORT);
	}

	public void initDefaultCommand() {

	}

	public void operate(double speed) {
		if (speed < 0 && this.reachedMinLimit()) {
			stopCherryPicker();
		} else if (speed > 0 && this.reachedMaxLimit()) {
			stopCherryPicker();
		} else {
			this.cherryPickerController.set(speed);
		}
	}

	/**
	 * @return If limit switch on Cherry Picker attachment has been triggered
	 *         Extend Limit (boolean)
	 */
	public boolean reachedMaxLimit() {
		return !maxLimitSwitch.get();
	}

	/**
	 * @return If limit switch on Cherry Picker attachment has been triggered
	 *         Retract Limit (Boolean)
	 */
	public boolean reachedMinLimit() {
		return !minLimitSwitch.get();
	}

	public void stopCherryPicker() {
		this.cherryPickerController.set(0);
	}

}
