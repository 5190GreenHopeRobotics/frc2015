package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickTrashCanCommandGroup extends CommandGroup {

	public PickTrashCanCommandGroup() {
		addSequential(new NavigationCalibratingCommand());
		addSequential(new DriveToObjectCommand());
		addSequential(new ArmSetAngleCommand(300));
		addSequential(new DriveSetDistanceCommand(-80));
		addSequential(new TurnCommand(-90));
		addSequential(new ArmSetAngleCommand(7));
		addSequential(new DriveSetDistanceCommand(-10));
	}
}
