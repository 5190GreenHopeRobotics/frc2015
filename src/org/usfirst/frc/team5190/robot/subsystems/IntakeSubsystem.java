package org.usfirst.frc.team5190.robot.subsystems;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.dashboard.SmartDashBoardDisplayer;
import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.joystick.IntakeJoystickCommand;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class IntakeSubsystem extends Subsystem implements Displayable {
	private static IntakeSubsystem instance;

	// DIO ports temporary in this class.
	private Talon leftIntakeController;
	private Talon rightIntakeController;
	private Compressor compressor1;
	private DoubleSolenoid squeezeSolenoid;
	private Solenoid lowerSolenoid;
	private Solenoid raiseSolenoid;
	private ButtonTracker tracker;
	double tempThrottle = 1.0;

	private class ButtonTracker {
		private boolean firstPressed;
		private boolean secondPressed;
		private boolean bothPressed;
		private boolean isChecking;
		private Timer timer;

		private class Check extends TimerTask {

			@Override
			public void run() {
				synchronized (this) {
					if (!bothPressed) {
						firstPressed = false;
						secondPressed = false;
					}
					isChecking = false;
				}

			}

		}

		protected ButtonTracker() {
			timer = new Timer();
		}

		public void firstPressed() {
			synchronized (this) {
				firstPressed = true;
			}
			if (!isChecking) {
				timer.schedule(new Check(),
						new Date(new Date().getTime() + 200));
				synchronized (this) {
					isChecking = true;
					if (secondPressed == true) {
						bothPressed = true;
					}
				}
			}
		}

		public void secondPressed() {
			synchronized (this) {
				secondPressed = true;
			}
			if (!isChecking) {
				timer.schedule(new Check(),
						new Date(new Date().getTime() + 200));
				synchronized (this) {
					isChecking = true;
					if (firstPressed = true) {
						bothPressed = true;
					}
				}
			}
		}

		public synchronized boolean isBothPressed() {
			return bothPressed;
		}

		public synchronized void reset() {
			firstPressed = false;
			secondPressed = false;
			bothPressed = false;
		}
	}

	private IntakeSubsystem() {
		SmartDashBoardDisplayer.getInstance().addDisplayable(this);
		leftIntakeController = new Talon(RobotMap.INTAKE_LEFT_TALON_PORT);
		rightIntakeController = new Talon(RobotMap.INTAKE_RIGHT_TALON_PORT);
		compressor1 = new Compressor(0);
		squeezeSolenoid = new DoubleSolenoid(RobotMap.PCM_MODULE_ID,
				RobotMap.SOLENOID1_Forward, RobotMap.SOLENOID1_REVERSE);
		lowerSolenoid = new Solenoid(2, 2);
		raiseSolenoid = new Solenoid(2, 3);
		tracker = new ButtonTracker();
	}

	public static IntakeSubsystem getInstance() {
		if (instance == null) {
			try {
				instance = new IntakeSubsystem();
			} catch (Throwable t) {
				t.printStackTrace();
				throw t;
			}
		}
		return instance;
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new IntakeJoystickCommand());
	}

	public void runIntake(double power) {

		// Read Direction Switch
		if (power > 0.05 || power < -0.05) {

			tempThrottle = Robot.oi.getShootStickSpeed();
			tempThrottle = tempThrottle / 2 + 0.5;

			power *= tempThrottle;

			leftIntakeController.set(power);
			rightIntakeController.set(-power);
		} else {
			leftIntakeController.set(0);
			rightIntakeController.set(0);
		}
	}

	public double findLeftIntakePower() {
		return leftIntakeController.get();
	}

	public double findRightIntakePower() {
		return rightIntakeController.get();
	}

	public void stopIntake() {
		leftIntakeController.set(0);
		rightIntakeController.set(0);
	}

	public void forwardPiston() {
		// doubleSolenoid1.set(Value.kForward);
		lowerSolenoid.set(true);
	}

	public void reversePiston() {
		// doubleSolenoid1.set(Value.kReverse);
		raiseSolenoid.set(true); // added third solenoid
	}

	public void stopPiston() {
		lowerSolenoid.set(false);
		raiseSolenoid.set(false);
	}

	public void pistonOff() {
		squeezeSolenoid.set(Value.kOff);
	}

	public void widenIntakeWheel() {
		squeezeSolenoid.set(Value.kReverse);
	}

	public void narrowIntakeWheel() {
		squeezeSolenoid.set(Value.kForward);
	}

	// Original from Shilong - modified to switch solenoids to different roles
	// public void forwardPiston() {
	// doubleSolenoid1.set(Value.kForward);
	// }
	//
	// public void reversePiston() {
	// doubleSolenoid1.set(Value.kReverse);
	// }
	//
	// public void pistonOff() {
	// doubleSolenoid1.set(Value.kOff);
	// }
	//
	// public void widenIntakeWheel() {
	// secondSolenoid.set(true);
	// }
	//
	// public void narrowIntakeWheel() {
	// secondSolenoid.set(false);
	// }

	public boolean isReady() {
		return !compressor1.getPressureSwitchValue();
	}

	// Display Values
	@Override
	public void displayValues(Display display) {
		display.putNumber("LeftIntakePower", findLeftIntakePower());
		display.putNumber("RightIntakePower", findRightIntakePower());
		display.putBoolean("pressure", isReady());
	}

	public void firstButtonPressed() {
		tracker.firstPressed();

	}

	public void secondButtonPressed() {
		tracker.secondPressed();

	}

	public void reset() {
		tracker.reset();

	}

	public boolean bothPressed() {
		return tracker.isBothPressed();
	}
}
