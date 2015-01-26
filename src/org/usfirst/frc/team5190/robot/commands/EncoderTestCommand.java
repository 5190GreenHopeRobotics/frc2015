package org.usfirst.frc.team5190.robot.commands;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;

/**
 * a encoder test
 */
public class EncoderTestCommand extends Command {
	private Encoder testEncoder;

	public EncoderTestCommand() {
		testEncoder = new Encoder(3, 4);
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
		@SuppressWarnings("unused")
		int test1 = testEncoder.get();
		boolean test2 = testEncoder.getDirection();
		double test3 = testEncoder.getDistance();
		double test4 = testEncoder.getRate();
		System.out.println("test1" + test1);
		System.out.println("test2" + test2);
		System.out.println("test3" + test3);
		System.out.println("test4" + test4);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
	}
}
