package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StackedTotesAutonomousCommandGroup extends CommandGroup {
	public int max = 0;

	public StackedTotesAutonomousCommandGroup() {
		// while(max<100)
		// {
		// addSequential(new ProtoAutonomousArmRaiseCommand());
		// addSequential(new TurnCommand(90));
		// addSequential(new ProtoAutonomousArmLowerCommand());
		// max = max + 1;
		// }
	}
}
