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
		pidController = new PIDController(.03, 0, 0, pidSource, pidOutput, 0.01);
		pidController.setContinuous(true);
		pidController.setInputRange(0, 360);
		pidController.setAbsoluteTolerance(2);
		pidController.setOutputRange(-0.3, 0.3);
	}

	@Override
	protected void initialize() {
		double currentYaw = navigationSubsystem.getYaw();
		double desiredYaw = currentYaw + degree;
		// known bug, this will not work if the number of degrees requested to
		// turn is more than 180
		if (desiredYaw > 180) {
			desiredYaw = desiredYaw - 360;
		} else if (desiredYaw < -180) {
			desiredYaw = desiredYaw + 360;
		}
		System.out.println("C: " + currentYaw + " D: " + desiredYaw);
		pidController.setSetpoint(desiredYaw);
		pidController.enable();
	}

	@Override
	protected void execute() {
		System.out.println("Yaw: " + navigationSubsystem.getYaw());
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
