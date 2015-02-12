package org.usfirst.frc.team5190.robot.subsystems;

import org.usfirst.frc.team5190.robot.RobotMap;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * the encoder used with PID in the drive train, include 2 encoder, left, and
 * right
 * 
 * @author sdai
 *
 */
public class PIDEncoderDriveTrain implements PIDSource {
	private Encoder right, left;

	PIDEncoderDriveTrain() {
		right = new Encoder(RobotMap.ENCODER_RIGHT_CHANNEL_A,
				RobotMap.ENCODER_RIGHT_CHANNEL_B, false, EncodingType.k2X);
		right.setDistancePerPulse(0.0735190);
		right.setSamplesToAverage(7);
		left = new Encoder(RobotMap.ENCODER_LEFT_CHANNEL_A,
				RobotMap.ENCODER_LEFT_CHANNEL_B, true, EncodingType.k2X);
		left.setDistancePerPulse(0.0735190);
		left.setSamplesToAverage(7);
	}

	public Encoder getRight() {
		return right;
	}

	public Encoder getLeft() {
		return left;
	}

	public double getDistance() {
		return (right.getDistance() + left.getDistance()) / 2;
	}

	@Override
	public double pidGet() {
		return getDistance();
	}

}
