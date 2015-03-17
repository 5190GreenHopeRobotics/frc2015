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
		double currentReading = src.pidGet();
		int countUpdate = countOutOfRange();
		turnCount += countUpdate;
		currentValue = 180 * turnCount;
		if (countUpdate == 1) {
			currentValue += 180 - (-1 * currentReading);
		} else if (countUpdate == -1) {
			currentValue += 180 - currentReading;
		} else {
			currentValue += currentReading;
		}
		prevValue = src.pidGet();
	}

	protected int countOutOfRange() {
		double currentReading = src.pidGet();
		if ((int) prevValue - (int) currentReading == 360) {
			return 1;
		} else if ((int) prevValue - (int) currentReading == -360) {
			return -1;
		} else {
			return 0;
		}

	}

}
