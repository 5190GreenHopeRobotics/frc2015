package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StackedTotesAutonomousCommandGroup extends CommandGroup {
	public StackedTotesAutonomousCommandGroup() {
		addParallel(new ProtoAutonomousArmCommand());
		addSequential(new TurnCommand(45));
		// addSequential(new DriveSetDistanceCommand(10));
	}
}