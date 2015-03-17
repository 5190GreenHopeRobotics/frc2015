package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous sequence of command that will take one tote and the robot to the
 * auto zone. It should drive forward pick up one tote then drive backward into
 * the autozone, turn, set the tote down, then back up so it's not touching.
 * 
 * @author 5190 Green Hope Robotics
 */
public class OneToteCommandGroup extends CommandGroup {

	public OneToteCommandGroup() {
		addSequential(new NavigationCalibratingCommand());
		addSequential(new ArmSetAngleCommand(0));
		addSequential(new DriveSetDistanceCommand(21));
		addSequential(new ArmSetAngleCommand(100));
		addSequential(new DriveSetDistanceCommand(-80));
		addSequential(new TurnCommand(-90));
		addSequential(new ArmSetAngleCommand(7));
		addSequential(new DriveSetDistanceCommand(-10));
	}

}
