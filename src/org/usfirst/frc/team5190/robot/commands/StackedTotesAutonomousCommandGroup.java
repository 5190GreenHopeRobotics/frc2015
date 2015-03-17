package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

// stack two totes in theory

public class StackedTotesAutonomousCommandGroup extends CommandGroup {

	public StackedTotesAutonomousCommandGroup() {
		// Pick up first tote and go to second
		addSequential(new ArmSetAngleCommand(500));
		addSequential(new DriveToObjectCommand());

		// Set tote down and back up a little
		addSequential(new ArmSetAngleCommand(400));
		addSequential(new DriveSetDistanceCommand(-5));

		// Completely lower arm, Go to tote stack, Pick up
		addSequential(new ArmSetAngleCommand(0));
		addSequential(new DriveToObjectCommand());
		addSequential(new ArmSetAngleCommand(300));

		// Turn left, go to autozone, set down stack
		addSequential(new TurnCommand(90));
		addSequential(new DriveSetDistanceCommand(50));

	}
}
