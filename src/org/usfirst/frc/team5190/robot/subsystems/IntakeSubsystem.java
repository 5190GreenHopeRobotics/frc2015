package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.dashboard.SmartDashBoardDisplayer;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.joystick.IntakeJoystickCommand;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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
	public static Compressor compressor1;
	public static DoubleSolenoid doubleSolenoid1;

	private IntakeSubsystem() {
		SmartDashBoardDisplayer.getInstance().addDisplayable(this);
		leftIntakeController = new Talon(RobotMap.INTAKE_LEFT_TALON_PORT);
		rightIntakeController = new Talon(RobotMap.INTAKE_RIGHT_TALON_PORT);
		compressor1 = new Compressor(0);
		doubleSolenoid1 = new DoubleSolenoid(2, 0, 1); // PCM0, Port 0, Port 1
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
		doubleSolenoid1.set(Value.kForward);
	}

	public void reversePiston() {
		doubleSolenoid1.set(Value.kReverse);
	}

	public void pistonOff() {
		doubleSolenoid1.set(Value.kOff);
	}

	public boolean isReady() {
		return !compressor1.getPressureSwitchValue();
	}

	// Display Values
	@Override
	public void displayValues(Display display) {
		display.putNumber("LeftIntakePower", findLeftIntakePower());
		display.putNumber("RightIntakePower", findRightIntakePower());
	}
}
