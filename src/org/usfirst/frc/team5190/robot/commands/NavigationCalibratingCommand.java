package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.NavigationSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command runs until the navigation subsystem is no longer calibrating. Should
 * be the first command that is run for any autonomous command group sequence
 * which involves using the {@link NavigationSubsystem}. The
 * {@link NavigationSubsystem} will return invalid heading values if the robot
 * is not kept still while it's doing it's initial calibration.
 * 
 * @author 5190 Green Hope Robotics
 *
 */
public class NavigationCalibratingCommand extends Command {

	private NavigationSubsystem navigationSubsystem = NavigationSubsystem
			.getInstance();

	public NavigationCalibratingCommand() {
		requires(navigationSubsystem);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return !navigationSubsystem.isHeadingCalibrating();
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}
}
