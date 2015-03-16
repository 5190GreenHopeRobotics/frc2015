package org.usfirst.frc.team5190.robot.commands.joystick;

import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.subsystems.PawlSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class PawlJoystickCommand extends Command {

	private PawlSubsystem pawlSubsystem = PawlSubsystem.getInstance();

	public PawlJoystickCommand() {
		super("PawlJoystickCommand");
		requires(pawlSubsystem);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		if (!pawlSubsystem.clutchEngaged()) {
			return;
		}
		if (pawlSubsystem.isLocked()) {
			return;
		}
		double power = Robot.oi.getPawlAxis();

		pawlSubsystem.goToAngle(power * 20);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
