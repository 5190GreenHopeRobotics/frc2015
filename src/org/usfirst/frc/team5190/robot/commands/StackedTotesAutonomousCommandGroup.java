package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StackedTotesAutonomousCommandGroup extends CommandGroup {
	public StackedTotesAutonomousCommandGroup() {
		addSequential(new ProtoAutonomousArmCommand());
		addParallel(new DriveToObjectCommand());
	}
}