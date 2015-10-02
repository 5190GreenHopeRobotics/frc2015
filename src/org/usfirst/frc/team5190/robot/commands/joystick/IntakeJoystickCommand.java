package org.usfirst.frc.team5190.robot.commands.joystick;

import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.subsystems.IntakeSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeJoystickCommand extends Command {

	private IntakeSubsystem intakeSubsystem = IntakeSubsystem.getInstance();

	public IntakeJoystickCommand() {
		super("IntakeTeleopCommand");
		requires(intakeSubsystem);
	}

	protected void initialize() {
	}

	protected void execute() {
//		double power = Robot.oi.getIntakeAxis();

//		intakeSubsystem.runIntake(power);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
		end();
	}
}
