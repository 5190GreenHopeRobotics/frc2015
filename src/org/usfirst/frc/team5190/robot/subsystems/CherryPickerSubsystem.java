package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.joystick.CherryPickerJoystickCommand;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CherryPickerSubsystem extends Subsystem implements Displayable {
	private static CherryPickerSubsystem instance;

	private static final double KEEP_CHERRY_PICKER_RETRACTED_POWER = -0.1;

	// DIO ports temporary in this class.
	private Talon cherryPickerController;
	private Counter minLimitSwitch;
	private Counter maxLimitSwitch;
	private ArmSubsystem armSubsystem = ArmSubsystem.getInstance();

	private CherryPickerSubsystem() {
		cherryPickerController = new Talon(RobotMap.CHERRY_PICKER_TALON_PORT);
		minLimitSwitch = new Counter(new DigitalInput(
				RobotMap.CHERRY_PICKER_MIN_LIMIT_SWITCH_PORT));
		maxLimitSwitch = new Counter(new DigitalInput(
				RobotMap.CHERRY_PICKER_MAX_LIMIT_SWITCH_PORT));
	}

	public static CherryPickerSubsystem getInstance() {
		if (instance == null) {
			try {
				instance = new CherryPickerSubsystem();
			} catch (Throwable t) {
				t.printStackTrace();
				throw t;
			}
		}
		return instance;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new CherryPickerJoystickCommand());
	}

	public void operate(double speed) {

		set(speed);
	}

	private void set(double power) {
		if (reachedMinLimit()) {
			if (power <= 0) {
				keepRetracted();
			} else {
				minLimitSwitch.reset();
				cherryPickerController.set(power);
			}
		} else if (reachedMaxLimit()) {
			if (power < 0) {
				maxLimitSwitch.reset();
				cherryPickerController.set(power);
			}
		} else if (armNotTooLow()) {
			cherryPickerController.set(power);
		}
	}

	private void keepRetracted() {
		this.cherryPickerController.set(KEEP_CHERRY_PICKER_RETRACTED_POWER);
	}

	private boolean armNotTooLow() {
		return armSubsystem.getAngle() >= 30;
	}

	/**
	 * @return If limit switch on Cherry Picker attachment has been triggered
	 *         Extend Limit (boolean)
	 */
	public boolean reachedMaxLimit() {
		return maxLimitSwitch.get() > 0;
	}

	/**
	 * @return If limit switch on Cherry Picker attachment has been triggered
	 *         Retract Limit (Boolean)
	 */
	public boolean reachedMinLimit() {
		return minLimitSwitch.get() > 0;
	}

	public void stopCherryPicker() {
		this.cherryPickerController.set(0);
	}

	// Display Values
	public void displayValues(Display display) {
		display.putBoolean("CP Max Limit", reachedMaxLimit());
		display.putBoolean("CP Min Limit", reachedMinLimit());
		display.putNumber("CP Power", cherryPickerController.getSpeed());
	}

}
