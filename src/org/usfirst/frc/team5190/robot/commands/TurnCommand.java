package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.NavigationSubsystem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Command;

public class TurnCommand extends Command {

	private DriveTrainSubsystem driveTrainSubsystem = DriveTrainSubsystem
			.getInstance();
	private NavigationSubsystem navigationSubsystem = NavigationSubsystem
			.getInstance();
	private PIDController pidController;

	private double degree;

	public TurnCommand(double degree) {
		requires(driveTrainSubsystem);
		requires(navigationSubsystem);

		this.degree = degree;

		PIDSource pidSource = navigationSubsystem.createRobotHeadingPIDSource();
		PIDOutput pidOutput = driveTrainSubsystem.createTurnPIDOutput();
		pidController = new PIDController(1, 0, 0, pidSource, pidOutput);
		pidController.setContinuous(true);
		pidController.setInputRange(0, 360);
		pidController.setAbsoluteTolerance(2);
		pidController.setOutputRange(-0.3, 0.3);
	}

	@Override
	protected void initialize() {
		double currentYaw = navigationSubsystem.getYaw();
		double desiredYaw = ((currentYaw + 180 + degree) % 360) - 180;
		System.out.println("C: " + currentYaw + " D: " + desiredYaw);
		pidController.setSetpoint(desiredYaw);
		pidController.enable();
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return pidController.onTarget();
	}

	@Override
	protected void end() {
		pidController.disable();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
