package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.dashboard.SmartDashBoardDisplayer;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.joystick.PawlJoystickCommand;
import org.usfirst.frc.team5190.robot.config.Configurable;
import org.usfirst.frc.team5190.robot.config.ConfigurationManager;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class PawlSubsystem extends Subsystem implements Displayable,
		Configurable, PIDOutput {
	public static final double DEFAULT_PAWL_ZERO_OFFSET = -154;
	public static final String PAWL_ZERO_OFFSET_PREF_KEY = "pawl.angle.zero.offset";
	private static final double PAWL_LOCK_P = 0.01;
	private static final double PAWL_LOCK_I = 0;
	private static final double PAWL_LOCK_D = 0;
	private static final double PAWL_LOCK_UPDATE_PERIOD = 0.01;
	private static final double PAWL_TICK_DELTA = 5;

	private static PawlSubsystem instance;

	private Preferences prefs = Preferences.getInstance();

	private Encoder encoder;
	private Jaguar motor;
	private Potentiometer pawlPotentiometer;
	private DigitalInput clutchEngagedSwitch;

	private double tickDelta = PAWL_TICK_DELTA;

	// private PIDController angleController;
	private PIDController lockController;

	private double lockP = PAWL_LOCK_P;
	private double lockI = PAWL_LOCK_I;
	private double lockD = PAWL_LOCK_D;
	private boolean limitReached = false;

	private PawlSubsystem() {
		SmartDashBoardDisplayer.getInstance().addDisplayable(this);
		ConfigurationManager.getInstance().addConfigurable(this);

		updateConfiguration();

		double zeroOffset = prefs.getDouble(PAWL_ZERO_OFFSET_PREF_KEY,
				DEFAULT_PAWL_ZERO_OFFSET);

		pawlPotentiometer = new AnalogPotentiometer(
				RobotMap.PAWL_POTENTIMETER_PORT, 270, zeroOffset);

		motor = new Jaguar(RobotMap.PAWL_JAGUAR_PORT);

		clutchEngagedSwitch = new DigitalInput(
				RobotMap.PAWL_CLUTCH_ENAGED_SWITCH_PORT);

		encoder = new Encoder(10, 11, false, EncodingType.k4X);
		encoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
		encoder.reset();

		double lockPidUpdatePeriod = prefs.getDouble("pawl.lock.update.period",
				PAWL_LOCK_UPDATE_PERIOD);
		lockController = new PIDController(lockP, lockI, lockD, encoder, this,
				lockPidUpdatePeriod);

		// angleController = new PIDController(0.005, 0, 0, pawlPotentiometer,
		// motor);
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

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new PawlJoystickCommand());
	}

	public double getAngle() {
		return pawlPotentiometer.get();
	}

	public void goToAngle(double angle) {

	}

	public boolean angleReached() {
		return true;
	}

	public void finishGoToAngle() {

	}

	public void movePawl(double power) {
		int moveTicks = (int) Math.round(power * tickDelta);

		if (clutchEngaged()) {
			synchronized (lockController) {
				if (!lockController.isEnable()) {
					lockController.setSetpoint(encoder.get() + moveTicks);
					lockController.enable();
				} else {
					double angle = getAngle();
					if ((angle < 30 && angle > -30)
							|| (angle < -30 && power > 0)
							|| (angle > 30 && power < 0)) {
						lockController.setSetpoint(encoder.get() + moveTicks);
						limitReached = false;
					} else {
						if (!limitReached) {
							limitReached = true;
							lockController.setSetpoint(encoder.get());
						}

					}
				}
			}
		} else {
			synchronized (lockController) {
				if (lockController.isEnable()) {
					lockController.reset();
				}
			}
			// still allow motor movement when clutch not engaged in case there
			// is an issue with the clutch engaged switch
			motor.set(power);
		}

	}

	@Override
	public void pidWrite(double output) {
		System.out.println("Pawl: " + output);
		if (output > 0.1) {
			output = 0.1;
		} else if (output < -0.1) {
			output = 0.1;
		}
		set(output);
	}

	private void set(double power) {
		// Invert the motor
		motor.set(-power);
	}

	public boolean clutchEngaged() {
		return clutchEngagedSwitch.get();
	}

	@Override
	public void displayValues(Display display) {
		display.putNumber("Pawl Angle", pawlPotentiometer.get());
		display.putNumber("Pawl Power", motor.get());
		display.putNumber("Pawl Encoder", encoder.get());
		display.putBoolean("Pawl Clutch Engaged", clutchEngaged());
	}

	@Override
	public void updateConfiguration() {
		double p = prefs.getDouble("pawl.lock.p", PAWL_LOCK_P);
		double i = prefs.getDouble("pawl.lock.i", PAWL_LOCK_I);
		double d = prefs.getDouble("pawl.lock.d", PAWL_LOCK_D);
		if (p != lockP || i != lockI || d != lockD) {
			boolean wasEnabled = lockController.isEnable();
			lockController.reset();
			lockController.setPID(p, i, d);
			lockP = p;
			lockI = i;
			lockD = d;
			if (wasEnabled) {
				lockController.enable();
			}
		}
		tickDelta = prefs.getDouble("pawl.tick.delta", PAWL_TICK_DELTA);
	}
}
