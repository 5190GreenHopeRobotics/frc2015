package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

// stack two totes in theory

public class StackedTotesAutonomousCommandGroup extends CommandGroup {

	public StackedTotesAutonomousCommandGroup() {
		// stack 3 tote and go to auto zone
		addSequential(new ArmSyncCommand());
		addSequential(new DriveToObjectCommand());
		addSequential(new TimedRaiseLowerArmCommand(6, true));
		addSequential(new DriveToObjectCommand());
		addSequential(new TimedRaiseLowerArmCommand(6, false));
		addSequential(new TimedRaiseLowerArmCommand(6, true));
		addSequential(new DriveToObjectCommand());
		addSequential(new TimedRaiseLowerArmCommand(6, false));
		addSequential(new TimedRaiseLowerArmCommand(6, true));
		addSequential(new TurnCommand(90));
		addSequential(new DriveSetDistanceCommand(12 * 3));
	}
}
