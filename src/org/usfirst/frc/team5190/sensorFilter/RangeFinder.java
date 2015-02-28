package org.usfirst.frc.team5190.sensorFilter;

import java.util.TimerTask;

import org.usfirst.frc.team5190.protocol.I2CPlus;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;

public class RangeFinder implements PIDSource{
	private I2CPlus i2c;
	private byte[] distance;
	private java.util.Timer updater;

	private final int RANGE_FINDER_ADDR = 0x29;
	private final int VL6180X_SYSRANGE_START = 0x0018;
	private final int VL6180X_RESULT_RANGE_VAL = 0x0062;

	public RangeFinder(Port port) {
		i2c = new I2CPlus(port, RANGE_FINDER_ADDR);

		distance = new byte[1];

		updater = new java.util.Timer();
		
	}

	// Distance in cm
	public int getDistance() {
		return Byte.toUnsignedInt(distance[0]);
	}

	@Override
	public double pidGet() {
		return getDistance();
	}

	// Start 10Hz polling
	public void start() {
		updater.scheduleAtFixedRate(new Updater(), 0, 100);
	}

	// Start polling for period in milliseconds
	public void start(int period) {
		updater.scheduleAtFixedRate(new Updater(), 0, period);
	}

	public void stop() {
		updater.cancel();
		updater = new java.util.Timer();
	}

	// Update distance variable
	public void update() {
		boolean devicePresent = i2c.addressOnly();
		
		i2c.write16bitRegister(VL6180X_SYSRANGE_START, 0x01); // Initiate measurement
		Timer.delay(0.10); // Delay for measurement to be taken
		i2c.read16bitRegister(VL6180X_RESULT_RANGE_VAL, 1, distance); // Read in
	}
	

	// Timer task to keep distance updated
	private class Updater extends TimerTask {
		@Override
		public void run() {
			update();
		}
	}
}