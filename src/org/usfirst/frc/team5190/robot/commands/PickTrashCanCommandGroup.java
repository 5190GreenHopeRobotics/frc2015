package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickTrashCanCommandGroup extends CommandGroup {

	public PickTrashCanCommandGroup() {
		addSequential(new NavigationCalibratingCommand());
		addSequential(new ArmSetAngleCommand(273));
		addSequential(new DriveSetDistanceCommand(-60));
		addSequential(new TurnCommand(-90));
	}
}
