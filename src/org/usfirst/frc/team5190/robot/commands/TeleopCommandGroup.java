package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TeleopCommandGroup extends CommandGroup {

	public TeleopCommandGroup() {
		addParallel(new DriveWithArcadeCommand());
		addParallel(new ArmTeleopCommand());
		addParallel(new CherryPickerOperateCommand());
	}
}