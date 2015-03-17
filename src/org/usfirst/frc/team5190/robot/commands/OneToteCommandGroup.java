package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous sequence of command that will take one tote and the robot to the
 * auto zone. It should drive forward pick up one tote then drive backward into
 * the autozone.
 * 
 * @author 5190 Green Hope Robotics
 */
public class OneToteCommandGroup extends CommandGroup {

	public OneToteCommandGroup() {
		addSequential(new NavigationCalibratingCommand());
		addSequential(new ArmSetAngleCommand(450));
		addSequential(new DriveSetDistanceCommand(-15));
	}

}
