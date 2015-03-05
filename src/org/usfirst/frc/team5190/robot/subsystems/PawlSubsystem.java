package org.usfirst.frc.team5190.robot.subsystems;

import java.util.concurrent.TimeUnit;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.joystick.PawlJoystickCommand;
import org.usfirst.frc.team5190.robot.motor.SmartSpeedController;
import org.usfirst.frc.team5190.robot.motor.SmartSpeedController.ControlMode;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class PawlSubsystem extends Subsystem implements Displayable {
	private static PawlSubsystem instance;
	private SmartSpeedController smartController;
	private Potentiometer pawlPotentiometer;
	private Potentiometer motorPotentiometer;
	private DigitalInput clutchEngagedSwitch;
	private Thread pawlSyncWorker;

	private PawlSubsystem() {
		smartController = new SmartSpeedController(new Jaguar(
				RobotMap.PAWL_JAGUAR_PORT));
		pawlPotentiometer = new AnalogPotentiometer(
				RobotMap.PAWL_POTENTIMETER_PORT, 265.3888684113527, -174.15);
		smartController.setPID(0.4, 0, 0.1);

		motorPotentiometer = new AnalogPotentiometer(
				RobotMap.PAWL_MOTOR_POTENTIMETER_PORT, -258.0645161290323,
				218.65);
		clutchEngagedSwitch = new DigitalInput(
				RobotMap.PAWL_CLUTCH_ENAGED_SWITCH_PORT);

		// set soft limit
		smartController.setPotentiometer(motorPotentiometer);
		smartController.setForwardSoftLimit(40);
		smartController.setReverseSoftLimit(-40);
		pawlSyncWorker = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!Thread.interrupted()) {
					if (!angleReached()) {
						followMotor();
					}
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						return;
					}
				}

			}

		});
		pawlSyncWorker.start();

	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new PawlJoystickCommand());
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

	protected synchronized void followMotor() {
		smartController.setControlMode(ControlMode.Angle);
		smartController.set(pawlPotentiometer.get());
	}

	public double getAngle() {
		return pawlPotentiometer.get();
	}

	public synchronized void goToAngle(double angle) {
		smartController.setControlMode(ControlMode.Angle);
		smartController.set(angle);

	}

	public synchronized void disablePid() {
		smartController.disablePid();
	}

	public synchronized boolean angleReached() {
		return smartController.isOnTarget();
	}

	public synchronized void movePawl(double speed) {
		smartController
				.setControlMode(org.usfirst.frc.team5190.robot.motor.SmartSpeedController.ControlMode.PercentVBus);
		smartController.set(speed);
	}

	public synchronized void stopPawl() {
		smartController.set(0);
	}

	@Override
	public void displayValues(Display display) {
		display.putNumber("Pawl Angle", pawlPotentiometer.get());
		display.putNumber("Pawl Motor Angle", motorPotentiometer.get());
		display.putBoolean("Pawl Clutch Engaged", clutchEngagedSwitch.get());
	}

}
