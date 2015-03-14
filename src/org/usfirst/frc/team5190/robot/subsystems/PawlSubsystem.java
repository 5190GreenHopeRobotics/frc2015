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
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class PawlSubsystem extends Subsystem implements Displayable {
	public static final double DEFAULT_PAWL_ZERO_OFFSET = -154;
	public static final String PAWL_ZERO_OFFSET_PREF_KEY = "pawl.angle.zero.offset";
	public static final int ENCODER_TICK_DEGREE = 360;
	private boolean isLocked;
	private Encoder encoder;
	private PIDController angleController;
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
		smartController.setPID(0.005, 0, 0);
		clutchEngagedSwitch = new DigitalInput(
				RobotMap.PAWL_CLUTCH_ENAGED_SWITCH_PORT);

		// dummy
		encoder = new Encoder(10, 11);
		encoder.setDistancePerPulse(360 / ENCODER_TICK_DEGREE);
		encoder.reset();

		// set soft limit
		smartController.setEncoder(encoder);
		smartController.setPotentiometer(pawlPotentiometer);
		smartController.setFeedbackDevice(FeedbackDevice.Potentiometer);
		smartController.setForwardSoftLimit(20);
		smartController.setReverseSoftLimit(-20);
		smartController.setForwardSoftLimitEnabled(true);
		smartController.setReverseSoftLimitEnabled(true);

		angleController = new PIDController(0.005, 0, 0, encoder,
				smartController);
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
		if (smartController.getControlMode() != ControlMode.PercentVBus) {
			smartController.setControlMode(ControlMode.PercentVBus);
		}
		angleController.setSetpoint(angle * (22 / 18));
		if (!angleController.isEnable()) {
			angleController.enable();
		}

	}

	public void disablePid() {
		smartController.disablePid();
	}

	public boolean angleReached() {
		return smartController.isOnTarget();
	}

	public boolean isLocked() {
		return isLocked;
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
