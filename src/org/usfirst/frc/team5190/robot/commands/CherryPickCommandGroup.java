package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CherryPickCommandGroup extends CommandGroup {

	public CherryPickCommandGroup() {
		addSequential(new NavigationCalibratingCommand());
		addSequential(new ArmSetAngleCommand(600));
		// addSequential(new CherryPickerRetractCommand());
		addSequential(new DriveSetDistanceCommand(-15));
	}
}
