package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.joystick.PawlJoystickCommand;
import org.usfirst.frc.team5190.robot.motor.SmartSpeedController;
import org.usfirst.frc.team5190.robot.motor.SmartSpeedController.ControlMode;
import org.usfirst.frc.team5190.robot.motor.SmartSpeedController.FeedbackDevice;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class PawlSubsystem extends Subsystem implements Displayable {
	public static final double DEFAULT_PAWL_ZERO_OFFSET = -154;
	public static final String PAWL_ZERO_OFFSET_PREF_KEY = "pawl.angle.zero.offset";
	private boolean isLocked;
	private static PawlSubsystem instance;
	private SmartSpeedController smartController;
	private Potentiometer pawlPotentiometer;
	private DigitalInput clutchEngagedSwitch;

	private PawlSubsystem() {
		Preferences preferences = Preferences.getInstance();
		double zeroOffset = preferences.getDouble("pawl.angle.zero.offset",
				DEFAULT_PAWL_ZERO_OFFSET);
		pawlPotentiometer = new AnalogPotentiometer(
				RobotMap.PAWL_POTENTIMETER_PORT, 270, zeroOffset);

		smartController = new SmartSpeedController(new Jaguar(
				RobotMap.PAWL_JAGUAR_PORT));
		smartController.setPID(0.4, 0, 0.1);
		clutchEngagedSwitch = new DigitalInput(
				RobotMap.PAWL_CLUTCH_ENAGED_SWITCH_PORT);

		// set soft limit
		smartController.setPotentiometer(pawlPotentiometer);
		smartController.setFeedbackDevice(FeedbackDevice.Potentiometer);
		smartController.setForwardSoftLimit(20);
		smartController.setReverseSoftLimit(-20);
		smartController.setForwardSoftLimitEnabled(true);
		smartController.setReverseSoftLimitEnabled(true);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new PawlJoystickCommand());
	}

	public void lock() {
		isLocked = true;
		goToAngle(getAngle());
	}

	public void unLock() {
		isLocked = false;
		smartController.disablePid();
	}

	public static PawlSubsystem getInstance() {
		if (instance == null) {
			try {
				instance = new PawlSubsystem();
			} catch (Throwable t) {
				t.printStackTrace();
				throw t;
			}
		}
		return instance;
	}

	public double getAngle() {
		return pawlPotentiometer.get();
	}

	public void goToAngle(double angle) {
		smartController.setControlMode(ControlMode.Angle);
		smartController.set(angle);

	}

	public void disablePid() {
		smartController.disablePid();
	}

	public boolean angleReached() {
		return smartController.isOnTarget();
	}

	public void movePawl(double power) {
		// if (clutchEngaged()) {
		// if (power > 0.05 || power < -0.05) {
		// if (smartController.getControlMode() != ControlMode.PercentVBus) {
		// smartController.setControlMode(ControlMode.PercentVBus);
		// }
		// smartController.set(power);
		// } else {
		// if (smartController.getControlMode() != ControlMode.Angle) {
		// smartController.setControlMode(ControlMode.Angle);
		// smartController.set(getAngle());
		// }
		// }
		// }
		if (smartController.getControlMode() != ControlMode.PercentVBus) {
			smartController.setControlMode(ControlMode.PercentVBus);
		}
		smartController.set(power);
	}

	public void stopPawl() {
		smartController.set(0);
	}

	public boolean clutchEngaged() {
		return clutchEngagedSwitch.get();
	}

	@Override
	public void displayValues(Display display) {
		display.putNumber("Pawl Angle", pawlPotentiometer.get());
		display.putBoolean("Pawl Clutch Engaged", clutchEngagedSwitch.get());
	}

}
