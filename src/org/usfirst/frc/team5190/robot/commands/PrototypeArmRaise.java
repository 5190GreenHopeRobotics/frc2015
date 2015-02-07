package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PrototypeArmRaise extends Command {

	public PrototypeArmRaise() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.prototypearm);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// if (Robot.prototypearm.getlimitswitch() == false
		// && Robot.prototypearm.started == false) {
		// Robot.prototypearm.maximized = true;
		// }
		// if (Robot.prototypearm.maximized == false) {
		// Robot.prototypearm.raisearm();
		// Robot.prototypearm.started = false;
		// Robot.prototypearm.minimized = false;
		// }
		Robot.prototypearm.raisearm();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.prototypearm.stoparm();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.prototypearm.stoparm();
	}
}
