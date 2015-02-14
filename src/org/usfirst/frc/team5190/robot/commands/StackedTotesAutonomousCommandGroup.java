package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StackedTotesAutonomousCommandGroup extends CommandGroup {

	public StackedTotesAutonomousCommandGroup() {
		// stack two totes in theory
		addSequential(new DriveToObjectCommand());
		addSequential(new ProtoAutonomousArmRaiseCommand());
		addSequential(new DriveToObjectCommand());
		addSequential(new ProtoAutonomousArmLowerCommand());
		addSequential(new ProtoAutonomousArmRaiseCommand());
	}
}
