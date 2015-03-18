package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.subsystems.DriveTrainSubsystem;
import org.usfirst.frc.team5190.robot.subsystems.NavigationSubsystem;
import org.usfirst.frc.team5190.sensor.TurnSensorWrapper;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

public class TurnCommand extends Command {
	private static final double TURN_P = 0.01;
	private static final double TURN_I = 0;
	private static final double TURN_D = 0;
	private static final double TURN_UPDATE_PERIOD = 0.01;
	private static final double TURN_TOLERANCE = 4;
	protected double numbersOfTurn = 0;
	private Preferences prefs = Preferences.getInstance();

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

		double p = prefs.getDouble("dt.turn.p", TURN_P);
		double i = prefs.getDouble("dt.turn.i", TURN_I);
		double d = prefs.getDouble("dt.turn.d", TURN_D);
		double period = prefs.getDouble("dt.turn.update.period",
				TURN_UPDATE_PERIOD);
		double tolerance = prefs.getDouble("dt.turn.tolerance", TURN_TOLERANCE);
		System.out.println("Turn P: " + p + " I: " + i + " D: " + d
				+ " period: " + period + " tolerance: " + tolerance);

		PIDSource pidSource = new TurnSensorWrapper(
				navigationSubsystem.createRobotHeadingPIDSource());
		PIDOutput pidOutput = driveTrainSubsystem.createTurnPIDOutput();
		pidController = new PIDController(TURN_P, TURN_I, TURN_D, pidSource,
				pidOutput, period);
		;
		// pidController.setContinuous(true);
		// pidController.setInputRange(-180, 180);
		pidController.setAbsoluteTolerance(TURN_TOLERANCE);
		pidController.setOutputRange(-1, 1);
	}

	@Override
	protected void initialize() {
		double p = prefs.getDouble("dt.turn.p", TURN_P);
		double i = prefs.getDouble("dt.turn.i", TURN_I);
		double d = prefs.getDouble("dt.turn.d", TURN_D);
		double tolerance = prefs.getDouble("dt.turn.tolerance", TURN_TOLERANCE);
		System.out.println("Turn P: " + p + " I: " + i + " D: " + d
				+ " tolerance: " + tolerance);

		pidController.setPID(p, i, d);
		pidController.setAbsoluteTolerance(tolerance);
		pidController.reset();

		double currentYaw = navigationSubsystem.getYaw();
		double desiredYaw = currentYaw + degree;
		// known bug, this will not work if the number of degrees requested to
		// turn is more than 180
		// if (desiredYaw > 180) {
		// desiredYaw = desiredYaw - 360;
		// } else if (desiredYaw < -180) {
		// desiredYaw = desiredYaw + 360;
		// }
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
