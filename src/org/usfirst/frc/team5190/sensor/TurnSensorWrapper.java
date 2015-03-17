package org.usfirst.frc.team5190.sensor;

import edu.wpi.first.wpilibj.PIDSource;

public class TurnSensorWrapper implements PIDSource {

	protected PIDSource src;
	protected int turnCount;
	protected double prevValue;
	protected double currentValue;
	protected Thread updater;

	public TurnSensorWrapper(PIDSource src) {
		this.src = src;
		updater = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!Thread.interrupted()) {
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						return;
					}
					update();
				}

			}

		});
		updater.start();
	}

	@Override
	public synchronized double pidGet() {
		return currentValue;
	}

	protected synchronized void update() {
		turnCount += countOutOfRange();
		currentValue = turnCount * 180 + src.pidGet();
		prevValue = src.pidGet();
	}

	protected int countOutOfRange() {
		double currentReading = src.pidGet();
		if (prevValue - currentReading > 350) {
			return 1;
		} else if (prevValue - currentReading < -350) {
			return -1;
		} else {
			return 0;
		}

	}

}
