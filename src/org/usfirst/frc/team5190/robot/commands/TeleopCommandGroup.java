package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.commands.joystick.ArmTeleopCommand;
import org.usfirst.frc.team5190.robot.commands.joystick.CherryPickerOperateCommand;
import org.usfirst.frc.team5190.robot.commands.joystick.DriveWithArcadeCommand;
import org.usfirst.frc.team5190.robot.commands.joystick.PawlJoystickCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TeleopCommandGroup extends CommandGroup {

	public TeleopCommandGroup() {
		addParallel(new DriveWithArcadeCommand());
		addParallel(new ArmTeleopCommand());
		addParallel(new CherryPickerOperateCommand());
		addParallel(new PawlJoystickCommand());
		//addParallel(new ZeroPawlCommand());
	}
}