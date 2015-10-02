package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.dashboard.Display;
import org.usfirst.frc.team5190.dashboard.Displayable;
import org.usfirst.frc.team5190.dashboard.SmartDashBoardDisplayer;
import org.usfirst.frc.team5190.robot.RobotMap;
import org.usfirst.frc.team5190.robot.commands.joystick.IntakeJoystickCommand;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
//import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wp
/**
 *
 */
public class IntakeSubsystem extends Subsystem implements Displayable {
	private static IntakeSubsystem instance;

	private static final double KEEP_CHERRY_PICKER_RETRACTED_POWER = -0.1;

	// DIO ports temporary in this class.
	private Talon leftIntakeController;
	private Talon rightIntakeController;
//	private Counter minLimitSwitch;
//	private Counter maxLimitSwitch;
	private ArmSubsystem armSubsystem = ArmSubsystem.getInstance();

	private IntakeSubsystem() {
		SmartDashBoardDisplayer.getInstance().addDisplayable(this);
		leftIntakeController = new Talon(RobotMap.INTAKE_LEFT_TALON_PORT);
		rightIntakeController = new Talon(RobotMap.INTAKE_RIGHT_TALON_PORT);
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

	public void initDefaultCommand() {
		setDefaultCommand(new IntakeJoystickCommand());
	}

	public void runIntake(double power) {
		
		//Read Direction Switch
		if (power > 0.05 || power < -0.05) {
			leftIntakeController.set(power);
			rightIntakeController.set(power);
		} else {
			leftIntakeController.set(0);
			rightIntakeController.set(0);
		}
	}

	public void stopIntake() {
		leftIntakeController.set(0);
		rightIntakeController.set(0);
		}

	// Display Values
	public void displayValues(Display display) {
//		display.putBoolean("CP Max Limit", reachedMaxLimit());
//		display.putBoolean("CP Min Limit", reachedMinLimit());
//		display.putNumber("CP Power", cherryPickerController.getSpeed());
	}

}
