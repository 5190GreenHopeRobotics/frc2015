package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


/**
 *
 */
public class TurnCommand extends Command {

	
	private Direction mDir = Direction.LEFT;
	
    public TurnCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	setTimeout(3);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    public void setDirection(Direction dir) {
    	mDir = dir;
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(mDir == Direction.LEFT) {
    		Robot.driveTrainSubsystem.turn(0.5);
    	}
    	else if(mDir == Direction.RIGHT) {
    		Robot.driveTrainSubsystem.turn(-0.5);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrainSubsystem.stopAll();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
