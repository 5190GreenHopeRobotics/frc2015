package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.PawlSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ZeroPawlCommand extends Command {

	public ZeroPawlCommand() {
		super("ZeroPawlCommand");

		requires(pawlSubsystem);
	}

	private PawlSubsystem pawlSubsystem = PawlSubsystem.getInstance();

	@Override
	protected void initialize() {
		pawlSubsystem.goToAngle(0);
	}

	@Override
	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		return pawlSubsystem.angleReached();
	}

	@Override
	protected void end() {
		pawlSubsystem.finishGoToAngle();
	}

	@Override
	protected void interrupted() {
		end();
	}

}
