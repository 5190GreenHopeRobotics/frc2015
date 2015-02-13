package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StackedTotesAutonomousCommandGroup extends CommandGroup {
	public StackedTotesAutonomousCommandGroup() {
		// addSequential(new ProtoAutonomousArmRaiseCommand());
		addSequential(new ProtoAutonomousArmLowerCommand());
		// addSequential(new TurnCommand(45));
		// addSequential(new DriveSetDistanceCommand(10));
	}
}