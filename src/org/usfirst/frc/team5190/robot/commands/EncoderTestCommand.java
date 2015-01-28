package org.usfirst.frc.team5190.robot.commands;

import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * a encoder test
 */
public class EncoderTestCommand extends Command {
	private Encoder testEncoder;

	public EncoderTestCommand() {
		testEncoder = new Encoder(RobotMap.ENCODER_CHANNEL_A,
				RobotMap.ENCODER_CHANNEL_B, true, EncodingType.k4X);
	}

	/**
	 * getter for encoder
	 * 
	 * @return the encoder
	 */
	public final Encoder getEncoder() {
		return testEncoder;
	}

	/**
	 * reset the encoder
	 */
	@Override
	protected void initialize() {
		testEncoder.reset();
	}

	/**
	 * get reading from encoder
	 */
	@Override
	protected void execute() {
		SmartDashboard.putNumber("Distance", testEncoder.getDistance());
		int test1 = testEncoder.get();
		boolean test2 = testEncoder.getDirection();
		double test3 = testEncoder.getDistance();
		double test4 = testEncoder.getRate();
		SmartDashboard.putNumber("Encoder Get:", test1);
		SmartDashboard.putBoolean("Encoder Get Direction", test2);
		SmartDashboard.putNumber("Encoder Distance:", test3);
		SmartDashboard.putNumber("Encoder Rate", test4);
		System.out.println("testEncoder.get():" + test1);
		System.out.println("testEncoder.getDirection():" + test2);
		System.out.println("testEncoder.getDistance():" + test3);
		System.out.println("testEncoder.getRate():" + test4);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
