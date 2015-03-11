package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmLevelUpCommand extends Command {

	private final ArmSubsystem armsubsystem = ArmSubsystem.getInstance();
	private double nextlevel;

	public ArmLevelUpCommand() {
		requires(armsubsystem);
		setInterruptible(false);
		setTimeout(5);
	}

	@Override
	protected void initialize() {
		nextlevel = armsubsystem.levelup();
	}

	@Override
	protected void execute() {

	}

	@Override
	protected boolean isFinished() {
		double toprange = nextlevel + 8;
		double bottomrange = nextlevel - 8;
		return armsubsystem.getAngle() < toprange
				&& armsubsystem.getAngle() > bottomrange;
	}

	@Override
	protected void end() {
		System.out
				.println("Something Ended The UpLevel Command Button As Well.");
	}

	@Override
	protected void interrupted() {
		System.out
				.println("Something Interrupted The UpLevel Command Button As Well.");
	}
}
