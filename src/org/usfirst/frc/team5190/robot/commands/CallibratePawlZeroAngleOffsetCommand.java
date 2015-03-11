package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.PawlSubsystem;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

public class CallibratePawlZeroAngleOffsetCommand extends Command {
	private PawlSubsystem pawlSubsystem = PawlSubsystem.getInstance();

	public CallibratePawlZeroAngleOffsetCommand() {
		requires(pawlSubsystem);
	}

	@Override
	protected void initialize() {
		Preferences preferences = Preferences.getInstance();
		double currentOffset = preferences.getDouble(
				PawlSubsystem.PAWL_ZERO_OFFSET_PREF_KEY,
				PawlSubsystem.DEFAULT_PAWL_ZERO_OFFSET);
		// We assume that someone set the pawl to the zero angle when this
		// command was run
		double currentAngle = pawlSubsystem.getAngle();
		double newOffset = currentOffset + currentAngle;
		preferences.putDouble(PawlSubsystem.PAWL_ZERO_OFFSET_PREF_KEY,
				newOffset);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}

}
