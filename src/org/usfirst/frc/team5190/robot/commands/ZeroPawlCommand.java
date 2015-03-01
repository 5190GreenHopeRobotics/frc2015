package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.Robot;
import org.usfirst.frc.team5190.robot.subsystems.PawlSubsystem;

import edu.wpi.first.wpilibj.command.Command;

public class ZeroPawlCommand extends Command {

	public ZeroPawlCommand() { 
		super("ZeroPawlCommand");

		requires(pawlSubsystem);
		
		
	}

	private PawlSubsystem pawlSubsystem = PawlSubsystem.getInstance();

	@Override
	protected void initialize() {

	}

	@Override
	protected void execute() {
		if (Robot.oi.zeroPawlButton()){
			
			pawlSubsystem.goToAngle(0);
		}

	}

	@Override
	protected boolean isFinished() {
		
		return false;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
