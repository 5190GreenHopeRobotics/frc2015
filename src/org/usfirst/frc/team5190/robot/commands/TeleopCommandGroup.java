package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.commands.joystick.PawlJoystickCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TeleopCommandGroup extends CommandGroup {

	public TeleopCommandGroup() {
		addParallel(new PawlJoystickCommand());
	}
}